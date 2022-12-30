package com.example.a3tracker.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a3tracker.R
import com.example.a3tracker.adapter.MyTaskListAdapter
import com.example.a3tracker.api.model.DepartmentResponse
import com.example.a3tracker.api.model.TaskResponse
import com.example.a3tracker.api.model.UserResponse
import com.example.a3tracker.viewmodel.DepartmentsViewModel
import com.example.a3tracker.viewmodel.GetUsersViewModel
import com.example.a3tracker.viewmodel.TasksViewModel
import java.nio.BufferUnderflowException


class MyTasksFragment : Fragment(R.layout.fragment_my_tasks), MyTaskListAdapter.OnItemClickListener,
    MyTaskListAdapter.OnItemLongClickListener {


    private val tasksViewModel: TasksViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyTaskListAdapter
    private val getUsersViewModel:GetUsersViewModel by activityViewModels()
    private val departmentsViewModel:DepartmentsViewModel by activityViewModels()
    private lateinit var users : ArrayList<UserResponse>
    private lateinit var departments:ArrayList<DepartmentResponse>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_tasks, container, false)

        users = getUsersViewModel.getUsers.value as ArrayList<UserResponse>
        departments = departmentsViewModel.departments.value as ArrayList<DepartmentResponse>


        recyclerView = view.findViewById(R.id.recycler_view2)
        setupRecyclerView()
        tasksViewModel.products.observe(viewLifecycleOwner) {
           // Log.d(TAG, "My tasks list = $it")
            adapter.setData(tasksViewModel.products.value as ArrayList<TaskResponse>,
                getUsersViewModel.getUsers.value as ArrayList<UserResponse>,
                departmentsViewModel.departments.value as ArrayList<DepartmentResponse> )
            adapter.notifyDataSetChanged()
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button: Button = view.findViewById(R.id.addTask)

        button.setOnClickListener{

            findNavController().navigate(R.id.addTaskFragment)
        }


    }
    private fun setupRecyclerView() {
        adapter = MyTaskListAdapter(ArrayList(), ArrayList(), requireContext(),
            ArrayList(),this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
    }

    override fun onItemClick(position: Int) {

        val clickedItem : TaskResponse ?= tasksViewModel.products.value?.get(position)
        val department_name :String?= getDepartmentById(clickedItem?.departmentID, departments  )
        val user_name : String? = getNameById(clickedItem?.createdByUserID,users)
        val createdBy :  String? = getNameById(clickedItem?.createdByUserID,users)


        val  bundle = bundleOf(
            "title" to clickedItem?.title,
            "description" to clickedItem?.description,
            "createdBy" to createdBy,
            "assigned_to_user_id" to user_name,
            "priority" to clickedItem?.priority.toString(),
            "deadline" to clickedItem?.deadline.toString(),
            "departmentId" to department_name,
            "status" to clickedItem?.status.toString(),
            "progress" to clickedItem?.progress.toString())

        findNavController().navigate(R.id.detailsFragment,bundle)
    }

    override fun onItemLongClick(position: Int) {
//        TODO("Not yet implemented")
    }

    fun getNameById(Id : Int?, listUser : ArrayList<UserResponse>) : String
    {
        val user = listUser.find { it.id == Id}
        //Log.d("user", user.toString())
        if (user != null) {
            return user.last_name + " " +user.first_name
        }
        return "Anonymus"
    }
    fun getDepartmentById(Id : Int?,departments:ArrayList<DepartmentResponse>) : String
    {
        val value = departments.find { it.Id == Id}
        //Log.d("user", user.toString())
        if (value != null) {
            return value.departmentName
        }
        return "Anonymus"
    }

}
