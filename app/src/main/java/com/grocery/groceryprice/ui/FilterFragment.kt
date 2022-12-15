package com.grocery.groceryprice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.grocery.groceryprice.databinding.FragmentFilterBinding
import com.grocery.groceryprice.ui.adapter.StringAutoCompleteAdapter
import com.grocery.groceryprice.utils.Constants
import com.grocery.groceryprice.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        val districts = ArrayList<String>()
        setSelectedData()

        val districtAdapter = StringAutoCompleteAdapter(districts)
        val marketAdapter = StringAutoCompleteAdapter(ArrayList())
        viewModel.districtsLiveData.observe(viewLifecycleOwner) {
            districts.clear()
            districts.addAll(it)
        }
        viewModel.marketsLiveData.observe(viewLifecycleOwner) {
            marketAdapter.setList(it)
        }

        binding.actvDistrict.apply {
            threshold = 1
            setAdapter(districtAdapter)
            setOnItemClickListener { parent, _, position, _ ->
                viewModel.selectedDistrict = parent?.getItemAtPosition(position) as String
                viewModel.getMarketsOfDistrict()
                this.clearFocus()
                viewModel.selectedMarket = ""
                binding.actvMarket.also {
                    it.setText("")
                    it.requestFocus()
                }
            }
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    viewModel.selectedDistrict = ""
                    this@apply.setText("")
                }
            }
        }

        binding.actvMarket.apply {
            threshold = 1
            setAdapter(marketAdapter)
            setOnItemClickListener { parent, _, position, _ ->
                viewModel.selectedMarket = parent?.getItemAtPosition(position) as String
                this.clearFocus()
            }
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    viewModel.selectedMarket = ""
                    this@apply.setText("")
                }
            }
        }

        binding.rgSortBy.setOnCheckedChangeListener { _, checkedId: Int ->
            viewModel.updateOrderBy(checkedId)
        }
        binding.bClear.setOnClickListener {
            viewModel.clearFilter()
            Navigation.findNavController(binding.root).popBackStack()
        }

        binding.bFilter.setOnClickListener {
            if (viewModel.selectedDistrict.isNotEmpty() || viewModel.selectedOrderBy.isNotEmpty()) {
                viewModel.filterRecords()
            } else {
                Snackbar.make(binding.root, "Please choose valid filters.", Snackbar.LENGTH_LONG)
                    .show()
            }
            Navigation.findNavController(binding.root).popBackStack()
        }
    }

    /**Set previous selected data*/
    private fun setSelectedData() {
        binding.actvDistrict.setText(viewModel.selectedDistrict)
        binding.actvMarket.setText(viewModel.selectedMarket)
        when (viewModel.selectedOrderBy) {
            Constants.TAG_PRICE -> {
                if (viewModel.selectedSortBy == 1) binding.rbPriceAsc.isChecked = true
                else binding.rbPriceDesc.isChecked = true
            }
            Constants.TAG_DATE -> {
                if (viewModel.selectedSortBy == 1) binding.rbDateAsc.isChecked = true
                else binding.rbDateDesc.isChecked = true
            }
            else -> binding.rbNone.isChecked = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}