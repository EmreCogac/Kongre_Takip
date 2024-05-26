package com.example.detapp.adapter

import android.app.AlertDialog
import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.detapp.R
import com.example.detapp.model.ButtonModelData
import com.example.detapp.model.PostDataModel
import java.lang.Appendable

class FilterAdapter(
    private var filterModel: List<ButtonModelData>,
    private val itemClickListener: (ButtonModelData)-> Unit
) : RecyclerView.Adapter<FilterAdapter.FilterChildHolder>(){

    interface ItemClickListener{
        fun onFilterButtonClick(position : ButtonModelData)
    }

    inner class FilterChildHolder(itemView : View): RecyclerView.ViewHolder(itemView){
    val button : Button = itemView.findViewById(R.id.kongreFiltre)
        init {
            button.setOnClickListener {
                val buttonPosition = absoluteAdapterPosition
                if (buttonPosition != RecyclerView.NO_POSITION) {
                    val clickedItem = filterModel[buttonPosition]
                    itemClickListener(clickedItem)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterChildHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.button_list, parent, false)
        return FilterChildHolder(view)
    }

    override fun getItemCount(): Int {
        return filterModel.size
    }

    override fun onBindViewHolder(holder: FilterChildHolder, position: Int) {
        val box = filterModel.get(position)
        holder.button.text = box.filterName
    }

}