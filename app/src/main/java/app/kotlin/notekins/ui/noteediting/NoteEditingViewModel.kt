package app.kotlin.notekins.ui.noteediting

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.kotlin.notekins.firestore.NotesRepository
import app.kotlin.notekins.entity.Note
import app.kotlin.notekins.ui.listofnotes.ListOfNotesViewModel
import kotlinx.android.synthetic.main.note_editing_fragment.*
import java.util.*

class NoteEditingViewModel(val notesRepository: NotesRepository) : ViewModel() {

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

    fun saveNote(note : Note, title : Editable?, text : Editable, color : Note.Color) {
        title?.let {
            if (it.length < 3) return
        }
        notesRepository.saveNote(note.copy(
            title = title.toString(),
            text = text.toString(),
            lastChange = Date(),
            color = color))
    }

    fun deleteNote() {
        editingNote.let {
            notesRepository.delete(it)

        }

    }
    /*pendingNote?.let {
        notesRepository.deleteNote(it.id).observeForever { result ->
            result ?: return@observeForever
            pendingNote = null
            when (result) {
                is NoteResult.Success<*> -> viewStateLiveData.value = NoteViewState(NoteViewState.Data(isDeleted = true))
                is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = result.error)
            }
        }
    }*/
}