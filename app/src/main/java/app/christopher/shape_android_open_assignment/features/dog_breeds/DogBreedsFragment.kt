package app.christopher.shape_android_open_assignment.features.dog_breeds

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import app.christopher.shape_android_open_assignment.databinding.FragmentDogBreedsBinding
import app.christopher.shape_android_open_assignment.features.MainViewModel
import app.christopher.shape_android_open_assignment.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList

@AndroidEntryPoint
class DogBreedsFragment : Fragment(), DogBreedsAdapter.OnItemClickListener {

    private lateinit var viewModel: MainViewModel
    private var binding: FragmentDogBreedsBinding? = null
    private lateinit var navController: NavController
    private var dbAdapter: DogBreedsAdapter? = null
    private var isFabVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDogBreedsBinding.inflate(inflater, container, false)

        //init view model
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        navController = findNavController()

        extendedFab()
        fabFunction()
        dbAdapter = DogBreedsAdapter(this)
        setUpRecyclerView()
        getDogBreeds()
        return binding!!.root
    }

    private fun getDogBreeds() = binding?.apply {
        viewModel.dogBreeds.observe(viewLifecycleOwner) { result ->
            if (result.data != null) {
                //convert message object to array list of strings
                val breeds = ArrayList<String>()
                result.data.message!!.javaClass.declaredFields.forEach {
                    breeds.add(it.name)
                }
                dbAdapter!!.submitList(breeds)
                progress.isVisible = result is Resource.Loading
            }
        }
    }

    override fun onItemClicked(breed: String) {
        //send to dog screen
        val action =
            DogBreedsFragmentDirections.actionDogBreedsFragmentToBreedGalleryFragment(breed)
        navController.navigate(action)

    }

    private fun fabFunction() = binding?.apply {
        fabViewFavorites.apply {
            setOnClickListener {
                val action =
                    DogBreedsFragmentDirections.actionDogBreedsFragmentToFavoriteDogsFragment()
                navController.navigate(action)
            }
        }
    }

    private fun extendedFab() = binding?.apply {
        extFab.shrink()
        extFab.apply {
            setOnClickListener {
                if (!isFabVisible) {
                    fabViewFavorites.apply {
                        show()
                        showMotionSpec
                    }
                    viewFavoritesActionText.visibility = View.VISIBLE
                    extendMotionSpec
                    extend()

                    isFabVisible = true
                } else {
                    fabViewFavorites.apply {
                        hide()
                        hideMotionSpec
                    }
                    viewFavoritesActionText.visibility = View.GONE
                    shrink()
                    shrinkMotionSpec

                    isFabVisible = false
                }
            }
        }
    }
    private fun setUpRecyclerView() = binding?.recyclerView?.apply {
        adapter = dbAdapter
        layoutManager = LinearLayoutManager(requireContext())
        addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }
}