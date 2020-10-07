package app.kotlin.notekins.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import app.kotlin.notekins.R
import app.kotlin.notekins.errors.NoAuthException
import app.kotlin.notekins.ui.mainActivity.MainActivity
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.getViewModel
import kotlin.coroutines.CoroutineContext

class SplashActivity : AppCompatActivity(), CoroutineScope {

    lateinit var viewModel: SplashViewModel
    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Main + Job()
    }

    companion object {
        private const val RC_SIGN_IN = 4242
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()

    }


    override fun onResume() {
        super.onResume()
        launch {
            try {
                viewModel.requestUser().await()?.let {
                    startMainActivity()
                } ?: throw NoAuthException()
            } catch (t : NoAuthException) {
                startLogin()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            startMainActivity()
        }
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

        startActivityForResult(intent, RC_SIGN_IN)
    }
}
