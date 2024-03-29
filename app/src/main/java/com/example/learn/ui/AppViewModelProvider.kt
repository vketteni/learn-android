package com.example.learn.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.learn.LearnApplication
import com.example.learn.ui.card.CardAddEditViewModel
import com.example.learn.ui.card.CardDetailViewModel
import com.example.learn.ui.deck.DeckDetailViewModel
import com.example.learn.ui.deck.DeckAddEditViewModel
import com.example.learn.ui.deck.DeckOverviewViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            DeckOverviewViewModel(
                learnApplication().container.decksRepository
            )
        }

        initializer {
            DeckAddEditViewModel(
                learnApplication().container.decksRepository,
                this.createSavedStateHandle()
            )
        }

        initializer {
            DeckDetailViewModel(
                learnApplication().container.cardsRepository,
                learnApplication().container.decksRepository,
                this.createSavedStateHandle()
            )
        }

        initializer {
            CardAddEditViewModel(
                learnApplication().container.cardsRepository,
                learnApplication().container.decksRepository,
                this.createSavedStateHandle()
            )
        }

        initializer {
            CardDetailViewModel(
                learnApplication().container.decksRepository,
                learnApplication().container.cardsRepository,
                this.createSavedStateHandle()
            )
        }

    }
}

fun CreationExtras.learnApplication(): LearnApplication =
    ( this[AndroidViewModelFactory.APPLICATION_KEY] as LearnApplication)