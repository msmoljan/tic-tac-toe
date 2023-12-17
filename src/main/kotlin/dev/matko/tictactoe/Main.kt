package dev.matko.tictactoe

import dev.matko.tictactoe.presentation.Cli

fun main() {
    var hasQuit = false

    val cliUpdateListener = object : Cli.ScreenUpdateListener {
        override fun onScreenUpdate(screen: String) {
            clearScreen()
            print(screen)
        }

        override fun onQuit() {
            hasQuit = true
        }
    }

    val cli = Cli()
    cli.setScreenUpdateListener(cliUpdateListener)

    while (!hasQuit) {
        val input = System.console().readLine()
        cli.processInput(input)
    }
}

private fun clearScreen() {
    val pb = ProcessBuilder("clear")
    val startProcess = pb.inheritIO().start()

    startProcess.waitFor()
}