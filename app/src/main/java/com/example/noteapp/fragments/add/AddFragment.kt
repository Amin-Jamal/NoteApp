package com.example.noteapp.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.model.Note
import com.example.noteapp.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {

    lateinit var mNoteViewModel : NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_add, container, false)
        // view-model
        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        layout.btnUpdate.setOnClickListener {

            insertDataToDatabase()

        }

        return layout
    }

    fun insertDataToDatabase() {
        val title = etUpdateTitle.text.toString()
        val text = etUpdateNote.text.toString()

        if (inputCheck(title,text)){

            var note = Note(0, title, text)
            mNoteViewModel.addNote(note)
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
            Toast.makeText(requireContext(),"Successfully added",Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(requireContext(),"Please fill all the fields",Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(title: String, text: String) : Boolean {
        return !(title.isEmpty() && text.isEmpty())
    }

}