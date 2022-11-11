package com.example.store.model.homestore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.store.databinding.CarouselItemViewBinding

class CarouselAdapter() :
    ListAdapter<CarouselModel, CarouselAdapter.CarouselViewHolder>(CarouselDiffUtil()) {
    inner class CarouselViewHolder(private val binding: CarouselItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CarouselModel) =
            with(binding) {
                carouselImage.load(item.imagePath)
                carouselTitle.text = item.brand
                carouselDescription.text = item.description
                button.visibility = View.VISIBLE

                if (item.isNew == true) {
                    newPhone.visibility = View.VISIBLE
                } else {
                    newPhone.visibility = View.INVISIBLE
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding = CarouselItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CarouselDiffUtil : DiffUtil.ItemCallback<CarouselModel>() {
        override fun areItemsTheSame(oldItem: CarouselModel, newItem: CarouselModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CarouselModel, newItem: CarouselModel): Boolean {
            return oldItem == newItem
        }
    }

}