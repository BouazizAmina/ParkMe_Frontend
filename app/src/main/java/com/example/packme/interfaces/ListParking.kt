package com.example.packme.interfaces

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.packme.R
import com.example.packme.adapter.AdapterParking
import com.example.packme.databinding.FragmentListParkingBinding
import com.example.packme.databinding.FragmentLoginBinding
import com.example.packme.viewModel.UtilisateurModel


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

        var pr = binding.progressBar4
        pr.visibility = View.VISIBLE

        val recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL,false)
        recyclerView.layoutManager = layoutManager

        val vm = ViewModelProvider(this).get(UtilisateurModel::class.java)
        vm.getParkings()
        vm.loadingParking.observe(requireActivity(), Observer {  loading->
            if(loading) {
                pr.visibility = View.VISIBLE
                Toast.makeText(requireActivity(), vm.errorMessage.toString(), Toast.LENGTH_LONG).show()
            }
            else {
                pr.visibility = View.GONE
                vm.dataParking.observe(requireActivity(), Observer {  data ->
                    recyclerView.adapter = AdapterParking({ position -> onClickDevice(position)},requireActivity(),data)
                })
            }
        })
    }

    private fun onClickDevice(position: Int) {
        var bundle = bundleOf("position" to position)
//        var bundle = Bundle()
//        bundle.putInt("position",position)
        view?.findNavController()?.navigate(R.id.action_listParking_to_detailsParking,bundle)
    }


}