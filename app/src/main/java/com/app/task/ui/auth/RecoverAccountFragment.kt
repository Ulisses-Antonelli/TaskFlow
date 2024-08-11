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
import com.app.task.databinding.FragmentRecoverAccountBinding
import com.app.task.databinding.FragmentRegisterBinding
import com.app.task.helper.FirebaseHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecoverAccountFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private var _binding : FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecoverAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun recoverAccountUser(email: String) {
        Firebase.auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Enviamos um link para seu E-mail", Toast.LENGTH_SHORT).show()
                } else {
                    binding.progressBar.isVisible = false
                    val errorCode = task.exception?.let { FirebaseHelper.validError(it) } ?: 5
                    FirebaseHelper.handleLoginError(requireContext(), errorCode)
                }
            }
    }

    private fun validateData(){
        val email = binding.edtEmail.text.toString().trim()

        if(email.isNotEmpty()) {
            recoverAccountUser(email)
            binding.progressBar.isVisible = true
        } else {
            Toast.makeText(requireContext(), "O campo deve ser preenchido - informe seu e-mail", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initClicks() {
        binding.btnSend.setOnClickListener { validateData() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initClicks()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}