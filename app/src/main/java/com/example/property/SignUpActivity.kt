package com.example.property

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.property.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var binding:ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set view binding
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_sign_up)

        binding.btnSignUp.setOnClickListener{
            val email = binding.etEmail.text.toString()
            val password =binding.etPassword.text.toString()
            if(checkAllField()){
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    //if successful account is created
                    if(it.isSuccessful){
                        auth.signOut()
                        Toast.makeText(this,"Account created successfully!",Toast.LENGTH_LONG).show()
                    }
                    else {
                        //account not created
                        Log.e("error:",it.exception.toString())
                    }
                }
            }

        }

    }

    private fun checkAllField(): Boolean {
        val email = binding.etEmail.text.toString()
        if(binding.etEmail.text.toString() == ""){
            binding.textInputLayoutEmail.error= "This is  required field "
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.textInputLayoutEmail.error = "Email format"
        }
        //Password should be at least 6 characters
        if(binding.etPassword.text.toString()==""){
            binding.textInputLayoutPassword.error ="This is required field"
            return false
        }
        if(binding.etPassword.length()<=8){
            binding.textInputLayoutPassword.error = "Password should at least have 8 characters"
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }
        if(binding.etConfirmPassword.text.toString()==""){
            binding.textInputLayoutConfirmPassword.error = "This is required field"
            binding.textInputLayoutConfirmPassword.errorIconDrawable= null
            return false
        }
        if (binding.etPassword.text.toString()!=binding.etConfirmPassword.text.toString()){
            binding.textInputLayoutPassword.error="Password do not match"
            return false
        }
        return true
    }
}