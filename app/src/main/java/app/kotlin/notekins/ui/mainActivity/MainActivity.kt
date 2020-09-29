package app.kotlin.notekins.ui.mainActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import app.kotlin.notekins.R
import app.kotlin.notekins.firestore.NotesRepository
import app.kotlin.notekins.ui.listofnotes.ListOfNotesFragment
import app.kotlin.notekins.ui.logoutdialog.LogoutDialog
import app.kotlin.notekins.ui.splash.SplashActivity
import com.firebase.ui.auth.AuthUI


class MainActivity : AppCompatActivity() {


    companion object {
        fun start(context: Context) = Intent(context, MainActivity::class.java).apply {
            context.startActivity(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            // 3
            .beginTransaction()
            // 4
            .add(R.id.frame_for_fragment, ListOfNotesFragment.newInstance(), "listOfNotes")
            // 5
            .commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
        MenuInflater(this).inflate(R.menu.main, menu).let { true }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        R.id.logout -> {
            showLogoutDialog()
            true
        }
        else -> super.onOptionsItemSelected(item)

    }

    fun showLogoutDialog() {
        supportFragmentManager.findFragmentByTag(LogoutDialog.TAG) ?: LogoutDialog(onLogout()).show(
            supportFragmentManager,
            LogoutDialog.TAG
        )
    }

    private fun onLogout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                startActivity(Intent(this, SplashActivity::class.java))
                finish()
            }
    }
}
