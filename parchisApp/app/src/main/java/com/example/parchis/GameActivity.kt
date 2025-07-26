package com.example.parchis

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GameActivity : AppCompatActivity() {

    private val gameState = GameState()
    private lateinit var gameClient: GameClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val playerNames = intent.getStringArrayListExtra("playerNames")
        if (playerNames != null) {
            gameState.setupGame(playerNames)
        }

        gameClient = GameClient(lifecycleScope)
        gameClient.connect("10.0.2.2", 8080)

        gameClient.incomingMessages
            .onEach { message ->
                // Handle incoming messages from the server
                Toast.makeText(this, "Mensaje recibido: $message", Toast.LENGTH_SHORT).show()
            }
            .launchIn(lifecycleScope)

        val buttonRollDice: Button = findViewById(R.id.buttonRollDice)
        buttonRollDice.setOnClickListener {
            gameState.dice.roll()
            val currentPlayer = gameState.players[gameState.currentPlayerIndex]
            val diceValue = gameState.dice.value
            val message = "${currentPlayer.name} ha sacado un $diceValue"

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            gameClient.sendMessage("roll_dice:$diceValue")

            gameState.nextPlayer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        gameClient.disconnect()
    }
}
