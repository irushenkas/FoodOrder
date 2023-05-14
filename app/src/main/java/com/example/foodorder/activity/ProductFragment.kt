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
import com.example.foodorder.viewmodel.ProductViewModel


class ProductFragment : Fragment() {

    private val viewModel: ProductViewModel by viewModels(
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
                viewModel.edit(product)
                val bundle = Bundle()
                bundle.putString("textArg", product.name)
                findNavController().navigate(R.id.action_productFragment_to_editProductFragment, bundle)
            }

            override fun onRemove(product: Product) {
                viewModel.removeById(product.id)
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_productFragment_to_newProductFragment)
        }


//        viewModel.edited.observe(this) { product ->
//            if (product.id == 0L) {
//                return@observe
//            }
//            with(binding.content) {
//                requestFocus()
//                setText(product.name)
//            }
//        }

        binding.send.setOnClickListener {

            val phoneNo: String = "+79092801805"

            val sendIntent = Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phoneNo, null))
            sendIntent.putExtra("sms_body", "default content")
            startActivity(sendIntent)
        }

        return binding.root
    }
}