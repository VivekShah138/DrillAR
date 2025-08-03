package com.example.drillar.presentation.DrillARScreen

data class DrillARStates(
    val selectedDrillId: Int = 1,
    val selectedDrillName: String = "Drill 1",
    val isLoading: Boolean = true,
    val isARInitialized: Boolean = false,
    val isPlaneDetected: Boolean = false,
    val isDrillPlaced: Boolean = false,
    val drillPosition: FloatArray? = null,
    val errorMessage: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DrillARStates

        if (selectedDrillId != other.selectedDrillId) return false
        if (selectedDrillName != other.selectedDrillName) return false
        if (isLoading != other.isLoading) return false
        if (isARInitialized != other.isARInitialized) return false
        if (isPlaneDetected != other.isPlaneDetected) return false
        if (isDrillPlaced != other.isDrillPlaced) return false
        if (drillPosition != null) {
            if (other.drillPosition == null) return false
            if (!drillPosition.contentEquals(other.drillPosition)) return false
        } else if (other.drillPosition != null) return false
        if (errorMessage != other.errorMessage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = selectedDrillId
        result = 31 * result + selectedDrillName.hashCode()
        result = 31 * result + isLoading.hashCode()
        result = 31 * result + isARInitialized.hashCode()
        result = 31 * result + isPlaneDetected.hashCode()
        result = 31 * result + isDrillPlaced.hashCode()
        result = 31 * result + (drillPosition?.contentHashCode() ?: 0)
        result = 31 * result + (errorMessage?.hashCode() ?: 0)
        return result
    }
}