package com.daniel.kanban.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.daniel.kanban.R
import com.daniel.kanban.databinding.FragmentLoginBinding
import com.daniel.kanban.databinding.FragmentRecoverAccountBinding
import com.daniel.kanban.ui.initToolbar
import com.daniel.kanban.ui.showBottomSheet
import com.google.firebase.auth.FirebaseAuth


class RecoverAccountFragment : Fragment() {
    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)

        auth = FirebaseAuth.getInstance()

        initListener()
    }

    private fun initListener(){
        binding.btnRecover.setOnClickListener{
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.editEmail.text.toString().trim()

        if (email.isNotBlank()) {
            binding.progressBar.isVisible = true
            recoverAccountUser(email)

        } else {
            showBottomSheet(message = getString(R.string.password_empty))
        }
    }

    private fun recoverAccountUser(email: String){
        try {
            auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                binding.progressBar.isVisible = false
                if (task.isSuccessful){
                    Toast.makeText(requireContext(),R.string.text_button_recover_account_fragment,
                        Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(),task.exception?.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
        }catch (erro: Exception){
            Toast.makeText(requireContext(),erro.message.toString(),
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}