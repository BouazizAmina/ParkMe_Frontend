package com.example.packme.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
//import com.example.packme.`object`.ParkingList
import com.example.packme.entity.Parking
import com.example.packme.entity.PotitionUser
import com.example.packme.entity.Utilisateur
import com.example.packme.retrofit.Endpoint
import kotlinx.coroutines.*

class ParkingModel : ViewModel() {

    var data = MutableLiveData<String>()
//    var dataParking = MutableLiveData<ParkingList>()
    var dataParking = MutableLiveData<List<Parking>>()
    var loading = MutableLiveData<Boolean>()
    var loadingParking = MutableLiveData<Boolean>()

    val errorMessage = MutableLiveData<String>()
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
//        onError(throwable.localizedMessage)
        println("err  "+throwable.message.toString())
    }




//    fun getParkings(pos: PotitionUser){
//        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
//            val response = Endpoint.createInstance().getAllParkings(pos)
//            withContext(Dispatchers.Main) {
//                if (response.isSuccessful ) {
//                    loadingParking.value = false
//                    dataParking.postValue(response.body())
//                } else {
//                    onError(response.message())
//                }
//            }
//        }
//    }

    fun getParkings(){
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = Endpoint.createInstance().getAllParkings()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful ) {
                    loadingParking.value = false
                    dataParking.postValue(response.body())
                } else {
                    onError(response.message())
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }


}