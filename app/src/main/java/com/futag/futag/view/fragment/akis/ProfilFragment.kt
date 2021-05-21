package com.futag.futag.view.fragment.akis

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.futag.futag.MainActivity
import com.futag.futag.R
import com.futag.futag.databinding.FragmentProfilBinding
import com.futag.futag.view.activity.AkisActivity
import com.futag.futag.viewmodel.ProfilViewModel

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfilViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ProfilViewModel::class.java)

        viewModel.profilBilgileriniGetir()
        profilBilgileriniCek()

        binding.buttonCikisYap.setOnClickListener {
            viewModel.cikisYap()
            veriyiGozlemleCikisYap()
        }
    }

    private fun profilBilgileriniCek(){

    }

    private fun veriyiGozlemleCikisYap(){
        viewModel.animasyon.observe(viewLifecycleOwner, { animasyon ->
            animasyon?.let { deger ->
                if (deger){
                    viewModel.girisVarMi.observe(viewLifecycleOwner, { giris ->
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