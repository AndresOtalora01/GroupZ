package cat.copernic.groupz.network

import android.content.Context
import android.util.Log
import cat.copernic.groupz.model.User
import cat.copernic.groupz.ui.activities.main.fragments.chat.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.kotlinandroidextensions.Item
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object FirestoreUtil {
    private val
            firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val chatChannelsCollectionRef = firestoreInstance.collection("chatChannels")

    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document(
            "Users/${
                FirebaseAuth.getInstance().currentUser?.email ?: throw NullPointerException(
                    "UID is null."
                )
            }"
        )

    fun getCurrentUser(onComplete: (User) -> Unit) {
        currentUserDocRef.get()
            .addOnSuccessListener {
                onComplete(it.toObject(User::class.java)!!)
            }
    }

    fun getOrCreateChatChannel(
        otherUserId: String,
        onComplete: (channelId: String) -> Unit
    ) {
        currentUserDocRef.collection("engagedChatChannels").document(otherUserId).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    onComplete(it["channelId"] as String)
                    return@addOnSuccessListener
                }

                val currentUserId = FirebaseAuth.getInstance().currentUser!!.email.toString()

                val newChannel = chatChannelsCollectionRef.document()
                newChannel.set(ChatChannel(mutableListOf(currentUserId, otherUserId)))

                currentUserDocRef.collection("engagedChatChannels").document(otherUserId)
                    .set(mapOf("channelId" to newChannel.id))

                firestoreInstance.collection("Users").document(otherUserId)
                    .collection("engagedChatChannels")
                    .document(currentUserId)
                    .set(mapOf("channelId" to newChannel.id))

                onComplete(newChannel.id)
            }

    }
    fun addChatMessageListener(channelId: String, onListen: (List<Item>) -> Unit) : ListenerRegistration {
        return chatChannelsCollectionRef.document(channelId).collection("messages")
            .orderBy("time")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if(firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "ChateMessageListener error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }
                val items = mutableListOf<Item>()
                querySnapshot!!.documents.forEach {
                    if(it["type"] == MessageType.TEXT)
                        items.add(TextMessageItem(it.toObject(TextMessage::class.java)!!))
                    else
                        TODO("add image message")
                }
                onListen(items)
            }
    }

    fun addUsersListener(onListen: (List<Item>) -> Unit): ListenerRegistration {
        return firestoreInstance.collection("Users")
            .addSnapshotListener{ querySnapshot, firebaseFirestoreException ->
                if(firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "Users listener error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }
                val items = mutableListOf<Item>()
                querySnapshot?.documents?.forEach {
                    val map: Map<String, String> = it.data as Map<String, String>
                  if(it.id != FirebaseAuth.getInstance().currentUser?.email) {
                      var description = map["Description"]
                      var mail = map["Mail"]
                      var birth = map["Birth"]
                      var image = map["Image"]
                      var hobbies = map["Hobbies"]
                      var name = map["Name"]
                      var location = map["Location"]
                      var user = User(name!!, mail!!, birth!!, hobbies!!, image!!, description!!, location!!)
                      items.add(UserItem(user, mail))
                  }
                }
                onListen(items)
            }
    }

    fun sendMessage(message:Message, channelId : String) {
        chatChannelsCollectionRef.document(channelId).collection("messages")
            .add(message)
    }

    fun removeListener(registration: ListenerRegistration) = registration.remove()
}