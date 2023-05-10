package com.example.foodorder.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.foodorder.adapter.FoodAdapter
import com.example.foodorder.adapter.OnInteractionListener
import com.example.foodorder.databinding.ActivityMainBinding
import com.example.foodorder.dto.Product
import com.example.foodorder.viewmodel.ProductViewModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: ProductViewModel by viewModels()
        val adapter = FoodAdapter(object : OnInteractionListener {
            override fun onEdit(product: Product) {
                viewModel.edit(product)
            }

            override fun onRemove(product: Product) {
                viewModel.removeById(product.id)
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { products ->
            adapter.submitList(products)
        }

        viewModel.edited.observe(this) { product ->
            if (product.id == 0L) {
                return@observe
            }
            with(binding.content) {
                requestFocus()
                setText(product.name)
            }
        }

        binding.send.setOnClickListener {

            val phoneNo: String = "+79092801805"

            val sendIntent = Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phoneNo, null))
            sendIntent.putExtra("sms_body", "default content")
            startActivity(sendIntent)
        }
    }
}