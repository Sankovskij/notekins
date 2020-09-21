package app.kotlin.notekins.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.kotlin.notekins.firestore.NoteResult
import app.kotlin.notekins.firestore.NotesRepository

object Model {

    private val notes = MutableLiveData<List<Note>>()

    fun getNotes(): LiveData<List<Note>> {
        when (val result = NotesRepository.getNotes()) {
            is NoteResult.Success<*> -> notes.value = result.data as? List<Note>
        }
        return notes
    }


    private fun saveNote(note: Note) {
        NotesRepository.saveNote(note)
    }

    fun addOrReplace(note: Note) {
    saveNote(note)
}


}

