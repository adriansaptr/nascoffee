package com.example.nascoffee3.ui.auth

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.nascoffee3.R
import com.example.nascoffee3.databinding.FragmentStartupBinding

class StartupFragment : Fragment() {

    private var _binding: FragmentStartupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menunggu selama 2.5 detik (2500 milidetik)
        Handler(Looper.getMainLooper()).postDelayed({
            // Setelah waktu tunggu selesai, pindah ke HomeFragment
            findNavController().navigate(R.id.action_startupFragment_to_homeFragment)
        }, 2500)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
