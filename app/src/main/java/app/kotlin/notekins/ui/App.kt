package app.kotlin.notekins.ui

import android.app.Application
import app.kotlin.notekins.ListOfNotesModule
import app.kotlin.notekins.SplashModule
import app.kotlin.notekins.appModule
import app.kotlin.notekins.noteEditingModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin(this, listOf(appModule, noteEditingModule, ListOfNotesModule, SplashModule))
    }
}

