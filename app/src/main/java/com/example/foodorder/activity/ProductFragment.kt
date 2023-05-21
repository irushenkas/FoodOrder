package com.example.foodorder.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.foodorder.R
import com.example.foodorder.adapter.FoodAdapter
import com.example.foodorder.adapter.OnInteractionListener
import com.example.foodorder.databinding.FragmentProductBinding
import com.example.foodorder.dto.Product
import com.example.foodorder.viewmodel.PhoneViewModel
import com.example.foodorder.viewmodel.ProductViewModel

class ProductFragment : Fragment() {

    private val productViewModel: ProductViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private val phoneViewModel: PhoneViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProductBinding.inflate(
            inflater,
            container,
            false
        )
        val adapter = FoodAdapter(object : OnInteractionListener {
            override fun onEdit(product: Product) {
                productViewModel.edit(product)
                val bundle = Bundle()
                bundle.putString("textArg", product.name)
                findNavController().navigate(R.id.action_productFragment_to_editProductFragment, bundle)
            }

            override fun onRemove(product: Product) {
                productViewModel.removeById(product.id)
            }

            override fun onSelect(product: Product) {
                productViewModel.select(product)
            }

            override fun onChangeComment(product: Product, comment: String?) {
                productViewModel.changeComment(product, comment)
            }
        }, context)
        binding.list.adapter = adapter
        productViewModel.data.observe(viewLifecycleOwner) { products ->
            adapter.submitList(products)
        }

        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_productFragment_to_newProductFragment)
        }

        binding.send.setOnClickListener {
            val phoneNumber: String = phoneViewModel.get()?: ""

            val selected = productViewModel.getSelected().map {
                if(it.comment != null) {
                    String.format("%s(%s)", it.name, it.comment)
                } else {
                    it.name
                }
            }.joinToString(separator = ", ")

            productViewModel.cleanTemporaryData()

            val sendIntent = Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phoneNumber, null))
            sendIntent.putExtra("sms_body", selected)
            startActivity(sendIntent)
        }

        return binding.root
    }
}