package com.example.doctorappointment.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.doctorappointment.Activity.DetailActivity
import com.example.doctorappointment.Model.DoctorsModel
import com.example.doctorappointment.databinding.RecyclerNdBinding
import com.google.android.gms.dynamite.DynamiteModule.load


class NearDoctorAdapter(val items:MutableList<DoctorsModel>): RecyclerView.Adapter<NearDoctorAdapter.Viewholder> (){


    class Viewholder(val binding:RecyclerNdBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Viewholder {

        return Viewholder(RecyclerNdBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.binding.nameTv.text=items[position].Name
        holder.binding.speTv.text=items[position].Special
        holder.binding.costTv.text=items[position].Cost
        Glide.with(holder.itemView.context)
            .load(items[position].Picture)
            .apply(RequestOptions().centerCrop()) // Apply the transformation properly
            .into(holder.binding.imageView)
        holder.binding.root.setOnClickListener {

                val intent = Intent(holder.itemView.context, DetailActivity::class.java)
                intent.putExtra("Object", items[position])
                holder.itemView.context?.startActivity(intent)


        }




    }

    override fun getItemCount(): Int {
       return items.size
    }
}