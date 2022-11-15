package com.example.store.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.store.databinding.FragmentPhonesBinding
import com.example.store.viewmodel.HomeStoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhonesFragment : Fragment() {
    private var _binding: FragmentPhonesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeStoreViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPhonesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            carouselRecyclerview.adapter = viewModel.carouselAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.homeStoreFlow.collect {
                        viewModel.carouselAdapter.submitList(it)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PhonesFragment()
    }

}