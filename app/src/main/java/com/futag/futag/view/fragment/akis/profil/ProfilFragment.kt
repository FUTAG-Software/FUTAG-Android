package com.futag.futag.view.fragment.akis.profil

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.futag.futag.MainActivity
import com.futag.futag.R
import com.futag.futag.databinding.FragmentProfileBinding
import com.futag.futag.model.UserModel
import com.futag.futag.viewmodel.ProfileViewModel
import com.squareup.picasso.Picasso

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel
    private var userProfilBilgileri: UserModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)

        viewModel.getProfileInfo(requireContext())
        profilBilgileriniCek()

        binding.buttonEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profilFragment_to_profiliDuzenleF)
        }

        binding.buttonSignOut.setOnClickListener {
            viewModel.signOut()
            veriyiGozlemleCikisYap()
        }
    }

    private fun profilBilgileriniCek(){
        viewModel.animation.observe(viewLifecycleOwner,{ animasyon ->
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
        viewModel.dataConfirmation.observe(viewLifecycleOwner, { veriOnayi ->
            veriOnayi?.let { veri ->
                if (veri){
                    userProfilBilgileri = viewModel.userInfo
                    binding.editTextUserMail.setText(userProfilBilgileri!!.email)
                    binding.editTextBirthday.setText(userProfilBilgileri!!.birthday)
                    val isim = userProfilBilgileri!!.name
                    val soyisim = userProfilBilgileri!!.surname
                    val isimSoyisim = "$isim $soyisim"
                    binding.textViewNameAndSurname.text = isimSoyisim
                    if(userProfilBilgileri!!.profileImage != null){
                        Picasso.get()
                            .load(userProfilBilgileri!!.profileImage)
                            .placeholder(R.drawable.kisi_yuksek_cozunurluk)
                            .into(binding.imageViewProfileImage)
                    } else{
                        binding.imageViewProfileImage.setImageDrawable(
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

    private fun veriyiGozlemleCikisYap(){
        viewModel.animation.observe(viewLifecycleOwner, { animasyon ->
            animasyon?.let { deger ->
                if (deger){
                    viewModel.isThereEntry.observe(viewLifecycleOwner, { giris ->
                        giris?.let {
                            if (it){
                                binding.constraintLayout.visibility = View.INVISIBLE
                                binding.progressBar.visibility = View.VISIBLE
                                activity?.let { activity ->
                                    val intent = Intent(activity, MainActivity::class.java)
                                    activity.startActivity(intent)
                                    activity.finish()
                                }
                            } else {
                                binding.constraintLayout.visibility = View.VISIBLE
                                binding.progressBar.visibility = View.GONE
                            }
                        }
                    })
                } else {
                    binding.constraintLayout.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}