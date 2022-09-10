package org.hyperskill.secretdiary

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.datetime.Clock
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val entries = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btnSave).setOnClickListener {
            save()
        }
        findViewById<Button>(R.id.btnUndo).setOnClickListener {
            undo()
        }
    }
    private fun save() {
        val newEntry = findViewById<EditText>(R.id.etNewWriting)
        val oldEntry = findViewById<TextView>(R.id.tvDiary)
        if (newEntry.text.isBlank()) {
            val errMsg = "Empty or blank input cannot be saved"
            Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show()
        } else {
            entries.add(0, getTime() + "\n" + newEntry.text.toString())
            oldEntry.text = entries.joinToString("\n\n")
            newEntry.text.clear()
        }
    }
    private fun undo() {
        val oldEntry = findViewById<TextView>(R.id.tvDiary)
        AlertDialog.Builder(this)
            .setTitle("Remove last note")
            .setMessage(" Do you really want to remove the last writing? This operation cannot be undone!")
            .setPositiveButton("Yes") { _, _ ->
                if (entries.isNotEmpty()) {
                    entries.removeAt(0)
                    oldEntry.text = entries.joinToString("\n\n")
                }
            }
            .setNegativeButton("No", null)
            .create().show()
    }
    private fun getTime():String {
        val zFormat = Clock.System.now().toEpochMilliseconds()
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault()).format(zFormat).toString()
    }
}

