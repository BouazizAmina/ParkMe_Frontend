package com.example.packme.adapter

import android.content.Context
import android.graphics.Color
import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.packme.R
import com.example.packme.entity.Parking
import com.example.packme.load
import java.util.*
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.hours
import kotlin.time.hours

class AdapterParking (private val onItemClicked: (position: Int) -> Unit,val context: Context,var data:List<Parking>):RecyclerView.Adapter<AdapterParking.MyViewHolder>()
{
    var df = DecimalFormat("0.00")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.parking_layout, parent, false),onItemClicked)

    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.apply {
            nom.text = data[position].nom
            var d = ((data[position].placeOcc.toFloat()/data[position].taille).times(100.0)).roundToInt().toString()
            taux.text = d+ "%"
//            println(data[position].placeOcc)
            image.load(data[position].image)
            commune.text = data[position].commune
//            etat.text =  data[position].etat etat se calcule

                val tempsOuv = data[position].tempsOuv
                val tempsFerm = data[position].tempsFerm
                val time =Date()
                if(time.hours>tempsOuv.hours && time.hours<tempsFerm.hours){
                    etat.text = "Ouvert"
                    etat.setTextColor(Color.GREEN)

                }
                else{
                    etat.text = "Fermé"
                    etat.setTextColor(Color.RED)
                }

            distance.text = ((data[position].distance?.times(100.0))?.roundToInt()?.div(100.0)).toString()+ " km"
            duree.text = (((data[position].duree?.div(60))?.times(100.0))?.roundToInt()?.div(100.0)).toString() + " min"

        }
    }

    class MyViewHolder(
        view: View,
        private val onItemClicked: (position: Int) -> Unit) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val image = view.findViewById(R.id.image) as ImageView
        val taux = view.findViewById (R.id.taux) as TextView
        val nom = view.findViewById (R.id.nom) as TextView
        val etat = view.findViewById (R.id.etat) as TextView
        val commune = view.findViewById (R.id.commune) as TextView
        val distance = view.findViewById (R.id.distance) as TextView
        val duree = view.findViewById (R.id.duree) as TextView
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View) {
            val position = adapterPosition
            onItemClicked(position)
        }
    }

}