package com.futag.futag.presentation.ui.fragment.flow.profile.update_password

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.futag.futag.presentation.ui.activity.MainActivity
import com.futag.futag.R
import com.futag.futag.databinding.FragmentUpdatePasswordBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UpdatePasswordFragment : Fragment() {

    private var _binding: FragmentUpdatePasswordBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<UpdatePasswordFragmentArgs>()
    private lateinit var viewModel: UpdatePasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdatePasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = args.email
        viewModel = ViewModelProvider(requireActivity())[UpdatePasswordViewModel::class.java]

        binding.buttonChangePassword.setOnClickListener {
            if (checkEmptyView()) {
                val newPassword = binding.editTextNewPassword.text.toString()
                val newPasswordAgain = binding.editTextNewPasswordAgain.text.toString()
                val oldPassword = binding.editTextOldPassword.text.toString()
                if (oldPassword == newPassword) {
                    Toast.makeText(
                        requireContext(),
                        R.string.please_enter_different_password,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (newPassword == newPasswordAgain) {
                        val credential = EmailAuthProvider
                            .getCredential(email, oldPassword)
                        val user = Firebase.auth.currentUser!!
                        user.reauthenticate(credential).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                viewModel.updatePassword(newPassword)
                                viewModel.animation.observe(viewLifecycleOwner, { animation ->
                                    animation?.let {
                                        if (it) {
                                            binding.linearLayout.visibility = View.INVISIBLE
                                            binding.lottieAnimation.visibility = View.VISIBLE
                                        } else {
                                            binding.lottieAnimation.visibility = View.GONE
                                            binding.linearLayout.visibility = View.VISIBLE
                                        }
                                    }
                                })
                                viewModel.updatePasswordData.observe(viewLifecycleOwner, { data ->
                                    data?.let {
                                        if (it) {
                                            Toast.makeText(
                                                requireContext(),
                                                R.string.password_changed,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            viewModel.signOut()
                                            observeDataSignOut()
                                        }
                                    }
                                })
                                viewModel.updatePasswordError.observe(viewLifecycleOwner, { error ->
                                    error?.let {
                                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                })
                            }
                        }.addOnFailureListener {
                            Toast.makeText(
                                requireContext(),
                                it.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            R.string.password_must_match,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), R.string.fill_in_the_blanks, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun observeDataSignOut() {
        viewModel.animation.observe(viewLifecycleOwner, { animation ->
            animation?.let { value ->
                if (value) {
                    viewModel.isThereEntry.observe(viewLifecycleOwner, { login ->
                        login?.let {
                            if (it) {
                                binding.linearLayout.visibility = View.INVISIBLE
                                binding.lottieAnimation.visibility = View.VISIBLE
                                activity?.let { activity ->
                                    val intent = Intent(activity, MainActivity::class.java)
                                    activity.startActivity(intent)
                                    activity.finish()
                                }
                            } else {
                                binding.linearLayout.visibility = View.VISIBLE
                                binding.lottieAnimation.visibility = View.GONE
                            }
                        }
                    })
                } else {
                    binding.linearLayout.visibility = View.VISIBLE
                    binding.lottieAnimation.visibility = View.GONE
                }
            }
        })
    }

    private fun checkEmptyView(): Boolean = binding.editTextNewPassword.text.isNotEmpty() &&
            binding.editTextNewPasswordAgain.text.isNotEmpty() && binding.editTextOldPassword.text.isNotEmpty()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}