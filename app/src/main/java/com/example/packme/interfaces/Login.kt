package com.example.packme.interfaces

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.packme.R
import com.example.packme.databinding.FragmentLoginBinding
import com.example.packme.entity.Utilisateur
import com.example.packme.retrofit.Endpoint
import com.example.packme.viewModel.UtilisateurModel
import kotlinx.coroutines.*


class Login : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var pr = binding.progressBar
        pr.visibility = View.GONE

        var login = binding.loginButton

        login.setOnClickListener {

            var email = binding.email.text.toString()
            var password = binding.password.text.toString()
            val user = Utilisateur(email = email, motPasse = password)
            val vm = ViewModelProvider(this).get(UtilisateurModel::class.java)
            binding.progressBar.visibility = View.VISIBLE

            vm.connexion(user)

            vm.loading.observe(requireActivity(), Observer {  loading->
                if(loading) {
                    pr = binding.progressBar
                    pr.visibility = View.GONE
                    if (vm.credentialsValidity.equals(true)){
                        view.findNavController().navigate(R.id.action_login_to_listParking2)
                    }else{
                        Toast.makeText(requireActivity(), vm.responseMessage.toString(), Toast.LENGTH_LONG).show()
                    }
                }
                else {
                    val pr = binding.progressBar
                    pr.visibility = View.VISIBLE
                }


            })
            // Error message observer
            vm.errorMessage.observe(requireActivity(), Observer {  message ->
                Toast.makeText(requireContext(),"Une erreur s'est produite",Toast.LENGTH_SHORT).show()
                //Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()

            })


//            if ( ) {
//                val sharedPreference =  getSharedPreferences("pref", Context.MODE_PRIVATE)
//                sharedPreference.edit {
//                    putBoolean("connected",true)
//                    putString("email",emailValue)
//                }
//
//                val intent = Intent(this@MainActivity, ListeParking::class.java)
//                startActivity(intent)
//                finish()
//
//            } else {
//                Toast.makeText(this@MainActivity, response.message(), Toast.LENGTH_LONG).show()
//            }

        }
    }
}