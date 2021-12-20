package com.example.kot6

import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {

    private val remainMinTv:TextView by lazy{
        findViewById(R.id.remainMinTv)
    }
    private val remainSecTv:TextView by lazy{
        findViewById(R.id.remainSecTv)
    }
    private val seekBar:SeekBar by lazy{
        findViewById(R.id.seekBar)
    }

    private val soundPool = SoundPool.Builder().build()

    private var tickingSoundId: Int?=null
    private var bellSoundId: Int?=null

    private var curCountdownTimer: CountDownTimer?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
        initSounds()
    }

    override fun onResume() {
        super.onResume()
        soundPool.autoResume()
    }

    override fun onPause() {
        super.onPause()
        soundPool.autoPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }

    private fun bindViews(){
        seekBar.setOnSeekBarChangeListener(
            object:SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if(p2){
                        updateRemainTime(p1*60*1000L)
                    }
                }
                override fun onStartTrackingTouch(p0: SeekBar?) {
                    curCountdownTimer?.cancel()
                    curCountdownTimer = null
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                    seekBar?:return
                    startCountDown()
                }
            }
        )
    }

    private fun initSounds(){
        tickingSoundId = soundPool.load(this,R.raw.therose,1)
        bellSoundId = soundPool.load(this,R.raw.ae999,1)
    }

    private fun createCountDownTimer(initialMillis:Long):CountDownTimer {
        return object : CountDownTimer(initialMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                updateRemainTime(millisUntilFinished)
                updateSeekBar(millisUntilFinished)
            }

            override fun onFinish() {
                completeCountDown()
            }
        }
    }

    private fun startCountDown(){
        curCountdownTimer = createCountDownTimer(seekBar.progress*60*1000L)
        curCountdownTimer?.start()
        tickingSoundId?.let{tickingSoundId->
            soundPool.play(tickingSoundId,1F,1F,0,-1,1F)
        }
    }

    private fun completeCountDown(){
        updateRemainTime(0)
        updateSeekBar(0)
        soundPool.autoPause()
        bellSoundId?.let { bellSoundId ->
            soundPool.play(bellSoundId, 1F, 1F, 0, 0, 1F)
        }
    }

    private fun updateRemainTime(remainMillis:Long){
        val remainSec = remainMillis/1000
        remainMinTv.text="%02d".format(remainSec / 60)
        remainSecTv.text="%02d".format(remainSec % 60)
    }

    private  fun updateSeekBar(remainMillis: Long){
        seekBar.progress = (remainMillis/1000/60).toInt()
    }
}