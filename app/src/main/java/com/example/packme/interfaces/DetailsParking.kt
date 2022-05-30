package com.example.packme.interfaces

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.packme.R


class DetailsParking : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_parking, container, false)
    }


//    val parking = intent.getSerializableExtra("parking") as? Parking
//        parking?.image?.let { findViewById<ImageView>(R.id.imageParking).setImageResource(it).toString() }
//        findViewById<TextView>(R.id.nom1).setText(parking?.nom)
//        findViewById<TextView>(R.id.nom2).setText(parking?.nom)
//        findViewById<TextView>(R.id.emplacement).setText(parking?.commune)
//        findViewById<TextView>(R.id.state).setText(parking?.etat)
//        if(parking?.etat == "Fermé"){
//            findViewById<TextView>(R.id.state).setTextColor(Color.RED)
//        }
//        else{
//            findViewById<TextView>(R.id.state).setTextColor(Color.GREEN)
//        }
//        findViewById<TextView>(R.id.pourcentage).setText(parking?.taux.toString()+"%")
//        findViewById<TextView>(R.id.dist).setText(parking?.distance.toString()+" km")
//        findViewById<TextView>(R.id.jour).setText("Dimanche")
//        findViewById<TextView>(R.id.tempsdispo).setText(parking?.tempsOuv.toString()+":00 à "+parking?.tempsFerm.toString()+":00")
//        findViewById<TextView>(R.id.nbh).setText(parking?.unitPrice.toString())
//        findViewById<TextView>(R.id.price).setText(parking?.prix.toString())
//        findViewById<TextView>(R.id.time).setText(parking?.duree.toString()+" min")
}