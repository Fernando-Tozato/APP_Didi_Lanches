package com.example.app_didi_lanches.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    private var selectedColor: String = ""

    private lateinit var category: Category
    private var newCategory: Boolean = true

    private lateinit var colorAdapter: ColorAdapter

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
            "#F5F5DC", "#D2B48C", "#C68A3C", "#D19A6A", "#704214", "#7B3F00",
            "#C7513A", "#7E3B3A", "#E27D60", "#556B2F", "#228B22", "#8B8B00",
            "#6A7B8A", "#3A5B7F", "#5B7F91", "#5C4D83", "#7E5B9D", "#6A4C93"
        )


        binding.colorRC.layoutManager = GridLayoutManager(requireContext(), 6)
        colorAdapter = ColorAdapter(requireContext(), colors) { selectedColor ->
            this.selectedColor = selectedColor
        }
        binding.colorRC.adapter = colorAdapter
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

        if (name.isEmpty() && selectedColor.isEmpty()){
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
                        findNavController().navigate(R.id.action_newCategoryFragment_to_viewCategoryFragment)
                        Toast.makeText(
                            requireContext(),
                            "Categoria salva com sucesso.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        binding.progressBar.visibility = View.INVISIBLE
                        findNavController().navigate(R.id.action_newCategoryFragment_to_viewCategoryFragment)
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