package com.vgapps.sudhanshupandey.imgenius

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_choose_category.*

class ChooseCategoryActivity : AppCompatActivity() {
    private var correctAnswerCounter = 0
    private var totalAnswerCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_category)

        cat_geography_tv.setOnClickListener {

            val intent = Intent(this,QuizActivity::class.java)
            intent.putExtra("tag", it!!.tag.toString())
            intent.putExtra("correctAnswer",correctAnswerCounter)
            intent.putExtra("totalAnswer",totalAnswerCounter)
            startActivity(intent)

        }

        cat_animal_tv.setOnClickListener {

            val intent = Intent(this,QuizActivity::class.java)
            intent.putExtra("tag", it!!.tag.toString())
            intent.putExtra("correctAnswer",correctAnswerCounter)
            intent.putExtra("totalAnswer",totalAnswerCounter)
            startActivity(intent)

        }

        cat_gk_tv.setOnClickListener {

            val intent = Intent(this,QuizActivity::class.java)
            intent.putExtra("tag", it!!.tag.toString())
            intent.putExtra("correctAnswer",correctAnswerCounter)
            intent.putExtra("totalAnswer",totalAnswerCounter)
            startActivity(intent)

        }

        cat_science_computer_tv.setOnClickListener {

            val intent = Intent(this,QuizActivity::class.java)
            intent.putExtra("tag", it!!.tag.toString())
            intent.putExtra("correctAnswer",correctAnswerCounter)
            intent.putExtra("totalAnswer",totalAnswerCounter)
            startActivity(intent)

        }

        cat_animal_tv.setOnClickListener {

            val intent = Intent(this,QuizActivity::class.java)
            intent.putExtra("tag", it!!.tag.toString())
            intent.putExtra("correctAnswer",correctAnswerCounter)
            intent.putExtra("totalAnswer",totalAnswerCounter)
            startActivity(intent)

        }

        cat_science_maths_tv.setOnClickListener {

            val intent = Intent(this,QuizActivity::class.java)
            intent.putExtra("tag", it!!.tag.toString())
            intent.putExtra("correctAnswer",correctAnswerCounter)
            intent.putExtra("totalAnswer",totalAnswerCounter)
            startActivity(intent)

        }

        cat_sports_tv.setOnClickListener {

            val intent = Intent(this,QuizActivity::class.java)
            intent.putExtra("tag", it!!.tag.toString())
            intent.putExtra("correctAnswer",correctAnswerCounter)
            intent.putExtra("totalAnswer",totalAnswerCounter)
            startActivity(intent)

        }

        cat_science_nature_tv.setOnClickListener {

            val intent = Intent(this,QuizActivity::class.java)
            intent.putExtra("tag", it!!.tag.toString())
            intent.putExtra("correctAnswer",correctAnswerCounter)
            intent.putExtra("totalAnswer",totalAnswerCounter)
            startActivity(intent)

        }

        correctAnswerCounter = intent.getIntExtra("correctAnswer",0)
        totalAnswerCounter = intent.getIntExtra("totalAnswer",0)
    }

}
