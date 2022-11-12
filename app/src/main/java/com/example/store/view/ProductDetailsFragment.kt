package com.example.store.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.store.R
import com.example.store.databinding.FragmentProductDetailsBinding
import com.example.store.model.details.CarouselDetailsAdapter
import com.example.store.viewmodel.HomeStoreViewModel
import com.example.store.viewmodel.ProductDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"

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
                    viewModelDetails.detailsImageFlow.collect {
                        viewModelDetails.carouselDetailsAdapter.submitList(it)
                        println(viewModelDetails.carouselDetailsAdapter.currentList)
                    }
                }
            }
            val text = "Shop"
            val mSpannableString = SpannableString(text)
            mSpannableString.setSpan(ColoredUnderlineSpan(Color.parseColor("#FF6E4E"), 10f),
                0,
                mSpannableString.length,
                SpannableString.SPAN_INCLUSIVE_EXCLUSIVE)
            detailsShop.text = mSpannableString
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProductDetailsFragment()
    }

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
