package com.nurtaz.shadowfoxcalculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.google.android.material.button.MaterialButton
import com.nurtaz.shadowfoxcalculatorapp.databinding.ActivityMainBinding
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    var firstNumber =""
    var secondNumber =""
    var currentNumber =""
    var currentOperator =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialize()
        binding.ivBackspace.setOnClickListener {
            if (currentNumber.isNotEmpty()){
                currentNumber = currentNumber.substring(0,currentNumber.length-1)
                updateDislay()
            }
        }
    }
    private fun initialize(){
        var buttonValues = arrayOf(
            "7","8","9","/",
            "4","5","6","*",
            "1","2","3","-",
            "0","C","=","+"
        )
        var buttonIds = arrayOf(
            R.id.btnSeven,R.id.btnEight,R.id.btnNine,R.id.btnDivide,
            R.id.btnFour,R.id.btnFive,R.id.btnSix,R.id.btnMultilply,
            R.id.btnOne,R.id.btnTwo,R.id.btnThree,R.id.btnMinus,
            R.id.btnZero,R.id.btnClear,R.id.btnEqual,R.id.btnPlus
        )
        buttonIds.forEachIndexed { index, buttonId ->
            findViewById<MaterialButton>(buttonId).setOnClickListener {
                handleButtonClick(buttonValues[index])
            }
        }
    }
    private fun handleButtonClick(value:String){
        when(value){
            in "0".."9" -> appendDigit(value)

            "+" -> {
                appendOperator("+")
                currentOperator = "+"
            }
            "-" -> {
                appendOperator("-")
                currentOperator = "-"
            }
            "*" -> {
                appendOperator("*")
                currentOperator = "*"
            }
            "/" -> {
                appendOperator("/")
                currentOperator = "/"
            }
            "%" -> {
                appendOperator("%")
                currentOperator = "%"
            }
            "C" -> clearInput()
            "=" -> checkResult()
        }
    }
    private fun clearInput(){
        firstNumber = ""
        secondNumber = ""
        currentNumber = ""
        binding.tvResult.text = ""
        binding.tvDisplay.text = ""
        updateDislay()
    }
    private fun checkResult(){
        if (firstNumber.isNotEmpty() && currentNumber.isNotEmpty()){
            secondNumber = currentNumber
            var result = performCalculation(currentOperator)
            binding.tvResult.text = " = " + result.toString()
            firstNumber = ""
            secondNumber = ""
            currentNumber = ""
        }
    }
    private  fun performCalculation(operator:String):Double{
        return when(operator){
            "+" -> firstNumber.toDouble() + secondNumber.toDouble()
            "*" -> firstNumber.toDouble() * secondNumber.toDouble()
            "/" -> firstNumber.toDouble() / secondNumber.toDouble()
            "-" -> firstNumber.toDouble() - secondNumber.toDouble()
            "%" -> firstNumber.toDouble() % secondNumber.toDouble()
            else->{
                throw IllegalArgumentException("Invalid Operator")
            }
        }

    }
    private fun appendOperator(opperatore:String){
        if (currentNumber.isNotEmpty()){
            firstNumber = currentNumber
            currentNumber = ""
        }
        binding.tvDisplay.append(opperatore)
       // updateDislay()
    }


    private fun appendDigit(digit:String){
        currentNumber += digit
        updateDislay()
    }

    fun updateDislay(){
      //  binding.tvDisplay.text =  currentNumber
        binding.tvDisplay.append(currentNumber)
    }
}