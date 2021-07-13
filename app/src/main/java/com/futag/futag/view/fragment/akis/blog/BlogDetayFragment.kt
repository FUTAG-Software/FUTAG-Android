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
import com.futag.futag.databinding.FragmentBlogDetayBinding
import com.squareup.picasso.Picasso

class BlogDetayFragment : Fragment() {

    private val args by navArgs<BlogDetayFragmentArgs>()
    private var _binding: FragmentBlogDetayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlogDetayBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val canliVeri = args.canliBlog
        binding.textViewYazar.text = canliVeri.author
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            var x = canliVeri.content.replace(Regex("<img.+>"), "")
            x = x.replace(Regex("\\n"),"<br>")
            binding.textViewDetay.text = Html.fromHtml(x,Html.FROM_HTML_MODE_COMPACT)
        } else {
            binding.textViewDetay.text = Html.fromHtml(canliVeri.content)
        }

        if(canliVeri.featuredImage != null){
            Picasso.get().load(canliVeri.featuredImage.large).placeholder(R.drawable.placeholder).into(binding.imageViewResim)
        } else {
            binding.imageViewResim.setImageDrawable(
                ContextCompat.getDrawable(requireContext(),R.drawable.deneme_blog)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}