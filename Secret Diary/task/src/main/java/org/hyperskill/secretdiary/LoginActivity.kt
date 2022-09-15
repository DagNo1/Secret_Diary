package org.hyperskill.secretdiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        findViewById<Button>(R.id.btnLogin).setOnClickListener { logIn() }
    }
    private fun logIn() {
        val enteredPin = findViewById<EditText>(R.id.etPin)
        if (enteredPin.text.toString() == "1234") {
            val diary = Intent(this,MainActivity::class.java)
            startActivity(diary)
        } else {
            enteredPin.error = "Wrong PIN!"
        }
    }
}