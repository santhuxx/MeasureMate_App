package com.example.measuremate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.measuremate.databinding.MeasureLayoutBinding
import com.example.measuremate.fragments.HomeFragmentDirections
import com.example.measuremate.model.Measure

class MeasureAdapter : RecyclerView.Adapter<MeasureAdapter.MeasureViewHolder>(){

    class MeasureViewHolder(val itemBinding:MeasureLayoutBinding):RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Measure>(){
        override fun areItemsTheSame(oldItem: Measure, newItem: Measure): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.cusName == newItem.cusName &&
                    oldItem.cusMobile == newItem.cusMobile &&
                    oldItem.cloth == newItem.cloth &&
                    oldItem.orderedDate == newItem.orderedDate &&
                    oldItem.deliveryDate == newItem.deliveryDate &&
                    oldItem.measurements == newItem.measurements


        }

        override fun areContentsTheSame(oldItem: Measure, newItem: Measure): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeasureViewHolder {
        return MeasureViewHolder(
            MeasureLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MeasureViewHolder, position: Int) {
        val currentMeasure = differ.currentList[position]

        holder.itemBinding.cusName.text = currentMeasure.cusName
        holder.itemBinding.deliveryDate.text = currentMeasure.deliveryDate
        holder.itemBinding.cloth.text = currentMeasure.cloth

        holder.itemView.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToEditMeasureFragment(currentMeasure)


            it.findNavController().navigate(direction)

        }
    }
}