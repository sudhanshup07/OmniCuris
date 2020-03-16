package com.vgapps.sudhanshupandey.imgenius

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private lateinit var constraintLayout: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        constraintLayout = findViewById(R.id.splash_activity_main_constraint_layout)
    }

    private fun goToNextActivity() {
        if (isConnectionAvailable()) {
            java.util.Timer().schedule(
                object : java.util.TimerTask() {
                    override fun run() {

                        val intent = Intent(this@SplashActivity, DashboardActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                },
                1000
            )
        }else{
            makeSnackBar()
        }
    }

    private fun  isConnectionAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }

    override fun onStart() {
        super.onStart()
        goToNextActivity()
    }

    private fun  makeSnackBar(){
        val snackBar = Snackbar.make(constraintLayout, "No Internet Connection.", Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.try_again) {
                goToNextActivity()
            }
        snackBar.setActionTextColor(Color.WHITE)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundResource(R.drawable.tab_background)
        snackBar.show()
    }
}
