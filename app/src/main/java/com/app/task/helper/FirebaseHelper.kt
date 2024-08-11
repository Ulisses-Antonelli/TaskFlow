package com.app.task.helper

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class FirebaseHelper {

    companion object {
        fun getDataBase() = FirebaseDatabase.getInstance().reference
        fun getAuth() = FirebaseAuth.getInstance()
        fun getIdUser() = FirebaseAuth.getInstance().uid
        fun isAutenticated() = FirebaseAuth.getInstance().currentUser != null

        fun validError(error: Exception) : Int {
            return when (error) {
                is FirebaseAuthWeakPasswordException -> 1       // *Senha fraca
                is FirebaseAuthInvalidCredentialsException -> 2 // Credenciais inválidas (email mal formatado)
                is FirebaseAuthUserCollisionException -> 3      // Email já em uso
                is FirebaseAuthInvalidUserException -> 4        // Usuário não encontrado
                else -> 5
            }
        }

        fun handleLoginError(context: Context, errorCode: Int) {
            val errorMessage = when (errorCode) {
                1 -> "Erro: Senha fraca."
                2 -> "Erro: Credenciais inválidas."
                3 -> "Erro: Email já em uso."
                4 -> "Erro: Usuário não encontrado."
                5 -> "Erro desconhecido."
                else -> "Erro desconhecido."
            }
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}