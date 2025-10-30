package com.daniel.kanban.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.daniel.kanban.R
import com.daniel.kanban.data.model.Status
import com.daniel.kanban.data.model.Task
import com.daniel.kanban.databinding.FragmentFormTaskBinding
import com.daniel.kanban.databinding.FragmentRecoverAccountBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database


class FormTaskFragment : Fragment() {

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var task: Task

    private var newTask: Boolean = true

    private var status: Status = Status.TODO

    private lateinit var reference: DatabaseReference

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)

        reference = Firebase.database.reference
        auth = Firebase.auth

        initListener()
    }

    private fun initListener() {
        binding.buttonSave.setOnClickListener {
            validateData()
        }

        binding.radioGroup.setOnCheckedChangeListener { _, id-> status =
        when(id){
            R.id.rbTodo -> Status.TODO
            R.id.rbDoing -> Status.DOING
            else -> Status.DONE
        }}
    }

    private fun validateData() {
        val description = binding.edittextDescricao.text.toString().trim()

        if (description.isNotBlank()) {
            binding.progressBar.isVisible = true

            if (newTask){
                val id = reference.database.reference.push().key?: ""
                val description = description
                val status = status
                task = Task(id,description,status)

                saveTask()
            }

            else {
                showBottomSheet(message = getString(R.string.password_empty))
            }
        }
    }

    private fun saveTask(){
        reference
            .child("task")
            .child(auth.currentUser?.uid?: "")
            .child(task.id)
            .setValue(task).addOnCompleteListener { result ->
                if(result.isSuccessful){
                    Toast.makeText(
                        requireContext(),
                        R.string.text_save_sucess_form_task_fragment,
                        Toast.LENGTH_SHORT
                    ).show()

                    if(newTask){
                        findNavController().popBackStack()
                    }else{
                        binding.progressBar.isVisible = false
                    }
                }
                else{
                    binding.progressBar.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        R.string.error_generic,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}