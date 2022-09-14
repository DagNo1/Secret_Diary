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
    private val entries = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        load()
        findViewById<Button>(R.id.btnSave).setOnClickListener { save() }
        findViewById<Button>(R.id.btnUndo).setOnClickListener { undo() }
    }
    private fun load() {
        val sharedPreferences = getSharedPreferences("PREF_DIARY",Context.MODE_PRIVATE)
        val oldEntries: String = sharedPreferences.getString("KEY_DIARY_TEXT", null) ?: return
        entries.addAll(oldEntries.split("|").toMutableList())
        val oldEntry: TextView = findViewById(R.id.tvDiary)
        oldEntry.text = entries.joinToString("\n\n")
    }
    private fun update() {
        val oldEntry: TextView = findViewById(R.id.tvDiary)
        val sharedPreferences = getSharedPreferences("PREF_DIARY",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("KEY_DIARY_TEXT", entries.joinToString("|"))
            apply()
        }
        oldEntry.text = entries.joinToString("\n\n")
    }
    private fun save() {
        val newEntry = findViewById<EditText>(R.id.etNewWriting)
        if (newEntry.text.isBlank()) {
            val errMsg = "Empty or blank input cannot be saved"
            Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show()
        } else {
            entries.add(0, getTime() + "\n" + newEntry.text.toString())
            newEntry.text.clear()
            update()
        }
    }
    private fun undo() {
        AlertDialog.Builder(this)
            .setTitle("Remove last note")
            .setMessage(" Do you really want to remove the last writing? This operation cannot be undone!")
            .setPositiveButton("Yes") { _, _ ->
                if (entries.isNotEmpty()) {
                    entries.removeAt(0)
                    update()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }
    private fun getTime():String {
        val zFormat = Clock.System.now().toEpochMilliseconds()
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault()).format(zFormat).toString()
    }
}



