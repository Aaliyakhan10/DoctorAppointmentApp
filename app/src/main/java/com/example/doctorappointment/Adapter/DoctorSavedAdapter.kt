package com.example.doctorappointment.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.doctorappointment.Activity.DetailActivity
import com.example.doctorappointment.Adapter.NearDoctorAdapter.Viewholder
import com.example.doctorappointment.databinding.RecyclerNdBinding
import com.example.doctorappointment.roomDb.DoctorsSaved
import kotlinx.coroutines.NonDisposableHandle.parent
class DoctorSavedAdapter : RecyclerView.Adapter<DoctorSavedAdapter.SavedcardViewholder>() {

    class SavedcardViewholder(val binding: RecyclerNdBinding) : RecyclerView.ViewHolder(binding.root)

    val diffUtil = object : DiffUtil.ItemCallback<DoctorsSaved>() {
        override fun areItemsTheSame(oldItem: DoctorsSaved, newItem: DoctorsSaved): Boolean {
            return oldItem.Id == newItem.Id
        }

        override fun areContentsTheSame(oldItem: DoctorsSaved, newItem: DoctorsSaved): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedcardViewholder {
        return SavedcardViewholder(RecyclerNdBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SavedcardViewholder, position: Int) {
        val items = differ.currentList[position]
        holder.binding.nameTv.text = items.Name
        holder.binding.speTv.text = items.Special
        holder.binding.costTv.text = items.Cost
        Glide.with(holder.itemView.context)
            .load(items.Picture)
            .apply(RequestOptions().centerCrop()) // Apply the transformation properly
            .into(holder.binding.imageView)

        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("Object", items)
            holder.itemView.context.startActivity(intent)
        }
    }
}
