package com.example.app_didi_lanches.ui.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_didi_lanches.R

class ColorAdapter(
    private val colors: List<Int>,
    private val onColorSelected: (Int) -> Unit
) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    private var selectedPosition = -1 // Índice da cor selecionada

    inner class ColorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val colorView: View = view.findViewById(R.id.colorView)
        val highlightView: View= view.findViewById(R.id.highlightView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_color, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = colors[position]

        // Define a cor do backgroundTint em vez de background
        holder.colorView.backgroundTintList = ColorStateList.valueOf(color)

        // Aplica o contorno marrom se for o item selecionado
        if (position == selectedPosition) {
            holder.highlightView.visibility = View.VISIBLE
        } else {
            holder.highlightView.visibility = View.INVISIBLE
        }

        // Define a ação de seleção
        holder.colorView.setOnClickListener {
            if (selectedPosition != position) {
                val previousPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(previousPosition) // Atualiza o item anterior
                notifyItemChanged(selectedPosition)  // Atualiza o item atual
                onColorSelected(color) // Chama o callback com a cor selecionada
            }
        }
    }

    override fun getItemCount() = colors.size
}

