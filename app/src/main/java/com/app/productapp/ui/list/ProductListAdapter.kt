package com.app.productapp.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.productapp.databinding.ItemProductGridBinding
import com.app.productapp.databinding.ItemProductListBinding
import com.app.productapp.model.MainModel
import com.app.productapp.utils.Constants
import com.app.productapp.utils.getProgressDrawable
import com.app.productapp.utils.loadImage

class ProductListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var productList: ArrayList<MainModel.ProductModel> = arrayListOf()
    var mCurrentLayoutManagerType: Constants.LayoutManagerType? = null

    fun refreshData(newProductList: ArrayList<MainModel.ProductModel>) {
        val diffCallback = DiffUtilCallback(productList, newProductList)
        val diffDefinition = DiffUtil.calculateDiff(diffCallback)
        productList.clear()
        productList.addAll(newProductList)
        diffDefinition.dispatchUpdatesTo(this)
    }

    class LinearViewHolder(private val binding: ItemProductListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val progressDrawable = getProgressDrawable(binding.root.context)

        fun bindData(model: MainModel.ProductModel) {
            binding.ivProduct.loadImage(model.thumbnail, progressDrawable)
            binding.tvTitle.text = model.title
            binding.tvDescription.text = model.description
            binding.tvPriceDiscount.text =
                "$${model.price} -> ${model.discountPercentage}% Discount"
            binding.tvRating.text = String.format("***** %f", model.rating)
        }

    }

    class GridViewHolder(private val binding: ItemProductGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val progressDrawable = getProgressDrawable(binding.root.context)

        fun bindData(model: MainModel.ProductModel) {
            binding.ivProduct.loadImage(model.thumbnail, progressDrawable)
            binding.tvTitle.text = model.title
            binding.tvPrice.text = model.price.toString()
            binding.tvRating.text = String.format("***** %f", model.rating)
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (mCurrentLayoutManagerType == Constants.LayoutManagerType.LINEAR_LAYOUT_MANAGER) {
            return Constants.LINEAR_LAYOUT_MANAGER
        } else if (mCurrentLayoutManagerType == Constants.LayoutManagerType.GRID_LAYOUT_MANAGER) {
            return Constants.GRID_LAYOUT_MANAGER
        }
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            Constants.GRID_LAYOUT_MANAGER -> {
                val binding = ItemProductGridBinding.inflate(inflater, parent, false)
                GridViewHolder(binding)
            }
            Constants.LINEAR_LAYOUT_MANAGER -> {
                val binding = ItemProductListBinding.inflate(inflater, parent, false)
                LinearViewHolder(binding)
            }
            else -> {
                throw java.lang.IllegalArgumentException("Unknown view type in recyclerview")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (mCurrentLayoutManagerType == Constants.LayoutManagerType.LINEAR_LAYOUT_MANAGER) {
            val linear = holder as LinearViewHolder
            linear.bindData(productList[position])
        } else if (mCurrentLayoutManagerType == Constants.LayoutManagerType.GRID_LAYOUT_MANAGER) {
            val grid = holder as GridViewHolder
            grid.bindData(productList[position])
        }
    }

    override fun getItemCount(): Int = productList.size

    private class DiffUtilCallback(private val oldList: ArrayList<MainModel.ProductModel>,
        private val newList: ArrayList<MainModel.ProductModel>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }
    }

}