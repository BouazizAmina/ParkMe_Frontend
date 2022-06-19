package com.example.packme.interfaces

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.packme.R
import com.example.packme.databinding.FragmentLoginBinding
import com.example.packme.databinding.FragmentSignUpBinding
import com.example.packme.entity.Utilisateur
import com.example.packme.viewModel.UtilisateurModel


class SignUp : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    var toast: Toast? = null
    lateinit var sharedPreference:SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreference =requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        var pr = binding.progressBar2
        pr.visibility = View.GONE
        var signup = binding.signups

        signup.setOnClickListener {
            var nom = binding.nom.text.toString()
            var prenom = binding.prenom.text.toString()
            var tel = binding.tel.text.toString()
            var email = binding.emails.text.toString()
            var password = binding.passwords.text.toString()
            val user = Utilisateur(nom = nom, prenom = prenom, numTel = tel, email = email, motPasse = password)

            val vm = ViewModelProvider(this).get(UtilisateurModel::class.java)
            vm.inscription(user)
            vm.loading.observe(requireActivity(), Observer {  loading->
                toast?.cancel()
                if(loading) {
                    pr.visibility = View.VISIBLE
                    Toast.makeText(requireActivity(), vm.errorMessage.toString(), Toast.LENGTH_LONG).show()
                }
                else {
                    vm.credentialsValidity.observe(requireActivity(), Observer {  data ->
                        if(data){
                            toast?.cancel()
                            val editor: SharedPreferences.Editor = sharedPreference.edit()
                            editor.putBoolean("connected",true)
                            editor.putString("email",email)
                            editor.commit()
                            view.findNavController().navigate(R.id.action_signUp_to_listParking)
                        }else{
                            toast = Toast.makeText(requireActivity(), "Email ou Numéro de téléphone déjà utilisé !", Toast.LENGTH_LONG)
                            toast?.show()
                        }
                    })
                }


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