package app.kotlin.notekins.firestore

import app.kotlin.notekins.entity.Note


class NotesRepository(val dataProvider: DataProvider) {



    suspend fun getCurrentUser() = dataProvider.getCurrentUser()
    suspend fun getNotes() = dataProvider.subscribeToAllNotes()
    suspend fun saveNote(note: Note) = dataProvider.saveNote(note)
    suspend fun delete(note: Note) = dataProvider.delete(note)


}
