package com.futag.futag.view.fragment.giris

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    private var secilenGorsel: Uri? = null
    private var secilenBitMap: Bitmap? = null

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

        binding.imageViewProfilResmi.setOnClickListener {
            if(ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                // izin verilmemis, izin gerekli
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
            } else {
                // izin verilmis, galeriye gidis
                val galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntent,2)
            }
        }

        binding.buttonKayitOl.setOnClickListener {
            if (binding.imageViewProfilResmi.drawable != null){
                klavyeyiKapat()
                if(veriGirisKontrolu()){
                    val isim = binding.editTextAd.text.toString()
                    val soyisim = binding.editTextSoyad.text.toString()
                    val email = binding.editTextMail.text.toString()
                    val sifre = binding.editTextSifre.text.toString()
                    val sifreTekrar = binding.editTextSifreTekrar.text.toString()
                    val dogumgunu = binding.editTextDogumGunu.text.toString()
                    if(sifre == sifreTekrar){
                        viewModel.kayitOnayDurumu(email, sifre, isim, soyisim, dogumgunu, secilenGorsel, requireContext())
                        veriyiGozlemle()
                    } else {
                        Toast.makeText(requireContext(),R.string.sifreler_ayni_olmalidir,Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), R.string.bosluklari_doldurunuz,Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), R.string.resim_seciniz,Toast.LENGTH_SHORT).show()
            }
        }

        binding.textViewGirisYap.setOnClickListener {
            findNavController().navigate(R.id.action_kayitOlFragment_to_girisYapFragment)
        }

    }

    private fun klavyeyiKapat(){
        val view = requireActivity().currentFocus
        if (view != null){
            val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
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

    // izin sonucu yapilacaklar
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 1){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // izin verildi
                val galeriIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntent,2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    // veri geldiyse
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // cevap donmus mu kontrolu
        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            secilenGorsel = data.data
            if (secilenGorsel != null){
                // sdk kontrolu
                if(Build.VERSION.SDK_INT >= 28){
                    val source = ImageDecoder.createSource(requireActivity().contentResolver,secilenGorsel!!)
                    secilenBitMap = ImageDecoder.decodeBitmap(source)
                    binding.imageViewProfilResmi.setImageBitmap(secilenBitMap)
                } else {
                    secilenBitMap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,secilenGorsel)
                    binding.imageViewProfilResmi.setImageBitmap(secilenBitMap)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
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