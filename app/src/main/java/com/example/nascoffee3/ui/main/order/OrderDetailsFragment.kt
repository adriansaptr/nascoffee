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

    private val detailsViewModel: OrderDetailsViewModel by viewModels()
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

        arguments?.getInt("coffeeId")?.let { detailsViewModel.start(it) }

        observeViewModel()
        setupClickListeners()
        setupChoiceListeners()
    }

    private fun observeViewModel() {
        detailsViewModel.coffee.observe(viewLifecycleOwner) { coffee ->
            coffee?.let {
                binding.tvCoffeeNameDetails.text = it.name
                val imageResId = resources.getIdentifier(it.imageUrl, "drawable", requireContext().packageName)
                if (imageResId != 0) binding.ivCoffeeImageDetails.setImageResource(imageResId)
                detailsViewModel.calculateTotalPrice()
            }
        }
        detailsViewModel.quantity.observe(viewLifecycleOwner) { quantity ->
            binding.tvQuantity.text = quantity.toString()
            detailsViewModel.calculateTotalPrice()
        }
        detailsViewModel.totalPrice.observe(viewLifecycleOwner) { price ->
            price?.let {
                val format = NumberFormat.getCurrencyInstance(Locale("en", "US"))
                binding.tvTotalAmountValue.text = format.format(it)
                binding.btnNextOrder.text = "Add to Cart - ${format.format(it)}"
            }
        }
    }

    private fun setupClickListeners() {
        binding.ivBackDetails.setOnClickListener { findNavController().popBackStack() }
        binding.btnAdd.setOnClickListener { detailsViewModel.quantity.value = (detailsViewModel.quantity.value ?: 1) + 1 }
        binding.btnSubtract.setOnClickListener {
            val currentQuantity = detailsViewModel.quantity.value ?: 1
            if (currentQuantity > 1) {
                detailsViewModel.quantity.value = currentQuantity - 1
            }
        }

        binding.btnAssemblage.setOnClickListener {
            val coffeeId = detailsViewModel.coffee.value?.id
            if (coffeeId != null) {
                val bundle = Bundle().apply { putInt("coffeeId", coffeeId) }
                findNavController().navigate(R.id.action_orderDetailsFragment_to_coffeeCustomizerFragment, bundle)
            }
        }

        binding.btnNextOrder.setOnClickListener {
            val currentCoffee = detailsViewModel.coffee.value
            if (currentCoffee != null) {
                val detailsString = "${detailsViewModel.volume.value}, ${detailsViewModel.ristrettoType.value}, ${detailsViewModel.locationType.value}"
                val newOrderItem = OrderItem(
                    id = currentCoffee.id,
                    name = currentCoffee.name,
                    details = detailsString,
                    price = currentCoffee.price,
                    quantity = detailsViewModel.quantity.value ?: 1,
                    imageUrl = currentCoffee.imageUrl
                )
                orderViewModel.addOrderItem(newOrderItem)
                Toast.makeText(context, "${currentCoffee.name} added to cart", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun setupChoiceListeners() {
        val typeChoices = mapOf(binding.choiceOne to "One", binding.choiceTwo to "Two")
        typeChoices.keys.forEach { choice ->
            choice.setOnClickListener {
                detailsViewModel.ristrettoType.value = typeChoices[it] ?: "One"
                updateSelection(typeChoices.keys.toList(), it)
            }
        }
        updateSelection(typeChoices.keys.toList(), binding.choiceOne)

        val locationChoices = mapOf(binding.choiceOnsite to "Onsite", binding.choiceTakeaway to "Takeaway")
        locationChoices.keys.forEach { choice ->
            choice.setOnClickListener {
                detailsViewModel.locationType.value = locationChoices[it] ?: "Onsite"
                updateSelection(locationChoices.keys.toList(), it)
            }
        }
        updateSelection(locationChoices.keys.toList(), binding.choiceOnsite)

        val volumeChoices = mapOf(binding.choiceVolumeSmall to "250 ml", binding.choiceVolumeMedium to "350 ml", binding.choiceVolumeLarge to "450 ml")
        volumeChoices.keys.forEach { choice ->
            choice.setOnClickListener {
                detailsViewModel.volume.value = volumeChoices[it] ?: "350 ml"
                updateSelection(volumeChoices.keys.toList(), it)
            }
        }
        updateSelection(volumeChoices.keys.toList(), binding.choiceVolumeMedium)
    }

    private fun updateSelection(choices: List<View>, selectedView: View) {
        choices.forEach { choice ->
            val isSelected = choice == selectedView
            choice.isSelected = isSelected
            updateChildViews(choice, isSelected)
        }
    }

    private fun updateChildViews(view: View, isSelected: Boolean) {
        val colorRes = if (isSelected) R.color.white else R.color.dark_blue
        val color = ContextCompat.getColor(requireContext(), colorRes)

        if (view is ViewGroup) {
            (0 until view.childCount).forEach { i ->
                val child = view.getChildAt(i)
                if (child is ImageView) child.setColorFilter(color)
                if (child is TextView) child.setTextColor(color)
            }
        } else {
            if (view is ImageView) view.setColorFilter(color)
            if (view is TextView) view.setTextColor(color)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
