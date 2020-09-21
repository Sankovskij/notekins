package app.kotlin.notekins.ui.mainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import app.kotlin.notekins.R
import app.kotlin.notekins.ui.listOfNotes.ListOfNotesFragment

lateinit var viewModel: ViewModel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel =
            ViewModelProvider(this).get(ViewModel::class.java)

        // 1
        if (savedInstanceState == null) {
            // 2
            supportFragmentManager
                // 3
                .beginTransaction()
                // 4
                .add(R.id.frame_for_fragment, ListOfNotesFragment.newInstance(), "listOfNotes")
                // 5
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}