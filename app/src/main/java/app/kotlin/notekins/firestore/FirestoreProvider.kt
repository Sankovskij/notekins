package app.kotlin.notekins.firestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.kotlin.notekins.entity.Note
import app.kotlin.notekins.entity.User
import app.kotlin.notekins.errors.NoAuthException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreProvider : DataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"
    }
    private val currentUser
        get() = FirebaseAuth.getInstance().currentUser
    private val store by lazy { FirebaseFirestore.getInstance() }

    private val notesReference
        get() = currentUser?.let {
            store.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
        } ?: throw NoAuthException()



    override fun getCurrentUser(): LiveData<User?> = MutableLiveData<User>().apply {
        value = currentUser?.let {
            User(it.displayName ?: "", it.email ?: "" )
        }
    }

    override fun subscribeToAllNotes(): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {
        try {
            notesReference.addSnapshotListener { snapshot, error ->
                error?.let {
                    } ?: snapshot?.let {
                        val notes = snapshot.documents.mapNotNull { it.toObject(Note::class.java) }
                        value = NoteResult.Success(notes)
                    }
            }
        } catch (t: Throwable) {
        value = NoteResult.Error(t)
        }
    }

    override fun saveNote(note: Note): LiveData<NoteResult>  = MutableLiveData<NoteResult>().apply {
        try {
            notesReference.document(note.id).set(note).addOnSuccessListener {
                value = NoteResult.Success(note)
            }.addOnFailureListener {
                value = NoteResult.Error(it)
            }
        } catch (t: Throwable) {
            value = NoteResult.Error(t)
        }
    }
}
