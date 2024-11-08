package com.example.app_didi_lanches.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.app_didi_lanches.R
import com.example.app_didi_lanches.databinding.FragmentNewCategoryBinding
import com.example.app_didi_lanches.helper.FirebaseHelper
import com.example.app_didi_lanches.model.Category
import com.example.app_didi_lanches.ui.adapters.ColorAdapter

class NewCategoryFragment : Fragment() {

    private var _binding: FragmentNewCategoryBinding? = null
    private val binding get() = _binding!!

    private var selectedColor: Int = 0

    private lateinit var category: Category
    private var newCategory: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentNewCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadPage()

        initClicks()
    }

    private fun loadPage() {
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

        /*if (!this.newCategory) {
            binding.categoryInput.text = category.name
        }*/
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
            validateData()
        }
    }

    private fun validateData() {
        val name = binding.categoryInput.text.toString().trim()

        if (name.isEmpty() && selectedColor == 0){
            Toast.makeText(requireContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
        } else {
            binding.progressBar.visibility = View.VISIBLE

            if (newCategory) this.category = Category()
            category.name = name
            category.color = selectedColor

            saveCategory()
        }
    }

    private fun saveCategory() {
        FirebaseHelper
            .getDatabase()
            .child("category")
            .child(FirebaseHelper.getIdUser().toString() ?: "")
            .child(category.id)
            .setValue(category)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (newCategory) {
                        binding.progressBar.visibility = View.INVISIBLE
                        findNavController().popBackStack()
                        Toast.makeText(
                            requireContext(),
                            "Categoria salva com sucesso.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        binding.progressBar.visibility = View.INVISIBLE
                        findNavController().popBackStack()
                        Toast.makeText(
                            requireContext(),
                            "Categoria atualizada com sucesso.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Log.d("DebugFB", "${task.exception}")
                    Toast.makeText(requireContext(), "Erro ao salvar a Categoria.", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                binding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(requireContext(), "Erro ao salvar a Categoria.", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}