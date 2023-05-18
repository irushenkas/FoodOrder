package com.example.foodorder.activity

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.ActionMenuItemView
import com.example.foodorder.R
import com.example.foodorder.viewmodel.PhoneViewModel

class AppActivity : AppCompatActivity(R.layout.activity_app) {

    private val viewModel: PhoneViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<ActionMenuItemView>(R.id.phoneText).text = viewModel.data.value

        findViewById<ActionMenuItemView>(R.id.phone).setOnClickListener { item ->
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle(resources.getString(R.string.phone_text))

            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_PHONE
            input.setText(viewModel.data.value?:"")
            builder.setView(input)

            builder.setPositiveButton(resources.getString(R.string.change_phone_ok)) { _, _ ->
                viewModel.edit(input.text.toString())
                viewModel.save()
                findViewById<ActionMenuItemView>(R.id.phoneText).text = viewModel.data.value
            }
            builder.setNegativeButton(resources.getString(R.string.change_phone_cancel)) { dialog, _ ->
                dialog.cancel()
            }

            builder.show()
        }
    }
}
