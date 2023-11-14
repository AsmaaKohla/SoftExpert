package com.example.petfinder.presentation.base

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petfinder.R

abstract class BaseActivity : AppCompatActivity() {
    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = Dialog(this, 0)
    }
    override fun onDestroy() {
        super.onDestroy()
        if (progressDialog.isShowing) {
            progressDialog.hide()
        }
        progressDialog.dismiss()
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun handleProgressBar(show: Boolean) {
        if (show)
            showProgressDialog()
        else
            hideProgressDialog()
    }

    private fun showProgressDialog() {
        if (!progressDialog.isShowing) {
            val view = this.layoutInflater.inflate(R.layout.progress_dialog, null)
            progressDialog.setContentView(view)
            progressDialog.setCancelable(false)
            progressDialog.show()
        }
    }

    private fun hideProgressDialog() {
        if (progressDialog.isShowing)
            progressDialog.hide()
    }
}