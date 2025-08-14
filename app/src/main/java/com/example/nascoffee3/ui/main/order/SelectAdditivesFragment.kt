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
import com.example.nascoffee3.data.model.Additive
import com.example.nascoffee3.databinding.FragmentSelectAdditivesBinding

class SelectAdditivesFragment : Fragment() {

    private var _binding: FragmentSelectAdditivesBinding? = null
    private val binding get() = _binding!!

    private val additivesList = mutableListOf(
        Additive("Ceylon cinnamon", isSelected = true),
        Additive("Grated chocolate"),
        Additive("Liquid chocolate"),
        Additive("Marshmallow", isSelected = true),
        Additive("Whipped cream"),
        Additive("Cream"),
        Additive("Nutmeg"),
        Additive("Ice cream")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectAdditivesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.ivBackAdditives.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnConfirmAdditives.setOnClickListener {
            val selectedAdditives = additivesList.filter { it.isSelected }.map { it.name }
            setFragmentResult("additives_request", bundleOf("selected_items" to selectedAdditives))
            findNavController().popBackStack()
        }
    }

    private fun setupRecyclerView() {
        val additivesAdapter = AdditivesAdapter(additivesList)
        binding.rvAdditivesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = additivesAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
