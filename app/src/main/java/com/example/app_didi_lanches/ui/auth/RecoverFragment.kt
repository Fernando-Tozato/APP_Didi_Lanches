package com.example.app_didi_lanches.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.app_didi_lanches.R
import com.example.app_didi_lanches.databinding.FragmentRecoverBinding
import com.example.app_didi_lanches.databinding.FragmentRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RecoverFragment : Fragment() {

    private var _binding: FragmentRecoverBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentRecoverBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        initClicks()
    }

    private fun initClicks() {
        binding.registerBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.emailInput.text.toString().trim()

        if (email.isNotEmpty()) {
            registerUser(email)
        } else {
            Toast.makeText(requireContext(), "Insira seu email.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerUser(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Email enviado com sucesso.", Toast.LENGTH_SHORT).show()
                }
                binding.progressBar.visibility = View.INVISIBLE
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}