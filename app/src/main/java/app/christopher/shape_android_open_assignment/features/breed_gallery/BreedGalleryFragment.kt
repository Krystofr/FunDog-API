package app.christopher.shape_android_open_assignment.features.breed_gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import app.christopher.shape_android_open_assignment.data.Favorite
import app.christopher.shape_android_open_assignment.databinding.FragmentBreedGalleryBinding
import app.christopher.shape_android_open_assignment.features.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedGalleryFragment : Fragment(), BreedGalleryAdapter.OnItemClickListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController
    private val breedArg: BreedGalleryFragmentArgs by navArgs()
    private var binding : FragmentBreedGalleryBinding? = null
    private var breedGalleryAdapter : BreedGalleryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBreedGalleryBinding.inflate(inflater, container, false)

        //init view model
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        navController = findNavController()

        breedGalleryAdapter = BreedGalleryAdapter(this, requireContext())
        viewModel.setBreed(breedArg.breed)

        setUpRecyclerView()
        getBreedImages()
        return binding!!.root
    }

    private fun getBreedImages() = binding?.apply {
        //get image object
        viewModel.breedImages.observe(viewLifecycleOwner) { result ->
            breedGalleryAdapter?.submitList(result.message)
            progress.isVisible = false
        }
    }
    override fun onItemClicked(breed: String) {
        //add dog to room database
        viewModel.setFav(Favorite(image = breed, breed = breedArg.breed))
        view?.let {
            Snackbar.make(it, ":)", Snackbar.LENGTH_SHORT).show() }
    }

    private fun setUpRecyclerView() = binding?.recyclerView?.apply {
        adapter = breedGalleryAdapter
        layoutManager = GridLayoutManager(requireContext(), 2)
        addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

}