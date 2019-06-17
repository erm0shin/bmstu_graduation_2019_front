package dto

import kotlinx.serialization.Serializable

@Serializable
data class PerformanceRequest (
    var unitType: String = "",
    var unit: String = "",
    var begin: String = "",
    var end: String = "",
    var course: String = ""
)