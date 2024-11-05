package com.example.app_didi_lanches.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.app_didi_lanches.R
import com.example.app_didi_lanches.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        initClicks()
    }

    private fun initClicks() {
        binding.btnInventory.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_inventoryFragment)
        }

        binding.btnViewCategories.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_viewCategoryFragment)
        }

        binding.btnNewProducts.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_newProductFragment)
        }

        binding.btnNewCategory.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_newCategoryFragment)
        }

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_homeFragment_to_authentication)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}