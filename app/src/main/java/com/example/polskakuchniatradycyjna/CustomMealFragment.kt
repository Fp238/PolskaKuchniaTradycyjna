package com.example.polskakuchniatradycyjna

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.polskakuchniatradycyjna.databinding.FragmentCustomMealBinding
import com.example.polskakuchniatradycyjna.databinding.FragmentReadyMealBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CustomMealFragment : Fragment() {
    private var _binding: FragmentCustomMealBinding?=null
    private val binding get()=_binding!!


    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentCustomMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val zupy = listOf("Brak", "Rosół", "Pomidorowa")
        val drugieDania = listOf("Brak", "Schabowy", "Pieczony kurczak")
        val napoje = listOf("Brak", "Woda", "Kompot")

        val zupaAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            zupy
        )
        zupaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.zupaSpinner.adapter = zupaAdapter

        val drugieDanieAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            drugieDania
        )
        drugieDanieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.drugieDanieSpinner.adapter = drugieDanieAdapter

        val napojAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            napoje
        )
        napojAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.napojSpinner.adapter = napojAdapter



        binding.customMealButton.setOnClickListener{

            val wybranaZupa = binding.zupaSpinner.selectedItem.toString()
            val wybraneDrugie = binding.drugieDanieSpinner.selectedItem.toString()
            val wybranyNapoj = binding.napojSpinner.selectedItem.toString()

            val dodatekDoZupy = when (binding.zupaRadioGroup.checkedRadioButtonId) {
                R.id.makaron -> "Makaron"
                R.id.ryz -> "Ryż"
                else -> "Brak"
            }

            val dodatkiDoDrugiegoDania = mutableListOf<String>()
            if (binding.surowka.isChecked) dodatkiDoDrugiegoDania.add("Surówka")
            if (binding.ziemniaki.isChecked) dodatkiDoDrugiegoDania.add("Ziemniaki")

            val typNapoju = when (binding.napojRadioGroup.checkedRadioButtonId) {
                R.id.cieply -> "Ciepły"
                R.id.zlodem -> "Z lodem"
                else -> "Brak"
            }



            findNavController().navigate(R.id.action_customMealFragment_to_summaryFragment)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CustomMealFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}