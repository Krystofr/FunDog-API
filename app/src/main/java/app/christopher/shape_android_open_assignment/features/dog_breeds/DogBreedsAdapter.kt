package app.christopher.shape_android_open_assignment.features.dog_breeds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.christopher.shape_android_open_assignment.databinding.BreedItemBinding

class DogBreedsAdapter(private val itemClickListener: OnItemClickListener) :
    ListAdapter<String, DogBreedsAdapter.DogBreedsViewHolder>(DogBreedsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogBreedsViewHolder {
        val binding =
            BreedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DogBreedsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogBreedsViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem, itemClickListener)
        }
    }

    class DogBreedsViewHolder(private val binding: BreedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dogbreed: String, itemClickListener: OnItemClickListener) {
            binding.apply {
                breed.text = dogbreed
                breed.setOnClickListener {
                    itemClickListener.onItemClicked(dogbreed)
                }
            }
        }
    }

    class DogBreedsComparator : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem
    }

    interface OnItemClickListener {
        fun onItemClicked(breed:String)
    }
}