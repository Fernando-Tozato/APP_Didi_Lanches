package com.example.app_didi_lanches.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_didi_lanches.databinding.AdapterProductBinding
import com.example.app_didi_lanches.model.Product

class ProductAdapter(
    private val context: Context,
    private val productList: List<Product>,
    val productSelected: (Product, Int) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    companion object {
        val SELECT_EDIT: Int = 1
        val SELECT_DELETE: Int = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            AdapterProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.binding.nameLbl.text = product.name
        holder.binding.quantityLbl.text = "${product.quantity} ${product.measure}"

        holder.binding.btnEdit.setOnClickListener { productSelected(product, SELECT_EDIT) }
        holder.binding.btnDelete.setOnClickListener { productSelected(product, SELECT_DELETE) }
    }

    override fun getItemCount() = productList.size

    inner class ProductViewHolder(val binding: AdapterProductBinding) :
            RecyclerView.ViewHolder(binding.root)
}