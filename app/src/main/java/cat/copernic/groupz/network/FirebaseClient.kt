package cat.copernic.groupz.network

import android.content.Context
import android.util.Log
import cat.copernic.groupz.model.Event
import cat.copernic.groupz.model.Group
import cat.copernic.groupz.model.User
import cat.copernic.groupz.ui.activities.main.fragments.chat.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.storage.FirebaseStorage
import com.xwray.groupie.kotlinandroidextensions.Item
import java.lang.NullPointerException

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

        fun addDatabaseGroup(group: Group): Boolean {
            var added: Boolean = true
            val groupMap = hashMapOf( //Rellenamos los datos para la base de datos
                "Admin" to group.admin,
                "Name" to group.name,
                "Description" to group.description,
                "Members" to group.members,
                "Image" to group.image,
                "Hobbies" to group.hobbies
            )
            db.collection("Group")
                .add(groupMap)
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



    }
}