package com.futag.futag.view.fragment.akis.blog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.futag.futag.R
import com.futag.futag.adapter.BlogRecyclerAdapter
import com.futag.futag.databinding.FragmentBlogBinding
import com.futag.futag.model.blog.BlogModel

class BlogFragment : Fragment() {

    private var _binding: FragmentBlogBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: BlogRecyclerAdapter
    private lateinit var blogListesi: ArrayList<BlogModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlogBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val x = BlogModel(1,"Ademin Gunleri", "Bu da konu icerigiyle alakali kardesim" +
                "istersen okuma ama okumak cok onemli, mesela insanlar okuya okuya ilerlerBu da konu icerigiyle alakali" +
                " kardesim\n\n\nistersen okuma ama okumak cok onemli, mesela insanlar okuya okuya ilerler","Adem Atici")
        val y = BlogModel(2,"Yazilim Nedir","Bu da konu icerigiyle alakali kardesim" +
                "istersen okuma ama okumak cok onemli, mesela insanlar okuya okuya ilerler\n" +
                "\n\n\t\t\t\tBu da konu icerigiyle alakali kardesim\n" +
                "istersen okuma ama okumak cok onemli, mesela insanlar okuya okuya ilerler" ,"Mehmet Musa")
        val z = BlogModel(3,"Yapay Zekanin Gelecegi ahjsfhj sdjklfjkl klasdf ","Bu da konu icerigiyle alakali kardesim" +
                "istersen okuma ama okumak cok onemli, mesela insanlar okuya okuya ilerler\n" +
                "\n\t\t\tBu da konu icerigiyle alakali kardesim\n" +
                "istersen okuma ama okumak cok onemli, mesela insanlar okuya okuya ilerler" ,"Kasim Asaf")
        val t = BlogModel(4,"Mukemmel Olmak", "Bu da konu icerigiyle alakali kardesim" +
                "Bu da konu icerigiyle alakali kardesim\n" +
                "istersen okuma ama okumak cok onemli, mesela insanlar okuya okuya ilerler", "Hale Adiguzel")

        blogListesi = ArrayList()
        blogListesi.add(x)
        blogListesi.add(y)
        blogListesi.add(z)
        blogListesi.add(t)

        val layoutManager = LinearLayoutManager(requireContext())
        adapter = BlogRecyclerAdapter(this,blogListesi)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}