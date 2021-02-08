package cat.copernic.groupz.network

import android.util.Log
import cat.copernic.groupz.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FirebaseClient {
    companion object {
        var db = FirebaseFirestore.getInstance()
        var auth = FirebaseAuth.getInstance()
        var storage = FirebaseStorage.getInstance()

        fun addDatabaseUser(userAdd: User): Boolean {
            val USERS = "Users"
            var added : Boolean = true
            val userMap = hashMapOf( //Rellenamos los datos para la base de datos
                "Mail" to userAdd.mail,
                "Name" to userAdd.name,
                "Birth" to userAdd.birth,
                "Hobbies" to userAdd.hobbies,
                "Description" to userAdd.description,
                "Location" to userAdd.location,
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

        fun getDatabaseUser(userMail : String): User{
            var loaded = false
            val USERS = "Users"
            val userGet = User()
            val data = db.collection(USERS).document(userMail)
            data.get()
                .addOnSuccessListener {
                    if (it != null) {
                        userGet.mail = auth.currentUser?.email.toString()
                        userGet.name = it.get("Name") as String
                        userGet.birth = it.get("Birth") as String
                        userGet.hobbies = it.get("Hobbies") as String
                        userGet.description = it.get("Description") as String
                        userGet.location = it.get("Location") as String
                        loaded = true
                    } else {
                        loaded = false
                    }
                }
                .addOnFailureListener { exception ->
                    loaded = false
                }
            return userGet
        }

        fun userLogIn():Boolean{
            if (auth.currentUser != null){
                return true
            } else {
                return false
            }
        }

        fun getDatabaseChatsFromUser(userMail : String){
            val messages = "Messages"
            val chats = "Chats"
            var queryMessages = db.collection(messages).whereEqualTo("from",userMail)

            queryMessages.get()
                .addOnSuccessListener {
                   db.collection(chats).get()
                }
                .addOnFailureListener{

                }
        }


    }
}

//              email: String,
//            dateOfBirth: String,
//            hobbies: String,
//            dbCollection: String
