package app.kotlin.notekins.firestore

import app.kotlin.notekins.entity.Note


object NotesRepository {

    private val dataProvider: DataProvider = FirestoreProvider()

    fun getCurrentUser() = dataProvider.getCurrentUser()
    fun getNotes() = dataProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = dataProvider.saveNote(note)


}
