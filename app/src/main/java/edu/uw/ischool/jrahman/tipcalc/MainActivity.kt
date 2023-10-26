package edu.uw.ischool.jrahman.tipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.Button
import android.text.TextWatcher
import android.widget.Toast
import android.widget.ArrayAdapter
import android.widget.Spinner

class MainActivity : AppCompatActivity() {

    var serviceChargeEditText: EditText? = null
    var tipButton: Button? = null
    private var tipPercentageSpinner: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        serviceChargeEditText = findViewById(R.id.serviceChargeEditText)
        tipButton = findViewById(R.id.tipButton)
        tipPercentageSpinner = findViewById(R.id.tipPercentageSpinner)

        val tipPercentages = arrayOf("10%", "15%", "18%", "20%")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tipPercentages)
        tipPercentageSpinner?.adapter = adapter


        serviceChargeEditText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val str = s.toString()
                if (!str.startsWith("$")) {
                    serviceChargeEditText?.setText("$")
                    serviceChargeEditText?.setSelection(1)
                } else if (str.count { it == '.' } > 1) {
                    serviceChargeEditText?.setText(str.substring(0, str.length - 1))
                    serviceChargeEditText?.setSelection(serviceChargeEditText?.text?.length ?: 0)
                } else if (str.contains('.') && str.substringAfter('.').length > 2) {
                    serviceChargeEditText?.setText(str.substring(0, str.length - 1))
                    serviceChargeEditText?.setSelection(serviceChargeEditText?.text?.length ?: 0)
                }
                tipButton?.isEnabled = str.isNotEmpty() && str != "$"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        tipButton?.setOnClickListener {
            val amount = serviceChargeEditText?.text.toString().removePrefix("$").toDoubleOrNull()
            if (amount != null) {
                val tip = amount * 0.15
                Toast.makeText(this, String.format("$%.2f", tip), Toast.LENGTH_LONG).show()
            }
        }

        tipButton?.setOnClickListener {
            val amount = serviceChargeEditText?.text.toString().removePrefix("$").toDoubleOrNull()
            val selectedPercentage = when (tipPercentageSpinner?.selectedItem?.toString()) {
                "10%" -> 0.10
                "15%" -> 0.15
                "18%" -> 0.18
                "20%" -> 0.20
                else -> 0.15
            }

            if (amount != null) {
                val tip = amount * selectedPercentage
                Toast.makeText(this, String.format("$%.2f", tip), Toast.LENGTH_LONG).show()
            }
        }
    }
}







