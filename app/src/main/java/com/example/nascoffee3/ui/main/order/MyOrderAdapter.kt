package com.example.nascoffee3.ui.main.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nascoffee3.R
import com.example.nascoffee3.data.model.OrderItem
import java.text.NumberFormat
import java.util.Locale

class MyOrderAdapter(
    private var orderItems: List<OrderItem>,
    private val onIncrease: (OrderItem) -> Unit,
    private val onDecrease: (OrderItem) -> Unit
) : RecyclerView.Adapter<MyOrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = orderItems[position]
        holder.bind(item)
        holder.btnAdd.setOnClickListener { onIncrease(item) }
        holder.btnSubtract.setOnClickListener { onDecrease(item) }
    }

    override fun getItemCount(): Int = orderItems.size

    fun updateItems(newItems: List<OrderItem>) {
        orderItems = newItems
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tv_order_coffee_name)
        private val details: TextView = itemView.findViewById(R.id.tv_order_coffee_details)
        private val price: TextView = itemView.findViewById(R.id.tv_order_price)
        private val quantity: TextView = itemView.findViewById(R.id.tv_order_quantity)
        private val image: ImageView = itemView.findViewById(R.id.iv_order_coffee_image)
        val btnAdd: ImageButton = itemView.findViewById(R.id.btn_order_add)
        val btnSubtract: ImageButton = itemView.findViewById(R.id.btn_order_subtract)

        fun bind(item: OrderItem) {
            name.text = item.name
            details.text = item.details
            quantity.text = item.quantity.toString()

            val format = NumberFormat.getCurrencyInstance(Locale("en", "US"))
            price.text = format.format(item.price * item.quantity)

            val imageResId = itemView.context.resources.getIdentifier(
                item.imageUrl, "drawable", itemView.context.packageName
            )
            if (imageResId != 0) {
                image.setImageResource(imageResId)
            }
        }
    }
}
