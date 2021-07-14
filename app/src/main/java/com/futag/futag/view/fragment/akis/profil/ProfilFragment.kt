package com.futag.futag.view.fragment.akis.profil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.futag.futag.MainActivity
import com.futag.futag.R
import com.futag.futag.databinding.FragmentProfilBinding
import com.futag.futag.model.KullaniciModel
import com.futag.futag.util.placeholderProgressBar
import com.futag.futag.util.resimleriUrlIleGetir
import com.futag.futag.viewmodel.ProfilViewModel
import com.squareup.picasso.Picasso

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfilViewModel
    private var kullaniciProfilBilgileri: KullaniciModel? = null

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

        viewModel.profilBilgileriniGetir(requireContext())
        profilBilgileriniCek()

        binding.buttonProfiliDuzenle.setOnClickListener {
            findNavController().navigate(R.id.action_profilFragment_to_profiliDuzenleF)
        }

        binding.buttonCikisYap.setOnClickListener {
            viewModel.cikisYap()
            veriyiGozlemleCikisYap()
        }
    }

    private fun profilBilgileriniCek(){
        viewModel.animasyon.observe(viewLifecycleOwner,{ animasyon ->
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
        viewModel.veriOnayi.observe(viewLifecycleOwner, { veriOnayi ->
            veriOnayi?.let { veri ->
                if (veri){
                    kullaniciProfilBilgileri = viewModel.kullaniciBilgileri
                    binding.editTextKullaniciMail.setText(kullaniciProfilBilgileri!!.email)
                    binding.editTextDogumTarihi.setText(kullaniciProfilBilgileri!!.dogumGunu)
                    val isim = kullaniciProfilBilgileri!!.isim
                    val soyisim = kullaniciProfilBilgileri!!.soyisim
                    val isimSoyisim = "$isim $soyisim"
                    binding.textViewIsimSoyisim.text = isimSoyisim
                    if(kullaniciProfilBilgileri!!.profilResmi != null){
                        Picasso.get().load(kullaniciProfilBilgileri!!.profilResmi)
                            .placeholder(R.drawable.kisi_yuksek_cozunurluk).into(binding.imageViewProfilResmi)
                    } else{
                        binding.imageViewProfilResmi.setImageDrawable(
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