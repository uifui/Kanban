package com.daniel.kanban.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniel.kanban.data.model.Status
import com.daniel.kanban.databinding.FragmentDoneBinding
import com.daniel.kanban.data.model.Task
import com.daniel.kanban.ui.adapter.TaskAdapter


class DoneFragment : Fragment() {

    private lateinit var taskAdapter: TaskAdapter

    private var _binding: FragmentDoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoneBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super. onViewCreated(view, savedInstanceState)

        initRecyclerViewTask()
        getTask()
    }

    private fun initRecyclerViewTask() {
        val taskAdapter = TaskAdapter(requireContext(),){ task, option -> optionSelected(task,option)}
        binding.recyclerviewTask.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerviewTask.setHasFixedSize(true)
        binding.recyclerviewTask.adapter = taskAdapter
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
                Toast.makeText(requireContext(), "Movendo para Feito: ${task.description}", Toast.LENGTH_SHORT).show()
            }
            TaskAdapter.SELECT_BACK -> {
                Toast.makeText(requireContext(), "Movendo para A Fazer: ${task.description}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getTask() {
        val taskList = listOf(
            Task(
                id = "20",
                description = "Finalizar a documentação do projeto",
                status = Status.DONE
            ),
            Task(
                id = "21",
                description = "Implementar o sistema de notificações",
                status = Status.DONE
            ),
            Task(
                id = "22",
                description = "Publicar a versão inicial na Play Store",
                status = Status.DONE
            ),
            Task(
                id = "23",
                description = "Criar a logo oficial do aplicativo",
                status = Status.DONE
            ),
        )
        taskAdapter.submitList(taskList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}