package com.example.nascoffee3.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nascoffee3.R
import com.example.nascoffee3.data.local.entity.Coffee
import com.example.nascoffee3.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var coffeeAdapter: CoffeeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        coffeeAdapter = CoffeeAdapter { coffee ->
            onCoffeeItemClicked(coffee)
        }
        binding.rvCoffeeList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = coffeeAdapter
        }
    }

    private fun onCoffeeItemClicked(coffee: Coffee) {
        val bundle = Bundle().apply {
            putInt("coffeeId", coffee.id)
        }
        findNavController().navigate(R.id.action_homeFragment_to_orderDetailsFragment, bundle)
    }

    private fun observeViewModel() {
        homeViewModel.allCoffee.observe(viewLifecycleOwner) { coffeeList ->
            coffeeAdapter.submitList(coffeeList)
        }
    }

    private fun setupClickListeners() {
        binding.ivCart.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_myOrderFragment)
        }
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> true
                R.id.navigation_gift -> {
                    Toast.makeText(context, "Gift clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.navigation_receipt -> {
                    Toast.makeText(context, "Receipt clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
