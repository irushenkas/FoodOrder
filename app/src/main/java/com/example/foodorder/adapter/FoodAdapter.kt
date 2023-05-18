package com.example.foodorder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.R
import com.example.foodorder.databinding.FoodBinding
import com.example.foodorder.dto.Product

interface OnInteractionListener {
    fun onEdit(product: Product) {}
    fun onRemove(product: Product) {}
    fun onSelect(product: Product) {}
}

class FoodAdapter(
    private val onInteractionListener: OnInteractionListener,
) : ListAdapter<Product, ProductViewHolder>(ProductDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = FoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }
}

class ProductViewHolder(
    private val binding: FoodBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product) {
        binding.apply {
            name.text = product.name
            name.setOnClickListener{
                onInteractionListener.onSelect(product)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_product)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(product)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(product)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }

        }
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}