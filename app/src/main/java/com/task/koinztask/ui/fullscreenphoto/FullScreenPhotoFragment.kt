package com.task.koinztask.ui.fullscreenphoto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.task.koinztask.databinding.FragmentFullScreenPhotoBinding

private const val IMAGE_URL_PARAM = "IMAGE_URL_PARAM"

class FullScreenPhotoFragment : Fragment() {

    private var imageUrl: String? = null

    private var _binding: FragmentFullScreenPhotoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageUrl = it.getString(IMAGE_URL_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFullScreenPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(imageUrl: String) =
            FullScreenPhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(IMAGE_URL_PARAM, imageUrl)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageUrl = imageUrl
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}