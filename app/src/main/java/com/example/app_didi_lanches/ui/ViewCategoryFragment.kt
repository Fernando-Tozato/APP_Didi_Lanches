package com.example.app_didi_lanches.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_didi_lanches.R
import com.example.app_didi_lanches.databinding.FragmentViewCategoryBinding
import com.example.app_didi_lanches.helper.FirebaseHelper
import com.example.app_didi_lanches.model.Category
import com.example.app_didi_lanches.ui.adapters.CategoryAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ViewCategoryFragment : Fragment() {

    private var _binding: FragmentViewCategoryBinding? = null
    private val binding get() = _binding!!

    private val categoryList = mutableListOf<Category>()

    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentViewCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClicks()

        getCategories()
    }

    private fun initClicks() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnMenu.setOnClickListener {
            findNavController().navigate(R.id.action_viewCategoryFragment_to_homeFragment)
        }

        binding.addCategory.setOnClickListener {
            findNavController().navigate(R.id.action_viewCategoryFragment_to_newCategoryFragment)
        }
    }

    private fun getCategories() {
        FirebaseHelper
            .getDatabase()
            .child("category")
            .child(FirebaseHelper.getIdUser().toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (view != null && viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                        if (snapshot.exists()) {
                            categoryList.clear()
                            for (snap in snapshot.children) {
                                val category = snap.getValue(Category::class.java) as Category
                                categoryList.add(category)
                            }
                            binding.progressBar.visibility = View.INVISIBLE
                            binding.loadingText.text = ""
                            initAdapter()
                        } else {
                            binding.progressBar.visibility = View.INVISIBLE
                            binding.loadingText.text = "Nenhuma categoria cadastrada."
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Erro ao buscar categorias.", Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun initAdapter() {
        binding.categoryRV.layoutManager = LinearLayoutManager(requireContext())
        binding.categoryRV.setHasFixedSize(true)

        categoryAdapter = CategoryAdapter(requireContext(), categoryList) { category, select ->
            optionSelect(category, select)
        }

        binding.categoryRV.adapter = categoryAdapter
    }

    private fun optionSelect(category: Category, select: Int) {
        when (select) {
            CategoryAdapter.SELECT_DELETE -> {
                deleteCategory(category)
            }

            CategoryAdapter.SELECT_EDIT -> {
            }
        }
    }

    private fun deleteCategory(category: Category) {
        FirebaseHelper
            .getDatabase()
            .child("category")
            .child(FirebaseHelper.getIdUser().toString())
            .child(category.id)
            .removeValue()

        categoryList.remove(category)
        categoryAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}