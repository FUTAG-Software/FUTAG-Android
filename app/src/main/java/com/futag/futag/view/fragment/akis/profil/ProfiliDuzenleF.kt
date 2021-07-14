package com.futag.futag.view.fragment.akis.profil

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.futag.futag.R
import com.futag.futag.databinding.FragmentProfiliDuzenleBinding
import com.futag.futag.model.KullaniciModel
import com.futag.futag.util.placeholderProgressBar
import com.futag.futag.util.resimleriUrlIleGetir
import com.futag.futag.view.activity.AkisActivity
import com.futag.futag.viewmodel.ProfilViewModel
import com.squareup.picasso.Picasso
import java.util.*

class ProfiliDuzenleF : Fragment() {

    private var _binding: FragmentProfiliDuzenleBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfilViewModel
    private var kullaniciProfilBilgileri: KullaniciModel? = null
    private var secilenGorsel: Uri? = null
    private var secilenBitMap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfiliDuzenleBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ProfilViewModel::class.java)

        viewModel.profilBilgileriniGetir(requireContext())
        profilBilgileriniCek()

        val takvim = Calendar.getInstance()
        val yil = takvim.get(Calendar.YEAR)
        val ay = takvim.get(Calendar.MONTH)
        val gun = takvim.get(Calendar.DAY_OF_MONTH)

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

        binding.buttonDegisiklikleriKaydet.setOnClickListener {
            if (veriGirisiVarMi()){
                val yeniIsim = binding.editTextAd.text.toString()
                val yeniSoyisim = binding.editTextSoyad.text.toString()
                val yeniDogumGunu = binding.editTextDogumGunu.text.toString()
                if(secilenGorsel != null){
                    viewModel.profilGuncelle(requireContext(),kullaniciProfilBilgileri!!
                        ,yeniIsim,yeniSoyisim,yeniDogumGunu,secilenGorsel)
                } else{
                    viewModel.profilGuncelle(requireContext(),kullaniciProfilBilgileri!!
                        ,yeniIsim,yeniSoyisim,yeniDogumGunu,null)
                }
            } else {
                Toast.makeText(requireContext(),R.string.bosluklari_doldurunuz,Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun profilBilgileriniCek(){
        viewModel.animasyon.observe(viewLifecycleOwner,{ animasyon ->
            animasyon?.let { deger ->
                if (deger){
                    binding.constraintLayout.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.constraintLayout.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
        viewModel.veriOnayi.observe(viewLifecycleOwner, { veriOnayi ->
            veriOnayi?.let { veri ->
                if (veri){
                    kullaniciProfilBilgileri = viewModel.kullaniciBilgileri
                    binding.editTextAd.setText(kullaniciProfilBilgileri!!.isim)
                    binding.editTextDogumGunu.text = kullaniciProfilBilgileri!!.dogumGunu
                    binding.editTextSoyad.setText(kullaniciProfilBilgileri!!.soyisim)
                    if(kullaniciProfilBilgileri!!.profilResmi != null){
                        Picasso.get().load(kullaniciProfilBilgileri!!.profilResmi)
                            .placeholder(R.drawable.kisi_yuksek_cozunurluk).into(binding.imageViewProfilResmi)
                    } else{
                        binding.imageViewProfilResmi.setImageDrawable(
                            ActivityCompat.getDrawable(requireContext(),R.drawable.kisi_yuksek_cozunurluk)
                        )
                    }
                    binding.constraintLayout.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                } else {
                    binding.constraintLayout.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun veriGirisiVarMi(): Boolean = binding.editTextAd.text.isNotEmpty()
            && binding.editTextSoyad.text.isNotEmpty() && binding.editTextDogumGunu.text.isNotEmpty()

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}