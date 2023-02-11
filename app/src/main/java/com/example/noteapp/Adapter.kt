package com.example.noteapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.fragments.list.ListFragmentDirections
import com.example.noteapp.fragments.update.UpdateFragment
import kotlinx.android.synthetic.main.note_item.view.*

class Adapter : RecyclerView.Adapter<Adapter.NoteViewHolder>()  {

    private var noteList = emptyList<com.example.noteapp.model.Note>()

    inner class  NoteViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(layout)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        var currentItem = noteList[position]
        holder.itemView.apply {
            tvTitle.text = currentItem.title
            tvNote.text = currentItem.text
            itemLayout.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                holder.itemView.findNavController().navigate(action)
            }
        }

    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun setData(note: List<com.example.noteapp.model.Note>) {
        this.noteList = note
        notifyDataSetChanged()
    }

}