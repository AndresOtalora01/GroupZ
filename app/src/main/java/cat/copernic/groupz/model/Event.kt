package cat.copernic.groupz.model


data class Event(
    var admin: String,
    var date: String,
    var description: String ,
    var image: String,
    var location: String ,
    var members: List<String> ,
    var name: String,
    var privacity: Boolean

    )

