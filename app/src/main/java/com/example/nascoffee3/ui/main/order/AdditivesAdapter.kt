package com.example.nascoffee3.ui.main.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nascoffee3.R
import com.example.nascoffee3.data.model.Additive

class AdditivesAdapter(private val additives: List<Additive>) :
    RecyclerView.Adapter<AdditivesAdapter.AdditiveViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdditiveViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_additive, parent, false)
        return AdditiveViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdditiveViewHolder, position: Int) {
        holder.bind(additives[position])
    }

    override fun getItemCount(): Int = additives.size

    inner class AdditiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tv_additive_name)
        private val checkmarkImageView: ImageView = itemView.findViewById(R.id.iv_checkmark)

        init {
            itemView.setOnClickListener {
                // Pastikan posisi valid
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val additive = additives[adapterPosition]
                    additive.isSelected = !additive.isSelected // Toggle status terpilih
                    notifyItemChanged(adapterPosition) // Perbarui tampilan item ini
                }
            }
        }

        fun bind(additive: Additive) {
            nameTextView.text = additive.name
            checkmarkImageView.visibility = if (additive.isSelected) View.VISIBLE else View.INVISIBLE
        }
    }
}
