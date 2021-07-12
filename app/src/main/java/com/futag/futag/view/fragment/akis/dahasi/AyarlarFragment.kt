package com.futag.futag.view.fragment.akis.dahasi

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.futag.futag.MainActivity
import com.futag.futag.R
import com.futag.futag.databinding.FragmentAyarlarBinding
import com.futag.futag.view.activity.AkisActivity

class AyarlarFragment : Fragment() {

    private var _binding: FragmentAyarlarBinding? = null
    private val binding get() = _binding!!
    internal lateinit var sharedPref: SharedPref

    private val CHANNEL_ID = "notification"
    private val notificationId = 101

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAyarlarBinding.inflate(inflater,container,false)
        val view = binding.root

        sharedPref = SharedPref(requireContext())

        if(sharedPref.loadNightModeState()){
            requireActivity().setTheme(R.style.Theme_FUTAGDark)
        } else {
            requireActivity().setTheme(R.style.Theme_FUTAG)
        }

        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.switchButtonKoyuMod.isChecked = false
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.switchButtonKoyuMod.isChecked = true
            }
        }

        binding.switchButtonBildirimler.isChecked = sharedPref.loadNotificationState()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createNotificationChannel()

        binding.switchButtonKoyuMod.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                sharedPref.setNightModeState(true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                restartApp()
            } else {
                sharedPref.setNightModeState(false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                restartApp()
            }
        }

        binding.switchButtonBildirimler.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                sharedPref.setNotificationModeState(true)
            } else {
                sharedPref.setNotificationModeState(false)
            }
        }

        binding.buttonNot.setOnClickListener {
            if(sharedPref.loadNotificationState()){
                sendNotification()
            } else {
                Toast.makeText(requireContext(),"Izin Ver",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun restartApp() {
        val intent = Intent(requireActivity(),MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*val mode = resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
        when (mode) {
            Configuration.UI_MODE_NIGHT_YES -> {}
            Configuration.UI_MODE_NIGHT_NO -> {}
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {}
        }*/

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Bildirim Uyarisi"
            val descriptionText = "Futag Bildirimi"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = requireActivity().getSystemService(
                Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(){
        val intent = Intent(requireActivity(), MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(requireContext()).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(101,PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val builder = NotificationCompat.Builder(requireContext(),CHANNEL_ID)
            .setSmallIcon(R.drawable.futag_icon)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setContentTitle("Yarisma Basliyor")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Yapilacak olan buyuk FUTAG yarismasi birazdan baslayacak.")
            )

        with(NotificationManagerCompat.from(requireContext())){
            notify(notificationId, builder.build())
        }
    }

}