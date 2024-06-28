package com.example.measuremate.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.measuremate.MainActivity
import com.example.measuremate.R
import com.example.measuremate.databinding.FragmentAddMeasureBinding
import com.example.measuremate.model.Measure
import com.example.measuremate.viewmodel.MeasureViewModel


class AddMeasureFragment : Fragment(R.layout.fragment_add_measure), MenuProvider {

    private var addMeasureBinding: FragmentAddMeasureBinding? = null
    private val binding get() = addMeasureBinding!!

    private lateinit var measureViewModel: MeasureViewModel
    private lateinit var addMeasureView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addMeasureBinding = FragmentAddMeasureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)

        measureViewModel=(activity as MainActivity).measureViewModel
        addMeasureView= view
    }

    private fun saveMeasures(view: View){
        val cusName = binding.addCusName.text.toString().trim()
        val cusMobile = binding.addMobile.text.toString().trim()
        val cusCloth = binding.addCloth.text.toString().trim()
        val orderedDate = binding.addOrderedDate.text.toString().trim()
        val deliveryDate = binding.addDeliverDate.text.toString().trim()
        val measurement = binding.addMeasurements.text.toString().trim()

        if (cusName.isNotEmpty()){
            val measure = Measure(0,cusName,cusMobile,cusCloth,orderedDate,deliveryDate,measurement)
           measureViewModel.addMeasure(measure)

            Toast.makeText(addMeasureView.context,"Measure Saved", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment,false)

        }else{
            Toast.makeText(addMeasureView.context,"Please enter customer name", Toast.LENGTH_SHORT).show()

        }

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_measure, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.saveMenu -> {
                saveMeasures(addMeasureView)
                true
            }

            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addMeasureBinding = null
    }
}