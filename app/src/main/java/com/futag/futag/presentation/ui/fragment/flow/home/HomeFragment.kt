package com.futag.futag.presentation.ui.fragment.flow.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.futag.futag.presentation.adapter.AdsRecyclerAdapter
import com.futag.futag.presentation.adapter.PostRecyclerAdapter
import com.futag.futag.databinding.FragmentHomeBinding
import com.futag.futag.model.post.PostModel
import com.futag.futag.util.LinePagerIndicatorDecoration

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adsAdapter: AdsRecyclerAdapter
    private lateinit var viewModel: HomeViewModel
    private val postAdapter = PostRecyclerAdapter(this, PostModel())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        val layoutManagerAds =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        viewModel.getPosts()
        viewModel.getAds()

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = postAdapter
        binding.recyclerViewAds.layoutManager = layoutManagerAds

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerViewAds)

        observePostLiveData()
        observeAdsLiveData()

        binding.recyclerViewAds.addItemDecoration(LinePagerIndicatorDecoration())
    }

    private fun observePostLiveData() {
        viewModel.postDatas.observe(viewLifecycleOwner, { posts ->
            posts?.let {
                binding.textViewErrorMessage.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.INVISIBLE
                binding.recyclerView.visibility = View.VISIBLE
                postAdapter.updatePost(it)
            }
        })
        viewModel.postError.observe(viewLifecycleOwner, { error ->
            error?.let {
                if (it) {
                    binding.textViewErrorMessage.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.textViewErrorMessage.visibility = View.GONE
                }
            }
        })
        viewModel.postLoading.observe(viewLifecycleOwner, { loading ->
            loading?.let {
                if (it) {
                    binding.textViewErrorMessage.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun observeAdsLiveData() {
        viewModel.adsDatas.observe(viewLifecycleOwner, { ads ->
            ads?.let { reklamModel ->
                binding.progressBarSlider.visibility = View.INVISIBLE
                binding.recyclerViewAds.visibility = View.VISIBLE
                adsAdapter = AdsRecyclerAdapter(reklamModel, this)
                binding.recyclerViewAds.adapter = adsAdapter
            }
        })
        viewModel.adsError.observe(viewLifecycleOwner, { error ->
            error?.let {
                if (it) {
                    binding.progressBarSlider.visibility = View.GONE
                    binding.recyclerViewAds.visibility = View.GONE
                }
            }
        })
        viewModel.adsLoading.observe(viewLifecycleOwner, { loading ->
            loading?.let {
                if (it) {
                    binding.progressBarSlider.visibility = View.VISIBLE
                    binding.recyclerViewAds.visibility = View.GONE
                } else {
                    binding.progressBarSlider.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}