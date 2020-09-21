package app.kotlin.notekins.ui.listOfNotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import app.kotlin.notekins.R
import kotlinx.android.synthetic.main.list_of_notes_fragment.*

class ListOfNotesFragment : DialogFragment() {

    companion object {
        fun newInstance() = ListOfNotesFragment()
    }

    private lateinit var viewModel: ListOfNotesViewModel
    lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_of_notes_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view ,savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListOfNotesViewModel::class.java)
        rv_notes.layoutManager = GridLayoutManager(context, 2)

        adapter = NoteAdapter{
            viewModel.sendNote(it)
            viewModel.openEditingNoteFragment(parentFragmentManager)

        }

        rv_notes.adapter = adapter
        viewModel.notes.observe(viewLifecycleOwner, Observer {
            adapter.notes = it
        })

        fab.setOnClickListener {
            viewModel.sendNote()
            viewModel.openEditingNoteFragment(parentFragmentManager)
        }
    }


}