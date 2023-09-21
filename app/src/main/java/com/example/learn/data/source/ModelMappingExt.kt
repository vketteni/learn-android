package com.example.learn.data.source

import Card
import CardContent
import CardReference
import Deck
import com.example.learn.data.source.local.DeckCardCrossRef
import com.example.learn.data.source.local.LocalCard
import com.example.learn.data.source.local.LocalDeck
import com.example.learn.data.source.network.NetworkCard
import com.example.learn.data.source.network.NetworkDeck
import com.example.learn.data.source.network.NetworkDeckCardCrossRef

fun Deck.toLocal() = LocalDeck(
    deckId = deckId,
    created = created,
    title = title
)
fun LocalDeck.toNetwork() = NetworkDeck(
    deckId = deckId,
    created = created,
    title = title
)

fun List<LocalDeck>.toNetworkDecks() = map(LocalDeck::toNetwork)

fun Card.toLocal() = LocalCard(
    cardId = cardId,
    frontContent = content.front,
    backContent = content.back,
    title = reference.title,
    position = reference.position,
    created = created
)

fun DeckCardCrossRef.toNetwork() = NetworkDeckCardCrossRef(
    deckId = deckId,
    cardId = cardId
)

fun List<DeckCardCrossRef>.toNetworkCrossRefs() = map(DeckCardCrossRef::toNetwork)

fun LocalDeck.toExternal() = Deck(
    deckId = deckId,
    created = created,
    title = title
)

fun LocalCard.toExternal() = Card(
    cardId = cardId,
    content = CardContent(frontContent, backContent),
    reference = CardReference(position, title),
    created = created
)

fun LocalCard.toNetwork() = NetworkCard(
    cardId,
    frontContent,
    backContent,
    title,
    position,
    created
)

fun List<LocalCard>.toNetworkCards() = map(LocalCard::toNetwork)