package com.example.homework12.ui.main

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.homework12.databinding.FragmentBlankBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

private const val TAG = "BlankFragment"

class BlankFragment : Fragment() {

    private var _binding: FragmentBlankBinding? = null
    private val binding get() = _binding!!
    private lateinit var filterButton: ImageButton
    private lateinit var searchButton: ImageButton
    private lateinit var progress: ProgressBar
    private lateinit var search: TextInputEditText
    private lateinit var inputLayoutSearch: TextInputLayout
    private val viewModel: BlankViewModel by viewModels{MainViewModelFactory()}

    companion object {
        fun newInstance() = BlankFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlankBinding.inflate(inflater, container, false)
        filterButton = binding.filterButton
        searchButton = binding.searchButton
        progress = binding.progress
        search = binding.search
        inputLayoutSearch = binding.InputLayoutSearch
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchButton.isEnabled = false

        search.addTextChangedListener {

            searchFieldValidation(search.text)
        }
        searchButton.setOnClickListener {
            val searchString = search.text.toString()
            viewModel.onClickSearch(searchString)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateSearch.collect { state ->
                    when (state) {
                        StateSearch.Loading -> {
                            progress.isVisible = true
                            filterButton.isEnabled = false
                            searchButton.isEnabled = false
                        }

                        StateSearch.Success -> {
                            progress.isVisible = false
                            filterButton.isEnabled = true
                        }

                        is StateSearch.Error -> {
                            progress.isVisible = false

                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collect { message ->
                    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun searchFieldValidation(text: Editable?) {
        val length = text?.length

        if (length != null && length > 3) {
            searchButton.isEnabled = true
        } else {
            searchButton.isEnabled = false
            Log.d("MaimViewModel", "request length must be more than 3 characters")
        }
    }
}