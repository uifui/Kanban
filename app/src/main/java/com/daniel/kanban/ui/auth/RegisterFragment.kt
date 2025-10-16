package com.daniel.kanban.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.daniel.kanban.R
import com.daniel.kanban.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!



    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()


    }
    private fun validateData(){
        val email = binding.editEmail.text.toString().trim()
        val senha = binding.editSenha.text.toString().trim()

        if (email.isNotBlank() and senha.isNotBlank()){
            binding.progressBar.isVisible = true
            registerUser(email, senha)
            Toast.makeText(
                requireContext(),
                "entrou1",
                Toast.LENGTH_SHORT
            ).show()
        }
        else{
            //mostrar mensagem de erro

        }
    }

    private fun initListener(){
        binding.btnRegister.setOnClickListener {
            validateData()
        }
    }

    private fun registerUser(email: String, password: String){

        try {

            val auth = FirebaseAuth.getInstance()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //mensagem de sucesso
                        findNavController().navigate(R.id.action_global_homeFragment)
                    } else {
                        //mensagem de erro
                        Toast.makeText(
                            requireContext(),
                            task.exception?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
        catch (e: Exception){
            Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}