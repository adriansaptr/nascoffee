package com.example.nascoffee3.ui.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nascoffee3.R
import com.example.nascoffee3.data.local.entity.Coffee

// DIPERBARUI: Menambahkan parameter 'onItemClick' di constructor
class CoffeeAdapter(private val onItemClick: (Coffee) -> Unit) : ListAdapter<Coffee, CoffeeAdapter.CoffeeViewHolder>(CoffeeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coffee, parent, false)
        return CoffeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
        val coffee = getItem(position)
        // DIPERBARUI: Memberikan aksi klik ke ViewHolder
        holder.itemView.setOnClickListener {
            onItemClick(coffee)
        }
        holder.bind(coffee)
    }

    class CoffeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val coffeeName: TextView = itemView.findViewById(R.id.tv_coffee_name)
        private val coffeeImage: ImageView = itemView.findViewById(R.id.iv_coffee_image)
        private val context: Context = itemView.context

        fun bind(coffee: Coffee) {
            coffeeName.text = coffee.name

            val imageResId = context.resources.getIdentifier(
                coffee.imageUrl, "drawable", context.packageName
            )

            if (imageResId != 0) {
                coffeeImage.setImageResource(imageResId)
            }
        }
    }
}

class CoffeeDiffCallback : DiffUtil.ItemCallback<Coffee>() {
    override fun areItemsTheSame(oldItem: Coffee, newItem: Coffee): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Coffee, newItem: Coffee): Boolean {
        return oldItem == newItem
    }
}
