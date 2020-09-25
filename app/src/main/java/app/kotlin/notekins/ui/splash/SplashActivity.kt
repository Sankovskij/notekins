package app.kotlin.notekins.ui.splash

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import app.kotlin.notekins.R
import app.kotlin.notekins.ui.mainActivity.MainActivity
import com.firebase.ui.auth.AuthUI
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: SplashViewModel by viewModel()

        viewModel.user.observe(this) {
            it?.let {
                startMainActivity()
            } ?: run {
                try {
                    startLogin()
                } catch (t: Throwable) {
                    Toast.makeText(this, "Что-то не так с авторизацией", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.authError.observe(this) {
            it?.let {
                Toast.makeText(this, "Настоящий АУФ ЕРРОР", Toast.LENGTH_SHORT).show()
            }
        }
    }




    override fun onResume() {
        super.onResume()


    }







        private fun startMainActivity() {
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
