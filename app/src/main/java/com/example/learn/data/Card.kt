
data class Card internal constructor(
    val cardId: String,
    val content: CardContent,
    val reference: CardReference,
    val created: Long,
)

data class CardContent internal constructor(
    val front: String,
    val back: String,
)

data class CardReference internal constructor(
    val position: Int,
    val title: String,
    // Add any additional information here
)