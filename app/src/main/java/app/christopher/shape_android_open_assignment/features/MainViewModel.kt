package app.christopher.shape_android_open_assignment.features

import androidx.lifecycle.*
import app.christopher.shape_android_open_assignment.data.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    var repository: DogsRepository,
) : ViewModel() {

    val dogBreeds = repository.getDogBreeds().asLiveData()
    val favoriteBreeds = repository.getFavorites()

    private val _breedName: MutableLiveData<String> by lazy { MutableLiveData() }
    fun setBreed(breed: String) {
        _breedName.value = breed
    }

    val breedImages: LiveData<BreedImageObject> = Transformations
        .switchMap(_breedName) {
            repository.getBreedImages(it)
        }

    fun setFav(favorite: Favorite) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertFavorite(favorite)
        }
    }

    fun removeFromFavorite(favorite: Favorite) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.removeFavorite(favorite)
        }
    }
}