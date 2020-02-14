package com.example.lab3_todo_layoutinf

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView

class TaskAdapter(private val context : Context, private val dataSource : MutableList<Task>) : BaseAdapter() {
    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private class ViewHolder{
        lateinit var textV : TextView
        lateinit var boxy : CheckBox
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView : View
        val holder : ViewHolder

        if(convertView == null) {
            rowView = inflater.inflate(R.layout.layout, parent, false)
            holder = ViewHolder()
            holder.textV = rowView.findViewById(R.id.textTask) as TextView
            holder.boxy = rowView.findViewById(R.id.checkBox) as CheckBox
            rowView.tag = holder
        }
        else{
            rowView = convertView
            holder = convertView.tag as ViewHolder
        }

        holder.textV.text = dataSource[position].getText()
        holder.boxy.setOnCheckedChangeListener(null)
        holder.boxy.setChecked(dataSource[position].getStatus())


        holder.boxy.setOnCheckedChangeListener { _, isChecked ->
            dataSource[position].setStatus(isChecked)
            holder.boxy.setChecked(dataSource[position].getStatus())
            Log.i(
                "boxy",
                "My checked val: " + isChecked.toString() + " val in task " + dataSource[position].getStatus() + " text: " + dataSource[position].getText()
            )
        }
        return rowView
    }

}