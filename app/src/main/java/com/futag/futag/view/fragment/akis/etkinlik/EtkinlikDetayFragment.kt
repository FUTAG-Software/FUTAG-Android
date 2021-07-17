package com.futag.futag.view.fragment.akis.etkinlik

import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.futag.futag.R
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

        var match: MatchResult? = null

        var resimsizHTML = canliVeri.content.replace(Regex("<img.+>"), "")
        resimsizHTML = resimsizHTML.replace(Regex("\\n"),"<br>")

        val inputString = resimsizHTML
        val regex = "https.+?true".toRegex()
        match = regex.find(inputString)

        val inputHtml = resimsizHTML
        val xxxd = "<p.+?</p>".toRegex()
        val detayYazisi = xxxd.find(inputHtml)

        if (detayYazisi != null){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                binding.textViewDetay.text = Html.fromHtml(detayYazisi.value, Html.FROM_HTML_MODE_COMPACT)
            } else {
                binding.textViewDetay.text = Html.fromHtml(detayYazisi.value)
            }
        }  else {
            binding.textViewDetay.setText(R.string.daha_sonra_tekrar_deneyiniz)
        }

        if (match != null) {
            binding.textViewLink.paintFlags = binding.textViewLink.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            binding.textViewLink.setText(R.string.kayit_icin_tiklayiniz)
        } else {
            binding.textViewLink.setText(R.string.linke_ulasilamadi)
        }

        binding.textViewLink.setOnClickListener {
            if (match != null) {
                val action = EtkinlikDetayFragmentDirections.actionEtkinlikDetayFragmentToFormFragment(match.value)
                Navigation.findNavController(it).navigate(action)
            } else {
                Toast.makeText(requireContext(),R.string.linke_ulasilamadi,Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}