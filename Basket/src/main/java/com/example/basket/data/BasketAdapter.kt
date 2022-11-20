package com.example.basket.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.basket.databinding.BasketItemViewBinding

class BasketAdapter() : ListAdapter<BasketModel, BasketAdapter.BasketViewHolder>(BasketDiffUtil()) {
    inner class BasketViewHolder(private val binding: BasketItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: BasketModel) =
            with(binding) {
                basketImage.load(item.image)
                basketModel.text = item.title
                basketPrice.text = ("$${item.price.toString()}")
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val binding = BasketItemViewBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        return BasketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BasketDiffUtil : DiffUtil.ItemCallback<BasketModel>() {
        override fun areItemsTheSame(
            oldItem: BasketModel,
            newItem: BasketModel,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: BasketModel,
            newItem: BasketModel,
        ): Boolean {
            return oldItem == newItem
        }
    }
}
