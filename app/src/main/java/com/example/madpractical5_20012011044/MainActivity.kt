package com.example.madpractical5_20012011044

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var seekBar: SeekBar
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        seekBar = findViewById(R.id.sbMusic)
        handler = Handler(Looper.getMainLooper())


//        val play = findViewById<Button>(R.id.btn_play)
//        play.setOnClickListener {
//            mediaPlayer.start()
//        }

        val play = findViewById<FloatingActionButton>(R.id.fabPlay)
        play.setOnClickListener {
            if(mediaPlayer == null){
                mediaPlayer = MediaPlayer.create(this,R.raw.song)
                initializeSeekBar()
            }

            mediaPlayer?.start()
        }


        val pause = findViewById<FloatingActionButton>(R.id.fabPause)
        pause.setOnClickListener {
            mediaPlayer?.pause()
        }


        val stop = findViewById<FloatingActionButton>(R.id.fabStop)
        stop.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
            handler.removeCallbacks(runnable)
            seekBar.progress = 0
        }
    }

    private fun initializeSeekBar(){
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        val played = findViewById<TextView>(R.id.tvPlayed)
        val due = findViewById<TextView>(R.id.tvDue)
        seekBar.max = mediaPlayer!!.duration
        runnable = Runnable {
            seekBar.progress = mediaPlayer!!.currentPosition
            handler.postDelayed(runnable, 1000)

            val playedTime = mediaPlayer!!.currentPosition/1000
            played.text = "$playedTime sec"

            val duration = mediaPlayer!!.duration/1000
            val dueTime  = duration - playedTime

            due.text = "$dueTime sec"
        }
        handler.postDelayed(runnable,1000)
    }
}