package com.daniel.kanban.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniel.kanban.R
import com.daniel.kanban.data.model.Status
import com.daniel.kanban.databinding.FragmentTodoBinding
import com.daniel.kanban.data.model.Task
import com.daniel.kanban.ui.adapter.TaskAdapter


class TodoFragment : Fragment() {

    private lateinit var taskAdapter: TaskAdapter

    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super. onViewCreated(view, savedInstanceState)
        initListeners()

        initRecyclerViewTask()
        getTask()
    }

    private fun initListeners() {
        binding.floatingActionButton2.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_formTaskFragment)
        }
    }

    private fun initRecyclerViewTask() {
        val taskAdapter = TaskAdapter(requireContext()){ task, option -> optionSelected(task,option)}
        with(binding.recyclerviewTask){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter
        }

    }

    private fun optionSelected(task: Task, option: Int) {
        when (option) {
            TaskAdapter.SELECT_REMOVER -> {
                Toast.makeText(requireContext(), "Removendo ${task.description}", Toast.LENGTH_SHORT).show()
            }
            TaskAdapter.SELECT_EDIT -> {
                Toast.makeText(requireContext(), "Editando ${task.description}", Toast.LENGTH_SHORT).show()
            }
            TaskAdapter.SELECT_DETAILS -> {
                Toast.makeText(requireContext(), "Detalhes ${task.description}", Toast.LENGTH_SHORT).show()
            }
            TaskAdapter.SELECT_NEXT -> {
                Toast.makeText(requireContext(), "Próximo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getTask() {
        val taskList = listOf(
            Task(id = "5", description = "Escrever testes de unidade para a API de login", status = Status.TODO),
            Task(id = "6", description = "Desenhar a interface do usuário para a tela de configurações", status = Status.TODO),
            Task(id = "7", description = "Configurar o banco de dados de desenvolvimento", status = Status.TODO),
            Task(id = "8", description = "Refatorar o código do serviço de autenticação", status = Status.TODO),
            Task(id = "9", description = "Criar o manual do usuário para o app", status = Status.TODO),
        )
        taskAdapter.submitList(taskList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}