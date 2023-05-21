package com.example.foodorder.adapter

import android.app.AlertDialog
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import androidx.appcompat.view.menu.ActionMenuItemView
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
    fun onChangeComment(product: Product, comment: String?) {}
}

class FoodAdapter(
    private val onInteractionListener: OnInteractionListener,
    private val context: Context?,
) : ListAdapter<Product, ProductViewHolder>(ProductDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = FoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, onInteractionListener, context)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }
}

class ProductViewHolder(
    private val binding: FoodBinding,
    private val onInteractionListener: OnInteractionListener,
    private val context: Context?,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product) {
        binding.apply {
            if(product.comment != null && product.comment!!.isNotEmpty()) {
                name.text = String.format("%s (%s)", product.name, product.comment)
            } else {
                name.text = product.name
            }
            name.setOnClickListener{
                onInteractionListener.onSelect(product)
            }

            comment.setOnClickListener {
                context?.let {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(it)
                    builder.setTitle(it.resources.getString(R.string.comment_text))

                    val input = EditText(it)
                    input.inputType = InputType.TYPE_CLASS_TEXT
                    input.setText(product.comment)
                    builder.setView(input)

                    builder.setPositiveButton(it.resources.getString(R.string.dialog_ok)) { _, _ ->
                        var text: String? = null
                        if(input.text.toString().isNotEmpty()) {
                            text = input.text.toString()
                        }
                        onInteractionListener.onChangeComment(product, text)
                    }
                    builder.setNegativeButton(it.resources.getString(R.string.dialog_cancel)) { dialog, _ ->
                        dialog.cancel()
                    }

                    builder.show()
                }
            }

            name.isChecked = product.isSelected

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