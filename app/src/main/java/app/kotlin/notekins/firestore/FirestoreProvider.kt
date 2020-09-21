package app.kotlin.notekins.firestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.kotlin.notekins.model.Note
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreProvider : DataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
    }

    private val store = FirebaseFirestore.getInstance()
    private val notesReference = store.collection(NOTES_COLLECTION)

    override fun subscribeToAllNotes(): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        notesReference.addSnapshotListener { snapshot, error ->
            error?.let {
                result.value = NoteResult.Error(it)
            } ?: snapshot?.let {
                val notes = snapshot.documents.mapNotNull { it.toObject(Note::class.java) }
                result.value = NoteResult.Success(notes)
            }
        }
        return result
    }

    override fun saveNote(note: Note): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        notesReference.document(note.id).set(note).addOnSuccessListener {
            result.value = NoteResult.Success(note)
        }.addOnFailureListener {
            result.value = NoteResult.Error(it)
        }
        return result
    }


    override fun getNoteById(id: String): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        notesReference.document(id).get().addOnSuccessListener {
            result.value = NoteResult.Success(it.toObject(Note::class.java))
        }.addOnFailureListener {
            result.value = NoteResult.Error(it)
        }
        return result
    }
}
