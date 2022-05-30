package com.example.packme.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.packme.entity.Parking
import com.example.packme.R

class AdapterParking (private val onItemClicked: (position: Int) -> Unit,val context: Context,var data:List<Parking>):RecyclerView.Adapter<AdapterParking.MyViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.parking_layout, parent, false),onItemClicked)

    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.apply {
            nom.text = data[position].nom
            etat.text =  data[position].etat
            if(etat.text == "Fermé"){
                etat.setTextColor(Color.RED)
            }
            else{
                etat.setTextColor(Color.GREEN)
            }
//            taux.text = data[position].taux.toString() + "%"
//            image.setImageResource(data[position].image).toString()
//            image.load(data[position].image)
//            commune.text = data[position].commune
//            distance.text = data[position].distance.toString() + " km"
//            duree.text = data[position].duree.toString() + " min"
        }
    }

    class MyViewHolder(
        view: View,
        private val onItemClicked: (position: Int) -> Unit) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val image = view.findViewById(R.id.image) as ImageView
        val etat = view.findViewById (R.id.etat) as TextView
        val taux = view.findViewById (R.id.taux) as TextView
        val nom = view.findViewById (R.id.nom) as TextView
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