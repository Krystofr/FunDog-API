package app.christopher.shape_android_open_assignment.features.favorite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.christopher.shape_android_open_assignment.R
import app.christopher.shape_android_open_assignment.data.Favorite
import app.christopher.shape_android_open_assignment.databinding.FavoriteItemBinding
import com.bumptech.glide.Glide


class FavoritesAdapter(private val itemClickListener: OnItemClickListener, private val context: Context) :
    ListAdapter<Favorite, FavoritesAdapter.FavoriteViewHolder>(BreedsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem, itemClickListener, context)
        }
    }

    class FavoriteViewHolder(private val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favorite: Favorite, itemClickListener: OnItemClickListener, context: Context) {
            binding.apply {
                Glide.with(context)
                    .load(favorite.image)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(image)
                breed.text = "${favorite.breed} ☼ Breed"
                unlike.setOnClickListener {
                    itemClickListener.onItemClicked(favorite)
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

    class BreedsComparator : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite) =
            oldItem.id == newItem.id
    }

    interface OnItemClickListener {
        fun onItemClicked(favorite: Favorite)
    }
}