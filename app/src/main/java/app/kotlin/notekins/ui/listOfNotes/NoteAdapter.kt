package app.kotlin.notekins.ui.listOfNotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import app.kotlin.notekins.R
import app.kotlin.notekins.model.Note
import kotlinx.android.synthetic.main.item_view.view.*

class NoteAdapter (val onItemClick: ((Note) -> Unit)? = null) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    var notes: List<Note> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(notes[position])

    override fun getItemCount() = notes.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) = with(itemView as CardView) {
            title.text = note.title
            body.text = note.text
            val color = when (note.color) {
                Note.Color.WHITE -> R.color.color_white
                Note.Color.VIOLET -> R.color.color_violet
                Note.Color.YELLOW -> R.color.color_yellow
                Note.Color.RED -> R.color.color_red
                Note.Color.PINK -> R.color.color_pink
                Note.Color.GREEN -> R.color.color_green
                Note.Color.BLUE -> R.color.color_blue
            }
            setCardBackgroundColor(ContextCompat.getColor(itemView.context, color))

            itemView.setOnClickListener {
                onItemClick?.invoke(note)
            }
        }



    }
}

