package com.futag.futag.view.fragment.flow.profile

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.app.Dialog
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
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.futag.futag.MainActivity
import com.futag.futag.R
import com.futag.futag.databinding.DeleteAccountConfirmDialogBinding
import com.futag.futag.databinding.FragmentEditProfileBinding
import com.futag.futag.model.UserModel
import com.futag.futag.viewmodel.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.*

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel
    private var userProfileInfo: UserModel? = null
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
        _binding = FragmentEditProfileBinding.inflate(inflater,container,false)
        val view = binding.root
        registerLauncher()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)

        viewModel.getProfileInfo(requireContext())
        getProfileInfo()

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        binding.editTextBirthday.setOnClickListener {
            val dpd = DatePickerDialog(requireContext(), { _, mYear, mMonth, mDay ->
                val tarih = "$mDay-${mMonth+1}-$mYear"
                binding.editTextBirthday.text = tarih
            }, year, month, day)
            dpd.show()
        }

        binding.imageViewProfileImage.setOnClickListener {
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
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(galleryIntent)
            }
        }

        binding.buttonSaveChanges.setOnClickListener {
            if (dataControl()){
                val newName = binding.editTextName.text.toString()
                val newSurname = binding.editTextSurname.text.toString()
                val newBirthday = binding.editTextBirthday.text.toString()
                if(selectedBitmap != null){
                    val smallBitmap = makeSmallerBitmap(selectedBitmap!!,400)
                    viewModel.updateProfile(requireContext(),userProfileInfo!!
                        ,newName,newSurname,newBirthday,getImageUri(requireContext(),smallBitmap))
                } else {
                    viewModel.updateProfile(requireContext(),userProfileInfo!!
                        ,newName,newSurname,newBirthday,null)
                }
            } else {
                Toast.makeText(requireContext(),R.string.fill_in_the_blanks,Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonDeleteMyAccount.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(R.string.delete_my_account)
            builder.setMessage(R.string.delete_account_confirm)
            builder.setCancelable(true)
            builder.setNegativeButton(R.string.no) { _, _ ->
            }
            builder.setPositiveButton(R.string.yes) { _, _ ->
                val email = userProfileInfo!!.email
                val dialog = Dialog(requireContext())
                val dialogPasswordBinding = DeleteAccountConfirmDialogBinding.inflate(
                    LayoutInflater.from(requireContext())
                )
                dialog.setContentView(dialogPasswordBinding.root)
                dialogPasswordBinding.buttonConfirm.setOnClickListener {
                    if (dialogPasswordBinding.editTextPass.text.isNotEmpty()){
                        dialog.cancel()
                        val password = dialogPasswordBinding.editTextPass.text.toString()
                        val credential = EmailAuthProvider
                            .getCredential(email, password)
                        val user = Firebase.auth.currentUser!!
                        user.reauthenticate(credential).addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                viewModel.deleteAccount(requireContext())
                                viewModel.deleteAccountAnimation.observe(viewLifecycleOwner,{ animation ->
                                    animation?.let {
                                        if (it){
                                            binding.constraintLayout.visibility = View.INVISIBLE
                                            binding.progressBar.visibility = View.VISIBLE
                                        } else {
                                            binding.progressBar.visibility = View.GONE
                                            val intent = Intent(requireActivity(), MainActivity::class.java)
                                            startActivity(intent)
                                            requireActivity().finish()
                                        }
                                    }
                                })
                            } else {
                                println(task.exception)
                                Toast.makeText(requireContext(),R.string.try_again_later,Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(),R.string.fill_in_the_blanks,Toast.LENGTH_SHORT).show()
                    }
                }
                dialog.show()
            }
            builder.show()
        }

    }

    private fun getProfileInfo(){
        viewModel.animation.observe(viewLifecycleOwner,{ animation ->
            animation?.let { value ->
                if (value){
                    binding.constraintLayout.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.constraintLayout.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
        viewModel.dataConfirmation.observe(viewLifecycleOwner, { dataConfirm ->
            dataConfirm?.let { data ->
                if (data){
                    userProfileInfo = viewModel.userInfo
                    binding.editTextName.setText(userProfileInfo!!.name)
                    binding.editTextBirthday.text = userProfileInfo!!.birthday
                    binding.editTextSurname.setText(userProfileInfo!!.surname)
                    if(userProfileInfo!!.profileImage != null){
                        Picasso.get()
                            .load(userProfileInfo!!.profileImage)
                            .placeholder(R.drawable.person_high_resolution)
                            .error(R.drawable.error)
                            .into(binding.imageViewProfileImage)
                    } else{
                        binding.imageViewProfileImage.setImageDrawable(
                            ActivityCompat.getDrawable(requireContext(),R.drawable.person_high_resolution)
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
                                binding.imageViewProfileImage.setImageBitmap(selectedBitmap)
                            } else {
                                selectedBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,imageData)
                                binding.imageViewProfileImage.setImageBitmap(selectedBitmap)
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

    private fun dataControl(): Boolean = binding.editTextName.text.isNotEmpty()
            && binding.editTextSurname.text.isNotEmpty() && binding.editTextBirthday.text.isNotEmpty()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}