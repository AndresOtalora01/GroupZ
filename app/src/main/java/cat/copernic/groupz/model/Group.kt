package cat.copernic.groupz.model


data class Group(
    var admin: String,
    var description: String ,
    var image: String,
    var members: List<String> ,
    var hobbies : String,
    var name: String
    )

