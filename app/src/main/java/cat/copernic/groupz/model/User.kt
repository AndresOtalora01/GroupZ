package cat.copernic.groupz.model


import java.io.Serializable

class User (
    var name : String,
    var mail : String,
    var birth : String,
    var hobbies : String,
    var image: String,
    var description : String,
    var location : String
): Serializable {
    constructor() : this("", "", "","","","", "")
}