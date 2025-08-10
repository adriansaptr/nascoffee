package com.example.nascoffee3.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.nascoffee3.databinding.FragmentRegisterBinding

/**
 * Ini adalah file logika untuk layar Register.
 */
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // DIPERBARUI: Memberi aksi pada tombol kembali di halaman Register
        binding.ivBackRegister.setOnClickListener {
            // Perintah ini akan kembali ke layar sebelumnya (LoginFragment)
            findNavController().popBackStack()
        }

        // Kita bisa tambahkan aksi untuk tombol register nanti
        // binding.btnRegister.setOnClickListener { ... }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
