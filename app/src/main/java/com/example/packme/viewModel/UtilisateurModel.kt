package com.example.packme.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.packme.entity.Utilisateur
import com.example.packme.retrofit.Endpoint
import kotlinx.coroutines.*

class UtilisateurModel : ViewModel() {
    // Les données à partager entre les fragments
    var data = MutableLiveData<String>()
    var loading = MutableLiveData<Boolean>()
    var credentialsValidity = MutableLiveData<Boolean>()
//    var credentialsValidity:Boolean = false

    var responseMessage = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
//        onError(throwable.localizedMessage)
        println("err  "+throwable.message.toString())
    }


    fun connexion(user: Utilisateur ){
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = Endpoint.createInstance().seConnecter(user)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful ) {
                    loading.value = false
                    credentialsValidity.value = response.body() != "-1"
//                    credentialsValidity= response.body() != "-1"
                    data.postValue(response.body())
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