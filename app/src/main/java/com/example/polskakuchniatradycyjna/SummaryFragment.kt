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
        super.onViewCreated(view, savedInstanceState)

        orderViewModel.currentOrders.observe(viewLifecycleOwner) { orders ->
            val orderStrings = orders.mapIndexed { index, order ->
                "${index + 1}. ${order.zupa} ${order.dodatkiDoZupy.joinToString()} | " +
                        "${order.drugieDanie} ${order.dodatkiDoDrugiegoDania.joinToString()} | " +
                        "${order.napoj} (${order.typNapoju}) = ${"%.2f".format(order.getTotalPrice())} zł"
            }

            binding.summaryListView.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                orderStrings
            )
        }

        orderViewModel.totalSum.observe(viewLifecycleOwner) { sum ->
            binding.sumSummaryTextView.text = "Suma: ${"%.2f".format(sum)} zł"
        }

        binding.finishSummaryButton.setOnClickListener {
            orderViewModel.clearOrders()
            findNavController().navigate(R.id.action_summaryFragment_to_startFragment)
        }

        binding.nextOrderSummaryButton.setOnClickListener {
            findNavController().navigate(R.id.action_summaryFragment_to_menuChoiceFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}