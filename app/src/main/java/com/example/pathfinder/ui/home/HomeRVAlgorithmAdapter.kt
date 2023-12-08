import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pathfinder.R
import com.example.pathfinder.data.model.Board
import com.example.pathfinder.ui.board.BoardInsideActivity

class HomeRVAlgorithmAdapter(private val boardList: MutableList<Board>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_MENTEE = 0
        const val TYPE_MENTOR = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (boardList[position].boardType) {
            "MENTOR" -> TYPE_MENTEE
            "MENTEE" -> TYPE_MENTOR
            else -> throw IllegalArgumentException("Unknown type of board at position $position")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_MENTEE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_algorithm_list_mentee, parent, false)
                HomeViewHolder(view)
            }
            TYPE_MENTOR -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_algorithm_list_mentor, parent, false)
                HomeViewHolder(view)
            }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val board = boardList[position]
        when (holder.itemViewType) {
            TYPE_MENTEE, TYPE_MENTOR -> {
                val homeHolder = holder as HomeViewHolder
                homeHolder.title.text = board.title
                homeHolder.content.text = board.content
                homeHolder.itemView.setOnClickListener {
                    val intent = Intent(holder.itemView.context, BoardInsideActivity::class.java)
                    intent.putExtra("boardId", board.id)
                    holder.itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return boardList.size
    }

    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.titleAreaAlgorithm)
        val content: TextView = view.findViewById(R.id.contentAreaAlgorithm)
    }
}
