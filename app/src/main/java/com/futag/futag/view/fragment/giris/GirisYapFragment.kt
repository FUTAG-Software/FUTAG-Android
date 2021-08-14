package com.futag.futag.view.fragment.giris

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.futag.futag.R
import com.futag.futag.databinding.FragmentLoginBinding
import com.futag.futag.view.activity.FlowActivity
import com.futag.futag.viewmodel.KayitOlGirisYapViewModel

class GirisYapFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: KayitOlGirisYapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(KayitOlGirisYapViewModel::class.java)

        binding.buttonLogIn.setOnClickListener {
            klavyeyiKapat()
            if (veriGirisKontrolu()){
                val email = binding.editTextMail.text.toString()
                val sifre = binding.editTextPassword.text.toString()

                viewModel.girisOnayDurumu(email,sifre,requireContext())
                veriyiGozlemle()
            } else {
                Toast.makeText(requireContext(),R.string.fill_in_the_blanks,Toast.LENGTH_SHORT).show()
            }
        }

        binding.textViewRegister.setOnClickListener {
            findNavController().navigate(R.id.action_girisYapFragment_to_kayitOlFragment)
        }

        binding.textViewForgotMyPassword.setOnClickListener {
            findNavController().navigate(R.id.action_girisYapFragment_to_sifremiUnuttumFragment)
        }
    }

    private fun klavyeyiKapat(){
        val view = requireActivity().currentFocus
        if (view != null){
            val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
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
        viewModel.animasyon.observe(viewLifecycleOwner, { animasyon ->
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
        viewModel.veriOnayi.observe(viewLifecycleOwner, { veriOnayi ->
            veriOnayi?.let { onay ->
                if (onay){
                    activity?.let {
                        val intent = Intent(it, FlowActivity::class.java)
                        it.startActivity(intent)
                        it.finish()
                    }
                }
            }
        })
    }

    private fun veriGirisKontrolu(): Boolean = binding.editTextMail.text.isNotEmpty()
            && binding.editTextPassword.text.isNotEmpty()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}