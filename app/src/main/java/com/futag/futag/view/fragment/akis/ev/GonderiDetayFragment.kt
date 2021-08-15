package com.futag.futag.view.fragment.akis.ev

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.futag.futag.R
import com.futag.futag.databinding.FragmentPostDetailBinding
import com.futag.futag.util.placeholderProgressBar
import com.futag.futag.util.fetchImagesWithUrl

class GonderiDetayFragment : Fragment() {

    private val args by navArgs<GonderiDetayFragmentArgs>()
    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostDetailBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val canliVeri = args.anaSayfaItem
        if(canliVeri.featuredImage != null){
            binding.imageViewImage.fetchImagesWithUrl(canliVeri.featuredImage.large,
                placeholderProgressBar(requireContext())
            )
        } else {
            binding.imageViewImage.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.error)
            )
        }
        binding.textViewAuthor.text = canliVeri.author

        var resimsizHTML = canliVeri.content.replace(Regex("<img.+>"), "")
        resimsizHTML = resimsizHTML.replace(Regex("\\n"),"<br>")

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            binding.textViewDetail.text = Html.fromHtml(resimsizHTML, Html.FROM_HTML_MODE_COMPACT)
        } else {
            binding.textViewDetail.text = Html.fromHtml(resimsizHTML)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}