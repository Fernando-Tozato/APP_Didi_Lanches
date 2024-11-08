package com.example.app_didi_lanches.ui.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.example.app_didi_lanches.R
import com.example.app_didi_lanches.databinding.SpinnerItemCategoryBinding
import com.example.app_didi_lanches.model.Category

class CategorySpinnerAdapter(
    context: Context,
    private var categoryList: List<Category>
) : ArrayAdapter<Category>(context, R.layout.spinner_item_category, categoryList) {

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView == null) {
            SpinnerItemCategoryBinding.inflate(inflater, parent, false)
        } else {
            SpinnerItemCategoryBinding.bind(convertView)
        }

        val category = categoryList[position]

        binding.spinnerText.text = category.name

        binding.colorView.backgroundTintList = ColorStateList.valueOf(
            Color.parseColor(category.color))

        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView == null) {
            SpinnerItemCategoryBinding.inflate(inflater, parent, false)
        } else {
            SpinnerItemCategoryBinding.bind(convertView)
        }

        val category = categoryList[position]

        binding.spinnerText.text = category.name

        binding.colorView.backgroundTintList = ColorStateList.valueOf(
            Color.parseColor(category.color))

        return binding.root
    }
}

