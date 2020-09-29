package app.kotlin.notekins.firestore

import androidx.lifecycle.LiveData
import app.kotlin.notekins.entity.Note
import app.kotlin.notekins.entity.User


interface DataProvider {
    fun getCurrentUser() : LiveData<User?>
    fun subscribeToAllNotes() : LiveData<NoteResult>
    fun saveNote(note: Note) : LiveData<NoteResult>
    fun delete(note: Note) : LiveData<NoteResult>
}
