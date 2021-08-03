package com.futag.futag.view.fragment.akis.ev

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.futag.futag.R
import com.futag.futag.databinding.FragmentWebSitesiBinding

class WebSitesiFragment : Fragment() {

    private var _binding: FragmentWebSitesiBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<WebSitesiFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebSitesiBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webViewSetup()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetup(){
        binding.webView.webViewClient = WebViewClient()
        binding.webView.apply {
            val url = args.siteLinki
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