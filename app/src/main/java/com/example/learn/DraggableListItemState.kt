package com.example.learn

import kotlin.math.abs

class DraggableListItemState(
    private var startPosition: Float,

) {
    private var draggedPosition = startPosition
    private var oldStartPosition: Float? = null
    private var newStartPosition: Float? = null

    companion object {
        private var _collisionDistance = 0f
        var collisionDistance: Float
            get() = _collisionDistance
            set(value) {
                if (value > 0) {
                    _collisionDistance = value
                } else {
                    throw IllegalArgumentException("Collision distance must be greater than 0")
                }
            }
    }

    fun isCollidingWith(stillState: DraggableListItemState): Boolean =
        abs(draggedPosition - stillState.startPosition) <= collisionDistance


    fun handleCollisionWith(stillState: DraggableListItemState) {
        if (isCollidingWith(stillState)) {
            oldStartPosition = startPosition
            newStartPosition = stillState.startPosition
            stillState.startPosition = startPosition
        }
    }
}