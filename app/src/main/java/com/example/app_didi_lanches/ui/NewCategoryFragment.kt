package com.example.app_didi_lanches.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.app_didi_lanches.R
import com.example.app_didi_lanches.databinding.FragmentNewCategoryBinding
import com.example.app_didi_lanches.ui.adapters.ColorAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class NewCategoryFragment : Fragment() {

    private var _binding: FragmentNewCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentNewCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val colors = listOf(
            R.color.category01, R.color.category02,R.color.category03,R.color.category04,
            R.color.category05, R.color.category06,R.color.category07,R.color.category08,
            R.color.category09, R.color.category10,R.color.category11,R.color.category12,
            R.color.category13, R.color.category14,R.color.category15,R.color.category16,
            R.color.category17, R.color.category18
        )

        binding.colorRC.layoutManager = GridLayoutManager(context, 6)
        binding.colorRC.adapter = ColorAdapter(colors) { selectedColor ->
            val selectedColor = selectedColor
        }

        auth = Firebase.auth

        initClicks()
    }

    private fun initClicks() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}