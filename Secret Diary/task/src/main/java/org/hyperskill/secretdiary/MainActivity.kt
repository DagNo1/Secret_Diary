package org.hyperskill.secretdiary

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.datetime.Clock
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var oldEntry: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        oldEntry = findViewById(R.id.tvDiary)
        load()
        findViewById<Button>(R.id.btnSave).setOnClickListener { save() }
        findViewById<Button>(R.id.btnUndo).setOnClickListener { undo() }
    }
    private fun load() {
        val sharedPreferences = getSharedPreferences("PREF_DIARY",Context.MODE_PRIVATE)
        oldEntry.text = sharedPreferences.getString("KEY_DIARY_TEXT", null) ?: return
    }
    private fun update() {
        val sharedPreferences = getSharedPreferences("PREF_DIARY",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("KEY_DIARY_TEXT", oldEntry.text.toString())
            apply()
        }
    }
    private fun save() {
        val newEntry = findViewById<EditText>(R.id.etNewWriting)
        if (newEntry.text.isBlank()) {
            val errMsg = "Empty or blank input cannot be saved"
            Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show()
        } else {
            oldEntry.text = getTime() + "\n" + newEntry.text.toString() +
                    if (oldEntry.text.isNotBlank()) "\n\n"+ oldEntry.text else ""
            newEntry.text.clear()
            update()
        }
    }
    private fun undo() {
        AlertDialog.Builder(this)
            .setTitle("Remove last note")
            .setMessage(" Do you really want to remove the last writing? This operation cannot be undone!")
            .setPositiveButton("Yes") { _, _ ->
                val k = oldEntry.text.toString().indexOf("\n\n")
                if (k != -1) oldEntry.text = oldEntry.text.toString().substring(k+2)
                else oldEntry.text = ""
                update()
            }
            .setNegativeButton("No", null)
            .show()
    }
    private fun getTime():String {
        val zFormat = Clock.System.now().toEpochMilliseconds()
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault()).format(zFormat).toString()
    }
}



