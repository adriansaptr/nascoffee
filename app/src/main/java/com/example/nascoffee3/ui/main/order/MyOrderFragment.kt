package com.example.nascoffee3.ui.main.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nascoffee3.data.model.OrderItem
import com.example.nascoffee3.databinding.FragmentMyOrderBinding
import java.text.NumberFormat
import java.util.Locale

class MyOrderFragment : Fragment() {

    private var _binding: FragmentMyOrderBinding? = null
    private val binding get() = _binding!!

    private val orderViewModel: OrderViewModel by activityViewModels()
    private lateinit var orderAdapter: MyOrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.ivBackMyOrder.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRecyclerView() {
        // DIPERBARUI: Saat membuat adapter, kita berikan perintah onIncrease dan onDecrease
        orderAdapter = MyOrderAdapter(
            orderItems = emptyList(),
            onIncrease = { item -> orderViewModel.increaseItemQuantity(item) },
            onDecrease = { item -> orderViewModel.decreaseItemQuantity(item) }
        )
        binding.rvOrderList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = orderAdapter
        }
    }

    private fun observeViewModel() {
        // Mengamati perubahan pada daftar pesanan dari ViewModel
        orderViewModel.orderItems.observe(viewLifecycleOwner) { orderList ->
            // Memperbarui adapter dengan daftar baru dan menghitung ulang total
            orderAdapter.updateItems(orderList)
            updateSummary(orderList)
        }
    }

    private fun updateSummary(orderList: List<OrderItem>) {
        val subtotal = orderList.sumOf { it.price * it.quantity }
        val deliveryFee = if (orderList.isNotEmpty()) 1.00 else 0.00
        val total = subtotal + deliveryFee

        val format = NumberFormat.getCurrencyInstance(Locale("en", "US"))

        binding.tvSubtotalValue.text = format.format(subtotal)
        binding.tvDeliveryValue.text = format.format(deliveryFee)
        binding.tvTotalValue.text = format.format(total)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
