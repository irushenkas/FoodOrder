package com.example.foodorder.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.foodorder.R

class AppActivity : AppCompatActivity(R.layout.activity_app) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        intent?.let {
//            if (it.action != Intent.ACTION_SEND) {
//                return@let
//            }
//
//            val text = it.getStringExtra(Intent.EXTRA_TEXT)
//            if (text?.isNotBlank() != true) {
//                return@let
//            }
//            intent.removeExtra(Intent.EXTRA_TEXT)
//            findNavController(R.id.nav_host_fragment).navigate(
//                R.id.action_feedFragment_to_newPostFragment,
//                Bundle().apply {
//                    textArg = text
//                }
//            )
//        }
    }
}