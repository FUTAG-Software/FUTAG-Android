package com.futag.futag.presentation.ui.fragment.flow.blog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.futag.futag.databinding.FragmentBlogBinding
import com.futag.futag.model.blog.BlogModelItem
import com.futag.futag.presentation.adapter.BlogRecyclerAdapter
import com.futag.futag.util.listener.BlogAdapterClickListener

class BlogFragment : Fragment(), BlogAdapterClickListener {

    private var _binding: FragmentBlogBinding? = null
    private val binding get() = _binding!!
    private lateinit var blogAdapter: BlogRecyclerAdapter
    private lateinit var viewModel: BlogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBlogBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        blogAdapter = BlogRecyclerAdapter(requireContext(), this)

        viewModel = ViewModelProvider(requireActivity())[BlogViewModel::class.java]
        viewModel.getBlogs()

        binding.swipeRefreshLayoutBlog.setOnRefreshListener {
            binding.textViewErrorMessage.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
            viewModel.getBlogs()
            binding.swipeRefreshLayoutBlog.isRefreshing = false
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.blogDatas.observe(viewLifecycleOwner) { blogs ->
            blogs?.let {
                binding.textViewErrorMessage.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.INVISIBLE
                binding.recyclerView.visibility = View.VISIBLE
                blogAdapter.blogList = it
                binding.recyclerView.adapter = blogAdapter
            }
        }
        viewModel.blogError.observe(viewLifecycleOwner) { error ->
            error?.let {
                if (it) {
                    binding.textViewErrorMessage.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.textViewErrorMessage.visibility = View.GONE
                }
            }
        }
        viewModel.blogLoading.observe(viewLifecycleOwner) { loading ->
            loading?.let {
                if (it) {
                    binding.textViewErrorMessage.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickListener(item: BlogModelItem) {
        val action = BlogFragmentDirections.actionBlogFragmentToBlogDetayFragment(item)
        findNavController().navigate(action)
    }

}