package com.futag.futag.presentation.ui.fragment.login.forgot_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.futag.futag.R
import com.futag.futag.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ForgotPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ForgotPasswordViewModel::class.java]

        binding.buttonSend.setOnClickListener {
            if (binding.editTextEMail.text.isNotEmpty()) {
                val email = binding.editTextEMail.text.toString()
                viewModel.forgotPassword(email)
                observeData()
            } else {
                Toast.makeText(
                    requireContext(),
                    R.string.please_enter_your_email_adress,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun observeData() {
        viewModel.animation.observe(viewLifecycleOwner) { animation ->
            animation?.let {
                if (it) {
                    binding.linearLayout.visibility = View.INVISIBLE
                    binding.lottieAnimation.visibility = View.VISIBLE
                } else {
                    binding.linearLayout.visibility = View.VISIBLE
                    binding.lottieAnimation.visibility = View.GONE
                }
            }
        }
        viewModel.success.observe(viewLifecycleOwner) { success ->
            success?.let {
                if (it) {
                    Toast.makeText(
                        requireContext(),
                        R.string.confirmation_email_sent,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}