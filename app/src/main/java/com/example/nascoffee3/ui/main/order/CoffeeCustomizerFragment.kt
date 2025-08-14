package com.example.nascoffee3.ui.main.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.nascoffee3.R
import com.example.nascoffee3.data.model.OrderItem
import com.example.nascoffee3.databinding.FragmentCoffeeCustomizerBinding
import com.example.nascoffee3.databinding.ItemCustomizerRowIconsBinding

class CoffeeCustomizerFragment : Fragment() {

    private var _binding: FragmentCoffeeCustomizerBinding? = null
    private val binding get() = _binding!!

    private val customizerViewModel: CoffeeCustomizerViewModel by viewModels()
    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoffeeCustomizerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupResultListeners()
        setupCustomizerRows()
        observeViewModel()
    }

    private fun setupResultListeners() {
        setFragmentResultListener("additives_request") { _, bundle ->
            val selectedItems = bundle.getStringArrayList("selected_items")
            customizerViewModel.additives.value = selectedItems
        }
        setFragmentResultListener("coffee_sort_request") { _, bundle ->
            val selectedItem = bundle.getString("selected_item")
            customizerViewModel.coffeeSort.value = selectedItem
        }
    }

    private fun observeViewModel() {
        val roastingRowBinding = ItemCustomizerRowIconsBinding.bind(binding.rowRoasting.root)
        val grindingRowBinding = ItemCustomizerRowIconsBinding.bind(binding.rowGrinding.root)
        val iceRowBinding = ItemCustomizerRowIconsBinding.bind(binding.rowIce.root)

        customizerViewModel.roastingLevel.observe(viewLifecycleOwner) { level ->
            updateLevelSelection(listOf(roastingRowBinding.ivIcon1, roastingRowBinding.ivIcon2, roastingRowBinding.ivIcon3), level)
        }
        customizerViewModel.grindingSize.observe(viewLifecycleOwner) { size ->
            val selectedIcon = if (size == "Small") grindingRowBinding.ivIcon1 else grindingRowBinding.ivIcon2
            updateSingleSelection(listOf(grindingRowBinding.ivIcon1, grindingRowBinding.ivIcon2), selectedIcon)
        }
        customizerViewModel.iceLevel.observe(viewLifecycleOwner) { level ->
            updateLevelSelection(listOf(iceRowBinding.ivIcon1, iceRowBinding.ivIcon2, iceRowBinding.ivIcon3), level)
        }
        customizerViewModel.coffeeSort.observe(viewLifecycleOwner) { sort ->
            binding.rowCoffeeSort.tvRowValue.text = sort
        }
        customizerViewModel.additives.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) {
                binding.rowAdditives.tvRowValue.text = "Select"
            } else {
                binding.rowAdditives.tvRowValue.text = "${list.size} selected"
            }
        }
    }

    private fun setupCustomizerRows() {
        binding.ivBackCustomizer.setOnClickListener { findNavController().popBackStack() }

        binding.rowCoffeeSort.tvRowTitle.text = "Coffee sort"
        binding.rowCoffeeSort.root.setOnClickListener { findNavController().navigate(R.id.action_coffeeCustomizerFragment_to_selectCoffeeSortFragment) }

        val roastingRowBinding = ItemCustomizerRowIconsBinding.bind(binding.rowRoasting.root)
        roastingRowBinding.tvRowTitle.text = "Roasting"
        roastingRowBinding.ivIcon1.setImageResource(R.drawable.flame)
        roastingRowBinding.ivIcon2.setImageResource(R.drawable.flame)
        roastingRowBinding.ivIcon3.setImageResource(R.drawable.flame)
        roastingRowBinding.ivIcon3.visibility = View.VISIBLE
        roastingRowBinding.ivIcon1.setOnClickListener { customizerViewModel.setRoastingLevel(1) }
        roastingRowBinding.ivIcon2.setOnClickListener { customizerViewModel.setRoastingLevel(2) }
        roastingRowBinding.ivIcon3.setOnClickListener { customizerViewModel.setRoastingLevel(3) }

        val grindingRowBinding = ItemCustomizerRowIconsBinding.bind(binding.rowGrinding.root)
        grindingRowBinding.tvRowTitle.text = "Grinding"
        grindingRowBinding.ivIcon1.setImageResource(R.drawable.bean)
        grindingRowBinding.ivIcon2.setImageResource(R.drawable.bean)
        grindingRowBinding.ivIcon1.setOnClickListener { customizerViewModel.setGrindingSize("Small") }
        grindingRowBinding.ivIcon2.setOnClickListener { customizerViewModel.setGrindingSize("Large") }

        binding.rowMilk.tvRowTitle.text = "Milk"
        binding.rowMilk.tvRowValue.text = "Whole Milk"
        binding.rowMilk.root.setOnClickListener { Toast.makeText(context, "Milk Clicked", Toast.LENGTH_SHORT).show() }

        binding.rowSyrup.tvRowTitle.text = "Syrup"
        binding.rowSyrup.tvRowValue.text = "Vanilla"
        binding.rowSyrup.root.setOnClickListener { Toast.makeText(context, "Syrup Clicked", Toast.LENGTH_SHORT).show() }

        binding.rowAdditives.tvRowTitle.text = "Additives"
        binding.rowAdditives.tvRowValue.text = "2 selected"
        binding.rowAdditives.root.setOnClickListener { findNavController().navigate(R.id.action_coffeeCustomizerFragment_to_selectAdditivesFragment) }

        val iceRowBinding = ItemCustomizerRowIconsBinding.bind(binding.rowIce.root)
        iceRowBinding.tvRowTitle.text = "Ice"
        iceRowBinding.ivIcon1.setImageResource(R.drawable.ice)
        iceRowBinding.ivIcon2.setImageResource(R.drawable.ice)
        iceRowBinding.ivIcon3.setImageResource(R.drawable.ice)
        iceRowBinding.ivIcon3.visibility = View.VISIBLE
        iceRowBinding.ivIcon1.setOnClickListener { customizerViewModel.setIceLevel(1) }
        iceRowBinding.ivIcon2.setOnClickListener { customizerViewModel.setIceLevel(2) }
        iceRowBinding.ivIcon3.setOnClickListener { customizerViewModel.setIceLevel(3) }

        binding.btnNextCustomizer.setOnClickListener {
            val detailsString = "Roast: ${customizerViewModel.roastingLevel.value}, Grind: ${customizerViewModel.grindingSize.value}"

            val customizedOrderItem = OrderItem(
                id = System.currentTimeMillis().toInt(),
                name = "Customized Coffee",
                details = detailsString,
                price = 9.00,
                quantity = 1,
                imageUrl = "cappucino",
                roastingLevel = customizerViewModel.roastingLevel.value,
                grindingSize = customizerViewModel.grindingSize.value,
                iceLevel = customizerViewModel.iceLevel.value,
                coffeeSort = customizerViewModel.coffeeSort.value,
                milkType = customizerViewModel.milkType.value,
                syrupType = customizerViewModel.syrupType.value,
                additives = customizerViewModel.additives.value
            )

            orderViewModel.addOrderItem(customizedOrderItem)
            findNavController().navigate(R.id.action_coffeeCustomizerFragment_to_myOrderFragment)
        }
    }

    private fun updateLevelSelection(icons: List<ImageView>, level: Int) {
        icons.forEachIndexed { index, icon ->
            val isSelected = (index + 1) <= level
            icon.alpha = if (isSelected) 1.0f else 0.5f
            val colorRes = if (isSelected) R.color.dark_blue else R.color.light_gray
            icon.setColorFilter(ContextCompat.getColor(requireContext(), colorRes))
        }
    }

    private fun updateSingleSelection(icons: List<ImageView>, selectedIcon: ImageView) {
        icons.forEach { icon ->
            val isSelected = icon == selectedIcon
            icon.alpha = if (isSelected) 1.0f else 0.5f
            val colorRes = if (isSelected) R.color.dark_blue else R.color.light_gray
            icon.setColorFilter(ContextCompat.getColor(requireContext(), colorRes))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
