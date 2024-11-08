package com.example.app_didi_lanches.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_didi_lanches.R
import com.example.app_didi_lanches.databinding.FragmentInventoryBinding
import com.example.app_didi_lanches.helper.FirebaseHelper
import com.example.app_didi_lanches.model.Product
import com.example.app_didi_lanches.ui.adapters.ProductAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class InventoryFragment : Fragment() {

    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!

    private val productList = mutableListOf<Product>()

    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentInventoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClicks()

        getProducts()
    }

    private fun initClicks() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnMenu.setOnClickListener {
            findNavController().navigate(R.id.action_inventoryFragment_to_homeFragment)
        }

        binding.addProduct.setOnClickListener {
            findNavController().navigate(R.id.action_inventoryFragment_to_newProductFragment)
        }
    }

    private fun getProducts() {
        FirebaseHelper
            .getDatabase()
            .child("product")
            .child(FirebaseHelper.getIdUser().toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (view != null && viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                        if (snapshot.exists()) {
                            productList.clear()

                            for (snap in snapshot.children) {
                                val product = snap.getValue(Product::class.java) as Product
                                Log.d("debug_adapter", "$product")
                                productList.add(product)
                            }

                            binding.progressBar.visibility = View.INVISIBLE
                            binding.loadingText.text = ""
                            initAdapter()
                        } else {
                            binding.progressBar.visibility = View.INVISIBLE
                            binding.loadingText.text = "Nenhum produto cadastrado."
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Erro ao buscar categorias.", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun initAdapter() {
        binding.inventoryRV.layoutManager = LinearLayoutManager(requireContext())
        binding.inventoryRV.setHasFixedSize(true)

        productAdapter = ProductAdapter(requireContext(), productList) { product, select ->
            optionSelect(product, select)
        }

        binding.inventoryRV.adapter = productAdapter
    }

    private fun optionSelect(product: Product, select: Int) {
        when (select) {
            ProductAdapter.SELECT_DELETE -> {
                deleteProduct(product)
            }

            ProductAdapter.SELECT_EDIT -> {
                editProduct(product)
            }
        }
    }

    private fun editProduct(product: Product) {

    }

    private fun deleteProduct(product: Product) {
        FirebaseHelper
            .getDatabase()
            .child("product")
            .child(FirebaseHelper.getIdUser().toString())
            .child(product.id)
            .removeValue()

        productList.remove(product)
        productAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}