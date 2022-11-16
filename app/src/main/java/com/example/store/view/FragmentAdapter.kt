package com.example.store.view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

//Адаптер для ViewPager2 для отображения в стиле слайдера
class FragmentAdapter(fragment: Fragment):FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return HotSalesFragment.newInstance()
        }
    }
