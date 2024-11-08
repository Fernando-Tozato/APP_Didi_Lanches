package com.example.app_didi_lanches.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.app_didi_lanches.R
import com.example.app_didi_lanches.databinding.AdapterProductBinding
import com.example.app_didi_lanches.helper.FirebaseHelper
import com.example.app_didi_lanches.model.Category
import com.example.app_didi_lanches.model.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

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

        getCategory(product.category) { category ->
            Log.d("debug_adapter", "${category?.color}")

            val colorHex = category?.color
            if (!colorHex.isNullOrEmpty()) {
                try {
                    holder.binding.colorView.backgroundTintList = ColorStateList.valueOf(Color.parseColor(colorHex))
                } catch (e: IllegalArgumentException) {
                    holder.binding.colorView.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(context, R.color.yellow)
                    )
                }
            } else {
                holder.binding.colorView.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(context, R.color.yellow)
                )
            }
        }

        holder.binding.nameLbl.text = product.name
        holder.binding.quantityLbl.text = "${product.quantity} ${product.measure}"

        holder.binding.btnEdit.setOnClickListener { productSelected(product, SELECT_EDIT) }
        holder.binding.btnDelete.setOnClickListener { productSelected(product, SELECT_DELETE) }
    }


    private fun getCategory(categoryID: String, onCategoryLoaded: (Category?) -> Unit) {
        FirebaseHelper
            .getDatabase()
            .child("category")
            .child(FirebaseHelper.getIdUser().toString())
            .child(categoryID)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val category = snapshot.getValue(Category::class.java)
                        onCategoryLoaded(category)
                    } else {
                        onCategoryLoaded(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Erro ao buscar categorias.", Toast.LENGTH_SHORT).show()
                    onCategoryLoaded(null)
                }
            })
    }


    override fun getItemCount() = productList.size

    inner class ProductViewHolder(val binding: AdapterProductBinding) :
            RecyclerView.ViewHolder(binding.root)
}