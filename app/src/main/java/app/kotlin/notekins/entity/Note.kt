package app.kotlin.notekins.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note (
    var id: String = UUID.randomUUID().toString(),
    var title: String = "",
    var text: String = "",
    var color: Color = Color.WHITE,
    var lastChange: Date = Date()
) : Parcelable {

    enum class Color {
        WHITE,
        VIOLET,
        YELLOW,
        RED,
        PINK,
        GREEN,
        BLUE
    }

    override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if(javaClass != other?.javaClass) return false
        other as Note
        if(id != other.id) return false
        return true
    }


}