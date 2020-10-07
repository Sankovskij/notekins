package app.kotlin.notekins.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.kotlin.notekins.entity.User
import app.kotlin.notekins.errors.NoAuthException
import app.kotlin.notekins.firestore.NotesRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SplashViewModel(private val notesRepository: NotesRepository) : ViewModel(), CoroutineScope {


    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Default + Job()
    }


    fun requestUser() = async {
        notesRepository.getCurrentUser()
    }
}