package cat.copernic.groupz.ui.activities.main.fragments.chat

data class ChatChannel(val userIds: MutableList<String>) {
    constructor() : this(mutableListOf())
}