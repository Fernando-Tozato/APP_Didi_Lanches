package com.example.app_didi_lanches.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.app_didi_lanches.R
import com.example.app_didi_lanches.databinding.FragmentNewCategoryBinding
import com.example.app_didi_lanches.ui.adapters.ColorAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlin.properties.Delegates

class NewCategoryFragment : Fragment() {

    private var _binding: FragmentNewCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    private var selectedColor by Delegates.notNull<Int>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentNewCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadColorSelector()

        auth = Firebase.auth

        initClicks()
    }

    private fun loadColorSelector() {
        val colors = listOf(
            ContextCompat.getColor(requireContext(), R.color.category01),
            ContextCompat.getColor(requireContext(), R.color.category02),
            ContextCompat.getColor(requireContext(), R.color.category03),
            ContextCompat.getColor(requireContext(), R.color.category04),
            ContextCompat.getColor(requireContext(), R.color.category05),
            ContextCompat.getColor(requireContext(), R.color.category06),
            ContextCompat.getColor(requireContext(), R.color.category07),
            ContextCompat.getColor(requireContext(), R.color.category08),
            ContextCompat.getColor(requireContext(), R.color.category09),
            ContextCompat.getColor(requireContext(), R.color.category10),
            ContextCompat.getColor(requireContext(), R.color.category11),
            ContextCompat.getColor(requireContext(), R.color.category12),
            ContextCompat.getColor(requireContext(), R.color.category13),
            ContextCompat.getColor(requireContext(), R.color.category14),
            ContextCompat.getColor(requireContext(), R.color.category15),
            ContextCompat.getColor(requireContext(), R.color.category16),
            ContextCompat.getColor(requireContext(), R.color.category17),
            ContextCompat.getColor(requireContext(), R.color.category18)
        )

        binding.colorRC.layoutManager = GridLayoutManager(requireContext(), 6)
        binding.colorRC.adapter = ColorAdapter(colors) { selectedColor ->
            this.selectedColor = selectedColor
        }
    }

    private fun initClicks() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnMenu.setOnClickListener {
            findNavController().navigate(R.id.action_newCategoryFragment_to_homeFragment)
        }

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnConfirm.setOnClickListener {
            createCategory()
        }
    }

    private fun createCategory() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}