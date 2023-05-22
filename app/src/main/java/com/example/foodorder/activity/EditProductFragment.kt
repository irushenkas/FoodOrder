package com.example.foodorder.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.foodorder.databinding.FragmentEditProductBinding
import com.example.foodorder.util.AndroidUtils
import com.example.foodorder.util.IntArg
import com.example.foodorder.util.StringArg
import com.example.foodorder.viewmodel.ProductViewModel

class EditProductFragment : Fragment() {

    companion object {
        var Bundle.name: String? by StringArg
        var Bundle.priority: Int? by IntArg
    }

    private val viewModel: ProductViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditProductBinding.inflate(
            inflater,
            container,
            false
        )

        arguments?.name?.let(binding.edit::setText)
        val priority = arguments?.priority?.toString()
        priority.let(binding.priority::setText)

        binding.ok.setOnClickListener {
            var priority = 0
            if(binding.priority.text.toString().isNotEmpty()) {
                priority = Integer.parseInt(binding.priority.text.toString())
            }
            viewModel.changeContent(binding.edit.text.toString(), priority)
            viewModel.save()
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }
        return binding.root
    }
}