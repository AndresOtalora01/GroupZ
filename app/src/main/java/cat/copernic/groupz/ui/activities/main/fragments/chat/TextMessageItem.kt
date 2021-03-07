package cat.copernic.groupz.ui.activities.main.fragments.chat

import android.view.Gravity
import android.widget.FrameLayout
import cat.copernic.groupz.R
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.row_message_chat.view.*
import java.text.SimpleDateFormat
import java.util.*


class TextMessageItem(val message: TextMessage) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tvMessageText.text = message.text
        setTimeText(viewHolder)
        setMessageRootGravity(viewHolder)
    }

    private fun setTimeText(viewHolder: ViewHolder) {
        val dateFormat = SimpleDateFormat
            .getTimeInstance(SimpleDateFormat.SHORT)
        val calendar = Calendar.getInstance()
        calendar.time = message.time
        calendar.add(Calendar.HOUR, 1)
        viewHolder.itemView.tvMessageTime.text = dateFormat.format(calendar.time)
    }

    private fun setMessageRootGravity(viewHolder: ViewHolder) {
        if (message.senderId == FirebaseAuth.getInstance().currentUser?.uid) {
            viewHolder.itemView.messageRoot.apply {
              tvMessageText.setBackgroundResource(R.drawable.to_message)
                val lParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    Gravity.END
                )
                this.layoutParams = lParams
            }
        } else {
            viewHolder.itemView.messageRoot.apply {
                tvMessageText.setBackgroundResource(R.drawable.from_message)
                val lParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    Gravity.START
                )
                this.layoutParams = lParams
            }
        }
    }
    override fun getLayout() = R.layout.row_message_chat

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        if (other !is TextMessageItem)
            return false
        if(this.message != other.message)
            return false
        return true
    }

    override fun equals(other: Any?): Boolean {
      return isSameAs(other as? TextMessageItem)
    }
}