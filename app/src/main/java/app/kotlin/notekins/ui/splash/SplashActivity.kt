package app.kotlin.notekins.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import app.kotlin.notekins.R
import app.kotlin.notekins.firestore.NotesRepository
import app.kotlin.notekins.ui.mainActivity.MainActivity
import com.firebase.ui.auth.AuthUI

class SplashActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        NotesRepository.getCurrentUser().observeForever {
            if (it == null) {
                try {
                    startLogin()
                } catch(t: Throwable) {
                    Toast.makeText (this, "Что-то не так с авторизацией", Toast.LENGTH_SHORT).show()
                }


            } else startMainActivity()
        }
    }

    private fun startMainActivity(){
        MainActivity.start(this)
        finish()
    }

    fun startLogin() {
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