package com.example.packme.interfaces

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.packme.R
import com.example.packme.databinding.FragmentListParkingBinding
import com.example.packme.databinding.FragmentLoginBinding


class ListParking : Fragment() {

    private lateinit var binding: FragmentListParkingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListParkingBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreference =  requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)

        val bool = sharedPreference.getBoolean("connected",false)
        if(!bool){
            view.findNavController().navigate(R.id.action_listParking_to_login)

        }else{
            val value = sharedPreference.getString("email","value")
            Toast.makeText(requireActivity(), "your email is: "+value , Toast.LENGTH_LONG).show()

        }
    }


}