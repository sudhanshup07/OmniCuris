package com.vgapps.sudhanshupandey.imgenius

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.gson.GsonBuilder
import com.vgapps.sudhanshupandey.imgenius.DashboardActivity.Companion.PREF_NAME
import com.vgapps.sudhanshupandey.imgenius.DashboardActivity.Companion.TOTAL_ANSWER_KEY
import com.vgapps.sudhanshupandey.imgenius.DashboardActivity.Companion.TOTAL_CORRECT_ANSWERS_KEY
import kotlinx.android.synthetic.main.activity_quiz.*
import okhttp3.*
import java.io.IOException
import java.util.*

class QuizActivity : AppCompatActivity() {

    var timerCounter = 30
    private var correctAnswerCounter = 0
    private var totalAnswerCounter = 0
    private var countdownTimer: CountDownTimer? = null
    private lateinit var loadingQuestionDialog: Dialog
    lateinit var sharedPref: SharedPreferences

    private var tag = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)


        sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        tag = intent.getStringExtra("tag")
        correctAnswerCounter = intent.getIntExtra("correctAnswer",0)
        totalAnswerCounter = intent.getIntExtra("totalAnswer",0)



        loadingQuestionDialog = Dialog(this)
        loadingQuestionDialog.setContentView(R.layout.loading_dialog)
        loadingQuestionDialog.setCancelable(false)
        loadingQuestionDialog.setCanceledOnTouchOutside(false)

        initializeCountdownTimer()
        fetchNewQuestion()
    }

    private fun initializeCountdownTimer() {

        countdownTimer = object: CountDownTimer(30_000,1_000){
            override fun onFinish() {
                resetCountdown()
                fetchNewQuestion()
                totalAnswerCounter++
            }
            override fun onTick(millisUntilFinished: Long) {
                if(timerCounter<=5){
                    counter_tv.setBackgroundResource(R.drawable.less_timer_bg)
                }
                counter_tv.text = timerCounter.toString()
                timerCounter--
            }
        }
    }

    @Suppress("DEPRECATION")
    @Throws(WindowManager.BadTokenException::class)
    private fun fetch() {

        val url = "https://opentdb.com/api.php?amount=1&category=$tag&type=multiple"
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                // get response body as string
                val body = response?.body()?.string()
                // parse response body as json object
                val gSon = GsonBuilder().create()
                val triviaRequest = gSon.fromJson(body, TriviaRequest::class.java)
                val result = triviaRequest.results[0]
                val question = Html.fromHtml(result.question)
                val correctAnswer = Html.fromHtml(result.correct_answer)
                val incorrectAnswer1 = Html.fromHtml(result.incorrect_answers[0])
                val incorrectAnswer2 = Html.fromHtml(result.incorrect_answers[1])
                val incorrectAnswer3 = Html.fromHtml(result.incorrect_answers[2])

                // build list of all questions
                val answers = listOf(
                    correctAnswer,
                    incorrectAnswer1,
                    incorrectAnswer2,
                    incorrectAnswer3
                )

                //shuffle list
                @Suppress("JavaCollectionsStaticMethodOnImmutableList")
                Collections.shuffle(answers)

                runOnUiThread {

                    question_tv.text = question
                    answer1_tv.text = answers[0]
                    answer1_tv.isClickable = true

                    answer2_tv.text = answers[1]
                    answer2_tv.isClickable = true

                    answer3_tv.text = answers[2]
                    answer3_tv.isClickable = true
                    answer4_tv.text = answers[3]
                    answer4_tv.isClickable = true
                    counter_tv.visibility = View.VISIBLE
                    resetView()
                    loadingQuestionDialog.dismiss()
                    enableViews()
                    countdownTimer!!.start()

                    answer1_tv.setOnClickListener {
                        answer1_tv.isClickable = false
                        if (answer1_tv.text == correctAnswer) {
                            answer1_tv.setTextColor(Color.WHITE)
                            correctAnswerCounter++
                            answer1_layout.setBackgroundResource(R.drawable.correct_answer_bg)
                            Handler().postDelayed({
                                fetchNewQuestion()
                            },1200)
                        }else{
                            answer1_tv.setTextColor(Color.WHITE)
                            answer1_layout.setBackgroundResource(R.drawable.incorrect_answer_bg)
                            highlightCorrectAnswer(correctAnswer)
                        }
                        totalAnswerCounter++
                    }

                    answer2_tv.setOnClickListener {
                        answer2_tv.isClickable = false
                        if (answer2_tv.text == correctAnswer) {
                            answer2_tv.setTextColor(Color.WHITE)
                            correctAnswerCounter++
                            answer2_layout.setBackgroundResource(R.drawable.correct_answer_bg)
                            Handler().postDelayed({
                                fetchNewQuestion()
                            },1200)
                        }else{
                            answer2_tv.setTextColor(Color.WHITE)
                            answer2_layout.setBackgroundResource(R.drawable.incorrect_answer_bg)

                            highlightCorrectAnswer(correctAnswer)
                        }
                        totalAnswerCounter++
                    }

                    answer3_tv.setOnClickListener {
                        answer3_tv.isClickable = false
                        if (answer3_tv.text == correctAnswer) {
                            answer3_tv.setTextColor(Color.WHITE)
                            correctAnswerCounter++
                            answer3_layout.setBackgroundResource(R.drawable.correct_answer_bg)
                            Handler().postDelayed({
                                fetchNewQuestion()
                            },1200)
                        }else{
                            answer3_tv.setTextColor(Color.WHITE)
                            answer3_layout.setBackgroundResource(R.drawable.incorrect_answer_bg)

                            highlightCorrectAnswer(correctAnswer)
                        }
                        totalAnswerCounter++
                    }

                    answer4_tv.setOnClickListener {
                        answer4_tv.isClickable = false
                        if (answer4_tv.text == correctAnswer) {
                            answer4_tv.setTextColor(Color.WHITE)
                            correctAnswerCounter++
                            answer4_layout.setBackgroundResource(R.drawable.correct_answer_bg)
                            Handler().postDelayed({
                                fetchNewQuestion()
                            },1200)
                        }else{
                            answer4_tv.setTextColor(Color.WHITE)
                            answer4_layout.setBackgroundResource(R.drawable.incorrect_answer_bg)

                            highlightCorrectAnswer(correctAnswer)
                        }
                        totalAnswerCounter++
                    }

                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }
        })
    }

    private fun highlightCorrectAnswer(correctAnswer: Spanned) {

        when (correctAnswer) {
            answer1_tv.text -> {
                answer1_tv.setTextColor(Color.WHITE)
                animateView(answer1_layout)
            }
            answer2_tv.text -> {
                answer2_tv.setTextColor(Color.WHITE)
                animateView(answer2_layout)
            }
            answer3_tv.text -> {
                answer3_tv.setTextColor(Color.WHITE)
                animateView(answer3_layout)
            }
            answer4_tv.text -> {
                answer3_tv.setTextColor(Color.WHITE)
                animateView(answer4_layout)
            }
        }
    }

    @Throws(WindowManager.BadTokenException::class)
    private fun fetchNewQuestion() {
        if (!this.isFinishing) {
            if (totalAnswerCounter > 1 && totalAnswerCounter % 5 == 0)
            resetCountdown()
            loadingQuestionDialog.show()
            disableViews()
            fetch()
        }
    }

    private fun disableViews() {
        answer1_layout.isEnabled = false
        answer1_tv.isEnabled = false

        answer2_layout.isEnabled = false
        answer2_tv.isEnabled = false

        answer3_layout.isEnabled = false
        answer3_tv.isEnabled = false

        answer4_layout.isEnabled = false
        answer4_tv.isEnabled = false
    }

    private fun enableViews() {
        answer1_layout.isEnabled = true
        answer1_tv.isEnabled = true

        answer2_layout.isEnabled = true
        answer2_tv.isEnabled = true

        answer3_layout.isEnabled = true
        answer3_tv.isEnabled = true

        answer4_layout.isEnabled = true
        answer4_tv.isEnabled = true
    }

    private fun resetView() {
        answer1_tv.setTextColor(Color.WHITE)
        answer1_layout.setBackgroundResource(R.drawable.answer_bg)

        answer2_tv.setTextColor(Color.WHITE)
        answer2_layout.setBackgroundResource(R.drawable.answer_bg)

        answer3_tv.setTextColor(Color.WHITE)
        answer3_layout.setBackgroundResource(R.drawable.answer_bg)

        answer4_tv.setTextColor(Color.WHITE)
        answer4_layout.setBackgroundResource(R.drawable.answer_bg)
    }

    private fun resetCountdown() {
        counter_tv.visibility = View.INVISIBLE
        countdownTimer!!.cancel()
        timerCounter = 30
        counter_tv.setBackgroundResource(R.drawable.normal_timer_bg)
    }

    private fun animateView(answer4_layout: ConstraintLayout) {
        answer4_layout.setBackgroundResource(R.drawable.correct_answer_bg)
        Handler().postDelayed({
            answer4_layout.setBackgroundResource(R.drawable.black_gradient_bg)
        },500)
        Handler().postDelayed({
            answer4_layout.setBackgroundResource(R.drawable.correct_answer_bg)
        },600)
        Handler().postDelayed({
            answer4_layout.setBackgroundResource(R.drawable.black_gradient_bg)
        },700)
        Handler().postDelayed({
            answer4_layout.setBackgroundResource(R.drawable.correct_answer_bg)
        },800)
        Handler().postDelayed({
            fetchNewQuestion()
        },1200)
    }

    override fun onDestroy() {
        super.onDestroy()
        val editor = sharedPref.edit()
        editor.putInt(TOTAL_ANSWER_KEY,totalAnswerCounter)
        editor.putInt(TOTAL_CORRECT_ANSWERS_KEY,correctAnswerCounter)
        editor.apply()
    }
}
