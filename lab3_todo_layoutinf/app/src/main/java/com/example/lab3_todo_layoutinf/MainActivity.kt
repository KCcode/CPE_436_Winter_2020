package com.example.lab3_todo_layoutinf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import org.intellij.lang.annotations.JdkConstants

class MainActivity : AppCompatActivity() {

    private lateinit var input: EditText
    private lateinit var list: ViewGroup


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input = findViewById(R.id.editText)
        list = findViewById(R.id.scrollChild)

        findViewById<Button>(R.id.button).setOnClickListener {
            if(!input.text.isNullOrBlank()) {

                val task = createListItem() //get view from layout.xml (ll, textview and checkbox)
                val textV : TextView = task.findViewById(R.id.textTask) //get the textview
                textV.text = editText.text //change text
                list.addView(task) //assign the layout to the Viewgroup INSIDE scrollview

                input.text.clear()
            }
            else{

                Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun createListItem() = layoutInflater.inflate(R.layout.layout, list, false)

}
