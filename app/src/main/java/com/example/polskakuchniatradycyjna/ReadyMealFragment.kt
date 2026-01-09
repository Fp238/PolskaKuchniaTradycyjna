package com.example.polskakuchniatradycyjna

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.polskakuchniatradycyjna.databinding.FragmentReadyMealBinding
import com.example.polskakuchniatradycyjna.model.PersonOrder
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
        super.onViewCreated(view, savedInstanceState)


        val radioGroups = listOf(
            binding.zupaRadioGroup,
            binding.drugieDanieRadioGroup,
            binding.napojRadioGroup
        )

        radioGroups.forEach { group ->
            group.setOnCheckedChangeListener { _, _ -> updateCurrentOrder() }
        }

        binding.readyMealButton.setOnClickListener {
            val current = createCurrentOrder()
            orderViewModel.addOrder(current)
            findNavController().navigate(R.id.action_readyMealFragment_to_summaryFragment)
        }
    }

    private fun updateCurrentOrder() {
        orderViewModel.updateCurrentOrder(createCurrentOrder())
    }

    private fun createCurrentOrder(): PersonOrder {
        val zupa = when (binding.zupaRadioGroup.checkedRadioButtonId) {
            R.id.rosol -> "Rosół"
            R.id.pomidorowa -> "Pomidorowa"
            else -> "Brak"
        }

        val drugieDanie = when (binding.drugieDanieRadioGroup.checkedRadioButtonId) {
            R.id.schabowy -> "Schabowy"
            R.id.pierogi -> "Pieczony kurczak"
            else -> "Brak"
        }

        val napoj = when (binding.napojRadioGroup.checkedRadioButtonId) {
            R.id.kompot -> "Kompot"
            R.id.sok -> "Sok"
            else -> "Brak"
        }

        return PersonOrder(
            zupa = zupa,
            dodatkiDoZupy = emptyList(),
            drugieDanie = drugieDanie,
            dodatkiDoDrugiegoDania = emptyList(),
            napoj = napoj
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}