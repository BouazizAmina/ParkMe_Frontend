package com.example.packme.interfaces

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationManager.NETWORK_PROVIDER
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.packme.R
import com.example.packme.adapter.AdapterParking
import com.example.packme.databinding.FragmentListParkingBinding
import com.example.packme.entity.Parking
import com.example.packme.entity.PositionUser
import com.example.packme.viewModel.ParkingModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class ListParking : Fragment() {
    private lateinit var binding: FragmentListParkingBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationUser: Location
    private lateinit var pos: PositionUser


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



//        pr.visibility = View.VISIBLE

        val recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL,false)
        recyclerView.layoutManager = layoutManager


        getCurrentLocation()
        //showParkingsList()

    }
    private fun showParkingsList(){
        val pr = binding.progressBar4
        Toast.makeText(requireActivity(), "before", Toast.LENGTH_LONG).show()
        val vm = ViewModelProvider(this).get(ParkingModel::class.java)
        pos = PositionUser(lon = locationUser.longitude,lat= locationUser.latitude)
        Toast.makeText(requireActivity(), "after l pos", Toast.LENGTH_LONG).show()
        vm.getParkings(pos)
        Toast.makeText(requireActivity(), "after", Toast.LENGTH_LONG).show()

        vm.loadingParking.observe(requireActivity(), Observer {  loading->
            if(loading) {
                Toast.makeText(requireActivity(), "loading", Toast.LENGTH_LONG).show()
                pr.visibility = View.VISIBLE
                Toast.makeText(requireActivity(), vm.errorMessage.toString(), Toast.LENGTH_LONG).show()
            }
            else {
                pr.visibility = View.GONE
                vm.dataParking.observe(requireActivity(), Observer {  data ->
                    val recyclerView = binding.recyclerView
                    val layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL,false)
                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = AdapterParking({ position -> onClickDevice(vm.dataParking.value!![position])},requireActivity(),data)
                })
            }
        })
    }

//    private fun onClickDevice(position: Int) {
//        //Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show()
//        val intent = Intent(this,ParkingDetails::class.java)
//        intent.apply { putExtra("parking"
//            , loadData()[position])
//        }
//        startActivity(intent)
//    }

    private fun onClickDevice(parking: Parking) {
        var bundle = bundleOf("parking" to parking, "location" to pos)
        view?.findNavController()?.navigate(R.id.action_listParking_to_detailsParking,bundle)
    }

    private fun getCurrentLocation(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if(checkPermission()){
            if(isLocationEnabled()){
                fusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) {task->
                    var location:Location? = task.result
                    if(location != null){
                        //Log.d("Debug:" ,"Your Location:"+ location.longitude)
                        //Toast.makeText(requireContext(),"You Current Location is : Long: "+ location.longitude + " , Lat: " + location.latitude + "\n",Toast.LENGTH_SHORT).show()
                        locationUser = location
                        showParkingsList()
                    }
                    else{
                        Toast.makeText(requireContext(),"null location",Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(requireContext(),"Please Turn on Your device Location",Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)

            }
        }else{
            requestPermission()
        }
    }

    companion object{
        private const val PERMISSION_REQUEST_ACCESS_LOCATION=100
    }
    private fun checkPermission():Boolean {
        if (
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    private fun requestPermission(){
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }
    private fun isLocationEnabled():Boolean{
        var locationManager= requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||  locationManager.isProviderEnabled(NETWORK_PROVIDER)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //super.onRequestPermissionsResult(requestCode,permissions,grantResults)
        if(requestCode == PERMISSION_REQUEST_ACCESS_LOCATION){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                Log.d("Debug", "YOU HAVE THE PERMISSION")
            }
            else
                Toast.makeText(requireActivity(),"permission denied ",Toast.LENGTH_SHORT).show()
        }
    }

}