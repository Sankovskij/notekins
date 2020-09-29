package app.kotlin.notekins.ui.noteediting

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import org.koin.android.viewmodel.ext.android.viewModel
import app.kotlin.notekins.R
import app.kotlin.notekins.entity.Note
import app.kotlin.notekins.extensions.getColorInt
import kotlinx.android.synthetic.main.note_editing_fragment.*
import java.text.SimpleDateFormat
import java.util.*


class NoteEditingFragment : Fragment() {

    companion object {
        fun newInstance() = NoteEditingFragment()
    }

    private val dateFormat = "dd.MM.yy HH.mm"
    val viewModel: NoteEditingViewModel by viewModel()
    var color: Note.Color = Note.Color.WHITE


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.note_editing_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.getEditingNoteLiveData().observe(viewLifecycleOwner, {
            initView(it)
            it.initToolbar()
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
                viewModel.saveNote(note, titleEt.text, bodyEt.text, color)
            }
        })
        bodyEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                viewModel.saveNote(note, titleEt.text, bodyEt.text, color)
            }
        })

        colorPicker.onColorClickListener = {
            color = it
            this.context?.let { it1 -> it.getColorInt(it1) }?.let { it2 ->
                toolbar.setBackgroundColor(
                    it2
                )
            }
            viewModel.saveNote(note, titleEt.text, bodyEt.text, color)
        }
    }

    private fun Note.initToolbar() {
        (activity as AppCompatActivity).let { appCompatActivity ->
            appCompatActivity.setSupportActionBar(toolbar)
            appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            appCompatActivity.supportActionBar?.title = this.let {
                SimpleDateFormat(dateFormat, Locale.getDefault()).format(it.lastChange)
            } ?: getString(R.string.new_note)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.editmain, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.palette -> {
            togglePallete()
            true
        }
        R.id.delete -> {
            viewModel.deleteNote()
            activity?.onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun togglePallete() {
        if (colorPicker.isOpen) {
            colorPicker.close()
        } else {
            colorPicker.open()
        }
    }


}



