package com.example.a3tracker.fragment
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.a3tracker.api.ThreeTrackerRepository
import com.example.a3tracker.databinding.FragmentUpdateBinding
import com.example.a3tracker.viewmodel.*

class UpdateFragment : Fragment() {


    private lateinit var binding : FragmentUpdateBinding
    private lateinit var profileViewModel : ProfileViewModel
    private lateinit var updateProfile : UpdateProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateBinding.inflate(inflater)
        val factoryUpdate = UpdateProfileViewModelFactory(ThreeTrackerRepository())
        updateProfile= ViewModelProvider(requireActivity(),factoryUpdate)[UpdateProfileViewModel::class.java]

        val factory = ProfileViewModelFactory(ThreeTrackerRepository())
        profileViewModel = ViewModelProvider(requireActivity(),factory)[ProfileViewModel::class.java]

        profileViewModel.user.observe(viewLifecycleOwner, Observer { user ->

            setValues()
        })
        binding.button.setOnClickListener {
            updateValues()

        }
        return binding.root
    }


    fun setValues()
    {
        binding.updateFirstName.setText(profileViewModel.user.value?.first_name)
        binding.updateLastName.setText(profileViewModel.user.value?.last_name)
        binding.updateAddress.setText(profileViewModel.user.value?.location)
        binding.updateNumber.setText(profileViewModel.user.value?.number)
    }
    fun updateValues()
    {
        val first_name  = binding.updateFirstName.text.toString()
        val last_name = binding.updateLastName.text.toString()
        val address = binding.updateAddress.text.toString()
        val number = binding.updateNumber.text.toString()
        val image = profileViewModel.user.value?.image

        Log.d("onclick: ",first_name)
        Log.d("onclick: ",last_name)
        Log.d("onclick: ",address)
        Log.d("onclick: ",number)
        if (image != null) {
            Log.d("onclick: ",image)
        }
       image?.let { updateProfile.update(last_name,first_name,address,number, it) }

        profileViewModel.getUsers()

    }

}