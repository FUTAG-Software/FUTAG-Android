package com.futag.futag.view.fragment.akis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.futag.futag.R
import com.futag.futag.adapter.BlogRecyclerAdapter
import com.futag.futag.databinding.FragmentBlogBinding
import com.futag.futag.model.BlogModel

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

        val x = BlogModel("Ademin Gunleri", "Adem Atici")
        val y = BlogModel("YAzilim Nedir", "Mehmet Musa")
        val z = BlogModel("Yapay Zekanin Gelecegi ahjsfhj sdjklfjkl klasdf ", "Kasim Asaf")
        val x1 = BlogModel("Mukemmel Olmak", "Hale Adiguzel")

        blogListesi = ArrayList()
        blogListesi.add(x)
        blogListesi.add(y)
        blogListesi.add(z)
        blogListesi.add(x1)

        val layoutManager = LinearLayoutManager(requireContext())
        adapter = BlogRecyclerAdapter(blogListesi)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}