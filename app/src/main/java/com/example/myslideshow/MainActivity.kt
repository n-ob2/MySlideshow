package com.example.myslideshow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myslideshow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
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
    }
}