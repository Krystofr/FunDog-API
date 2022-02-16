package app.christopher.shape_android_open_assignment.features.breed_gallery

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.christopher.shape_android_open_assignment.R
import app.christopher.shape_android_open_assignment.databinding.BreedImageItemBinding
import com.bumptech.glide.Glide

class BreedGalleryAdapter(private val itemClickListener: OnItemClickListener, private val context: Context) :
    ListAdapter<String, BreedGalleryAdapter.BreedsGalleryViewHolder>(BreedsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedsGalleryViewHolder {
        val binding =
            BreedImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreedsGalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreedsGalleryViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem, itemClickListener, context)
        }
    }

    class BreedsGalleryViewHolder(private val binding: BreedImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String, itemClickListener: OnItemClickListener, context: Context) {
            binding.apply {
                Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(image)
                like.setOnClickListener {
                    itemClickListener.onItemClicked(imageUrl)
                    textLiked.visibility = View.VISIBLE
                }
                image.setOnLongClickListener {
                    itemClickListener.let {
                        val shareIntent : Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            type = "image/jpeg"
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share to..."))
                    }
                    true
                }
                share.setOnClickListener {
                    itemClickListener.let {
                        val shareIntent : Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            type = "image/jpeg"
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share to..."))
                    }
                }
            }
        }
    }

    class BreedsComparator : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem
    }

    interface OnItemClickListener {
        fun onItemClicked(breed: String)
    }
}