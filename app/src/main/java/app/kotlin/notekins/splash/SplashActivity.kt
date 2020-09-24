package app.kotlin.notekins.splash

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import app.kotlin.notekins.R
import app.kotlin.notekins.errors.NoAuthException
import app.kotlin.notekins.firestore.NotesRepository
import app.kotlin.notekins.ui.mainActivity.MainActivity
import app.kotlin.notekins.ui.mainActivity.ViewModel
import app.kotlin.notekins.ui.mainActivity.viewModel
import com.firebase.ui.auth.AuthUI

class SplashActivity : AppCompatActivity() {


    companion object {
        private const val RC_SIGN_IN = 4242
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.SplashTheme)
        super.onCreate(savedInstanceState)

        NotesRepository.getCurrentUser().observeForever {
            if (it == null) {
                startLogin()
            }
            startMainActivity()

        }


    }


    private fun startMainActivity(){
        MainActivity.start(this)
        finish()
    }

    private fun startLogin() {
        val providers = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setLogo(R.drawable.android_robot)
            .setTheme(R.style.LoginStyle)
            .setAvailableProviders(providers)
            .build()

        startActivity(intent)
    }


}