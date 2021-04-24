package br.com.mmdevelopment.tiptime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.mmdevelopment.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalculate.setOnClickListener { calculateTip() }
    }

    /**
     * Calculates the tip according to user selected options
     */
    private fun calculateTip() {
        // If cost of value EditText is null ou 0.0, set the tip TextView to 0.0 and return
        val cost = binding.etCostOfService.text.toString().toDoubleOrNull()
        if (cost == null) {
            displayTip(0.0)
            return
        }

        // Get the tip percentage according to radio button option
        val tipPercentage = when (binding.rgTipOptions.checkedRadioButtonId) {
            R.id.rb_option_twenty_percent -> 0.20
            R.id.rb_option_eighteen_percent -> 0.18
            R.id.rb_option_fifteen_percent -> 0.15
            else -> 0.1
        }

        // Calculate the tip and round up if switch is selected
        var tip = tipPercentage * cost
        if (binding.switchRoundUp.isChecked) {
            tip = kotlin.math.ceil(tip)
        }

        // Displays the tip amount
        displayTip(tip)
    }

    /**
     * Displays formatted tip on the TextView according to user currency
     * @param tip Tip amount
     */
    private fun displayTip(tip : Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tvTipResult.text = getString(R.string.tip_amount, formattedTip)
    }

}