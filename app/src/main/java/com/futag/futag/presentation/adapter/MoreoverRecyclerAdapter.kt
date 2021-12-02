package com.futag.futag.presentation.adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.futag.futag.R
import com.futag.futag.databinding.MoreoverRecyclerRowBinding
import com.futag.futag.model.MoreoverItemModel

class MoreoverRecyclerAdapter(
    val parentFragment: Fragment,
    val context: Context,
    val itemList: ArrayList<MoreoverItemModel>
) : RecyclerView.Adapter<MoreoverRecyclerAdapter.MoreOverViewHolder>() {

    class MoreOverViewHolder(val itemBinding: MoreoverRecyclerRowBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreOverViewHolder {
        val binding =
            MoreoverRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoreOverViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoreOverViewHolder, position: Int) {
        holder.itemBinding.imageViewCircleImage.setImageDrawable(itemList[position].image)
        holder.itemBinding.textViewTitle.text = itemList[position].title
        holder.itemBinding.cardView.setOnClickListener {
            changePage(itemList[position].title)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    private fun changePage(title: String) {
        when (title) {
            context.getString(R.string.our_units) -> {
                parentFragment.findNavController()
                    .navigate(R.id.action_dahaFragment_to_birimlerimizF)
            }
            context.getString(R.string.rate_us) -> {
                val packageName = context.packageName
                try {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=$packageName")
                        )
                    )
                } catch (e: ActivityNotFoundException) {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                        )
                    )
                }
            }
            context.getString(R.string.notification) -> {
                parentFragment.findNavController()
                    .navigate(R.id.action_dahaFragment_to_bildirimlerF)
            }
            context.getString(R.string.feedback) -> {
                feedbackEmail()
            }
            context.getString(R.string.about_us) -> {
                parentFragment.findNavController().navigate(R.id.action_dahaFragment_to_hakkimizdaF)
            }
            context.getString(R.string.settings) -> {
                parentFragment.findNavController()
                    .navigate(R.id.action_dahaFragment_to_ayarlarFragment)
            }
            else -> Toast.makeText(context, R.string.error_message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun feedbackEmail() {
        val email = "futag@firat.edu.tr"
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
//      putExtra(Intent.EXTRA_EMAIL,email)
        }
//      Toast.makeText(context,R.string.cok_yakinda,Toast.LENGTH_SHORT).show()
        context.startActivity(intent)
    }

}