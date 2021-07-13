package com.futag.futag.view.fragment.akis.blog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.futag.futag.R
import com.futag.futag.adapter.BlogRecyclerAdapter
import com.futag.futag.databinding.FragmentBlogBinding
import com.futag.futag.model.blog.BlogModel
import com.futag.futag.viewmodel.AkisViewModel

class BlogFragment : Fragment() {

    private var _binding: FragmentBlogBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: BlogRecyclerAdapter
    private lateinit var viewModel: AkisViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlogBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(requireActivity()).get(AkisViewModel::class.java)
        viewModel.blogVerileriniAl()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = layoutManager

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.veriler.observe(viewLifecycleOwner, { veri ->
            veri?.let {
                //binding.textViewErrorMessage.visibility = View.INVISIBLE
                //binding.progressBar.visibility = View.INVISIBLE
                binding.recyclerView.visibility = View.VISIBLE

                adapter = BlogRecyclerAdapter(this, it)
                binding.recyclerView.adapter = adapter
                Toast.makeText(requireContext(),"Basarili",Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, { error ->
            error?.let {
                if (it){
                    //binding.textViewErrorMessage.visibility = View.VISIBLE
                    //binding.progressBar.visibility = View.GONE
                    //binding.recyclerView.visibility = View.GONE
                    Toast.makeText(requireContext(),"Hata",Toast.LENGTH_SHORT).show()
                } else {
                    //binding.textViewErrorMessage.visibility = View.GONE
                }
            }
        })
        viewModel.progressBar.observe(viewLifecycleOwner, { message ->
            message?.let {
                if (it){
                    //binding.textViewErrorMessage.visibility = View.GONE
                    //binding.progressBar.visibility = View.VISIBLE
                    //binding.recyclerView.visibility = View.GONE
                } else {
                    //binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

}