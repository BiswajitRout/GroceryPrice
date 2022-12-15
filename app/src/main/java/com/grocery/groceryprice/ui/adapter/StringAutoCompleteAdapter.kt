package com.grocery.groceryprice.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import com.grocery.groceryprice.databinding.ItemDropDownBinding

class StringAutoCompleteAdapter(private var models: List<String>) : BaseAdapter(), Filterable {

    private val filteredTypes = ArrayList<String>()
    private lateinit var binding: ItemDropDownBinding

    fun setList(types: List<String>) {
        models = types
    }

    override fun getCount(): Int = filteredTypes.size

    override fun getItem(position: Int): String {
        return filteredTypes[position]
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView
        if (convertView == null) {
            binding = ItemDropDownBinding.inflate(
                LayoutInflater.from(parent?.context),
                parent,
                false
            )
            view = binding.root
        }

        val data = getItem(position)
        binding.text1.text = data
        return view!!
    }

    override fun getFilter(): Filter {
        return ListFilter()
    }

    inner class ListFilter : Filter() {
        override fun performFiltering(prefix: CharSequence?): FilterResults? {
            return null
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            val filterText = constraint?.trim()
            filteredTypes.clear()
            if (filterText.isNullOrEmpty()) {
                filteredTypes.addAll(models)
            } else {
                filteredTypes.addAll(models.filter {
                    it.contains(filterText, true)
                })
            }
            notifyDataSetChanged()
        }
    }

}