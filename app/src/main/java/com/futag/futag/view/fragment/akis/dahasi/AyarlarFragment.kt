package com.futag.futag.view.fragment.akis.dahasi

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.futag.futag.databinding.FragmentSettingsBinding
import com.futag.futag.util.SharedPref
import java.util.*

class AyarlarFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    internal lateinit var sharedPref: SharedPref

//    private val CHANNEL_ID = "notification"
//    private val notificationId = 101

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

        when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.switchButtonDarkTheme.isChecked = false
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.switchButtonDarkTheme.isChecked = true
            }
        }

        val locate = Locale.getDefault().language
        if(locate.equals("en")){
            sharedPref.setLanguageState(true)
        } else {
            sharedPref.setLanguageState(false)
        }

        binding.switchButtonChooseLanguage.isChecked = sharedPref.loadOnLanguageState()

        binding.switchButtonNotification.isChecked = sharedPref.loadNotificationState()

        // createNotificationChannel()
        binding.switchButtonNotification.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sharedPref.setNotificationModeState(true)
            } else {
                sharedPref.setNotificationModeState(false)
            }
        }

        /*binding.switchButtonKoyuMod.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sharedPref.setNightModeState(true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                restartApp()
            } else {
                sharedPref.setNightModeState(false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                restartApp()
            }
        }

        binding.switchButtonDilSecimi.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                sharedPref.setLanguageState(true)
                setLocale("en")
                restartApp()
            } else {
                sharedPref.setLanguageState(false)
                setLocale("tr")
                restartApp()
            }
        }*/
    }

    /*private fun restartApp() {
        val intent = Intent(requireActivity(),MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

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

    private fun setLocale(state: String){
        val locale = Locale(state)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLocale(locale)
        res.updateConfiguration(conf, dm)
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}