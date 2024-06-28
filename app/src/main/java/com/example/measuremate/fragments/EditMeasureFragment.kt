package com.example.measuremate.fragments

import android.app.AlertDialog
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
import androidx.navigation.fragment.navArgs
import com.example.measuremate.MainActivity
import com.example.measuremate.R
import com.example.measuremate.databinding.FragmentEditMeasureBinding
import com.example.measuremate.model.Measure
import com.example.measuremate.viewmodel.MeasureViewModel


class EditMeasureFragment : Fragment(R.layout.fragment_edit_measure), MenuProvider {

    private var editMeasureBinding: FragmentEditMeasureBinding? = null
    private val binding get() = editMeasureBinding!!

    private lateinit var measureViewModel: MeasureViewModel
    private lateinit var currentMeasure: Measure

    private val args: com.example.measuremate.fragments.EditMeasureFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        editMeasureBinding = FragmentEditMeasureBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)

        measureViewModel=(activity as MainActivity).measureViewModel
        currentMeasure= args.measure!!

        binding.editCusName.setText(currentMeasure.cusName)
        binding.editMobile.setText(currentMeasure.cusMobile)
        binding.editCloth.setText(currentMeasure.cloth)
        binding.editOrderedDate.setText(currentMeasure.orderedDate)
        binding.editDeliverDate.setText(currentMeasure.deliveryDate)
        binding.editMeasurements.setText(currentMeasure.measurements)


        binding.deleteMeasureFab.setOnClickListener {
            val cusName = binding.editCusName.text.toString().trim()
            val cusMobile = binding.editMobile.text.toString().trim()
            val cloth = binding.editCloth.text.toString().trim()
            val orderedDate = binding.editOrderedDate.text.toString().trim()
            val deliveryDate = binding.editDeliverDate.text.toString().trim()
            val measurements = binding.editMeasurements.text.toString().trim()


            if (cusName.isNotEmpty()){
                val measure = Measure(currentMeasure.id, cusName,cusMobile,cloth,orderedDate,deliveryDate,measurements)
                measureViewModel.updateMeasure(measure)
                view.findNavController().popBackStack(R.id.homeFragment,false)

            }else{
                Toast.makeText(context,"Please enter user name", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun deleteMeasure(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Measure")
            setMessage("Do you want to delete this measurement?")
            setPositiveButton("Delete"){_,_ ->
                measureViewModel.deleteMeasure(currentMeasure)
                Toast.makeText(context,"Measure Deleted", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment, false)

            }
            setNegativeButton("Cancel",null)

        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_measure,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId){
            R.id.deleteMenu -> {
                deleteMeasure()
                true
            } else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editMeasureBinding= null
    }


}