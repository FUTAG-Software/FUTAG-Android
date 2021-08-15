package com.futag.futag.view.fragment.flow.blog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.futag.futag.adapter.BlogRecyclerAdapter
import com.futag.futag.databinding.FragmentBlogBinding
import com.futag.futag.model.blog.BlogModel
import com.futag.futag.viewmodel.FlowViewModel

class BlogFragment : Fragment() {

    private var _binding: FragmentBlogBinding? = null
    private val binding get() = _binding!!
    private val blogAdapter =  BlogRecyclerAdapter(this, BlogModel())
    private lateinit var viewModel: FlowViewModel

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

        val layoutManager = LinearLayoutManager(requireContext())
        viewModel = ViewModelProvider(requireActivity()).get(FlowViewModel::class.java)
        viewModel.getBlogs()

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = blogAdapter

        binding.swipeRefreshLayoutBlog.setOnRefreshListener {
            binding.textViewErrorMessage.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
            viewModel.getBlogs()
            binding.swipeRefreshLayoutBlog.isRefreshing = false
        }

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.blogDatas.observe(viewLifecycleOwner, { blogs ->
            blogs?.let {
                binding.textViewErrorMessage.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.INVISIBLE
                binding.recyclerView.visibility = View.VISIBLE
                blogAdapter.updateBlogs(it)
            }
        })
        viewModel.blogError.observe(viewLifecycleOwner, { error ->
            error?.let {
                if (it){
                    binding.textViewErrorMessage.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.textViewErrorMessage.visibility = View.GONE
                }
            }
        })
        viewModel.blogLoading.observe(viewLifecycleOwner, { loading ->
            loading?.let {
                if (it){
                    binding.textViewErrorMessage.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}