package com.futag.futag.view.fragment.akis.etkinlik

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.futag.futag.databinding.FragmentEtkinlikDetayBinding
import com.futag.futag.util.placeholderProgressBar
import com.futag.futag.util.resimleriUrlIleGetir

class EtkinlikDetayFragment : Fragment() {

    private val args by navArgs<EtkinlikDetayFragmentArgs>()
    private var _binding: FragmentEtkinlikDetayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEtkinlikDetayBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val canliVeri = args.canliEtkinlik

        binding.imageViewResim.resimleriUrlIleGetir(
            canliVeri.image,
            placeholderProgressBar(requireContext())
        )

        binding.textViewDetay.text = canliVeri.details
        binding.textViewLink.setOnClickListener {
            val action = EtkinlikDetayFragmentDirections.actionEtkinlikDetayFragmentToFormFragment(canliVeri.formLink)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}