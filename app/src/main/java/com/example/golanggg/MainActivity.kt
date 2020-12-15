package com.example.golanggg


import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var data = IntArray(9)
    var top = "按下 START 開始遊戲"
    var s:Int = 0
    var m:Int = 0
    var Sc = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        for(i in 0 until 9)
            data[i]=0;
        grid.numColumns = 3
        grid.adapter = MyAdapter(R.layout.item, data,this);
        Score.text = "Score:"+Sc
        play.setOnClickListener {
            play.isClickable = false
            m = 0
            s = 20
            Sc=0
            Score.text = "Score:"+String.format("%02d",Sc)
            timer()
            gameStart()
        }
    }
    var handler = Handler {
        when (it.what) {
            1 -> Top.text = String.format("%02d:%02d", m, s)
        }
        true
    }

    fun timer() {
        Thread(Runnable {
            while(s+m !=0){
                Thread.sleep(1000)
                s--
                if(s==-1){
                    m--
                    s=59
                }
                val msg= Message()
                msg.what = 1
                handler.sendMessage(msg)
            }
        }).start()
    }


    private fun gameStart() {
        //傳入的三個參數代表:輸入的型別、進度更新的型別、結果回傳的型別
        object : AsyncTask<Void, Void, Int>(){
            override fun doInBackground(vararg p0: Void?) :Int{
                while(!(s<=0 && m==0)){
                    Thread.sleep((Math.random()*2+1).toLong()*1000)
                    publishProgress()
                }
                return 0
            }

            override fun onProgressUpdate(vararg values: Void?) {
                //產生新的畫面
                newRound()
            }

            override fun onPostExecute(result: Int?) {
                //讓TextView可以點選
                final()
            }
        }.execute()
    }
    private fun newRound(){
        for(i in 0 until 9)
            data[i]=0
        for(i in 0 until (Math.random()*3).toInt()+1){
            data[(Math.random()*9).toInt()]=1;
        }
        grid.adapter = MyAdapter(R.layout.item,data,this)
    }

    fun score(){
        Sc+=10;
        Score.text = "Score:"+String.format("%02d",Sc)
    }

    fun final(){
        for(i in 0 until 9)
            data[i]=0
        Top.text = "重新開始"
        play.isClickable = true
    }

}



