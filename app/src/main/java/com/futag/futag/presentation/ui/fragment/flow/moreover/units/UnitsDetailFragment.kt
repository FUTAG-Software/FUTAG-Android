package com.futag.futag.presentation.ui.fragment.flow.moreover.units

import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.futag.futag.databinding.FragmentUnitDetailBinding
import com.futag.futag.util.Units.Companion.getUnitDetail

class UnitsDetailFragment : Fragment() {

    private var _binding: FragmentUnitDetailBinding? = null
    private val binding get() = _binding!!
    private val args: UnitsDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUnitDetailBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val unit = getUnitDetail(args.unitId)
        binding.apply {
            imageView.setImageResource(unit.unitImage)
            textViewUnitName.text = getString(unit.unitName)
            textViewUnitDescription.text = getString(unit.unitDescription)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            binding.textViewUnitDescription.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}