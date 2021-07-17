package com.futag.futag.view.fragment.akis.profil

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
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.*

class ProfiliDuzenleF : Fragment() {

    private var _binding: FragmentProfiliDuzenleBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfilViewModel
    private var kullaniciProfilBilgileri: KullaniciModel? = null
    private var selectedBitmap: Bitmap? = null
    private var selectedUri: Uri? = null
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<out String>>
    private val neededRuntimePermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfiliDuzenleBinding.inflate(inflater,container,false)
        val view = binding.root
        registerLauncher()
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
            if((ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        + ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE))
                != PackageManager.PERMISSION_GRANTED) {
                // izin verilmemis, izin gerekli
                if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Snackbar.make(
                        it,
                        R.string.galeri_izni,
                        Snackbar.LENGTH_LONG).setAction(R.string.izin_ver,View.OnClickListener {
                        permissionLauncher.launch(neededRuntimePermissions)
                    }).show()
                } else {
                    permissionLauncher.launch(neededRuntimePermissions)
                }
            } else {
                // izin verilmis, galeriye gidis
                val galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(galeriIntent)
            }
        }

        binding.buttonDegisiklikleriKaydet.setOnClickListener {
            if (veriGirisiVarMi()){
                val yeniIsim = binding.editTextAd.text.toString()
                val yeniSoyisim = binding.editTextSoyad.text.toString()
                val yeniDogumGunu = binding.editTextDogumGunu.text.toString()
                if(selectedBitmap != null){
                    val smallBitmap = makeSmallerBitmap(selectedBitmap!!,400)
                    viewModel.profilGuncelle(requireContext(),kullaniciProfilBilgileri!!
                        ,yeniIsim,yeniSoyisim,yeniDogumGunu,getImageUri(requireContext(),smallBitmap))
                } else {
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
                        Picasso.get()
                            .load(kullaniciProfilBilgileri!!.profilResmi)
                            .placeholder(R.drawable.kisi_yuksek_cozunurluk)
                            .error(R.drawable.error)
                            .into(binding.imageViewProfilResmi)
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

    private fun registerLauncher(){
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK){
                val intentFromResult = result.data
                if (intentFromResult != null){
                    val imageData = intentFromResult.data
                    selectedUri = imageData
                    if (imageData != null){
                        try {
                            if (Build.VERSION.SDK_INT >= 28){
                                val source = ImageDecoder.createSource(requireActivity().contentResolver,imageData)
                                selectedBitmap = ImageDecoder.decodeBitmap(source)
                                binding.imageViewProfilResmi.setImageBitmap(selectedBitmap)
                            } else {
                                selectedBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,imageData)
                                binding.imageViewProfilResmi.setImageBitmap(selectedBitmap)
                            }
                        } catch (e: Exception){
                            e.printStackTrace()
                        }
                    }
                }
            }
        }

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                if (it.value && it.key == Manifest.permission.READ_EXTERNAL_STORAGE) {
                    val galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncher.launch(galeriIntent)
                }
            }
        }
    }

    private fun makeSmallerBitmap(image: Bitmap, maximumSize: Int): Bitmap{
        var width = image.width
        var height = image.height

        val bitmapRatio: Double = width.toDouble() / height.toDouble()

        if (bitmapRatio > 1){
            // Landscape - yatay
            width = maximumSize
            val scaleHeight = width / bitmapRatio
            height = scaleHeight.toInt()
        } else {
            // Portrait - dikey
            height = maximumSize
            val scaleWidth = height * bitmapRatio
            width = scaleWidth.toInt()
        }

        return Bitmap.createScaledBitmap(image,width,height,true)
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "newImage", null)
        return Uri.parse(path)
    }

    private fun veriGirisiVarMi(): Boolean = binding.editTextAd.text.isNotEmpty()
            && binding.editTextSoyad.text.isNotEmpty() && binding.editTextDogumGunu.text.isNotEmpty()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}