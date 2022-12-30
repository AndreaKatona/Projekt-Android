package com.example.a3tracker.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a3tracker.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {


    private lateinit var binding: FragmentDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentDetailsBinding.inflate(inflater)
        binding.descriptionDescription.movementMethod = ScrollingMovementMethod()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setValues()



    }

    @SuppressLint("SetTextI18n")
    private fun setValues()
    {

        val statusBar: String? = arguments?.getString("status")
        val assigned_By: String?= arguments?.getString("createdBy")
        val assignee:String? = arguments?.getString("assigned_to_user_id")
        val departmentName =  arguments?.getString("departmentId")
        val priority = arguments?.getString("priority")
        val fakeData : String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Massa ultricies mi quis hendrerit dolor. Scelerisque purus semper eget duis. Laoreet id donec ultrices tincidunt arcu non sodales neque sodales. Odio eu feugiat pretium nibh ipsum. Aliquam etiam erat velit scelerisque. Ut ornare lectus sit amet est placerat in egestas erat. Vulputate ut pharetra sit amet. Nunc mattis enim ut tellus elementum sagittis vitae. Id eu nisl nunc mi ipsum. Est placerat in egestas erat. In fermentum et sollicitudin ac orci. At auctor urna nunc id.\n" +
                "\n" +
                "Nec nam aliquam sem et tortor. Etiam non quam lacus suspendisse faucibus interdum posuere lorem ipsum. Volutpat maecenas volutpat blandit aliquam etiam erat velit scelerisque. Enim sed faucibus turpis in. Dictum non consectetur a erat nam at. Nec feugiat nisl pretium fusce id velit. Dui sapien eget mi proin sed libero enim sed. Accumsan lacus vel facilisis volutpat est velit egestas dui id. Faucibus purus in massa tempor nec feugiat nisl pretium. Sed tempus urna et pharetra pharetra massa massa. Nunc vel risus commodo viverra maecenas. Pellentesque adipiscing commodo elit at imperdiet dui accumsan sit. Ullamcorper a lacus vestibulum sed arcu non odio euismod lacinia. Ut consequat semper viverra nam libero. Vivamus at augue eget arcu dictum varius duis at."


        binding.descriptionTitle.text = arguments?.getString("title")
        binding.descriptionDescription.text = fakeData//arguments?.getString("description")

        binding.descriptionAssignedBy.text = "Assigned by ${assigned_By}"
        binding.descriptionAssignedTo.text = "Assignee by ${assignee}"

        binding.descriptionDeadline.text = arguments?.getString("deadline")
        binding.descriptionProject.text ="${departmentName} project"
        binding.descriptionStatus.text = "${statusBar}% "
        binding.progressBar2.progress = statusBar?.toInt()!!

        when (priority) {
            "0"-> {

                binding.descriptionPriority.text =  "URGENT"
            }
            "1" -> {
                binding.descriptionPriority.text = "NON-CRITICAL"
            }
            "2" -> {
                binding.descriptionPriority.text = "LOW"
            }
        }

    }


}