@file:Suppress("UNCHECKED_CAST")

package com.example.appteste.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appteste.R
import com.example.appteste.data.AppDatabase
import com.example.appteste.domain.repository.AppTesteDataSource
import com.example.appteste.domain.repository.AppTesteRepository
import com.example.appteste.extension.navigateWithAnimations
import com.example.appteste.ui.register.RegisterFragmentArgs
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment(R.layout.fragment_list) {

    private val navigationArgs: RegisterFragmentArgs by navArgs()

    private val viewModel: ListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {

                val appTesteDao = AppDatabase.getInstance(requireContext()).appTesteDao()
                val repository: AppTesteRepository = AppTesteDataSource(appTesteDao)
                return ListViewModel(repository) as T
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModelEvents()
        clickListeners()
    }

    private fun observeViewModelEvents() {
        viewModel.allAppTesteEvents.observe(viewLifecycleOwner) { allAppTesteEvents ->
            val appTesteListAdapter = AppTesteListAdapter(allAppTesteEvents).apply {
                onItemClick = {
                    val directions = ListFragmentDirections
                        .actionListFragmentToRegisterFragment()
                    findNavController().navigateWithAnimations(directions)
                }
            }

            with(rv_fragment_list) {
                setHasFixedSize(true)
                adapter = appTesteListAdapter
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRegisters()
    }

    private fun clickListeners() {
        fab_fragment_list.setOnClickListener {
            findNavController().navigateWithAnimations(
                R.id.action_listFragment_to_registerFragment)
        }
    }
}
