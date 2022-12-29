package com.example.a3tracker.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a3tracker.R
import com.example.a3tracker.adapter.MyTaskListAdapter
import com.example.a3tracker.api.model.TaskResponse
import com.example.a3tracker.api.model.UserResponse
import com.example.a3tracker.viewmodel.GetUsersViewModel
import com.example.a3tracker.viewmodel.TasksViewModel


class MyTasksFragment : Fragment(R.layout.fragment_my_tasks), MyTaskListAdapter.OnItemClickListener,
    MyTaskListAdapter.OnItemLongClickListener {

    companion object {
        private val TAG: String = javaClass.simpleName
    }

    private val tasksViewModel: TasksViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyTaskListAdapter
    private val getUsersViewModel:GetUsersViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_tasks, container, false)

        recyclerView = view.findViewById(R.id.recycler_view2)
        setupRecyclerView()
        tasksViewModel.products.observe(viewLifecycleOwner) {
           // Log.d(TAG, "My tasks list = $it")
            adapter.setData(tasksViewModel.products.value as ArrayList<TaskResponse>,getUsersViewModel.getUsers.value as ArrayList<UserResponse>)
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
        adapter = MyTaskListAdapter(ArrayList(), ArrayList(),this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerView.setHasFixedSize(true)
    }

    override fun onItemClick(position: Int) {
//        TODO("Not yet implemented")
    }

    override fun onItemLongClick(position: Int) {
//        TODO("Not yet implemented")
    }


}
