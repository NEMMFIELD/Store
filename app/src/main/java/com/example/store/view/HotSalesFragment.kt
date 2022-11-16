package com.example.store.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.store.databinding.FragmentHotSalesBinding
import com.example.store.model.network.State
import com.example.store.viewmodel.HomeStoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HotSalesFragment : Fragment() {
    private var _binding: FragmentHotSalesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeStoreViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHotSalesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            carouselRecyclerview.adapter = viewModel.carouselAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.homeStoreFlow.collect { state ->
                        when (state) {
                            is State.Success -> {
                                viewModel.carouselAdapter.submitList(state.data)
                            }
                            is State.Failure -> {
                                Log.d("Error in Hot sales", "Error is ${state.message}")
                            }
                            is State.Empty -> {}
                        }
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HotSalesFragment()
    }
}