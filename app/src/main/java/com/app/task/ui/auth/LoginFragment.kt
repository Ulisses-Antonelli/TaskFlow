package com.app.task.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.app.task.R
import com.app.task.databinding.FragmentLoginBinding
import com.app.task.helper.FirebaseHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks() {
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnRecover.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recoverAccountFragment)
        }

        binding.btnLogin.setOnClickListener {
            validateData()
        }
    }

    private fun validateData(){
        val email = binding.edtEmail.text.toString().trim()
        val senha = binding.edtSenha.text.toString().trim()

        if(email.isNotEmpty()) {
            if(senha.isNotEmpty()) {
                binding.progressBar.isVisible = true
                loginUser(email, senha)

            } else {
                Toast.makeText(requireContext(), "O campo deve ser preenchido - informe sua senha.", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(requireContext(), "O campo deve ser preenchido - informe seu e-mail", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginUser(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    binding.progressBar.isVisible = false
                    val errorCode = task.exception?.let { FirebaseHelper.validError(it) } ?: 5
                    FirebaseHelper.handleLoginError(requireContext(), errorCode)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}