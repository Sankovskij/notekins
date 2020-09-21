package app.kotlin.notekins.firestore

import androidx.lifecycle.LiveData
import app.kotlin.notekins.model.Note


interface DataProvider {
    fun subscribeToAllNotes() : LiveData<NoteResult>
    fun saveNote(note: Note) : LiveData<NoteResult>
    fun getNoteById(id: String) : LiveData<NoteResult>
}
