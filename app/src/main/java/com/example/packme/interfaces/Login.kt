package com.example.packme.interfaces

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.packme.R
import com.example.packme.databinding.FragmentLoginBinding
import com.example.packme.entity.Utilisateur
import com.example.packme.viewModel.UtilisateurModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class Login : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    var toast: Toast? = null
    lateinit var sharedPreference:SharedPreferences
    private lateinit var gso: GoogleSignInOptions
    private val RC_SIGN_IN = 123
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }*/

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
        sharedPreference =requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        var pr = binding.progressBar
        pr.visibility = View.GONE
        var login = binding.loginButton
        var signup = binding.signup
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
        // Set the dimensions of the sign-in button.
        val signInButton: SignInButton = binding.signInButton
        signInButton.setSize(SignInButton.SIZE_WIDE)
        signInButton.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        login.setOnClickListener {
            var email = binding.email.text.toString()
            var password = binding.password.text.toString()
            val user = Utilisateur(email = email, motPasse = password)

            val vm = ViewModelProvider(this).get(UtilisateurModel::class.java)
            vm.connexion(user)
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
                            view.findNavController().navigate(R.id.action_login_to_listParking2)
                        }else{
                            toast = Toast.makeText(requireActivity(), "Email ou Mot de pass erroné !", Toast.LENGTH_LONG)
                            toast?.show()
                        }
                    })
                }


            })

        }

        signup.setOnClickListener {
            view.findNavController().navigate(R.id.action_login_to_signUp)
        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            val acct = GoogleSignIn.getLastSignedInAccount(requireActivity())
            if (acct != null) {
                val personEmail = acct.email
                /*val personName = acct.displayName
                val personGivenName = acct.givenName
                val personFamilyName = acct.familyName
                val personId = acct.id
                val personPhoto: Uri? = acct.photoUrl*/
                val editor: SharedPreferences.Editor = sharedPreference.edit()
                editor.putBoolean("connected",true)
                editor.putBoolean("connected",true)
                editor.putString("email",personEmail)
                editor.commit()
                getView()?.findNavController()?.navigate(R.id.action_login_to_listParking2)
            }

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            toast = Toast.makeText(requireActivity(), "Connexion echouée !", Toast.LENGTH_LONG)
            toast?.show()
        }
    }
}