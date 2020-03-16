package com.vgapps.sudhanshupandey.imgenius

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class SnackBar {
  companion object {

      fun onSNACK(view: View, message: String) {

          val snackBar = Snackbar.make(
              view, message,
              Snackbar.LENGTH_INDEFINITE
          )
          snackBar.setActionTextColor(Color.WHITE)
          val snackBarView = snackBar.view
          snackBarView.setBackgroundResource(R.drawable.tab_background)
          snackBarView.elevation = 16f
          val textView =
              snackBarView.findViewById(R.id.snackbar_text) as TextView
          textView.setTextColor(Color.WHITE)
          textView.textSize = 15f
          snackBar.show()
      }
  }
}