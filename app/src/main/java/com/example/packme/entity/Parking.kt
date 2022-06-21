package com.example.packme.entity

import java.io.Serializable

class Parking (
    val id : Int,
    val nom:String,
    val commune:String,
    val latitude:Float,
    val longitude: Float,
    val etat:String,
    val taille: Int,
    val placeOcc: Int,
    val image: String,
    val prix: Int,
    val tempsOuv:String,
    val tempsFerm: String): Serializable