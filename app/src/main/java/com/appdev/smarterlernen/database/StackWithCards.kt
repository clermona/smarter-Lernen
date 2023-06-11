package com.appdev.smarterlernen.database

import androidx.room.Embedded
import androidx.room.Relation

data class StackWithCards (

    // Beziehung Stack -> Card: 1:n

    @Embedded val stack: Stack,
    @Relation (
        parentColumn = "id",
        entityColumn = "id"
            )
    val cards: List<Card>
)