package com.example.drillar.presentation.DrillARScreen

sealed interface DrillAREvents {
    data object OnARSessionCreated : DrillAREvents
    data object OnPlaneDetected : DrillAREvents
    data class OnDrillPlaced(val position: FloatArray) : DrillAREvents {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as OnDrillPlaced

            return position.contentEquals(other.position)
        }

        override fun hashCode(): Int {
            return position.contentHashCode()
        }
    }
    data object OnDrillRemoved : DrillAREvents
    data class OnError(val message: String) : DrillAREvents
    data object OnRetry : DrillAREvents
}