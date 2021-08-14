package com.futag.futag.adapter

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
import com.futag.futag.model.DahasiItemModel

class DahasiRecyclerAdapter(val parentFragment: Fragment, val context: Context, val itemList: ArrayList<DahasiItemModel>)
    : RecyclerView.Adapter<DahasiRecyclerAdapter.DahasiViewHolder>() {

    class DahasiViewHolder(val itemBinding: MoreoverRecyclerRowBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DahasiViewHolder {
        val binding = MoreoverRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DahasiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DahasiViewHolder, position: Int) {
        holder.itemBinding.imageViewCircleImage.setImageDrawable(itemList[position].resim)
        holder.itemBinding.textViewTitle.text = itemList[position].baslik
        holder.itemBinding.cardView.setOnClickListener {
            sayfaDegis(itemList[position].baslik)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    private fun sayfaDegis(baslik: String){
        when(baslik){
            context.getString(R.string.our_units) -> {
                parentFragment.findNavController().navigate(R.id.action_dahaFragment_to_birimlerimizF)
            }
            context.getString(R.string.rate_us) -> {
                Toast.makeText(context,R.string.very_soon,Toast.LENGTH_SHORT).show()
            }
            context.getString(R.string.notification) -> {
                parentFragment.findNavController().navigate(R.id.action_dahaFragment_to_bildirimlerF)
            }
            context.getString(R.string.feedback) -> {
                geriBildirimMail()
            }
            context.getString(R.string.about_us) -> {
                parentFragment.findNavController().navigate(R.id.action_dahaFragment_to_hakkimizdaF)
            }
            context.getString(R.string.settings) -> {
                parentFragment.findNavController().navigate(R.id.action_dahaFragment_to_ayarlarFragment)
            }
            else -> Toast.makeText(context,R.string.error_message,Toast.LENGTH_SHORT).show()
        }
    }

    private fun geriBildirimMail(){
        val email = "futag@firat.edu.tr"
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
//      putExtra(Intent.EXTRA_EMAIL,email)
        }
//      Toast.makeText(context,R.string.cok_yakinda,Toast.LENGTH_SHORT).show()
        context.startActivity(intent)
    }

}