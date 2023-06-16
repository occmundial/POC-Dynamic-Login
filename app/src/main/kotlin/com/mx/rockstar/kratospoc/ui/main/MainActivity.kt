package com.mx.rockstar.kratospoc.ui.main

import android.content.Intent
import android.os.Bundle
import com.mx.rockstar.kratospoc.R
import com.mx.rockstar.kratospoc.databinding.LayoutMainBinding
import com.mx.rockstar.kratospoc.ui.login.LoginActivity
import com.mx.rockstar.kratospoc.ui.registration.RegistrationActivity
import com.skydoves.bindables.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<LayoutMainBinding>(R.layout.layout_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.login.setOnClickListener {
            Intent(this@MainActivity, LoginActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.registration.setOnClickListener {
            Intent(this@MainActivity, RegistrationActivity::class.java).also {
                startActivity(it)
            }
        }

    }

}