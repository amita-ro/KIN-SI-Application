package com.example.kin_si.utils

import android.app.AlertDialog
import android.app.Dialog
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kin_si.MainActivity
import com.example.kin_si.R


fun Fragment.getLoading(): Dialog {
    val builder = AlertDialog.Builder((activity as MainActivity))
    builder.setView(R.layout.progress)
    builder.setCancelable(false)
    var dialog = builder.create()
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    return dialog
}

