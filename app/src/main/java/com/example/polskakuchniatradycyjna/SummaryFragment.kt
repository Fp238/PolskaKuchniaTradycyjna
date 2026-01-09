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
import com.example.polskakuchniatradycyjna.databinding.FragmentSummaryBinding
import com.example.polskakuchniatradycyjna.model.PersonOrder
import com.example.polskakuchniatradycyjna.viewmodel.OrderViewModel


class SummaryFragment : Fragment() {

    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!

    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        fun updateList(orders: List<PersonOrder>) {
            val items = orders.map {
                buildString {
                    append("Zupa: ${it.zupa}")
                    if (it.zupaDodatek != null) append(" (${it.zupaDodatek})")
                    append("\nDrugie: ${it.drugieDanie}")
                    if (it.dodatki.isNotEmpty()) append(" + ${it.dodatki.joinToString()}")
                    append("\nNapój: ${it.napoj}")
                    if (it.napojTyp != null) append(" (${it.napojTyp})")
                    append("\nCena: ${it.cena} zł")
                }
            }

            binding.summaryListView.adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        }


        orderViewModel.orders.observe(viewLifecycleOwner) { savedOrders ->
            val current = orderViewModel.currentOrder.value
            val combined = if (current != null) {
                savedOrders + current
            } else {
                savedOrders
            }
            updateList(combined)
        }


        orderViewModel.currentOrder.observe(viewLifecycleOwner) { current ->
            val saved = orderViewModel.orders.value ?: emptyList()
            val combined = if (current != null) saved + current else saved
            updateList(combined)
        }


        orderViewModel.totalSum.observe(viewLifecycleOwner) {
            binding.sumSummaryTextView.text = "Suma: $it zł"
        }


        binding.nextOrderSummaryButton.setOnClickListener {
            orderViewModel.confirmCurrentOrder()
            findNavController().navigate(
                R.id.action_summaryFragment_to_menuChoiceFragment
            )
        }


        binding.finishSummaryButton.setOnClickListener {
            orderViewModel.confirmCurrentOrder()
            orderViewModel.clearAllOrders()
            findNavController().navigate(
                R.id.action_summaryFragment_to_startFragment
            )
        }
    }

    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}