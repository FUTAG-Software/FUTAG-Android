package com.futag.futag.view.fragment.akis.etkinlik

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.futag.futag.R
import com.futag.futag.databinding.FragmentEventDetailBinding
import com.futag.futag.util.placeholderProgressBar
import com.futag.futag.util.resimleriUrlIleGetir

class EtkinlikDetayFragment : Fragment() {

    private val args by navArgs<EtkinlikDetayFragmentArgs>()
    private var _binding: FragmentEventDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventDetailBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val canliVeri = args.canliEtkinlik

        binding.imageViewImage.resimleriUrlIleGetir(
            canliVeri.image,
            placeholderProgressBar(requireContext())
        )

        binding.textViewDetail.text = canliVeri.details
        binding.textViewLink.setOnClickListener {
            if (canliVeri.formLink == "no_link"){
                Toast.makeText(requireContext(), R.string.registration_is_closed,Toast.LENGTH_SHORT).show()
            } else {
                val action = EtkinlikDetayFragmentDirections.actionEtkinlikDetayFragmentToFormFragment(canliVeri.formLink)
                Navigation.findNavController(it).navigate(action)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}