package app.kotlin.notekins.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.kotlin.notekins.entity.User
import app.kotlin.notekins.errors.NoAuthException
import app.kotlin.notekins.firestore.NotesRepository

class SplashViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    private val hiddenUser = MutableLiveData<User>()
    private val authErrorMutable = MutableLiveData<Throwable>()


    init {
        notesRepository.getCurrentUser().observeForever {

            try {
                if (it != null) {
                    hiddenUser.value = it
                } else {
                    throw NoAuthException()
                }
            } catch(t: Throwable) {
                authErrorMutable.value = t
            }

    }
}
    val user: LiveData<User> = hiddenUser
    val authError: LiveData<Throwable> = authErrorMutable

}