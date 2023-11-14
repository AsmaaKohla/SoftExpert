package com.example.petfinder.presentation.petsList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petfinder.R
import com.example.petfinder.data.appData.PetData

class PetsAdapter(
    private var petsList: ArrayList<PetData>,
    private val listener: OnPetClickListener
) : RecyclerView.Adapter<PetsAdapter.PetViewHolder>() {

    fun clear() {
        petsList.clear()
        notifyDataSetChanged()
    }

    fun addItems(items: List<PetData>) {
        petsList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.pet_item, parent, false)
        return PetViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return petsList.size
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val petData = petsList[position]
        holder.nameTextView.text = petData.name
        holder.genderTextView.text = petData.gender
        holder.typeTextView.text = petData.type

        Glide.with(holder.petImageView.context)
            .load(petData.smallImage)
            .placeholder(R.drawable.pet_placeholder)
            .error(R.drawable.pet_placeholder)
            .fallback(R.drawable.pet_placeholder)
            .into(holder.petImageView)
    }

    inner class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val nameTextView: TextView = itemView.findViewById(R.id.pet_name_text)
        val genderTextView: TextView = itemView.findViewById(R.id.pet_gender_text)
        val typeTextView: TextView = itemView.findViewById(R.id.pet_type_text)
        val petImageView: ImageView = itemView.findViewById(R.id.pet_image_view)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val pet = petsList[position]
                listener.onPetClick(pet)
            }
        }
    }

    interface OnPetClickListener {
        fun onPetClick(pet: PetData)
    }
}