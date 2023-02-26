package com.example.noteapp.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.noteapp.R
import com.example.noteapp.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    lateinit var mNoteViewmodel : NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layout = inflater.inflate(R.layout.fragment_update, container, false)

        mNoteViewmodel = ViewModelProvider(this).get(NoteViewModel::class.java)

        layout.etUpdateTitle.setText(args.currentNote.title)
        layout.etUpdateNote.setText(args.currentNote.text)

        layout.btnUpdate.setOnClickListener {
            updateItem()
        }

        setHasOptionsMenu(true)

        return layout
    }

    private fun updateItem() {
        val title = etUpdateTitle.text.toString()
        val text = etUpdateNote.text.toString()
        if (inputCheck(title, text)){
            val updatedNote = com.example.noteapp.model.Note(args.currentNote.id, title, text)
            mNoteViewmodel.updateNote(updatedNote)
            Toast.makeText(requireContext(), "Note Updated!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please Fill Atleast One Field!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(title: String, text: String) : Boolean {
        return !(title.isEmpty() && text.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteMenu){
            deleteNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            mNoteViewmodel.deleteNote(args.currentNote)
            Toast.makeText(requireContext(), "Note Deleted", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Delete ${args.currentNote.title}")
        builder.setMessage("Are you sure? You want to delete this note?")
        builder.create().show()
    }

}