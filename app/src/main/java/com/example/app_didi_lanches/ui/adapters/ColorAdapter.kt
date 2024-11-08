package com.example.app_didi_lanches.ui.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_didi_lanches.databinding.AdapterColorBinding

class ColorAdapter(
    private val colors: List<Int>,
    private val onColorSelected: (Int) -> Unit
) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    private var selectedPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(
            AdapterColorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ColorViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val color = colors[position]

        holder.binding.colorView.backgroundTintList = ColorStateList.valueOf(color)

        if (position == selectedPosition) {
            holder.binding.highlightView.visibility = View.VISIBLE
        } else {
            holder.binding.highlightView.visibility = View.INVISIBLE
        }

        holder.binding.colorView.setOnClickListener {
            if (selectedPosition != position) {
                val previousPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
                onColorSelected(color)
            }
        }
    }

    override fun getItemCount() = colors.size

    inner class ColorViewHolder(val binding: AdapterColorBinding) :
        RecyclerView.ViewHolder(binding.root)
}

