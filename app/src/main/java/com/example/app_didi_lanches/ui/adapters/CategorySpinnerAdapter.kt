package com.example.app_didi_lanches.ui.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.app_didi_lanches.databinding.SpinnerItemCategoryBinding
import com.example.app_didi_lanches.model.Category

class CategorySpinnerAdapter(
    context: Context,
    categoryList: List<Category>
) : ArrayAdapter<Category>(context, 0, categoryList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView == null) {
            SpinnerItemCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            SpinnerItemCategoryBinding.bind(convertView)
        }

        val category = getItem(position)

        category?.let {
            binding.colorView.backgroundTintList = ColorStateList.valueOf(it.color)
            binding.nameTextView.text = it.name
        }

        return binding.root
    }
}

