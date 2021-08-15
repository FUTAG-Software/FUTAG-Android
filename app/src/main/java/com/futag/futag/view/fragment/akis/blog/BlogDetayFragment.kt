package com.futag.futag.view.fragment.akis.blog

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
import com.futag.futag.databinding.FragmentBlogDetailBinding
import com.squareup.picasso.Picasso

class BlogDetayFragment : Fragment() {

    private val args by navArgs<BlogDetayFragmentArgs>()
    private var _binding: FragmentBlogDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlogDetailBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val canliVeri = args.canliBlog
        binding.textViewAuthor.text = canliVeri.author

        var resimsizHTML = canliVeri.content.replace(Regex("<img.+>"), "")
        resimsizHTML = resimsizHTML.replace(Regex("\\n"),"<br>")

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            binding.textViewDetail.text = Html.fromHtml(resimsizHTML,Html.FROM_HTML_MODE_COMPACT)
        } else {
            binding.textViewDetail.text = Html.fromHtml(resimsizHTML)
        }

        if(canliVeri.featuredImage != null){
            Picasso.get().load(canliVeri.featuredImage.large).placeholder(R.drawable.placeholder).into(binding.imageView)
        } else {
            binding.imageView.setImageDrawable(
                ContextCompat.getDrawable(requireContext(),R.drawable.blog_test)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}