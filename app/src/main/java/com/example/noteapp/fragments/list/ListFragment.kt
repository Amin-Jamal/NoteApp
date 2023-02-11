package com.example.noteapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.Adapter
import com.example.noteapp.R
import com.example.noteapp.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var layout = inflater.inflate(R.layout.fragment_list, container, false)

        //RecyclerView
        var recyclerView = layout.recyclerView
        var adapter = Adapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        //mNoteViewModel
        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        mNoteViewModel.readAllData.observe(viewLifecycleOwner, Observer {  user ->
            adapter.setData(user)
        })


        layout.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        setHasOptionsMenu(true)

        return layout
    }
        // Creating menu options
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteMenu){
            deleteAllNotes()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllNotes(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            mNoteViewModel.deleteAllNotes()
            Toast.makeText(requireContext(), "All Notes Deleted", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Delete Everything")
        builder.setMessage("Are you sure? You want to delete all notes?")
        builder.create().show()
    }

}