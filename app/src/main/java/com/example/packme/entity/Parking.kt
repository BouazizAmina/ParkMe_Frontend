package com.example.packme.entity

import java.io.Serializable

class Parking (
    val id : Int,
    val nom:String,
    val image: Int,
    val etat:String,
    val commune:String,
    val latitude:Float,
    val longitude: Float,
    val taille: Int,
    val placeOcc: Int,
    val prix: Int): Serializable