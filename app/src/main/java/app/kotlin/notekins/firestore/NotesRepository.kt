package app.kotlin.notekins.firestore

import app.kotlin.notekins.entity.Note


class NotesRepository(val dataProvider: DataProvider) {



    fun getCurrentUser() = dataProvider.getCurrentUser()
    fun getNotes() = dataProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = dataProvider.saveNote(note)
    fun delete(note: Note) = dataProvider.delete(note)


}
