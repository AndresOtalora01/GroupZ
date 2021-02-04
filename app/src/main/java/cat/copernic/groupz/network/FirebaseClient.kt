package cat.copernic.groupz.network

import cat.copernic.groupz.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseClient {
    companion object {
        var db = FirebaseFirestore.getInstance()
        var auth = FirebaseAuth.getInstance()


        fun addDatabaseUser(userAdd: User): Boolean {
            val USERS = "Users"
            var added : Boolean = true
            val userMap = hashMapOf( //Rellenamos los datos para la base de datos
                "Mail" to userAdd.mail,
                "Name" to userAdd.name,
                "Birth" to userAdd.birth,
                "Hobbies" to userAdd.hobbies,
                "Description" to userAdd.description,
                "isOnline" to "false"
            )
            db.collection(USERS)
                .document(userAdd.mail) //Añadimos el hash a la base de datos, el id del fichero sera el mail.
                .set(userMap)
                .addOnSuccessListener { e -> //Si es correcto, avisa al usuario de que la cuenta se ha creado correctamente, y manda al siguiente fragment
                    added = true
                }
                .addOnFailureListener { e -> //En caso de fallar, avisa al usuario de que ha habido un error.
                    added = false
                }
            return added
        }

        fun getDatabaseUser(userGet: User): Boolean{
            var loaded = false
            val USERS = "Users"
            val data = db.collection(USERS).document(userGet.mail)
            data.get()
                .addOnSuccessListener {
                    if (it != null) {
                        userGet.name = it.get("Name") as String
                        userGet.birth = it.get("Birth") as String
                        userGet.hobbies = it.get("Hobbies") as String
                        userGet.description = it.get("Description") as String
                        loaded = true
                    } else {
                        loaded = false
                    }
                }
                .addOnFailureListener { exception ->
                    loaded = false
                }
            return loaded

        }

        fun userLogIn():Boolean{
            if (auth.currentUser != null){
                return true
            } else {
                return false
            }
        }


    }
}

//              email: String,
//            dateOfBirth: String,
//            hobbies: String,
//            dbCollection: String
