package com.app.productapp.ui.add

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.productapp.databinding.ItemImageBinding

class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    private var imageList: ArrayList<Uri> = arrayListOf()

    fun refreshData(newImageList: ArrayList<Uri>) {
        val diffCallback = DiffUtilCallback(imageList, newImageList)
        val diffDefinition = DiffUtil.calculateDiff(diffCallback)
        imageList.clear()
        imageList.addAll(newImageList)
        diffDefinition.dispatchUpdatesTo(this)
    }

    class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(uri: Uri) {
            binding.ivProduct.setImageURI(uri)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImageBinding.inflate(inflater, parent, false)
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindData(imageList[position])
    }

    private class DiffUtilCallback(private val oldList: ArrayList<Uri>,
        private val newList: ArrayList<Uri>) : DiffUtil.Callback() {

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
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}