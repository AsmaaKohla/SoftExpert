package com.example.petfinder.presentation.petsList

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petfinder.R

class PetTypeAdapter(
    private var petTypeList: List<String>,
    private val listener: OnPetTypeClickListener
) : RecyclerView.Adapter<PetTypeAdapter.PetTypeViewHolder>() {

    var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetTypeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.pet_type_item, parent, false)
        return PetTypeViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return petTypeList.size
    }

    override fun onBindViewHolder(holder: PetTypeViewHolder, position: Int) {
        holder.typeTextView.text = petTypeList[position]

        if (selectedPosition == position) {
            holder.typeTextView.setBackgroundColor(Color.parseColor("#FFDDDDDD"))
        } else {
            holder.typeTextView.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
        }
    }

    inner class PetTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val typeTextView: TextView = itemView.findViewById(R.id.pet_type_text)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val oldSelectedPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(position)
                notifyItemChanged(oldSelectedPosition)
                
                val type = petTypeList[position]
                listener.onPetTypeClick(type)
            }
        }
    }

    interface OnPetTypeClickListener {
        fun onPetTypeClick(type: String)
    }
}
