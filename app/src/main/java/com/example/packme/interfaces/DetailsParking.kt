package com.example.packme.interfaces

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.icu.text.DecimalFormat
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.packme.R
import com.example.packme.adapter.AdapterParking
import com.example.packme.databinding.FragmentDetailsParkingBinding
import com.example.packme.databinding.FragmentListParkingBinding
import com.example.packme.entity.Parking
import com.example.packme.entity.PositionUser
import com.example.packme.load
import com.example.packme.viewModel.ParkingModel
import java.util.*
import kotlin.math.roundToInt


class DetailsParking : Fragment() {
    private lateinit var binding : FragmentDetailsParkingBinding
    var df = DecimalFormat("0.00")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsParkingBinding.inflate(layoutInflater)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*val vm = ViewModelProvider(this).get(ParkingModel::class.java)
        val pos = PotitionUser(locationUser.longitude.toString(),locationUser.latitude.toString())
        vm.getParkings(null)
        val list = data*/
            var park = requireArguments().getSerializable("parking") as? Parking
            var location = requireArguments().getSerializable("location") as? PositionUser

        //val img = view.findViewById(R.id.imageParking) as ImageView
            //img.load(list[pos].image)
//            list[pos].image.let { requireActivity().findViewById<ImageView>(R.id.imageParking).setImageResource(it).toString() }
            binding.nom1.setText(park?.nom)
            binding.nom2.setText(park?.nom)
            binding.emplacement.setText(park?.commune)
            val tempsOuv = park?.tempsOuv
            val tempsFerm = park?.tempsFerm
            val time = Date()
            if(time.hours> tempsOuv?.hours!! && time.hours<tempsFerm?.hours!!){
                binding.state.setText("Ouvert")
                binding.state.setTextColor(Color.GREEN)

            }
            else{
                binding.state.setText("Fermé")
                binding.state.setTextColor(Color.RED)
            }
            var d = (((park?.placeOcc?.toFloat() !! ) / park?.taille!!).times(100.0)).roundToInt().toString()
            binding.pourcentage.setText(d + "%")
            binding.dist.setText(((park.distance?.times(100.0))?.roundToInt()?.div(100.0)).toString()+ " km")
            binding.jour.setText("Chaque jour")
            binding.tempsdispo.setText(park.tempsOuv.hours.toString()+":"+park.tempsOuv.minutes.toString()+" à "+park.tempsFerm.hours.toString()+":"+park.tempsFerm.minutes.toString())
            //binding.nbh.setText(list[pos].unitPrice.toString())
            binding.price.setText(park.prix.toString()+".00")
            binding.time.setText(park.duree?.div(60)?.roundToInt().toString() + " min")
            binding.showTrack.setOnClickListener {
                val source = location?.lat.toString()+","+location?.lon.toString()
                val destination = park.latitude.toString()+","+park.longitude.toString()
                if(location == null){
                    Toast.makeText(requireContext(), "location is null", Toast.LENGTH_SHORT).show()
                }
                else{
                    displayTrack(source,destination)
                }
            }




    }

    private fun displayTrack(source: String, destination: String) {
            try {
                val uri : Uri = Uri.parse("https://www.google.co.in/maps/dir/"+source+'/'+destination)
                val intent:Intent  = Intent(Intent.ACTION_VIEW,uri)
                intent.setPackage("com.google.android.apps.maps")
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            catch (e:ActivityNotFoundException){
                val uri : Uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps")
                val intent:Intent = Intent(Intent.ACTION_VIEW,uri)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
    }


//
}