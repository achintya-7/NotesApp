package com.example.notes_k

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), INoteRVAdapter {

    private lateinit var viewModel: NoteViewModal
    private lateinit var editText: EditText
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        editText = findViewById(R.id.input)
        val adapter = NotesRVAdapter(this, this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModal::class.java)
        viewModel.allNotes.observe(this, Observer {list ->
            list?.let {
                adapter.updateNewList(it)
            }
        })

    }

    override fun OnItemClicked(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show()
    }

    fun submitData(view: View) {
        val noteText = editText.text.toString()
        if(noteText.isNotEmpty()) {
            viewModel.insertNote(Note(noteText))
        }
        editText.setText("")
    }

}