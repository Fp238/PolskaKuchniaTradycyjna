package com.example.polskakuchniatradycyjna

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.polskakuchniatradycyjna.databinding.FragmentMenuChoiceBinding
import com.example.polskakuchniatradycyjna.databinding.FragmentReadyMealBinding
import com.example.polskakuchniatradycyjna.viewmodel.OrderViewModel


class ReadyMealFragment : Fragment() {

    private var _binding: FragmentReadyMealBinding? = null
    private val binding get() = _binding!!

    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReadyMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.readyMealButton.setOnClickListener {

            val zupa = when (binding.zupaRadioGroup.checkedRadioButtonId) {
                R.id.rosol -> "Rosół"
                R.id.pomidorowa -> "Pomidorowa"
                else -> "Brak"
            }

            val drugie = when (binding.drugieDanieRadioGroup.checkedRadioButtonId) {
                R.id.schabowy -> "Schabowy"
                else -> "Pieczony kurczak"
            }

            val napoj = when (binding.napojRadioGroup.checkedRadioButtonId) {
                R.id.kompot -> "Kompot"
                else -> "Sok"
            }

            orderViewModel.setOrder(
                zupa = zupa,
                zupaDodatek = null,
                drugie = drugie,
                dodatki = emptyList(),
                napoj = napoj,
                napojTyp = null
            )

            findNavController().navigate(
                R.id.action_readyMealFragment_to_summaryFragment
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}