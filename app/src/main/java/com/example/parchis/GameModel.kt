package com.example.parchis

data class Player(val id: Int, val name: String, val icon: Int)

data class Pawn(val playerId: Int, val pawnId: Int, var position: Int = -1) // -1 means at home

data class Dice(var value: Int = 1) {
    fun roll() {
        value = (1..6).random()
    }
}

class GameState {
    val players = mutableListOf<Player>()
    val pawns = mutableListOf<Pawn>()
    val dice = Dice()
    var currentPlayerIndex = 0

    fun setupGame(playerNames: List<String>) {
        // Clear previous game state
        players.clear()
        pawns.clear()

        // Create players
        playerNames.forEachIndexed { index, name ->
            players.add(Player(index, name, R.drawable.ic_launcher_foreground)) // Using a default icon for now
        }

        // Create pawns for each player
        players.forEach { player ->
            for (i in 0 until 4) {
                pawns.add(Pawn(player.id, i))
            }
        }

        // Set the first player
        currentPlayerIndex = 0
    }

    fun nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size
    }
}
