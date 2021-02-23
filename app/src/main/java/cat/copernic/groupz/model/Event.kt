package cat.copernic.groupz.model

data class Event(
    var admin: String = "",
    var date: String = "",
    var description: String = "",
    var location: String = "",
    var members: List<String> = emptyList(),
    var name: String= "",
    var privacity: Boolean = false
)

