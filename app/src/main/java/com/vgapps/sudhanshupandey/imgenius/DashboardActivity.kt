package com.vgapps.sudhanshupandey.imgenius

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {


    private var correctAnswerCounter = 0
    private var totalAnswerCounter = 0
    private lateinit var sharedPreferences: SharedPreferences


    companion object{
        const val TOTAL_ANSWER_KEY = "TOTAL_ANSWER_KEY"
        const val TOTAL_CORRECT_ANSWERS_KEY = "TOTAL_CORRECT_ANSWERS_KEY"
        const val PREF_NAME = "GAME_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        sharedPreferences = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        totalAnswerCounter = sharedPreferences.getInt(TOTAL_ANSWER_KEY,0)
        correctAnswerCounter = sharedPreferences.getInt(TOTAL_CORRECT_ANSWERS_KEY,0)

        updateView()

        play_button.setOnClickListener {
            val intent = Intent(this,ChooseCategoryActivity::class.java)
            intent.putExtra("correctAnswer",correctAnswerCounter)
            intent.putExtra("totalAnswer",totalAnswerCounter)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        totalAnswerCounter = sharedPreferences.getInt(TOTAL_ANSWER_KEY,0)
        correctAnswerCounter = sharedPreferences.getInt(TOTAL_CORRECT_ANSWERS_KEY,0)
        updateView()
    }

    fun updateView(){
        correctAnswer_tv.text = "$correctAnswerCounter"
        totalAnswer_tv.text = "$totalAnswerCounter"
    }
}
