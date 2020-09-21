package app.kotlin.notekins.ui.listOfNotes

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.kotlin.notekins.R
import app.kotlin.notekins.model.Model
import app.kotlin.notekins.model.Note
import app.kotlin.notekins.ui.noteEditing.NoteEditingFragment

class ListOfNotesViewModel : ViewModel() {

    private val hiddenNotes = MutableLiveData<List<Note>>()

    companion object {
        lateinit var editingNoteLiveData: LiveData<Note>
    }

    init {
        Model.getNotes().observeForever {
            hiddenNotes.value = it
        }
    }

    val notes: LiveData<List<Note>> = hiddenNotes

    fun openEditingNoteFragment(fragmentManager: FragmentManager) {
        fragmentManager
            .beginTransaction()
            .replace(R.id.frame_for_fragment, NoteEditingFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun sendNote (note: Note = Note()) {
        val editingNoteMutableLiveData = MutableLiveData<Note>().apply {
            this.value = note
        }
        editingNoteLiveData = editingNoteMutableLiveData
    }
}