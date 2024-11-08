package com.example.app_didi_lanches.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.app_didi_lanches.R
import com.example.app_didi_lanches.databinding.AdapterCategoryBinding
import com.example.app_didi_lanches.model.Category

class CategoryAdapter(
    private val context: Context,
    private val categoryList: List<Category>,
    val categorySelected: (Category, Int) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    companion object {
        val SELECT_EDIT: Int = 1
        val SELECT_DELETE: Int = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            AdapterCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]

        if (category.color in listOf("#704214", "#7B3F00", "#7E3B3A")){
            holder.binding.nameLbl.setTextColor(ContextCompat.getColor(context, R.color.yellow))
        } else {
            holder.binding.nameLbl.setTextColor(ContextCompat.getColor(context, R.color.brown))
        }

        holder.binding.nameLbl.text = category.name
        holder.binding.colorCard.setBackgroundColor(Color.parseColor(category.color))

        holder.binding.btnEdit.setOnClickListener { categorySelected(category, SELECT_EDIT) }
        holder.binding.btnDelete.setOnClickListener { categorySelected(category, SELECT_DELETE) }
    }

    override fun getItemCount() = categoryList.size

    inner class CategoryViewHolder(val binding: AdapterCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}