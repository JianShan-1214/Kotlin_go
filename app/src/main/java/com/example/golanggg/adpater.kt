package com.example.golanggg


import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.item.view.*



class MyAdapter constructor(private val layout:Int, private val data: IntArray, private val context:Context):BaseAdapter() {
    override fun getCount() = data.size
    override fun getItem(p: Int) = data[p]
    override fun getItemId(p: Int) = 0L
    override fun getView(position: Int, converView: View?, parent: ViewGroup?): View {
        val view = View.inflate(parent?.context ,layout, null)
        when(data[position]){
            0-> view.img.setImageResource(R.drawable.no)
            else-> view.img.setImageResource(R.drawable.up)
        }
        view.img.setOnClickListener {
            if(data[position] == 1){
                view.img.setImageResource(R.drawable.down)
                Thread {
                    (context as MainActivity).score()
                    Thread.sleep(300)
                    view.img.setImageResource(R.drawable.no)
                }.start()
            }
        }
        return view
    }
}