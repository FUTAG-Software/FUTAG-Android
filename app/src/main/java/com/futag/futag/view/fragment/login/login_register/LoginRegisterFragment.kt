package com.futag.futag.view.fragment.login.login_register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.futag.futag.R
import com.futag.futag.databinding.FragmentLoginRegisterBinding
import com.futag.futag.view.activity.FlowActivity

class LoginRegisterFragment : Fragment() {

    private var _binding: FragmentLoginRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginRegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(requireActivity())[LoginRegisterViewModel::class.java]
        viewModel.autoLoginStatus()
        observeData()

        binding.buttonLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_girisKayitFragment_to_girisYapFragment)
        }

        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(R.id.action_girisKayitFragment_to_kayitOlFragment)
        }

    }

    private fun observeData() {
        viewModel.animation.observe(viewLifecycleOwner, { animation ->
            animation?.let { value ->
                if (value) {
                    viewModel.isThereEntry.observe(viewLifecycleOwner, { login ->
                        login?.let {
                            if (it) {
                                binding.constraintLayout.visibility = View.INVISIBLE
                                binding.lottieAnimation.visibility = View.VISIBLE
                                activity?.let { activity ->
                                    val intent = Intent(activity, FlowActivity::class.java)
                                    activity.startActivity(intent)
                                    activity.finish()
                                }
                            } else {
                                binding.constraintLayout.visibility = View.VISIBLE
                                binding.lottieAnimation.visibility = View.GONE
                            }
                        }
                    })
                } else {
                    binding.constraintLayout.visibility = View.VISIBLE
                    binding.lottieAnimation.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}