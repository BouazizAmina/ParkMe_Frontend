package com.example.packme.interfaces

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
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
import com.example.packme.entity.PotitionUser
import com.example.packme.entity.Utilisateur
import com.example.packme.viewModel.ParkingModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*


class ListParking : Fragment() {
    private val permissionId = 2
    private lateinit var binding: FragmentListParkingBinding
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    var locationUser = Location("")
//    private  lateinit var locationUser: Location
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
//        locationUser = new Location("dummyprovider");
        getLocation()

    }
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
//        pr.visibility = View.VISIBLE

        val recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL,false)
        recyclerView.layoutManager = layoutManager

        val vm = ViewModelProvider(this).get(ParkingModel::class.java)
//        val pos = PotitionUser(locationUser.longitude.toString(),locationUser.latitude.toString())
        vm.getParkings()
        println("test  "+vm.dataParking.toString())
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

    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions()
                    return
                }
                mFusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity()) { location ->
                    //val location: Location? = task.result

                    if (location != null) {
                        val geocoder = Geocoder(requireContext(), Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            locationUser = location
//                            binding.lon.setText(locationUser.longitude.toString())


                    }
                    else{
                        view?.findNavController()?.navigate(R.id.action_listParking_to_login)
                    }
                }
            } else {
                Toast.makeText(requireActivity(), "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireContext().applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    private fun checkPermissions(): Boolean {
        if (
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    private fun requestPermissions() {

        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }
}