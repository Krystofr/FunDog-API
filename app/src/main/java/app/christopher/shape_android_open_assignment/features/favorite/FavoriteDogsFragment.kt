package app.christopher.shape_android_open_assignment.features.favorite

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import app.christopher.shape_android_open_assignment.data.Favorite
import app.christopher.shape_android_open_assignment.databinding.FragmentFavoriteDogsBinding
import app.christopher.shape_android_open_assignment.features.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteDogsFragment : Fragment(), FavoritesAdapter.OnItemClickListener {
    private lateinit var viewModel: MainViewModel
    private var binding: FragmentFavoriteDogsBinding? = null
    private var favoritesAdapter: FavoritesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        binding = FragmentFavoriteDogsBinding.inflate(inflater, container, false)

        favoritesAdapter = FavoritesAdapter(this, requireContext())
        //init view model
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setUpRecyclerView()
        getDogFavorites()
        return binding!!.root
    }

    private fun getDogFavorites() = binding?.apply {
        //get favorite breeds
        viewModel.favoriteBreeds.observe(viewLifecycleOwner) {
            favoritesAdapter?.submitList(it)
            progress.isVisible = false
        }
    }

    override fun onItemClicked(favorite: Favorite) {
        //Remove favorite from Room and show Snack bar
        viewModel.removeFromFavorite(favorite)
        view?.let { Snackbar.make(it, "Favorite removed", Snackbar.LENGTH_SHORT).show() }
    }

    private fun setUpRecyclerView() = binding?.recyclerView?.apply {
        adapter = favoritesAdapter
        layoutManager = GridLayoutManager(requireContext(), 2)
        addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }
}
