package com.futag.futag.view.fragment.akis.dahasi.birimlerimiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.futag.futag.R
import com.futag.futag.databinding.FragmentBirimDetayBinding

class BirimDetayF : Fragment() {

    private var _binding: FragmentBirimDetayBinding? = null
    private val binding get() = _binding!!
    private val args: BirimDetayFArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBirimDetayBinding.inflate(inflater,container,false)
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
                binding.textViewBirimAdi.text = getString(R.string.dis_iliskiler_birimi)
                binding.textViewBirimAciklamasi.text = getString(R.string.dis_iliskiler_birim_yazisi)
            }
            1 -> {
                binding.imageView.setImageResource(R.drawable.girisimcilik_birim_foto2)
                binding.textViewBirimAdi.text = getString(R.string.girisimcilik_birimi)
                binding.textViewBirimAciklamasi.text = getString(R.string.girisimcilik_inovasyon_birim_yazisi)
            }
            2 -> {
                binding.imageView.setImageResource(R.drawable.medya_birim_foto2)
                binding.textViewBirimAdi.text = getString(R.string.medya_birimi)
                binding.textViewBirimAciklamasi.text = getString(R.string.medya_tasarim_birim_yazisi)
            }
            3 -> {
                binding.imageView.setImageResource(R.drawable.proje_arge_birim_foto2)
                binding.textViewBirimAdi.text = getString(R.string.proje_arge_birimi)
                binding.textViewBirimAciklamasi.text = getString(R.string.proje_arge_birim_yazisi)
            }
            4 -> {
                binding.imageView.setImageResource(R.drawable.sosyal_sorumluluk_birim_foto2)
                binding.textViewBirimAdi.text = getString(R.string.sosyal_sorumluluk_birimi)
                binding.textViewBirimAciklamasi.text = getString(R.string.sosyal_sorumluluk_birim_yazisi)
            }
            5 -> {
                binding.imageView.setImageResource(R.drawable.yazilim_birim_foto2)
                binding.textViewBirimAdi.text = getString(R.string.yazilim_birimi)
                binding.textViewBirimAciklamasi.text = getString(R.string.yazilim_birim_yazisi)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}