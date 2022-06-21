package com.example.packme.interfaces

import android.content.Context
import android.graphics.Color
import android.icu.text.DecimalFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.packme.R
import com.example.packme.adapter.AdapterParking
import com.example.packme.load
import com.example.packme.viewModel.ParkingModel


class DetailsParking : Fragment() {

    var df = DecimalFormat("0.00")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_parking, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vm = ViewModelProvider(this).get(ParkingModel::class.java)
//        val pos = PotitionUser(locationUser.longitude.toString(),locationUser.latitude.toString())
        vm.getParkings()
        vm.dataParking.observe(requireActivity(), Observer {  data ->
            val list = data

            var pos = requireArguments().getInt("position")
//            println(list[1])
            val img = view.findViewById(R.id.imageParking) as ImageView
            img.load(list[pos].image)
//            list[pos].image.let { requireActivity().findViewById<ImageView>(R.id.imageParking).setImageResource(it).toString() }
            requireActivity().findViewById<TextView>(R.id.nom1).setText(list[pos].nom)
            requireActivity().findViewById<TextView>(R.id.nom2).setText(list[pos].nom)
            requireActivity().findViewById<TextView>(R.id.emplacement).setText(list[pos].commune)
            requireActivity().findViewById<TextView>(R.id.state).setText(list[pos].etat)
            if(list[pos].etat == "Fermé"){
                requireActivity().findViewById<TextView>(R.id.state).setTextColor(Color.RED)
            }
            else{
                requireActivity().findViewById<TextView>(R.id.state).setTextColor(Color.GREEN)
            }
            var d = (list[pos].placeOcc.toFloat()/list[pos].taille)
            requireActivity().findViewById<TextView>(R.id.pourcentage).setText(df.format(d).toString() + "%")
//            requireActivity().findViewById<TextView>(R.id.dist).setText(list[pos].distance.toString()+" km")
            requireActivity().findViewById<TextView>(R.id.jour).setText("Dimanche")
            requireActivity().findViewById<TextView>(R.id.tempsdispo).setText(list[pos].tempsOuv.toString()+":00 à "+list[pos].tempsFerm.toString()+":00")
//            requireActivity().findViewById<TextView>(R.id.nbh).setText(list[pos].unitPrice.toString())
            requireActivity().findViewById<TextView>(R.id.price).setText(list[pos].prix.toString())
//            requireActivity().findViewById<TextView>(R.id.time).setText(list[pos].duree.toString()+" min")

        })



    }


//
}