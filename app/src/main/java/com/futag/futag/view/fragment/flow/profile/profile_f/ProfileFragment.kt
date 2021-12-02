package com.futag.futag.view.fragment.flow.profile.profile_f

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.futag.futag.MainActivity
import com.futag.futag.R
import com.futag.futag.databinding.FragmentProfileBinding
import com.futag.futag.model.UserModel
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel
    private var userProfileInfo: UserModel? = null

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

        viewModel.getProfileInfo()
        getProfileInfo()

        binding.buttonEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profilFragment_to_profiliDuzenleF)
        }

        binding.buttonSignOut.setOnClickListener {
            viewModel.signOut()
            observeDataSignOut()
        }
    }

    private fun getProfileInfo() {
        viewModel.animation.observe(viewLifecycleOwner, { animation ->
            animation?.let { value ->
                if (value) {
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
                if (data) {
                    userProfileInfo = viewModel.userInfo
                    binding.editTextUserMail.setText(userProfileInfo!!.email)
                    binding.editTextBirthday.setText(userProfileInfo!!.birthday)
                    val name = userProfileInfo!!.name
                    val surname = userProfileInfo!!.surname
                    val nameSurname = "$name $surname"
                    binding.textViewNameAndSurname.text = nameSurname
                    if (userProfileInfo!!.profileImage != null) {
                        Picasso.get()
                            .load(userProfileInfo!!.profileImage)
                            .placeholder(R.drawable.person_high_resolution)
                            .into(binding.imageViewProfileImage)
                    } else {
                        binding.imageViewProfileImage.setImageDrawable(
                            ActivityCompat.getDrawable(
                                requireContext(),
                                R.drawable.person_high_resolution
                            )
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
        viewModel.errorMessage.observe(viewLifecycleOwner, { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun observeDataSignOut() {
        viewModel.animation.observe(viewLifecycleOwner, { animation ->
            animation?.let { value ->
                if (value) {
                    viewModel.isThereEntry.observe(viewLifecycleOwner, { login ->
                        login?.let {
                            if (it) {
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