package com.example.packme.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.packme.entity.Utilisateur
import com.example.packme.retrofit.Endpoint
import kotlinx.coroutines.*

class UtilisateurModel : ViewModel() {
    // Les données à partager entre les fragments
    var loading = MutableLiveData<Boolean>(false)
    var credentialsValidity = MutableLiveData<Boolean>()
    var responseMessage = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
//        onError(throwable.localizedMessage)
        println("err  "+throwable.message.toString())
    }

    private fun onError(message: String) {
        errorMessage.value = message
    }

    fun connexion(user: Utilisateur ){
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
//              val  data = HashMap<String,String>()
//            data.put("identifier",user.email)
//            data.put("motPasse")
                val response = Endpoint.createInstance().seConnecter(user)
                withContext(Dispatchers.Main) {
                    loading.value = true
                    if (response.isSuccessful ) {
                        credentialsValidity.value = true
//                        responseMessage.postValue((response.message()))
                    } else {
                        credentialsValidity.value = false
                       responseMessage.postValue((response.toString()))
                    }
                }
        }

    }


}