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

        val zupy = listOf("Brak", "Rosół", "Pomidorowa")
        val drugieDania = listOf("Brak", "Schabowy", "Pieczony kurczak")
        val napoje = listOf("Brak", "Woda", "Kompot")

        binding.zupaSpinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, zupy)

        binding.drugieDanieSpinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, drugieDania)

        binding.napojSpinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, napoje)

        binding.customMealButton.setOnClickListener {

            val zupa = binding.zupaSpinner.selectedItem.toString()
            val drugie = binding.drugieDanieSpinner.selectedItem.toString()
            val napoj = binding.napojSpinner.selectedItem.toString()

            val zupaDodatek = when (binding.zupaRadioGroup.checkedRadioButtonId) {
                R.id.makaron -> "Makaron"
                R.id.ryz -> "Ryż"
                else -> null
            }

            val dodatki = mutableListOf<String>()
            if (binding.surowka.isChecked) dodatki.add("Surówka")
            if (binding.ziemniaki.isChecked) dodatki.add("Ziemniaki")

            val napojTyp = when (binding.napojRadioGroup.checkedRadioButtonId) {
                R.id.cieply -> "Ciepły"
                R.id.zlodem -> "Z lodem"
                else -> null
            }

            orderViewModel.setOrder(
                zupa,
                zupaDodatek,
                drugie,
                dodatki,
                napoj,
                napojTyp
            )

            findNavController().navigate(
                R.id.action_customMealFragment_to_summaryFragment
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}