package com.example.store

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.store.databinding.ActivityMainBinding
import com.example.store.view.BasketFragment
import com.example.store.view.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace<MainFragment>(R.id.fragment_container)
                setReorderingAllowed(true)
            }
        }
       
        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bucket -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, BasketFragment.newInstance()).commit()
                }
                else -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MainFragment()).commit()

                }
            }
            true
        }
    }
}