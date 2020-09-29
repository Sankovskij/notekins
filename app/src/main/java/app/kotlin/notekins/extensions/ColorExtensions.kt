package app.kotlin.notekins.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import app.kotlin.notekins.R
import app.kotlin.notekins.entity.Note

inline fun Note.Color.getColorInt(context: Context) = ContextCompat.getColor(context, getColorRes())

inline fun Note.Color.getColorRes() = when (this) {
    Note.Color.VIOLET -> R.color.color_violet
    Note.Color.YELLOW -> R.color.color_yellow
    Note.Color.RED -> R.color.color_red
    Note.Color.PINK -> R.color.color_pink
    Note.Color.GREEN -> R.color.color_green
    else ->  R.color.color_white
}