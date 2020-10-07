package app.kotlin.notekins.firestore
import app.kotlin.notekins.entity.Note
import app.kotlin.notekins.entity.User
import kotlinx.coroutines.channels.ReceiveChannel


interface DataProvider {

    suspend fun subscribeToAllNotes() : ReceiveChannel<NoteResult>
    suspend fun getCurrentUser() : User?
    suspend fun saveNote(note: Note) : Note
    suspend fun delete(note: Note)
}
