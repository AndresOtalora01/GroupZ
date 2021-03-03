package cat.copernic.groupz.network

import android.util.Log
import cat.copernic.groupz.model.Event
import cat.copernic.groupz.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FirebaseClient {
    companion object {
        var db = FirebaseFirestore.getInstance()
        var auth = FirebaseAuth.getInstance()
        var storage = FirebaseStorage.getInstance()

        var TAG = "FirebaseClient"
        fun addDatabaseUser(userAdd: User): Boolean {
            val USERS = "Users"
            var added: Boolean = true
            val userMap = hashMapOf(
                //Rellenamos los datos para la base de datos
                "Mail" to userAdd.mail,
                "Name" to userAdd.name,
                "Birth" to userAdd.birth,
                "Hobbies" to userAdd.hobbies,
                "Image" to userAdd.image,
                "Description" to userAdd.description,
                "Location" to userAdd.location
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

        fun getDatabaseUser(userMail: String): User? {
            val USERS = "Users"
            var userGet: User? = null
            val data = db.collection(USERS).document(userMail)
            data.get()
                .addOnSuccessListener {
                    if (it != null) {
                        userGet = User(
                            it.get("Name") as String,
                            userMail,
                            it.get("Birth") as String,
                            it.get("Hobbies") as String,
                            it.get("Images") as String,
                            it.get("Description") as String,
                            it.get("Location") as String
                        )
                        Log.d(TAG, "Success")
                    }

                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Failure")
                }

            return userGet
        }

        fun addDatabaseCommunityEvent(eventAdd: Event): Boolean {
            var added: Boolean = true
            val eventMap = hashMapOf( //Rellenamos los datos para la base de datos
                "Admin" to eventAdd.admin,
                "Name" to eventAdd.name,
                "Date" to eventAdd.date,
                "Location" to eventAdd.location,
                "Description" to eventAdd.description,
                "Members" to eventAdd.members,
                "Privacity" to eventAdd.privacity,
                "Image" to eventAdd.image
            )
            db.collection("CommunityEvents")
                .add(eventMap)
                .addOnSuccessListener { e -> //Si es correcto, avisa al usuario de que la cuenta se ha creado correctamente, y manda al siguiente fragment
                    added = true
                }
                .addOnFailureListener { e -> //En caso de fallar, avisa al usuario de que ha habido un error.
                    added = false
                }
            return added
        }

        fun userLogIn(): Boolean {
            return auth.currentUser != null
        }

        fun insertMessageChatFromUser() {


        }

        fun getDatabaseChatsFromUser(userMail: String) {
            val messages = "Mensaje"
            val chats = "Chats"
            var queryMessages = db.collection(messages).whereEqualTo("from", userMail)

            queryMessages.get()
                .addOnSuccessListener { documents ->

                    for (document in documents) {
                        /*var data = db.collection("Chat").document(document.get("idChat").toString())
                        data.get().addOnSuccessListener {
                            Log.d(TAG,it.get("name")as String)
                        }*/

                    }
                }
                .addOnFailureListener {

                }
        }


    }
}

//            email: String,
//            dateOfBirth: String,
//            hobbies: String,
//            dbCollection: String
