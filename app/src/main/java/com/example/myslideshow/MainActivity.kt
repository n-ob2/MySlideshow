package com.example.myslideshow

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myslideshow.databinding.ActivityMainBinding
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private lateinit var player: MediaPlayer    //MediaPlayerクラスを継承した変数を保持、初期化はonCreateメソッド内なのでlateinitを付ける
    class MyAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa){   //クラス継承

        private val resources = listOf(  //画像のリスト
            R.drawable.slide00, R.drawable.slide01,
            R.drawable.slide02, R.drawable.slide03,
            R.drawable.slide04, R.drawable.slide05,
            R.drawable.slide06, R.drawable.slide07,
            R.drawable.slide08, R.drawable.slide09,
        )

        override fun getItemCount(): Int = resources.size    //sizeプロパティで画像リストの要素数を取得

        override fun createFragment(position: Int): Fragment
            = ImageFragment.newInstance(resources[position])    //ImageFragment.ktに書いたImageFragmentクラスのnewInstanceメソッドを呼んでインスタンスを生成
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pager.adapter = MyAdapter(this) //xmlでセットしたpager(Viewpager2)と↑で書いたMyAdapterクラスをくっつける
        binding.pager.setPageTransformer(ViewPager2PageTransformation())    //スライドのアニメーションクラスをpagerに設定

        val handler = Handler(Looper.getMainLooper())   //Handler(Looper)コンストラクタでインスタンス生成
        timer(period = 5000){
            handler.post{      //生成した定数handlerのポストメソッドに処理したい内容を渡す
                binding.apply{
                    pager.currentItem = (pager.currentItem + 1) % 10        //ページめくりの仕組み
                }
            }
        }
        player = MediaPlayer.create(this,R.raw.getdown) //MediaPlayerクラスを継承したインスタンスを生成
        player.isLooping = true //ループon
    }
    override fun onResume(){    //BGMスタート
        super.onResume()
        player.start()
    }
    override fun onPause(){ //BGMポーズ
        super.onPause()
        player.pause()
    }

    //スライド時のアニメーション↓↓
    class ViewPager2PageTransformation : ViewPager2.PageTransformer{    //ViewPager2.PageTransformerを継承したクラスを作成
        override fun transformPage(page: View, position: Float){    //transformPageメソッドの内容を上書き

            when{
                position < -1 -> {   //画面の左外へはみ出してる時
                    page.alpha = 0.2f   //透過率
                    page.scaleX = 0.2f  //横方向の拡大率
                    page.scaleY = 0.2f  //縦方向の拡大率
                }
                position <= 1 -> {   //画面の右外へはみ出してる時
                    page.alpha = Math.max(0.2f, 1 - Math.abs(position))
                    page.scaleX = Math.max(0.2f, 1 - Math.abs(position))
                    page.scaleY = Math.max(0.2f, 1 - Math.abs(position))
                }
                else -> {    //そのほかの時
                    page.alpha = 0.2f
                    page.scaleX = 0.2f
                    page.scaleY = 0.2f
                }
            }
        }
    }
    //スライド時のアニメーション↑↑
}