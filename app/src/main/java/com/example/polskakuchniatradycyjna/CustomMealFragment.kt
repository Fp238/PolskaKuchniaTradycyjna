package com.example.polskakuchniatradycyjna

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.polskakuchniatradycyjna.databinding.FragmentCustomMealBinding
import com.example.polskakuchniatradycyjna.databinding.FragmentReadyMealBinding
import com.example.polskakuchniatradycyjna.model.PersonOrder
import com.example.polskakuchniatradycyjna.viewmodel.OrderViewModel


class CustomMealFragment : Fragment() {

    private var _binding: FragmentCustomMealBinding? = null
    private val binding get() = _binding!!
    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val zupy = listOf("Brak", "Rosół", "Pomidorowa")
        val drugieDania = listOf("Brak", "Schabowy", "Pieczony kurczak")
        val napoje = listOf("Brak", "Woda", "Kompot")

        binding.zupaSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, zupy)
        binding.drugieDanieSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, drugieDania)
        binding.napojSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, napoje)

        binding.customMealButton.setOnClickListener {
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

            val personOrder = PersonOrder(
                zupa = wybranaZupa,
                dodatkiDoZupy = if (dodatekDoZupy != "Brak") listOf(dodatekDoZupy) else emptyList(),
                drugieDanie = wybraneDrugie,
                dodatkiDoDrugiegoDania = dodatkiDoDrugiegoDania,
                napoj = wybranyNapoj,
                typNapoju = typNapoju
            )

            orderViewModel.addOrder(personOrder)

            findNavController().navigate(R.id.action_customMealFragment_to_summaryFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}