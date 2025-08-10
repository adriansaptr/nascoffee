package com.example.nascoffee3.ui.main.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nascoffee3.data.model.CoffeeSort
import com.example.nascoffee3.databinding.FragmentSelectCoffeeSortBinding

class SelectCoffeeSortFragment : Fragment() {

    private var _binding: FragmentSelectCoffeeSortBinding? = null
    private val binding get() = _binding!!

    private val coffeeSorts = mutableListOf(
        CoffeeSort("Arabica", true),
        CoffeeSort("Robusta")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectCoffeeSortBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CoffeeSortAdapter(coffeeSorts) { selectedSort ->
            setFragmentResult("coffee_sort_request", bundleOf("selected_item" to selectedSort.name))
            findNavController().popBackStack()
        }

        binding.rvSortList.layoutManager = LinearLayoutManager(context)
        binding.rvSortList.adapter = adapter

        binding.ivBackSort.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}