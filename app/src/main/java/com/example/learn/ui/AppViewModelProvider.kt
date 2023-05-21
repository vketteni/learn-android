package com.example.learn.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.learn.LearnApplication
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

    }
}

fun CreationExtras.learnApplication(): LearnApplication =
    ( this[AndroidViewModelFactory.APPLICATION_KEY] as LearnApplication)