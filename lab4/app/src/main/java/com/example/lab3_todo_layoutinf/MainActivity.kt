package com.example.lab3_todo_layoutinf

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout.view.*

/*
class Task{
    private var text : String = ""
    private var status : Boolean = false

    constructor(_text : String, _status : Boolean){
        text = _text
        status = _status
    }

    fun setStatus(inDone : Boolean){
        status = inDone
    }

    fun getText() : String{
        return text
    }

    fun getStatus() : Boolean{
        return status
    }
}*/

class MainActivity : AppCompatActivity() {

    private lateinit var submitButton : Button
    private lateinit var input: EditText
    private lateinit var list: ListView
    private lateinit var adapter : TaskAdapter

    private val viewModel : ListViewModel by lazy{
        ViewModelProvider(this).get(ListViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //consider case in which it list is empty
        if(item?.itemId == R.id.share && !viewModel.list.isEmpty()){
            //perform action
            val it = viewModel.list.size
            var str : String = ""
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"

                for(i in 0 until it) {
                    var status = "0"
                    if(viewModel.list[i].getStatus()){
                        status = "1"
                    }
                    str = str + viewModel.list[i].getText()+ "\t" + status + "\n"
                }
                putExtra(Intent.EXTRA_TEXT, str)
            }
            if(intent.resolveActivity(packageManager) != null){
                startActivity(Intent.createChooser(intent, "Share to "))
                Toast.makeText(this, "testing menu", Toast.LENGTH_SHORT).show()
            }
            return true
        }
        else {
            Toast.makeText(this, "Nothing to Share", Toast.LENGTH_SHORT).show()
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = TaskAdapter(this, viewModel.list)

        submitButton = findViewById(R.id.button)
        input = findViewById(R.id.editText)
        list = findViewById(R.id.list_view)
        list.adapter = adapter

        editText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                submit()
                return@OnKeyListener true
            }
            false
        })

        submitButton.setOnClickListener {
            submit()
        }



        list.setOnItemLongClickListener(OnItemLongClickListener { adapterView, view, i, l ->
            confirmRemoval(i)
            true
        })

    }

    private fun confirmRemoval(i : Int){
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage("Do you want to delete this entry?")
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> viewModel.list.remove(viewModel.list[i])
                adapter.notifyDataSetChanged()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("Warning")
        alert.show()
    }

    private fun submit(){

        if(!input.text.isNullOrBlank()) {
            val mtask = Task(editText.text.toString(), false)
            viewModel.addTask(mtask)
            input.text.clear()
        }
        else{
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
        }
        adapter.notifyDataSetChanged()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        val size = viewModel.list.size
        for(i in 0 until size) {
            Log.i("restore instance", "text: "+viewModel.list[i].getText() + " status: "+viewModel.list[i].getStatus().toString())
        }
        adapter = TaskAdapter(this, viewModel.list)
        list.adapter = adapter
        adapter.notifyDataSetChanged()

    }


}
