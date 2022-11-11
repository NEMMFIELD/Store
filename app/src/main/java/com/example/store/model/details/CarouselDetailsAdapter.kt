package com.example.store.model.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.store.databinding.CarouseldetailsItemViewBinding


class CarouselDetailsAdapter() :
    ListAdapter<CarouselDetailsModel, CarouselDetailsAdapter.CarouselDetailsViewHolder>(
        CarouselDetailsDiffUtil()) {
    inner class CarouselDetailsViewHolder(private val binding: CarouseldetailsItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CarouselDetailsModel) =
            with(binding) {
                detailsPhoneImage.load(item.detailsImg)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselDetailsViewHolder {
        val binding = CarouseldetailsItemViewBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        return CarouselDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarouselDetailsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CarouselDetailsDiffUtil : DiffUtil.ItemCallback<CarouselDetailsModel>() {
        override fun areItemsTheSame(
            oldItem: CarouselDetailsModel,
            newItem: CarouselDetailsModel,
        ): Boolean {
            return oldItem.detailsImg?.length == newItem.detailsImg?.length
        }

        override fun areContentsTheSame(
            oldItem: CarouselDetailsModel,
            newItem: CarouselDetailsModel,
        ): Boolean {
            return oldItem == newItem
        }
    }
}

