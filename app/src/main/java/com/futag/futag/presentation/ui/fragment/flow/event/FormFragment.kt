package com.futag.futag.presentation.ui.fragment.flow.event

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.futag.futag.databinding.FragmentFormBinding

class FormFragment : Fragment() {

    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<FormFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentEvent = args.formLink
        webViewSetup(currentEvent)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetup(url: String){
        binding.webView.webViewClient = WebViewClient()
        binding.webView.apply {
            loadUrl(url)
            settings.javaScriptEnabled = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                settings.safeBrowsingEnabled = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}