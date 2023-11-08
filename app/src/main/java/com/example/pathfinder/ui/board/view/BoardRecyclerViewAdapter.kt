import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pathfinder.R
import com.example.pathfinder.data.models.Board
import com.example.pathfinder.ui.board.view.BoardInsideActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class BoardRecyclerViewAdapter(private val boardList: MutableList<Board>) :
    RecyclerView.Adapter<BoardRecyclerViewAdapter.BoardViewHolder>() {

    // ViewHolder 클래스 정의
    class BoardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.titleArea)
        val content: TextView = view.findViewById(R.id.contentArea)
        val date: TextView = view.findViewById(R.id.timeArea)
        val author: TextView = view.findViewById(R.id.userName)
        val tagsLayout: LinearLayout = view.findViewById(R.id.tagsLayout)
        // 추가적으로 필요한 뷰를 여기에 정의합니다.
    }

    // 새로운 뷰 홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.board_list_item, parent, false)
        return BoardViewHolder(view)
    }

    // 뷰 홀더의 데이터 바인딩
    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        val board = boardList[position]
        holder.title.text = board.title
        holder.content.text = board.content
        holder.date.text = formatDate(board.date.toLong())
        holder.author.text = board.author
        // 태그 표시 로직
        displayTags(holder.tagsLayout, board.tags)

        holder.itemView.setOnClickListener {
            // 클릭 시 동작
            val intent = Intent(holder.itemView.context, BoardInsideActivity::class.java)
            intent.putExtra("boardId", board.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    // 데이터 셋의 크기 반환
    override fun getItemCount(): Int {
        return boardList.size
    }

    private fun formatDate(timeStamp: Long): String {
        val date = Date(timeStamp)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return sdf.format(date)
    }

    private fun displayTags(tagsLayout: LinearLayout, tags: List<String>) {
        tagsLayout.removeAllViews()
        for (tag in tags) {
            val tagViewLayout = LayoutInflater.from(tagsLayout.context).inflate(R.layout.item_tag, tagsLayout, false)
            val tagTextView = tagViewLayout.findViewById<TextView>(R.id.tagName)
            tagTextView.text = tag
            tagsLayout.addView(tagViewLayout)
        }
    }
}
