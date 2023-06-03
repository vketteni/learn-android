package com.example.learn.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.learn.LearnApplication
import com.example.learn.ui.card.CardAddEditViewModel
import com.example.learn.ui.deck.DeckDetailViewModel
import com.example.learn.ui.deck.DeckEntryViewModel
import com.example.learn.ui.deck.DeckOverviewViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            DeckOverviewViewModel(
                learnApplication().container.decksRepository
            )
        }

        initializer {
            DeckEntryViewModel(
                learnApplication().container.decksRepository
            )
        }

        initializer {
            DeckDetailViewModel(
                learnApplication().container.cardsRepository,
                this.createSavedStateHandle()
            )
        }

        initializer {
            CardAddEditViewModel(
                learnApplication().container.cardsRepository,
                this.createSavedStateHandle()
            )
        }

    }
}

fun CreationExtras.learnApplication(): LearnApplication =
    ( this[AndroidViewModelFactory.APPLICATION_KEY] as LearnApplication)