package com.example.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.store.databinding.ActivityMainBinding
import com.example.store.view.BasketFragment
import com.example.store.view.BestSellerFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
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
                replace<BestSellerFragment>(R.id.fragment_container)
                setReorderingAllowed(true)
            }
        }
        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bucket -> {
                    replaceFragment(BasketFragment.newInstance())
                }
                else -> {
                    replaceFragment(BestSellerFragment())
                }
            }
            true
        }
        setBadgeNumber(1, 2)
    }

    private fun replaceFragment(selectedFragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setBadgeNumber(index: Int, count: Int) {
        val menuViewInBottomNavigationView = binding.navigation.getChildAt(0) as BottomNavigationMenuView
        val itemView = menuViewInBottomNavigationView.getChildAt(index) as BottomNavigationItemView

        val badgeNubmerViewHolder = LayoutInflater.from(this)
            .inflate(R.layout.view_holder_badge_nubmer, menuViewInBottomNavigationView, false)
        itemView.addView(badgeNubmerViewHolder)

        val textViewBadgeNumber =
            badgeNubmerViewHolder.findViewById<TextView>(R.id.textViewBadgeNumber)
        textViewBadgeNumber.text = count.toString()
        textViewBadgeNumber.visibility = if (count > 0) View.VISIBLE else View.GONE
    }
}