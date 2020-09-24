package app.kotlin.notekins.ui.noteEditing

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.kotlin.notekins.firestore.NotesRepository
import app.kotlin.notekins.entity.Note
import app.kotlin.notekins.ui.listOfNotes.ListOfNotesViewModel
import java.util.*

class NoteEditingViewModel : ViewModel() {

    private var editingNote = Note()
    private val editingNoteMutableLiveData = MutableLiveData<Note>()
    private lateinit var editingNoteLiveData : LiveData<Note>

   init {
       ListOfNotesViewModel.editingNoteLiveData.observeForever {
           editingNote = it
           editingNoteMutableLiveData.value = it
           editingNoteLiveData = editingNoteMutableLiveData
       }
   }

    fun getEditingNoteLiveData() : LiveData<Note> {
        return editingNoteLiveData
    }

    fun saveNote(note : Note, title : Editable?, text : Editable) {
        title?.let {
            if (it.length < 3) return
        }
        NotesRepository.saveNote(note.copy(
            title = title.toString(),
            text = text.toString(),
            lastChange = Date()))
    }
}