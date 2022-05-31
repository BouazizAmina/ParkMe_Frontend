package com.example.packme.interfaces

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import com.example.packme.R
import com.example.packme.databinding.FragmentLoginBinding
import com.example.packme.databinding.FragmentReservationBinding
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Reservation.newInstance] factory method to
 * create an instance of this fragment.
 */
class Reservation : Fragment() {
    private lateinit var binding: FragmentReservationBinding
    private var heureA=0
    private var minA=0
    private var heureD=0
    private var minD=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentReservationBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.floatingActionButton.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, heureA, minA ->
                cal.set(Calendar.HOUR_OF_DAY, heureA)
                cal.set(Calendar.MINUTE, minA)
                binding.timeA.setText(String.format("%d : %d",heureA,minA))
            }
            TimePickerDialog(requireActivity(), timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

        }
        binding.floatingActionButton2.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, heureD)
                cal.set(Calendar.MINUTE, minD)
                binding.timeA.setText(String.format("%d : %d",heureD,minD))
            }
            TimePickerDialog(requireActivity(), timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
    }



}