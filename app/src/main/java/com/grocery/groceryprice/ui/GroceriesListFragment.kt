package com.grocery.groceryprice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grocery.groceryprice.R
import com.grocery.groceryprice.databinding.FragmentGroceriesListBinding
import com.grocery.groceryprice.ui.adapter.GroceryAdapter
import com.grocery.groceryprice.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroceriesListFragment : Fragment() {

    private var _binding: FragmentGroceriesListBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroceriesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        binding.rvGroceries.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = GroceryAdapter()
        }

        viewModel.groceriesLiveData.observe(viewLifecycleOwner) {
            (binding.rvGroceries.adapter as GroceryAdapter).submitList(it)
        }

        binding.rvGroceries.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // if the recycler view is scrolled down shrink the FAB
                if (dy > 10 && binding.fabFilter.isExtended) {
                    binding.fabFilter.shrink()
                }

                // if the recycler view is scrolled up extend the FAB
                if (dy < -10 && !binding.fabFilter.isExtended) {
                    binding.fabFilter.extend()
                }

                // of the recycler view is at the first item always extend the FAB
                if (!recyclerView.canScrollVertically(-1)) {
                    binding.fabFilter.extend()
                }
            }
        })

        binding.fabFilter.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_ListFragment_to_FilterFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}