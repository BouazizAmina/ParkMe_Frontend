package com.example.packme.entity

import java.io.Serializable
import java.util.*

class Parking (
    val id : Int,
    val nom:String,
    val commune:String,
    val latitude:Float,
    val longitude: Float,
    val taille: Int,
    val placeOcc: Int,
    val image: String,
    val prix: Int,
    val tempsOuv:Date,
    val tempsFerm: Date,
    var duree:Double?=null,
    var distance:Double?=null
    ): Serializable