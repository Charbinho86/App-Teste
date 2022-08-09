@file:Suppress("UNCHECKED_CAST")

package com.example.appteste.ui.register

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appteste.R
import com.example.appteste.data.AppDatabase
import com.example.appteste.data.dao.AppTesteDao
import com.example.appteste.domain.repository.AppTesteDataSource
import com.example.appteste.domain.repository.AppTesteRepository
import com.example.appteste.extension.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val viewModel: RegisterViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {

                val appTesteDao: AppTesteDao =
                    AppDatabase.getInstance(requireContext()).appTesteDao()

                val repository: AppTesteRepository = AppTesteDataSource(appTesteDao)
                return RegisterViewModel(repository) as T
            }
        }
    }

    private val args: RegisterFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.register?.let { appTesteEntity ->
            btn_adicionar.text = getString(R.string.mb_add_update)
            tiedt_name.setText(appTesteEntity.nome)
            tiedt_email.setText(appTesteEntity.email)
            tiedt_telefone.setText(appTesteEntity.telefone)
        }

        observeEvents()
        setListeners()
    }

    private fun observeEvents() {
        viewModel.appTesteStateEventData.observe(viewLifecycleOwner) { appTesteState ->
            when (appTesteState) {
                is RegisterViewModel.AppTesteState.Inserted -> {
                    clearFields()
                    hideKeyboard()
                    requireView().requestFocus()
                    findNavController().popBackStack()
                }
                is RegisterViewModel.AppTesteState.Updated -> {
                    clearFields()
                    hideKeyboard()
                    findNavController().popBackStack()
                }
            }
        }

        viewModel.messageEventData.observe(viewLifecycleOwner) { stringResId ->
            Snackbar.make(requireView(), stringResId, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun clearFields() {
        tiedt_name.text?.clear()
        tiedt_email.text?.clear()
        tiedt_telefone.text?.clear()
    }

    private fun hideKeyboard() {

        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity) {
            parentActivity.hideKeyboard()
        }
    }


    private fun setListeners() {
        btn_adicionar.setOnClickListener {
            val name = tiedt_name.text.toString()
            val email = tiedt_email.text.toString()
            val telefone = tiedt_telefone.text.toString()

            viewModel.addUpdateAppTeste(name, email, telefone, args.register?.id ?: 0)
        }
    }
}