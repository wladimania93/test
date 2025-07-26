package com.example.parchis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class LobbyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)

        val username = intent.getStringExtra("username") ?: "Player"

        val editTextRoomName: EditText = findViewById(R.id.editTextRoomName)
        val buttonCreateRoom: Button = findViewById(R.id.buttonCreateRoom)
        val buttonJoinRoom: Button = findViewById(R.id.buttonJoinRoom)

        buttonCreateRoom.setOnClickListener {
            val roomName = editTextRoomName.text.toString()
            if (roomName.isNotEmpty()) {
                // For now, we'll just start the game with the creator.
                // In a real implementation, we would wait for other players to join.
                val playerNames = arrayListOf(username)
                val intent = Intent(this, GameActivity::class.java).apply {
                    putStringArrayListExtra("playerNames", playerNames)
                    putExtra("roomName", roomName)
                    putExtra("isCreator", true)
                }
                startActivity(intent)
            }
        }

        buttonJoinRoom.setOnClickListener {
            val roomName = editTextRoomName.text.toString()
            if (roomName.isNotEmpty()) {
                // In a real implementation, we would join an existing room.
                // For now, we'll just start the game and assume the room exists.
                val playerNames = arrayListOf(username) // This would be populated by the server
                val intent = Intent(this, GameActivity::class.java).apply {
                    putStringArrayListExtra("playerNames", playerNames)
                    putExtra("roomName", roomName)
                    putExtra("isCreator", false)
                }
                startActivity(intent)
            }
        }
    }
}
