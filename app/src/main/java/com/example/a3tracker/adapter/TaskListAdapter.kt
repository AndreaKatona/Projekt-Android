package com.example.a3tracker.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a3tracker.R
import com.example.a3tracker.api.model.TaskResponse
import kotlin.collections.ArrayList

class TasksListAdapter(
    private var list: ArrayList<TaskResponse>,
    private val context: Context,
    private val listener: OnItemClickListener,
    private val listener2: OnItemLongClickListener
) :
    RecyclerView.Adapter<TasksListAdapter.SimpleDataViewHolder>() {



    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    open inner class SimpleDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

        override fun onLongClick(v: View?): Boolean {
            TODO("Not yet implemented")
        }
    }

    // 1. user defined ViewHolder type - Embedded class!
    inner class DataViewHolder(itemView: View) : SimpleDataViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {

        val taskTitleTextView: TextView = itemView.findViewById(R.id.task_title_view)
        val taskDescriptionTextView: TextView = itemView.findViewById(R.id.task_description_view)
        val taskPriorityTextView: TextView = itemView.findViewById(R.id.task_priority_view)
        val taskDateTextView: TextView = itemView.findViewById(R.id.task_date_view)
        val taskCreatedBy: TextView = itemView.findViewById(R.id.text_created_by)
        val taskDepartment : TextView = itemView.findViewById(R.id.text_department)

        val taskOwnerProfileImage: ImageView =
            itemView.findViewById(R.id.task_owner_profile_image_view)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(p0: View?) {
            val currentPosition = this.adapterPosition
            listener.onItemClick(currentPosition)

        }

        override fun onLongClick(p0: View?): Boolean {
            val currentPosition = this.adapterPosition
            listener2.onItemLongClick(currentPosition)
            return true
        }
    }

    // 2. Called only a few times = number of items on screen + a few more ones
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleDataViewHolder {
        return when (viewType) {
            TaskListItemType.SIMPLE.value -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.simple_task_list_item, parent, false)
                SimpleDataViewHolder(itemView)
            }
            TaskListItemType.COMPLEX.value -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.task_list_item, parent, false)
                DataViewHolder(itemView)
            }
            else -> {
                throw IllegalStateException("Type is not supported!")
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        val tmp = position - 1

        return if (tmp<0) {
            TaskListItemType.SIMPLE.value
        } else {
            TaskListItemType.COMPLEX.value
        }

    }

    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: SimpleDataViewHolder, position: Int) {
        if (getItemViewType(position) == TaskListItemType.COMPLEX.value) {
            val complexHolder = (holder as DataViewHolder)
            val currentItem = list[position]

            complexHolder.taskTitleTextView.text = currentItem.title
            complexHolder.taskDescriptionTextView.text = currentItem.description
            val departmentID = currentItem.departmentID.toString()
            complexHolder.taskDepartment.text = "${departmentID} department"

            if(currentItem.deadline.toString() == "0")
            {
                complexHolder.taskDateTextView.text = "No deadline specified"
            }else
            {
                complexHolder.taskDateTextView.text = "Due "
            }

            complexHolder.taskCreatedBy.text = "Created by "+currentItem.createdByUserID.toString()

            when (currentItem.priority) {
                0 -> {
                    //complexHolder.taskPriorityTextView.setBackgroundColor(Color.RED)
                    complexHolder.taskPriorityTextView.text = "URGENT"
                }
                1 -> {
                    //complexHolder.taskPriorityTextView.setBackgroundColor(Color.YELLOW)
                    complexHolder.taskPriorityTextView.text = "NON-CRITICAL"
                }
                2 -> {
                    //complexHolder.taskPriorityTextView.setBackgroundColor(Color.GREEN)
                    complexHolder.taskPriorityTextView.text = "LOW"
                }
            }

            Glide.with(context)
                //.load(R.drawable.ic_launcher_background)
                .load("https://devinit.org/assets/img/profile-fallback.e7a6f788830c.jpg")
                //.placeholder(R.drawable.ic_launcher_background)
                .override(100, 100)
                .into(complexHolder.taskOwnerProfileImage)
        }
    }

    override fun getItemCount() = list.size

    // Update the list
    fun setData(newList: ArrayList<TaskResponse>) {
        list = newList

    }

    private enum class TaskListItemType(val value: Int) {
        SIMPLE(0),
        COMPLEX(1)
    }
}

