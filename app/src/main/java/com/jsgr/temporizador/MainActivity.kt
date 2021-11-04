package com.jsgr.temporizador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    //private int score = 0
    private var score = 0
    private var started = false

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var countDownTimer: CountDownTimer
    private val initialCountDown : Long = 10000
    private val countDownInterval : Long = 1000
    private var timeLeft = 10

    //propiedades de clase o actividad
    private lateinit var textViewScore: TextView
    // private TextView textViewScore;
    private lateinit var textViewTime : TextView
    private lateinit var buttonIniciar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "Se ha llamado On Create!")
        textViewScore = findViewById(R.id.textViewScore)
        textViewTime = findViewById(R.id.textViewTime)
        buttonIniciar = findViewById(R.id.buttonIniciar)

        buttonIniciar.setOnClickListener{ incrementScore() }

        if(savedInstanceState != null) {
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeft = savedInstanceState.getInt(TIME_KEY)
            buttonIniciar.text = savedInstanceState.getString(BUTTON_KEY)
            loadApp()
        }
        else
            reset()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCORE_KEY, score)
        outState.putInt(TIME_KEY, timeLeft)
        outState.putString(BUTTON_KEY, buttonIniciar.text.toString())
        countDownTimer.cancel()
        Log.d(TAG, "Instancia guardada con score: $score y time: $timeLeft")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Se ha llamado on destroy.")
    }

    private fun incrementScore() {
        if(!started){
            start()
        }

        score++

        //val newScore = "Puntaje : $score"
        val newScore = getString(R.string.puntaje, score)
        textViewScore.text = newScore
    }

    private fun reset() {
        score = 0

        Log.d(TAG,"Se ha reiniciado el juego!")

        val initialScore = getString(R.string.puntaje, score)
        textViewScore.text = initialScore

        val initialTimeLeft = getString(R.string.tiempo, timeLeft)
        textViewTime.text = initialTimeLeft

        //countdownTimer = new CountDownTimer(initialCountDown, countDownInterval);
        //
        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished.toInt() / 1000

                val timeLeftString = getString(R.string.tiempo, timeLeft)
                textViewTime.text = timeLeftString
            }

            override fun onFinish() {
                terminar()
            }
        }
        started = false
    }

    private fun loadApp(){
        val restoredScore = getString(R.string.puntaje, score)
        textViewScore.text = restoredScore

        val restoredTime = getString(R.string.tiempo, timeLeft)
        textViewTime.text = restoredTime

        countDownTimer = object :  CountDownTimer((timeLeft * 1000).toLong(), countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished.toInt() / 1000

                val timeLeftString = getString(R.string.tiempo, timeLeft)
                textViewTime.text = timeLeftString
            }
            override fun onFinish() {
                terminar()
            }
        }
        countDownTimer.start()
        started = true
    }

    private fun terminar() {
        Toast.makeText(this,getString(R.string.mensaje_terminar, score), Toast.LENGTH_LONG).show()
        buttonIniciar.text = getString(R.string.click_aqui_antes)
        Log.d(TAG, "Ha terminado el juego con puntaje de $score")
        reset()
    }

    private fun start() {
        countDownTimer.start()
        started = true
        Log.d(TAG, "Ha dado inicio el juego!")
        buttonIniciar.text = getString(R.string.click_aqui)
    }

    companion object {
        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_KEY = "TIME_KEY"
        private const val BUTTON_KEY = "BUTTON_KEY"
    }
}