package com.gooddayjobber.ui.MainScreen

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.gooddayjobber.R
import com.gooddayjobber.databinding.FragmentFirstBinding
import com.gooddayjobber.ui.MainScreen.adapter.LendersAdapter
import com.gooddayjobber.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val viewModel by viewModels<FirstFragViewModel>()
    private var textFieldData: String? = null
    private val recyclerColumns = 2
    private val adapter = LendersAdapter(emptyList(), LendersAdapter.OnClickListener { item ->
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

    })

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.hoiData.collect { hoiData ->
                    when (hoiData.status) {
                        201 -> viewModel.getSozoData()
                        else -> Timber.d("hoiResponse: ${hoiData.status} ${hoiData.message}")
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sozoData.collect { lendersList ->
                    if (lendersList.isNotEmpty()) {
                        adapter.updateData(lendersList)
                        binding.lendersRecycler.visibility = View.VISIBLE
                    } else {
                        binding.lendersRecycler.visibility = View.GONE
                    }

                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.hoiErrorFlow.collect { hoiError ->
                    Toast.makeText(context, hoiError, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sozoErrorFlow.collect { hoiError ->
                    Toast.makeText(context, hoiError, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.lendersRecycler.layoutManager = GridLayoutManager(context, recyclerColumns)
        binding.lendersRecycler.addItemDecoration(
            LendersGridItemDecorator(
                spanCount = 2,
                spacing = 100,
                includeEdge = true
            )
        )


        binding.lendersRecycler.adapter = adapter

        binding.seekBarMaxValueText.text = 60000.toString()
        binding.seekBarStartValueText.text = 0.toString()
        binding.seekBarCurrentValueText.text = 0.toString()
        binding.sliderHeader.text = getString(R.string.slider_header)

        binding.buttonAccept.isEnabled = false

        binding.sliderBar.addOnChangeListener { _, value, _ ->
            if (value != 0F) {
                binding.phoneNumberDescriptionText.visibility = View.VISIBLE
                binding.textField.visibility = View.VISIBLE
            } else {
                binding.phoneNumberDescriptionText.visibility = View.GONE
                binding.textField.visibility = View.GONE

                clearTextField()
            }
            binding.seekBarCurrentValueText.text = value.toInt().toString()
        }
        val prefixNumber = binding.textField.prefixText

        binding.textFieldInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Do nothing
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                text?.let { enteredText ->
                    prefixNumber?.let { prefixNumber ->
                        if (prefixNumber.length + enteredText.length > 10) {
                            binding.textField.error = "Please enter only 10 digit number"
                        } else {
                            binding.textField.error = null
                        }

                        binding.buttonAccept.isEnabled =
                            enteredText.length + prefixNumber.length == 10 && binding.textField.error == null
                        textFieldData = prefixNumber[0] + enteredText.toString()
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                //Do nothing
            }

        })



        binding.buttonAccept.setOnClickListener {
            hideKeyboard()

            textFieldData?.let {
                Toast.makeText(context, "Loading data", Toast.LENGTH_SHORT).show()
                viewModel.registerNumber(it)
            }

            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun clearTextField() {
        binding.textFieldInput.setText("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}