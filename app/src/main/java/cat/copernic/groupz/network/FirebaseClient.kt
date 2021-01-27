package cat.copernic.groupz.network

import com.google.firebase.firestore.FirebaseFirestore

class FirebaseClient {
    var db = FirebaseFirestore.getInstance()

    companion object {
        var db = FirebaseFirestore.getInstance()
        fun addDatabaseUser(
            email: String,
            dateOfBirth: String,
            hobbies: String
        ): Boolean {
            val USERS = "Users"
            var added = false
            val user = hashMapOf( //Rellenamos los datos para la base de datos
                "Mail" to email,
                "birth" to dateOfBirth,
                "hobbies" to hobbies,
                "description" to "",
                "isOnline" to "false"
            )
            db.collection(USERS)
                .document(email) //Añadimos el hash a la base de datos, el id del fichero sera el mail.
                .set(user)
                .addOnSuccessListener { documentReference -> //Si es correcto, avisa al usuario de que la cuenta se ha creado correctamente, y manda al siguiente fragment
                    added = true
                }
                .addOnFailureListener { e -> //En caso de fallar, avisa al usuario de que ha habido un error.
                    added = false
                }
            return added
        }
    }
}

// email: String,
//            dateOfBirth: String,
//            hobbies: String,
//            dbCollection: String
