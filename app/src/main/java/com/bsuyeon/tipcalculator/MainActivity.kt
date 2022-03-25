package com.bsuyeon.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15

class MainActivity : AppCompatActivity() {
    private lateinit var baseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tipPercentLabel: TextView
    private lateinit var tipAmount: TextView
    private lateinit var totalAmount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        baseAmount = findViewById(R.id.baseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        tipPercentLabel = findViewById(R.id.tipPercentLabel)
        tipAmount = findViewById(R.id.tipAmount)
        totalAmount = findViewById(R.id.totalAmount)

        seekBarTip.progress = INITIAL_TIP_PERCENT
        tipPercentLabel.text ="$INITIAL_TIP_PERCENT%"
        seekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tipPercentLabel.text = "$progress%"
                computeTipAndTotal()
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        baseAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(text: Editable?) {
                Log.i(TAG, "afterTextChanged $text")
                computeTipAndTotal()
            }
        })
    }

    private fun computeTipAndTotal() {
        if (baseAmount.text.isEmpty()) {
            tipAmount.text = ""
            totalAmount.text = ""
            return
        }

        val baseAmt = baseAmount.text.toString().toDouble()
        val tipPercent = seekBarTip.progress
        val tipAmt = baseAmt * tipPercent / 100
        val totalAmt = baseAmt + tipAmt

        tipAmount.text = "%.2f".format(tipAmt)
        totalAmount.text = "%.2f".format(totalAmt)
    }
}