package com.futag.futag.view.fragment.giris

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.futag.futag.R
import com.futag.futag.databinding.FragmentForgotPasswordBinding
import com.futag.futag.viewmodel.LoginRegisterViewModel

class SifremiUnuttumFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginRegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(LoginRegisterViewModel::class.java)

        binding.buttonSend.setOnClickListener {
            if (binding.editTextMail.text.isNotEmpty()){
                val mail = binding.editTextMail.text.toString()
                viewModel.forgotPasswordStatus(mail, requireContext())
                veriyiGozlemle()
            } else {
                Toast.makeText(requireContext(), R.string.please_enter_your_email_adress,Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun animasyonuGoster(){
        binding.lottieAnimation.setAnimation("ziplayanarianimation.json")
        binding.lottieAnimation.playAnimation()
    }

    private fun animasyonuDurdur(){
        binding.lottieAnimation.cancelAnimation()
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
                    Toast.makeText(requireContext(),R.string.confirmation_email_sent,Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}