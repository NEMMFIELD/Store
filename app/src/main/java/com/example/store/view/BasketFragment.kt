package com.example.store.view

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.store.databinding.FragmentBasketBinding
import com.example.store.model.network.State
import com.example.store.viewmodel.BasketViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BasketFragment : Fragment() {
    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!
    private val viewModelBasket: BasketViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBasketBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.basketList.layoutManager = LinearLayoutManager(requireContext())
        binding.basketList.adapter = viewModelBasket.basketAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelBasket.basketFlow.collect { state ->
                    when (state) {
                        is State.Success -> {
                            viewModelBasket.basketAdapter.submitList(state.data)
                            if (state.data.isNotEmpty()) {
                                binding.basketTotalSum.text = ("$${
                                    state.data.stream().mapToInt { it.price ?: 0 }
                                        .summaryStatistics().sum
                                } us")
                            }
                        }
                        is State.Failure -> {
                            Log.d("TagError", "Error: ${state.message}")
                        }
                        is State.Empty -> {}
                    }
                }
            }
        }
        binding.basketBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BasketFragment()
    }
}
