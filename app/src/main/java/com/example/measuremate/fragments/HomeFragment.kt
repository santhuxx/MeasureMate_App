package com.example.measuremate.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.measuremate.MainActivity
import com.example.measuremate.R
import com.example.measuremate.adapter.MeasureAdapter
import com.example.measuremate.databinding.FragmentHomeBinding
import com.example.measuremate.model.Measure
import com.example.measuremate.viewmodel.MeasureViewModel


class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener, MenuProvider {

    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!

    private lateinit var measureViewModel: MeasureViewModel
    private lateinit var measureAdapter: MeasureAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner,Lifecycle.State.RESUMED)

        measureViewModel=(activity as MainActivity).measureViewModel
        setupHomeRecyclerView()

        binding.addMeasureFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addMeasureFragment)
        }
    }

    private fun updateUI(measure: List<Measure>?){
        if(measure!=null){
            if(measure.isNotEmpty()){
                binding.emptyMeasureImage.visibility = View.GONE
                binding.homeRecyclerView.visibility = View.VISIBLE
            }else{
                binding.emptyMeasureImage.visibility = View.VISIBLE
                binding.homeRecyclerView.visibility = View.GONE
            }
        }
    }

    private fun setupHomeRecyclerView(){
        measureAdapter = MeasureAdapter()
        binding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = measureAdapter
        }

        activity.let {
            measureViewModel.getAllMeasures().observe(viewLifecycleOwner){measure ->
                measureAdapter.differ.submitList(measure)
                updateUI(measure)
            }
        }

    }

    private fun searchMeasure(query: String?){
        val searchQuery = "%$query%"

        measureViewModel.searchMeasure(searchQuery).observe(this){list ->
            measureAdapter.differ.submitList(list)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null){
            searchMeasure(newText)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu,menu)

        val menuSearch = menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }
}