package com.example.parchis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var selectedIcon: Int = R.drawable.ic_launcher_foreground

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextUsername: EditText = findViewById(R.id.editTextUsername)
        val imageViewIcon1: ImageView = findViewById(R.id.imageViewIcon1)
        val imageViewIcon2: ImageView = findViewById(R.id.imageViewIcon2)
        val imageViewIcon3: ImageView = findViewById(R.id.imageViewIcon3)
        val buttonSave: Button = findViewById(R.id.buttonSave)

        imageViewIcon1.setOnClickListener {
            selectedIcon = R.drawable.ic_launcher_foreground
            Toast.makeText(this, "Icono 1 seleccionado", Toast.LENGTH_SHORT).show()
        }

        imageViewIcon2.setOnClickListener {
            // Placeholder for a different icon
            selectedIcon = R.drawable.ic_launcher_background
            Toast.makeText(this, "Icono 2 seleccionado", Toast.LENGTH_SHORT).show()
        }

        imageViewIcon3.setOnClickListener {
            // Placeholder for another different icon
            selectedIcon = R.drawable.ic_launcher_foreground
            Toast.makeText(this, "Icono 3 seleccionado", Toast.LENGTH_SHORT).show()
        }

        buttonSave.setOnClickListener {
            val username = editTextUsername.text.toString()
            if (username.isNotEmpty()) {
                val intent = Intent(this, LobbyActivity::class.java)
                intent.putExtra("username", username)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Por favor, introduce un nombre de usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
