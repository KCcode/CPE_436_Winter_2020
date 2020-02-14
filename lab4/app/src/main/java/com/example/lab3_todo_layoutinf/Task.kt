package com.example.lab3_todo_layoutinf

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
}