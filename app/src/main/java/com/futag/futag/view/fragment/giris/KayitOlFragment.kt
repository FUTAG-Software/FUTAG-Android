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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.futag.futag.R
import com.futag.futag.databinding.BottomSheetDialogKvkkBinding
import com.futag.futag.databinding.FragmentRegisterBinding
import com.futag.futag.view.activity.FlowActivity
import com.futag.futag.viewmodel.LoginRegisterViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.*

class KayitOlFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginRegisterViewModel
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
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        val view = binding.root
        registerLauncher()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(LoginRegisterViewModel::class.java)

        val takvim = Calendar.getInstance()
        val yil = takvim.get(Calendar.YEAR)
        val ay = takvim.get(Calendar.MONTH)
        val gun = takvim.get(Calendar.DAY_OF_MONTH)

        binding.textViewKvkkText.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(requireContext(),R.style.ThemeOverlay_MaterialComponents_BottomSheetDialog)
            val bottomSheetBinding = BottomSheetDialogKvkkBinding.inflate(LayoutInflater.from(requireContext()))
            bottomSheetDialog.setContentView(bottomSheetBinding.root)
            bottomSheetDialog.show()
        }

        // Kullanici dogum gununun alinmasi; Gun,Ay,Yil
        binding.editTextBirthday.setOnClickListener {
            val dpd = DatePickerDialog(requireContext(), { _, mYil, mAy, mGun ->
                val tarih = "$mGun-${mAy+1}-$mYil"
                binding.editTextBirthday.text = tarih
            }, yil, ay, gun)
            dpd.show()
        }

        binding.imageViewCircleImage.setOnClickListener {
            if((ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        + ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE))
                != PackageManager.PERMISSION_GRANTED) {
                // izin verilmemis, izin gerekli
                if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Snackbar.make(
                        it,
                        R.string.gallery_permission,
                        Snackbar.LENGTH_LONG).setAction(R.string.give_permission) {
                        permissionLauncher.launch(neededRuntimePermissions)
                    }.show()
                } else {
                    permissionLauncher.launch(neededRuntimePermissions)
                }
            } else {
                // izin verilmis, galeriye gidis
                val galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(galeriIntent)
            }
        }

        binding.buttonRegister.setOnClickListener {
            if (binding.imageViewCircleImage.drawable != null){
                klavyeyiKapat()
                if (selectedBitmap != null) {
                    val smallBitmap = makeSmallerBitmap(selectedBitmap!!,400)
                    firebaseVeriKaydi(getImageUri(requireContext(),smallBitmap))
                } else {
                    firebaseVeriKaydi(null)
                }
            } else {
                Toast.makeText(requireContext(), R.string.select_picture,Toast.LENGTH_SHORT).show()
            }
        }

        binding.textViewLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_kayitOlFragment_to_girisYapFragment)
        }

    }

    private fun firebaseVeriKaydi(secilenGorsel: Uri?){
        if(veriGirisKontrolu()){
            val isim = binding.editTextName.text.toString()
            val soyisim = binding.editTextSurname.text.toString()
            val email = binding.editTextMail.text.toString()
            val sifre = binding.editTextPassword.text.toString()
            val sifreTekrar = binding.editTextAgainPassword.text.toString()
            val dogumgunu = binding.editTextBirthday.text.toString()
            if(sifre == sifreTekrar){
                viewModel.registrationConfirmationStatus(email, sifre, isim, soyisim, dogumgunu, secilenGorsel, requireContext())
                veriyiGozlemle()
            } else {
                Toast.makeText(requireContext(),R.string.password_must_match,Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), R.string.fill_in_the_blanks,Toast.LENGTH_SHORT).show()
        }
    }

    private fun veriyiGozlemle(){
        viewModel.animation.observe(viewLifecycleOwner, { animasyon ->
            animasyon?.let {
                if (it){
                    binding.linearLayout.visibility = View.INVISIBLE
                    binding.lottieAnimation.visibility = View.VISIBLE
                    animasyonuGoster()
                } else {
                    binding.linearLayout.visibility = View.VISIBLE
                    binding.lottieAnimation.visibility = View.GONE
                    animasyonuDurdur()
                }
            }
        })
        viewModel.dataConfirmation.observe(viewLifecycleOwner, { veriOnayi ->
            veriOnayi?.let { onay ->
                if (onay){
                    activity?.let {
                        Toast.makeText(
                            requireContext(),
                            "${resources.getString(R.string.welcome)} ${binding.editTextName.text}"
                            ,Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(it, FlowActivity::class.java)
                        it.startActivity(intent)
                        it.finish()
                    }
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
                                binding.imageViewCircleImage.setImageBitmap(selectedBitmap)
                            } else {
                                selectedBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,imageData)
                                binding.imageViewCircleImage.setImageBitmap(selectedBitmap)
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
        inImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val path =
                MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "futagProfileImage", null)
        return Uri.parse(path)
    }

    // Butun alanlarin dolu olma durumunun kontrolu
    private fun veriGirisKontrolu(): Boolean = binding.editTextName.text.isNotEmpty()
            && binding.editTextSurname.text.isNotEmpty() && binding.editTextMail.text.isNotEmpty() &&
            binding.editTextPassword.text.isNotEmpty() && binding.editTextAgainPassword.text.isNotEmpty()
            && binding.editTextBirthday.text.isNotEmpty()

    private fun animasyonuGoster(){
        binding.lottieAnimation.setAnimation("ziplayanarianimation.json")
        binding.lottieAnimation.playAnimation()
    }

    private fun animasyonuDurdur(){
        binding.lottieAnimation.cancelAnimation()
    }

    private fun klavyeyiKapat(){
        val view = requireActivity().currentFocus
        if (view != null){
            val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}