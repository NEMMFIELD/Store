package com.example.productdetails.presentation

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.productdetails.data.network.State
import com.example.productdetails.databinding.FragmentProductDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModelDetails: ProductDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            carouselRecyclerDetails.adapter = viewModelDetails.carouselDetailsAdapter

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModelDetails.detailsImageFlow.collect { state ->
                        when (state) {
                            is State.Success -> {
                                viewModelDetails.carouselDetailsAdapter.submitList(mutableListOf(
                                    state.data))
                                detailsProductTitle.text = state.data.title
                                ratingBar.rating = state.data.phoneRating?.toFloat() ?: 0f
                                detailsCpu.text = state.data.cpu
                                detailsProductCamera.text = state.data.camera
                                detailsProductRam.text = state.data.ssd
                                detailsProductSdRam.text = state.data.sd
                            }
                            is State.Failure -> Log.d("DetailsError", "Error: ${state.message}")
                            is State.Empty -> {}
                        }
                    }
                }
            }
            detailsShop.text = spanText()
            detailsBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProductDetailsFragment()
    }

    private fun spanText(): SpannableString {
        val text = "Shop"
        val mSpannableString = SpannableString(text)
        mSpannableString.setSpan(ColoredUnderlineSpan(Color.parseColor("#FF6E4E"), 10f),
            0,
            mSpannableString.length,
            SpannableString.SPAN_INCLUSIVE_EXCLUSIVE)
        return mSpannableString
    }

    class ColoredUnderlineSpan constructor(
        private val underlineColor: Int,
        private val underlineThickness: Float,
    ) : CharacterStyle() {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun updateDrawState(textPaint: TextPaint) {
            textPaint.underlineColor = underlineColor
            textPaint.underlineThickness = underlineThickness
        }
    }
}


