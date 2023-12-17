package dev.matko.tictactoe.domain

enum class Sign {
    X, O;

    companion object {
        fun from(signAsString: String): Sign {
            return entries
                .firstOrNull { signAsString.uppercase() == it.name }
                ?: throw IllegalArgumentException("Expected one of the Sign enum values, but was $signAsString instead.")
        }
    }

    override fun toString(): String {
        return this.name
    }
}
