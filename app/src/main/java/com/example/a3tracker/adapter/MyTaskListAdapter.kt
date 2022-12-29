package com.example.a3tracker.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a3tracker.R
import com.example.a3tracker.api.model.TaskResponse
import com.example.a3tracker.api.model.UserResponse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyTaskListAdapter(
    private var list: ArrayList<TaskResponse>,
    private var listUser: ArrayList<UserResponse>,
    private val listener: OnItemClickListener,
    private val listener2: OnItemLongClickListener
) :
    RecyclerView.Adapter<MyTaskListAdapter.DataViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    // 1. user defined ViewHolder type - Embedded class!
    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {

        val taskTitleTextView: TextView = itemView.findViewById(R.id.my_task_title_view)
        val taskDescriptionTextView: TextView = itemView.findViewById(R.id.my_task_description_view)
        val taskDeadlineTextView: TextView = itemView.findViewById(R.id.my_task_deadline_view)
        val taskPriorityView: TextView = itemView.findViewById(R.id.my_task_priority_view)
        val taskCreatedByUserId: TextView = itemView.findViewById(R.id.my_task_createdBy)
        val taskAssignedTo: TextView = itemView.findViewById(R.id.my_task_assignedTo)
        val progressBar : ProgressBar = itemView.findViewById(R.id.progressBar)
        val progress : TextView = itemView.findViewById(R.id.progress)

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.my_task_list_item, parent, false)
        return DataViewHolder(itemView)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val complexHolder = holder
        val currentItem = list[position]

        when (currentItem.priority) {
            0 -> {
                //complexHolder.taskPriorityTextView.setBackgroundColor(Color.RED)
                complexHolder.taskPriorityView.text = "URGENT"
            }
            1 -> {
                //complexHolder.taskPriorityTextView.setBackgroundColor(Color.YELLOW)
                complexHolder.taskPriorityView.text = "NON-CRITICAL"
            }
            2 -> {
                //complexHolder.taskPriorityTextView.setBackgroundColor(Color.GREEN)
                complexHolder.taskPriorityView.text = "LOW"
            }
        }



        val simpleDateFormat = SimpleDateFormat("dd MM yyy, HH:mm",Locale.ENGLISH)
        val assignedName = getNameById(currentItem.assignedToUserID)
        val createdName = getNameById(currentItem.createdByUserID)

        holder.taskTitleTextView.text = currentItem.title
        holder.taskDescriptionTextView.text = currentItem.description

        if(currentItem.deadline.toString()=="0")
        {
            holder.taskDeadlineTextView.text = "No deadline specified"
        }
        else
        {
            holder.taskDeadlineTextView.text =  simpleDateFormat.format(currentItem.deadline)
        }

        holder.taskCreatedByUserId.text = "Created by ${createdName}"
        holder.taskAssignedTo.text = "Assigned to ${assignedName}"
        holder.progressBar.progress = currentItem.status
        holder.progress.text = "${currentItem.status}%"
    }

    override fun getItemCount() = list.size

    // Update the list
    fun setData(newList: ArrayList<TaskResponse>, newListUser: ArrayList<UserResponse>) {
        list = newList
        list.reverse()
        listUser = newListUser
    }

    fun getNameById(Id : Int) : String
    {
       val user = listUser.find { it.id == Id}
        //Log.d("user", user.toString())
        if (user != null) {
            return user.last_name + " " +user.first_name
        }
        return "Anonymus"
    }
}