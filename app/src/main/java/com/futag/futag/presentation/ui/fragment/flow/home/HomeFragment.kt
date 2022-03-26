package com.futag.futag.presentation.ui.fragment.flow.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.futag.futag.databinding.FragmentHomeBinding
import com.futag.futag.model.advertising.AdsModelItem
import com.futag.futag.model.post.PostModelItem
import com.futag.futag.presentation.adapter.AdsRecyclerAdapter
import com.futag.futag.presentation.adapter.PostRecyclerAdapter
import com.futag.futag.util.LinePagerIndicatorDecoration
import com.futag.futag.util.listener.AdsAdapterClickListener
import com.futag.futag.util.listener.PostAdapterClickListener

class HomeFragment : Fragment(), AdsAdapterClickListener, PostAdapterClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adsAdapter: AdsRecyclerAdapter
    private lateinit var viewModel: HomeViewModel
    private lateinit var postAdapter: PostRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adsAdapter = AdsRecyclerAdapter(requireContext(), this)
        postAdapter = PostRecyclerAdapter(requireContext(), this)

        val layoutManager = LinearLayoutManager(requireContext())
        val layoutManagerAds =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
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
        viewModel.postDatas.observe(viewLifecycleOwner) { posts ->
            posts?.let {
                binding.textViewErrorMessage.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.INVISIBLE
                binding.recyclerView.visibility = View.VISIBLE
                postAdapter.postList = it
            }
        }
        viewModel.postError.observe(viewLifecycleOwner) { error ->
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
        viewModel.postLoading.observe(viewLifecycleOwner) { loading ->
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

    private fun observeAdsLiveData() {
        viewModel.adsDatas.observe(viewLifecycleOwner) { ads ->
            ads?.let {
                binding.progressBarSlider.visibility = View.INVISIBLE
                binding.recyclerViewAds.visibility = View.VISIBLE
                adsAdapter.adsList = it
                binding.recyclerViewAds.adapter = adsAdapter
            }
        }
        viewModel.adsError.observe(viewLifecycleOwner) { error ->
            error?.let {
                if (it) {
                    binding.progressBarSlider.visibility = View.GONE
                    binding.recyclerViewAds.visibility = View.GONE
                }
            }
        }
        viewModel.adsLoading.observe(viewLifecycleOwner) { loading ->
            loading?.let {
                if (it) {
                    binding.progressBarSlider.visibility = View.VISIBLE
                    binding.recyclerViewAds.visibility = View.GONE
                } else {
                    binding.progressBarSlider.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickItem(item: AdsModelItem) {
        val action =
            HomeFragmentDirections.actionEvFragmentToWebSitesiFragment(item.redirectingLink)
        findNavController().navigate(action)
    }

    override fun clickListener(item: PostModelItem) {
        val action = HomeFragmentDirections.actionEvFragmentToGonderiDetayFragment(item)
        findNavController().navigate(action)
    }

}