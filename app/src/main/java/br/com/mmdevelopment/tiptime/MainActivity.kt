package br.com.mmdevelopment.tiptime

import android.os.Bundle
import android.view.inputmethod.EditorInfo
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
        // If cost of value EditText is null ou 0.0, set the results TextView's to 0.0 and return
        val cost = binding.etCostOfService.text.toString().toDoubleOrNull()
        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            displayTotal(0.0)
            displayPerPerson(0.0)
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

        // Displays the amounts to the TextView
        displayTip(tip)
        displayTotal(tip)
        displayPerPerson(tip)

        // Dismiss keyboard on Calculate button click
        binding.etCostOfService.onEditorAction(EditorInfo.IME_ACTION_DONE)
        binding.etNumberPeople.onEditorAction(EditorInfo.IME_ACTION_DONE)
    }

    /**
     * Displays formatted tip on the TextView according to user currency
     * @param tip Tip amount
     */
    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tvTipResult.text = getString(R.string.tip_amount, formattedTip)
    }

    /**
     * Displays the grand total when cost has a value
     * @param tip Tip amount
     * @return total amount
     */
    private fun displayTotal(tip: Double): Double {
        var total = 0.0
        if (tip != 0.0) {
            total = tip + binding.etCostOfService.text.toString().toDouble()
            val formattedTotal = NumberFormat.getCurrencyInstance().format(total)
            binding.tvTotal.text = getString(R.string.grand_total, formattedTotal)
        }
        return total
    }

    /**
     * Displays amount divided per person
     * @param tip Tip amount
     */
    private fun displayPerPerson(tip: Double) {
        val perPerson = displayTotal(tip) / binding.etNumberPeople.text.toString().toDouble()
        val formattedPerPerson = NumberFormat.getCurrencyInstance().format(perPerson)
        binding.tvPerPerson.text = getString(R.string.per_person, formattedPerPerson.toString())
    }

}
