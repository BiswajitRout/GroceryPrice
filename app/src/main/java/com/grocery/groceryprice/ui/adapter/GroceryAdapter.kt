package com.grocery.groceryprice.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.grocery.groceryprice.databinding.ItemGroceryBinding
import com.grocery.groceryprice.model.Grocery

class GroceryAdapter : ListAdapter<Grocery, GroceryAdapter.ViewModel>(GroceryDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel =
        ViewModel(ItemGroceryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewModel, position: Int) {
        val model = currentList[position]
        holder.bind(model)

    }

    inner class ViewModel(private val binding: ItemGroceryBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(model: Grocery) {
                binding.apply {
                    tvDate.text = model.arrival_date
                    tvValueState.text = model.state
                    tvValueDistrict.text = model.district
                    tvValueMarket.text = model.market
                    tvValueVariety.text = model.variety
                    tvValueCommodity.text = model.commodity
                    tvValuePrice.text = model.modal_price
                }
            }
        }
}

class GroceryDiffUtils : DiffUtil.ItemCallback<Grocery>() {
    override fun areItemsTheSame(oldItem: Grocery, newItem: Grocery): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Grocery, newItem: Grocery): Boolean =
        oldItem == newItem

}