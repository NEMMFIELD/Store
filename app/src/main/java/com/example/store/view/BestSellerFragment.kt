package com.example.store.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.store.R
import com.example.store.databinding.FragmentBestSellerBinding
import com.example.store.model.bestseller.BestSellerAdapter
import com.example.store.model.bestseller.BestSellerModel
import com.example.store.model.network.State
import com.example.store.viewmodel.BestSellerViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class BestSellerFragment : Fragment(), BestSellerAdapter.ItemClick {
    private var _binding: FragmentBestSellerBinding? = null
    private val binding get() = _binding!!

    private val bestSellerViewModel: BestSellerViewModel by viewModels()
    private val adapter = BestSellerAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBestSellerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = FragmentAdapter(this)
        binding.viewPager.isUserInputEnabled = false
        setTabLayouts()
        setupBestSellersAdapter()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                bestSellerViewModel.bestSellerFlow.collect { state ->
                    when (state) {
                        is State.Success -> {
                            adapter.submitList(state.data)
                        }
                        is State.Failure -> {
                            Log.d("Error", "Error in BestSeller's: ${state.message} ")
                        }
                        is State.Empty -> {}
                    }
                }
            }
        }

        binding.filter.setOnClickListener { showDialog() }
        adapter.onModelClick = { moveToDetailsFragment(it) }
    }

    private fun setupBestSellersAdapter() {
        binding.bestSellerRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.bestSellerRecycler.adapter = adapter
    }

    private fun setTabLayouts() {
        val tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Phones"
                        tab.icon = ContextCompat.getDrawable(requireActivity(), R.drawable.phones)
                    }
                    1 -> {
                        tab.text = "Computer"
                        tab.icon = ContextCompat.getDrawable(requireActivity(),
                            R.drawable.computer)
                    }
                    2 -> {
                        tab.text = "Health"
                        tab.icon = ContextCompat.getDrawable(requireActivity(), R.drawable.health)
                    }
                    3 -> {
                        tab.text = "Books"
                        tab.icon = ContextCompat.getDrawable(requireActivity(), R.drawable.books)
                    }
                }
            }
        tabLayoutMediator.attach()
    }

    private fun showDialog() {
        val dialog = FiltersDialogFragment()
        dialog.show(childFragmentManager, "customDialog")
    }

    private fun moveToDetailsFragment(bestSellerModel: BestSellerModel) {
        val fragmentTransaction: FragmentTransaction? =
            activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container,
            ProductDetailsFragment.newInstance())
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()

    }

    override fun onItemClick(bestSellerModel: BestSellerModel, position: Int) {
        bestSellerModel.isFavourites = bestSellerModel.isFavourites == false
        bestSellerViewModel.setLike(bestSellerModel)
        adapter.notifyItemChanged(position, 1)
    }
}