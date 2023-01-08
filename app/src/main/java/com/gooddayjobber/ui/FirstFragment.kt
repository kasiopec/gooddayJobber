package com.gooddayjobber.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gooddayjobber.R
import com.gooddayjobber.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val viewModel by viewModels<FirstFragViewModel>()
    private var textFieldData: String? = null

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

            textFieldData?.let {
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