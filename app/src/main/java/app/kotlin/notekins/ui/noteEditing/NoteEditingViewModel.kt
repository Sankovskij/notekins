package app.kotlin.notekins.ui.noteEditing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.kotlin.notekins.model.Note
import app.kotlin.notekins.ui.listOfNotes.ListOfNotesViewModel

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


}