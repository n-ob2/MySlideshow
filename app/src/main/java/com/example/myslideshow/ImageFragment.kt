package com.example.myslideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myslideshow.databinding.FragmentImageBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val IMG_RES_ID = "IMG_RES_ID" //Bundleクラスのキーをインスタンス化
/**
 * A simple [Fragment] subclass.
 * Use the [ImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var imageResId: Int? = null //アーギュメンツからデータを取り出す。画像IDを格納する変数をインスタンス化。この時点で中身はわからないのでnull

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {    //安全呼び出しと一緒に使うとnull?を扱いやすくなるlet
            imageResId = it.getInt(IMG_RES_ID)  // bundleキー「IMG_RES_ID」に格納されてる画像IDを取得
        }
    }

    // FragmentでviewBindingを使うためのコード ↓↓
    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // FragmentでviewBindingを使うためのコード ↑↑

    companion object {  //インスタンスを生成しなくても使えるメソッド
        @JvmStatic  //Javaのコードからこのクラスを呼び出した時にスタティックメソッドとして使えるように
        fun newInstance(imageResId: Int) =
            ImageFragment().apply { // フラグメントを生成してその中に↓↓
                arguments = Bundle().apply { //アーギュメントを生成して画像リソースIDを格納
                    putInt(IMG_RES_ID, imageResId)  //Bundleキー"IMG_RES_ID"に、変数imageResIdの中身を格納
                }
            }
    }
    override fun onActivityCreated(saveInstanceState: Bundle?) {
        super.onActivityCreated(saveInstanceState)
        imageResId?.let{
            binding.imageView.setImageResource(it)
        }
    }
}