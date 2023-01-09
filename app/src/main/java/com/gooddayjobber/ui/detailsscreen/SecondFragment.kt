package com.gooddayjobber.ui.detailsscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.gooddayjobber.databinding.FragmentSecondBinding
import com.squareup.picasso.Picasso


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val args by navArgs<SecondFragmentArgs>()
    private val sozoItem by lazy {
        args.landerObj
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lenderName.text = sozoItem.name ?: ""
        Picasso.get().load(sozoItem.image).into(binding.lenderImage)
        binding.lenderSlogan.text = sozoItem.slogan ?: ""
        binding.lenedersZeroPercent.text = "Zero percent: ${sozoItem.zeroPercent.toString()}"
        binding.lenderMinY.text = "Min years: ${sozoItem.minYears}"
        binding.lenderMaxY.text = "Max years: ${sozoItem.maxYears}"
        binding.lenderFirstLoan.text = "First Loan: ${sozoItem.firstLoan}"
        binding.lenderMaxAmount.text = "Max amount: ${sozoItem.maxAmount}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}