package com.example.nascoffee3.ui.main.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.nascoffee3.R
import com.example.nascoffee3.databinding.FragmentCoffeeCustomizerBinding
import com.example.nascoffee3.databinding.ItemCustomizerRowBinding
import com.example.nascoffee3.databinding.ItemCustomizerRowIconsBinding
import com.example.nascoffee3.databinding.ItemCustomizerSliderBinding

class CoffeeCustomizerFragment : Fragment() {

    private var _binding: FragmentCoffeeCustomizerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoffeeCustomizerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mendengarkan hasil dari halaman Additives
        setFragmentResultListener("additives_request") { _, bundle ->
            val selectedItems = bundle.getStringArrayList("selected_items")
            val additivesRow = ItemCustomizerRowBinding.bind(binding.rowAdditives.root)
            if (selectedItems.isNullOrEmpty()) {
                setupTextRow(additivesRow, "Additives", "Select")
            } else {
                setupTextRow(additivesRow, "Additives", "${selectedItems.size} selected")
            }
        }

        setupClickListeners()
        setupCustomizerRows()
    }

    private fun setupClickListeners() {
        binding.ivBackCustomizer.setOnClickListener {
            findNavController().popBackStack()
        }

        // DIPERBARUI: Memberi aksi pada tombol Next
        binding.btnNextCustomizer.setOnClickListener {
            findNavController().navigate(R.id.action_coffeeCustomizerFragment_to_myOrderFragment)
        }
    }

    private fun setupCustomizerRows() {
        // Mengatur baris slider
        ItemCustomizerSliderBinding.bind(binding.rowCoffeeType.root)

        // Mengatur baris-baris dengan teks dan panah (dengan data dummy)
        setupTextRow(ItemCustomizerRowBinding.bind(binding.rowCoffeeSort.root), "Coffee sort", "Arabica")
        setupTextRow(ItemCustomizerRowBinding.bind(binding.rowMilk.root), "Milk", "Whole Milk")
        setupTextRow(ItemCustomizerRowBinding.bind(binding.rowSyrup.root), "Syrup", "Vanilla")

        // Mengatur baris Additives agar bisa di-klik
        val additivesRow = ItemCustomizerRowBinding.bind(binding.rowAdditives.root)
        setupTextRow(additivesRow, "Additives", "2 selected") // Teks awal
        additivesRow.root.setOnClickListener {
            findNavController().navigate(R.id.action_coffeeCustomizerFragment_to_selectAdditivesFragment)
        }

        // Mengatur baris-baris dengan ikon interaktif
        setupIconRow(ItemCustomizerRowIconsBinding.bind(binding.rowRoasting.root), "Roasting", listOf(R.drawable.flame, R.drawable.flame, R.drawable.flame))
        setupIconRow(ItemCustomizerRowIconsBinding.bind(binding.rowGrinding.root), "Grinding", listOf(R.drawable.bean, R.drawable.bean), isGrinding = true)
        setupIconRow(ItemCustomizerRowIconsBinding.bind(binding.rowIce.root), "Ice", listOf(R.drawable.ice, R.drawable.ice, R.drawable.ice))
    }

    private fun setupTextRow(rowBinding: ItemCustomizerRowBinding, title: String, value: String = "Select") {
        rowBinding.tvRowTitle.text = title
        rowBinding.tvRowValue.text = value
        // Menambahkan Toast untuk baris yang belum punya tujuan
        if (title != "Additives") {
            rowBinding.root.setOnClickListener {
                Toast.makeText(context, "$title clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupIconRow(rowBinding: ItemCustomizerRowIconsBinding, title: String, iconResIds: List<Int>, isGrinding: Boolean = false) {
        rowBinding.tvRowTitle.text = title
        rowBinding.iconGroup.removeAllViews() // Hapus ikon lama jika ada

        val icons = mutableListOf<ImageView>()
        iconResIds.forEachIndexed { index, resId ->
            val icon = createIcon(resId, isGrinding, index)
            rowBinding.iconGroup.addView(icon)
            icons.add(icon)
        }

        icons.forEach { icon ->
            icon.setOnClickListener {
                if (title == "Roasting" || title == "Ice") {
                    updateLevelSelection(icons, it as ImageView)
                } else {
                    updateSingleSelection(icons, it as ImageView)
                }
            }
        }

        // Atur pilihan default
        if (title == "Roasting") updateLevelSelection(icons, icons[2]) // Level 3
        if (title == "Grinding") updateSingleSelection(icons, icons[1]) // Large
        if (title == "Ice") updateLevelSelection(icons, null, true) // Default tidak ada es
    }

    private fun createIcon(resId: Int, isGrinding: Boolean, index: Int): ImageView {
        val imageView = ImageView(requireContext())
        val size = if (isGrinding) if (index == 0) 20 else 28 else 24
        val sizeInDp = (size * resources.displayMetrics.density).toInt()
        val layoutParams = LinearLayout.LayoutParams(sizeInDp, sizeInDp)
        if (index > 0) {
            layoutParams.marginStart = (8 * resources.displayMetrics.density).toInt()
        }
        imageView.layoutParams = layoutParams
        imageView.setImageResource(resId)
        return imageView
    }

    private fun updateLevelSelection(icons: List<ImageView>, selectedIcon: ImageView?, deselectAll: Boolean = false) {
        val selectedIndex = if (deselectAll || selectedIcon == null) -1 else icons.indexOf(selectedIcon)
        icons.forEachIndexed { index, icon ->
            val isSelected = index <= selectedIndex
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
