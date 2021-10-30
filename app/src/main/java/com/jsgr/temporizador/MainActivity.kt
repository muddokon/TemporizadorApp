package com.jsgr.temporizador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    //private int score = 0
    private var score = 0
    private var started = false

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
        textViewScore = findViewById(R.id.textViewScore)
        textViewTime = findViewById(R.id.textViewTime)
        buttonIniciar = findViewById(R.id.buttonIniciar)

        buttonIniciar.setOnClickListener{ incrementScore() }

        reset()
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

    private fun terminar() {
        Toast.makeText(this,getString(R.string.mensaje_terminar, score), Toast.LENGTH_LONG).show()
        reset()
    }

    private fun start() {
        countDownTimer.start()
        started = true
        buttonIniciar.text = getString(R.string.click_aqui)
    }
}