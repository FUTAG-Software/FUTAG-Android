package com.futag.futag.presentation.ui.fragment.flow.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.futag.futag.R
import com.futag.futag.databinding.FragmentEventDetailBinding
import com.futag.futag.util.Constants.FORM_LINK_DEFAULT_NULL_STATE
import com.futag.futag.util.fetchImagesWithUrl
import com.futag.futag.util.placeholderProgressBar

class EventDetailFragment : Fragment() {

    private val args by navArgs<EventDetailFragmentArgs>()
    private var _binding: FragmentEventDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentData = args.currentEvent

        binding.imageViewImage.fetchImagesWithUrl(
            currentData.image,
            placeholderProgressBar(requireContext())
        )

        binding.textViewDetail.text = currentData.details
        binding.textViewLink.setOnClickListener {
            if (currentData.formLink == FORM_LINK_DEFAULT_NULL_STATE) {
                Toast.makeText(
                    requireContext(),
                    R.string.registration_is_closed,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val action =
                    EventDetailFragmentDirections.actionEtkinlikDetayFragmentToFormFragment(
                        currentData.formLink
                    )
                findNavController().navigate(action)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}