package com.example.nascoffee3.ui.main.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nascoffee3.R
import com.example.nascoffee3.data.model.CoffeeSort

class CoffeeSortAdapter(
    private val sorts: List<CoffeeSort>,
    private val onItemClick: (CoffeeSort) -> Unit
) : RecyclerView.Adapter<CoffeeSortAdapter.SortViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coffee_sort, parent, false)
        return SortViewHolder(view)
    }

    override fun onBindViewHolder(holder: SortViewHolder, position: Int) {
        val sort = sorts[position]
        holder.bind(sort)
        holder.itemView.setOnClickListener {
            onItemClick(sort)
        }
    }

    override fun getItemCount(): Int = sorts.size

    class SortViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tv_sort_name)
        private val checkmarkImageView: ImageView = itemView.findViewById(R.id.iv_checkmark_sort)

        fun bind(sort: CoffeeSort) {
            nameTextView.text = sort.name
            checkmarkImageView.visibility = if (sort.isSelected) View.VISIBLE else View.INVISIBLE
        }
    }
}
