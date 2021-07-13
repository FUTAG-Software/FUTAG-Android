package com.futag.futag.view.fragment.akis.ev

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.futag.futag.R
import com.futag.futag.adapter.GonderilerRecyclerAdapter
import com.futag.futag.databinding.FragmentEvBinding
import com.futag.futag.model.GonderiModel

class EvFragment : Fragment() {

    private var _binding: FragmentEvBinding? = null
    private val binding get() = _binding!!
    private lateinit var sliderListesi: ArrayList<SlideModel>
    private var gonderiListesi = emptyList<GonderiModel>()
    private lateinit var adapter: GonderilerRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEvBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GonderilerRecyclerAdapter(requireParentFragment())
        sliderListesi = ArrayList()
        sliderDoldur()

        binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                when(position){
                    0 -> {
                        findNavController().navigate(R.id.action_evFragment_to_webSitesiFragment)
                    }
                    1 -> {

                    }
                    2 -> {

                    }
                }
            }
        })

        val x = GonderiModel(1,"Kocatepe Camii","Turkiyenin en buyuk camilerinden olan Kocatepe Camii buyuk bir mirasa sahiptir","Adem Atici")
        val y = GonderiModel(2,"Yapay Zekanin Gelecegi","Son yillarda sik sik duydugumuz yapay zeka sozleri cagi asti","Abdulselam Sarigul")
        val z = GonderiModel(3,"Parisin Incisi Eyfel Kulesi","Fransanin en fazla ziyaterci akinina ugrayan eseri olan Eyfel Kulesi ona bakanlari buyuler","Deneme Uzun Isimli Biri")

        val gonderiler = ArrayList<GonderiModel>()
        gonderiler.add(x)
        gonderiler.add(y)
        gonderiler.add(z)

        gonderiListesi = gonderiler
        adapter.gonderiGuncelle(gonderiListesi)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun sliderDoldur(){
        sliderListesi.add(SlideModel("https://pbs.twimg.com/media/E2f1rgLWYAg-fOW?format=jpg&name=large"))
        sliderListesi.add(SlideModel("https://static.daktilo.com/sites/77/uploads/2019/07/11/large/kocaelinin-turistik-yerler-1511338290-3585-1562846155.png"))
        sliderListesi.add(SlideModel("https://scontent.fist2-4.fna.fbcdn.net/v/t1.6435-9/149011924_3252086314892718_5970936174401427651_n.jpg?_nc_cat=111&ccb=1-3&_nc_sid=973b4a&_nc_ohc=CssAj9YuDkUAX8d95k2&_nc_ht=scontent.fist2-4.fna&oh=46ce83c9d2a570809283a59de64e819e&oe=60EE3479"))

        binding.imageSlider.setImageList(sliderListesi,ScaleTypes.FIT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}