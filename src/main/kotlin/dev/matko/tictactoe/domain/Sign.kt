package dev.matko.tictactoe.domain

enum class Sign {
    X, O;

    companion object {
        fun from(signAsString: String): Sign {
            return values()
                .firstOrNull { signAsString.uppercase() == it.name }
                ?: throw IllegalArgumentException("Expected one of the Sign enum values, but was $signAsString instead.")
        }
    }
}
