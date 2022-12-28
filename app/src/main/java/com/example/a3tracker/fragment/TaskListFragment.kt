package com.example.a3tracker.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a3tracker.R
import com.example.a3tracker.adapter.TasksListAdapter
import com.example.a3tracker.api.ThreeTrackerRepository
import com.example.a3tracker.api.model.TaskResponse
import com.example.a3tracker.viewmodel.TasksViewModel
import com.example.a3tracker.viewmodel.TasksViewModelFactory


class TaskListFragment : Fragment(R.layout.fragment_task_list), TasksListAdapter.OnItemClickListener,
    TasksListAdapter.OnItemLongClickListener {

    companion object {
        private val TAG: String = javaClass.simpleName
    }

    private lateinit var tasksViewModel: TasksViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TasksListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = TasksViewModelFactory(ThreeTrackerRepository())
        tasksViewModel = ViewModelProvider(this, factory)[TasksViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        setupRecyclerView()
        tasksViewModel.products.observe(viewLifecycleOwner) {
            Log.d(TAG, "Tasks list = $it")
            adapter.setData(tasksViewModel.products.value as ArrayList<TaskResponse>)
            adapter.notifyDataSetChanged()
        }

        return view
    }

    private fun setupRecyclerView() {
        adapter = TasksListAdapter(ArrayList(), requireContext(), this, this)
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