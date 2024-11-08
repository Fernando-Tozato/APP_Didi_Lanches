package com.example.app_didi_lanches.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.app_didi_lanches.R
import com.example.app_didi_lanches.databinding.FragmentNewProductBinding
import com.example.app_didi_lanches.helper.FirebaseHelper
import com.example.app_didi_lanches.model.Category
import com.example.app_didi_lanches.model.Product
import com.example.app_didi_lanches.ui.adapters.CategorySpinnerAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class NewProductFragment : Fragment() {

    private var _binding: FragmentNewProductBinding? = null
    private val binding get() = _binding!!

    private var categoryList = mutableListOf<Category>()

    private var selectedMeasure: String = ""

    private var selectedCategory: Category? = null

    private lateinit var product: Product
    private var newProduct: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentNewProductBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseHelper.getDatabase()
            .child("product")
            .child(FirebaseHelper.getIdUser().toString() ?: "")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val category = snap.getValue(Category::class.java) as Category
                            categoryList.add(category)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Ocorreu um erro ao recuperar as categorias.", Toast.LENGTH_SHORT).show()
                }
            })

        loadPage()

        initClicks()
    }
    private fun loadPage() {
        val measureAdapter = ArrayAdapter.createFromResource(requireContext(),
            R.array.unit_options,
            R.layout.spinner_item)

        measureAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        binding.measureSpinner.adapter = measureAdapter

        binding.measureSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedMeasure = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        val categoryAdapter = CategorySpinnerAdapter(requireContext(), categoryList)

        categoryAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        binding.categorySpinner.adapter = categoryAdapter

        binding.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCategory = parent?.getItemAtPosition(position) as Category?
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun initClicks() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnMenu.setOnClickListener {
            findNavController().navigate(R.id.action_newProductFragment_to_homeFragment)
        }

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnConfirm.setOnClickListener {
            createProduct()
        }
    }

    private fun createProduct() {
        val name = binding.productInput.text.toString().trim()
        val quantity = binding.quantityInput.text.toString().toDouble()

        if (name.isEmpty() && quantity == 0.0 && selectedMeasure.isEmpty()){
            Toast.makeText(requireContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
        } else {
            binding.progressBar.visibility = View.VISIBLE

            if (newProduct) product = Product()
            product.name = name
            product.quantity = quantity
            product.measure = selectedMeasure

            saveProduct()
        }
    }

    private fun saveProduct() {
        FirebaseHelper
            .getDatabase()
            .child("product")
            .child(FirebaseHelper.getIdUser().toString() ?: "")
            .child(product.id)
            .setValue(product)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (newProduct) {
                        binding.progressBar.visibility = View.INVISIBLE
                        findNavController().popBackStack()
                        Toast.makeText(
                            requireContext(),
                            "Produto salvo com sucesso.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        binding.progressBar.visibility = View.INVISIBLE
                        findNavController().popBackStack()
                        Toast.makeText(
                            requireContext(),
                            "Produto atualizado com sucesso.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Erro ao salvar o produto.", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                binding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(requireContext(), "Erro ao salvar o produto.", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}