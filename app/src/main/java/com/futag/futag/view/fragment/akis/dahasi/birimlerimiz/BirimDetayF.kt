package com.futag.futag.view.fragment.akis.dahasi.birimlerimiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.futag.futag.R
import com.futag.futag.databinding.FragmentUnitDetailBinding

class BirimDetayF : Fragment() {

    private var _binding: FragmentUnitDetailBinding? = null
    private val binding get() = _binding!!
    private val args: BirimDetayFArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUnitDetailBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // kullaninin tikladigi birim numarasi
        val id = args.birimId

        when(id){
            0 -> {
                binding.imageView.setImageResource(R.drawable.dis_iliskiler_birim_foto2)
                binding.textViewUnitName.text = getString(R.string.foreign_relations_unit)
                binding.textViewUnitDescription.text = getString(R.string.foreign_relations_unit_text)
            }
            1 -> {
                binding.imageView.setImageResource(R.drawable.girisimcilik_birim_foto2)
                binding.textViewUnitName.text = getString(R.string.entrepreneurship_unit)
                binding.textViewUnitDescription.text = getString(R.string.entrepreneurship_unit_text)
            }
            2 -> {
                binding.imageView.setImageResource(R.drawable.medya_birim_foto2)
                binding.textViewUnitName.text = getString(R.string.media_unit)
                binding.textViewUnitDescription.text = getString(R.string.media_unit_text)
            }
            3 -> {
                binding.imageView.setImageResource(R.drawable.proje_arge_birim_foto2)
                binding.textViewUnitName.text = getString(R.string.project_rd_unit)
                binding.textViewUnitDescription.text = getString(R.string.project_rd_unit_text)
            }
            4 -> {
                binding.imageView.setImageResource(R.drawable.sosyal_sorumluluk_birim_foto2)
                binding.textViewUnitName.text = getString(R.string.social_responsibility_unit)
                binding.textViewUnitDescription.text = getString(R.string.social_responsibility_unit_text)
            }
            5 -> {
                binding.imageView.setImageResource(R.drawable.yazilim_birim_foto2)
                binding.textViewUnitName.text = getString(R.string.software_unit)
                binding.textViewUnitDescription.text = getString(R.string.software_unit_text)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}