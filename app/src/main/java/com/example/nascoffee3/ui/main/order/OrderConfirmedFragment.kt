package com.example.nascoffee3.ui.main.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.nascoffee3.R
import com.example.nascoffee3.databinding.FragmentOrderConfirmedBinding
import com.example.nascoffee3.ui.main.user.UserViewModel

class OrderConfirmedFragment : Fragment() {

    private var _binding: FragmentOrderConfirmedBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderConfirmedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.userName.observe(viewLifecycleOwner) { name ->
            binding.tvSuccessSubtitle.text = "$name, your order has been successfully placed."
        }

        binding.ivBackConfirmed.setOnClickListener {
            findNavController().navigate(R.id.action_orderConfirmedFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}