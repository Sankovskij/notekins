package app.kotlin.notekins.ui.noteEditing

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.kotlin.notekins.R
import app.kotlin.notekins.firestore.NotesRepository
import app.kotlin.notekins.model.Note
import kotlinx.android.synthetic.main.note_editing_fragment.*
import java.text.SimpleDateFormat
import java.util.*


class NoteEditingFragment : Fragment() {

    companion object {
        fun newInstance() = NoteEditingFragment()
    }

    private val dateFormat = "dd.MM.yy HH.mm"
    private lateinit var viewModel: NoteEditingViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.note_editing_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(NoteEditingViewModel::class.java)
        viewModel.getEditingNoteLiveData().observe(viewLifecycleOwner, Observer {
            initView(it)
            initToolbar(it)
        })
    }

    private fun initView(note : Note) {
        note?.let {
            titleEt.setText(it.title)
            bodyEt.setText(it.text)
            val color = when (it.color) {
                Note.Color.WHITE -> R.color.color_white
                Note.Color.VIOLET -> R.color.color_violet
                Note.Color.YELLOW -> R.color.color_yellow
                Note.Color.RED -> R.color.color_red
                Note.Color.PINK -> R.color.color_pink
                Note.Color.GREEN -> R.color.color_green
                Note.Color.BLUE -> R.color.color_blue
            }
            toolbar.setBackgroundColor(ResourcesCompat.getColor(resources, color, null))
        }
        titleEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                saveNote(note)
            }
        })
        bodyEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                saveNote(note)
            }
        })
    }

    private fun initToolbar(note : Note) {
        (activity as AppCompatActivity)?.let { appCompatActivity ->
            appCompatActivity.setSupportActionBar(toolbar)
            appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            appCompatActivity.supportActionBar?.title = note?.let {
                SimpleDateFormat(dateFormat, Locale.getDefault()).format(it.lastChange)
            } ?: getString(R.string.new_note)
        }
    }

    private fun saveNote(note : Note) {
        titleEt.text?.let {
            if (it.length < 3) return
        } ?: return
        NotesRepository.saveNote(note.copy(
            title = titleEt.text.toString(),
            text = bodyEt.text.toString(),
            lastChange = Date()))
    }
}



