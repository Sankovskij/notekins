package app.kotlin.notekins.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import app.kotlin.notekins.R
import app.kotlin.notekins.ui.mainActivity.MainActivity
import com.firebase.ui.auth.AuthUI
import org.koin.android.viewmodel.ext.android.getViewModel

class SplashActivity : AppCompatActivity() {

    lateinit var viewModel: SplashViewModel

    companion object {
        private const val RC_SIGN_IN = 4242
    }

    private val authobserver = Observer<app.kotlin.notekins.entity.User> { result ->
        result?.let {
            startMainActivity()
        }?: return@Observer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
        viewModel.user.observe(this, authobserver)
        startLogin()
    }


    override fun onResume() {
        super.onResume()
        viewModel.user.observe(this, authobserver)
        }


    override fun onPause() {
        super.onPause()
        viewModel.user.removeObserver(authobserver)
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
