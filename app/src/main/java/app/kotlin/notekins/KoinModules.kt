package app.kotlin.notekins

import app.kotlin.notekins.firestore.DataProvider
import app.kotlin.notekins.firestore.FirestoreProvider
import app.kotlin.notekins.firestore.NotesRepository
import app.kotlin.notekins.ui.listofnotes.ListOfNotesViewModel
import app.kotlin.notekins.ui.noteediting.NoteEditingViewModel
import app.kotlin.notekins.ui.splash.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module.module
import org.koin.android.viewmodel.ext.koin.viewModel

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single<DataProvider> { FirestoreProvider(get(), get()) }
    single { NotesRepository(get()) }
}

val noteEditingModule = module {
    viewModel { NoteEditingViewModel(get()) }
}

val ListOfNotesModule = module {
    viewModel { ListOfNotesViewModel(get()) }
}

val SplashModule = module {
    viewModel { SplashViewModel(get()) }
}
