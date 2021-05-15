package com.futag.futag.view.fragment.giris

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.futag.futag.R
import com.futag.futag.databinding.FragmentKayitOlBinding
import com.futag.futag.view.activity.AkisActivity
import com.futag.futag.viewmodel.KayitOlGirisYapViewModel
import java.util.*

class KayitOlFragment : Fragment() {

    private var _binding: FragmentKayitOlBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: KayitOlGirisYapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKayitOlBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(KayitOlGirisYapViewModel::class.java)

        val takvim = Calendar.getInstance()
        val yil = takvim.get(Calendar.YEAR)
        val ay = takvim.get(Calendar.MONTH)
        val gun = takvim.get(Calendar.DAY_OF_MONTH)

        // Kullanici dogum gununun alinmasi; Gun,Ay,Yil
        binding.editTextDogumGunu.setOnClickListener {
            val dpd = DatePickerDialog(requireContext(), { _, mYil, mAy, mGun ->
                val tarih = "$mGun-${mAy+1}-$mYil"
                binding.editTextDogumGunu.text = tarih
            }, yil, ay, gun)
            dpd.show()
        }

        binding.buttonKayitOl.setOnClickListener {
            if(veriGirisKontrolu()){
                val isim = binding.editTextAd.text.toString()
                val soyisim = binding.editTextSoyad.text.toString()
                val email = binding.editTextMail.text.toString()
                val sifre = binding.editTextSifre.text.toString()
                val sifreTekrar = binding.editTextSifreTekrar.text.toString()
                val dogumgunu = binding.editTextDogumGunu.text.toString()

                if(sifre == sifreTekrar){
                    viewModel.kayitOnayDurumu(email, sifre, isim, soyisim, dogumgunu, requireContext())
                    veriyiGozlemle()
                } else {
                    Toast.makeText(requireContext(),R.string.sifreler_ayni_olmalidir,Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), R.string.bosluklari_doldurunuz,Toast.LENGTH_SHORT).show()
            }
        }

        binding.textViewGirisYap.setOnClickListener {
            findNavController().navigate(R.id.action_kayitOlFragment_to_girisYapFragment)
        }

    }

    private fun animasyonuGoster(){
        binding.lottieAnimasyon.setAnimation("ziplayanarianimation.json")
        binding.lottieAnimasyon.playAnimation()
    }

    private fun animasyonuDurdur(){
        binding.lottieAnimasyon.cancelAnimation()
    }

    private fun veriyiGozlemle(){
        viewModel.animasyon.observe(viewLifecycleOwner, { animasyon ->
            animasyon?.let {
                if (it){
                    binding.linearLayout.visibility = View.INVISIBLE
                    binding.lottieAnimasyon.visibility = View.VISIBLE
                    animasyonuGoster()
                } else {
                    binding.linearLayout.visibility = View.VISIBLE
                    binding.lottieAnimasyon.visibility = View.GONE
                    animasyonuDurdur()
                }
            }
        })
        viewModel.veriOnayi.observe(viewLifecycleOwner, { veriOnayi ->
            veriOnayi?.let { onay ->
                if (onay){
                    activity?.let {
                        val intent = Intent(it, AkisActivity::class.java)
                        it.startActivity(intent)
                        it.finish()
                    }
                }
            }
        })
    }

    // Butun alanlarin dolu olma durumunun kontrolu
    private fun veriGirisKontrolu(): Boolean = binding.editTextAd.text.isNotEmpty()
            && binding.editTextSoyad.text.isNotEmpty() && binding.editTextMail.text.isNotEmpty() &&
            binding.editTextSifre.text.isNotEmpty() && binding.editTextSifreTekrar.text.isNotEmpty()
            && binding.editTextDogumGunu.text.isNotEmpty()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}