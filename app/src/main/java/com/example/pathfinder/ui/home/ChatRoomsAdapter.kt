package com.example.pathfinder.ui.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pathfinder.data.models.ChatRoom
import com.example.pathfinder.databinding.ItemChatRoomBinding

class ChatRoomsAdapter(
    private var chatRooms: List<ChatRoom>,
    private val clickListener: ChatRoomClickListener // 클릭 리스너 추가
) : RecyclerView.Adapter<ChatRoomsAdapter.ChatRoomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolder {
        val binding = ItemChatRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatRoomViewHolder(binding, clickListener) // 클릭 리스너를 ViewHolder에 전달
    }

    override fun onBindViewHolder(holder: ChatRoomViewHolder, position: Int) {
        holder.bind(chatRooms[position])
    }

    override fun getItemCount(): Int = chatRooms.size

    fun updateList(newList: List<ChatRoom>) {
        chatRooms = newList
        notifyDataSetChanged()
    }

    class ChatRoomViewHolder(
        private val binding: ItemChatRoomBinding,
        private val clickListener: ChatRoomClickListener // 클릭 리스너를 ViewHolder에서 사용
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(chatRoom: ChatRoom) {
            binding.textViewChatRoomName.text = chatRoom.name
            binding.root.setOnClickListener {
                clickListener.onChatRoomClicked(chatRoom.id) // 클릭 시 리스너 호출
            }
        }
    }
}
interface ChatRoomClickListener {
    fun onChatRoomClicked(chatRoomId: String)
}
