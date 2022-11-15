package com.example.store.model.bestseller

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.store.R
import com.example.store.databinding.BestsellerItemBinding
import javax.inject.Inject

class BestSellerAdapter @Inject constructor(
    private val likeListener: ItemClick,
) :
    ListAdapter<BestSellerModel, BestSellerAdapter.ViewHolder>(BestSellerDiffUtil()) {
    var onModelClick: ((BestSellerModel) -> Unit)? = null

    inner class ViewHolder(private val binding: BestsellerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: BestSellerModel) =
            with(binding) {
                bestSellerImage.load(item.picturePath)
                discountPrice.text = "$${item.priceWithoutDiscount.toString()}"
                price.text = "$${item.discountPrice.toString()}"
                description.text = item.title

                if (item.isFavourites) liked.setImageResource(R.drawable.fav)
                else liked.setImageResource(R.drawable.notfav)

                liked.setOnClickListener {
                    likeListener.onItemClick(
                        item,
                        adapterPosition
                    )
                }
                itemView.setOnClickListener {
                    onModelClick?.invoke(item)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            BestsellerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BestSellerDiffUtil : DiffUtil.ItemCallback<BestSellerModel>() {
        override fun areItemsTheSame(oldItem: BestSellerModel, newItem: BestSellerModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: BestSellerModel,
            newItem: BestSellerModel,
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface ItemClick {
        fun onItemClick(bestSellerModel: BestSellerModel, position: Int)
    }

}