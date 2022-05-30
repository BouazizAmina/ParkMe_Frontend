package com.example.packme.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Utilisateur (
    val id : Int? = null,
    val nom:String? = null,
    val prenom: String? = null,
    val numTel:String? = null,
//    @SerializedName("identifier")
    val email:String,
    val motPasse:String): Serializable