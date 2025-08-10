package com.example.nascoffee3.ui.main.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.nascoffee3.R
import com.example.nascoffee3.data.model.OrderItem
import com.example.nascoffee3.databinding.FragmentOrderDetailsBinding
import java.text.NumberFormat
import java.util.Locale

class OrderDetailsFragment : Fragment() {

    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!

    // ViewModel untuk halaman ini (mengelola detail satu kopi)
    private val detailsViewModel: OrderDetailsViewModel by viewModels()
    // ViewModel untuk keranjang belanja (dibagikan ke seluruh aplikasi)
    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt("coffeeId")?.let { coffeeId ->
            detailsViewModel.start(coffeeId)
        }

        observeViewModel()
        setupClickListeners()
        setupChoiceListeners()
    }

    private fun observeViewModel() {
        detailsViewModel.coffee.observe(viewLifecycleOwner) { coffee ->
            coffee?.let {
                binding.tvCoffeeNameDetails.text = it.name
                val imageResId = resources.getIdentifier(
                    it.imageUrl, "drawable", requireContext().packageName
                )
                if (imageResId != 0) {
                    binding.ivCoffeeImageDetails.setImageResource(imageResId)
                }
                detailsViewModel.calculateTotalPrice()
            }
        }
        detailsViewModel.quantity.observe(viewLifecycleOwner) { quantity ->
            binding.tvQuantity.text = quantity.toString()
        }
        detailsViewModel.totalPrice.observe(viewLifecycleOwner) { price ->
            val format = NumberFormat.getCurrencyInstance(Locale("en", "US"))
            binding.tvTotalAmountValue.text = format.format(price)
            binding.btnNextOrder.text = "Add to Cart - ${format.format(price)}"
        }
    }

    private fun setupClickListeners() {
        binding.ivBackDetails.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnAdd.setOnClickListener {
            detailsViewModel.increaseQuantity()
        }
        binding.btnSubtract.setOnClickListener {
            detailsViewModel.decreaseQuantity()
        }
        binding.btnAssemblage.setOnClickListener {
            findNavController().navigate(R.id.action_orderDetailsFragment_to_coffeeCustomizerFragment)
        }

        // Logika tombol Add to Cart
        binding.btnNextOrder.setOnClickListener {
            val currentCoffee = detailsViewModel.coffee.value
            val currentQuantity = detailsViewModel.quantity.value ?: 1

            if (currentCoffee != null) {
                val newOrderItem = OrderItem(
                    id = currentCoffee.id,
                    name = currentCoffee.name,
                    details = "350 ml", // Nanti bisa kita buat dinamis
                    price = currentCoffee.price,
                    quantity = currentQuantity,
                    imageUrl = currentCoffee.imageUrl
                )
                orderViewModel.addOrderItem(newOrderItem)
                Toast.makeText(context, "${currentCoffee.name} added to cart", Toast.LENGTH_SHORT).show()
                // Kembali ke menu utama setelah menambahkan ke keranjang
                findNavController().popBackStack()
            }
        }
    }

    private fun setupChoiceListeners() {
        val typeChoices = listOf(binding.choiceOne, binding.choiceTwo)
        typeChoices.forEach { choice ->
            choice.setOnClickListener { updateSelection(typeChoices, it) }
        }
        updateSelection(typeChoices, binding.choiceOne)

        val locationChoices = listOf(binding.choiceOnsite, binding.choiceTakeaway)
        locationChoices.forEach { choice ->
            choice.setOnClickListener { updateSelection(locationChoices, it) }
        }
        updateSelection(locationChoices, binding.choiceOnsite)

        val volumeChoices = listOf(binding.choiceVolumeSmall, binding.choiceVolumeMedium, binding.choiceVolumeLarge)
        volumeChoices.forEach { choice ->
            choice.setOnClickListener { updateSelection(volumeChoices, it) }
        }
        updateSelection(volumeChoices, binding.choiceVolumeMedium)
    }

    private fun updateSelection(choices: List<View>, selectedView: View) {
        choices.forEach { choice ->
            val isSelected = choice == selectedView
            choice.isSelected = isSelected

            when (choice) {
                is TextView -> {
                    val color = if (isSelected) R.color.white else R.color.dark_blue
                    choice.setTextColor(ContextCompat.getColor(requireContext(), color))
                }
                is ImageView -> {
                    val color = if (isSelected) R.color.white else R.color.dark_blue
                    choice.setColorFilter(ContextCompat.getColor(requireContext(), color))
                }
                is ViewGroup -> { // Untuk pilihan Volume yang merupakan LinearLayout
                    (0 until choice.childCount).forEach { i ->
                        val child = choice.getChildAt(i)
                        val colorRes = if (isSelected) R.color.white else R.color.dark_blue
                        val color = ContextCompat.getColor(requireContext(), colorRes)
                        if (child is ImageView) {
                            child.setColorFilter(color)
                        }
                        if (child is TextView) {
                            child.setTextColor(color)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
