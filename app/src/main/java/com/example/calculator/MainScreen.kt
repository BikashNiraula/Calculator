package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainScreenBinding

class MainScreen : AppCompatActivity() {
    private lateinit var binding: ActivityMainScreenBinding
    private var canAddOperators = false
    private var canAddDecimal = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)




        binding.btnEqualsTo?.setOnClickListener {
            onEqualsAction()

        }
        //For numbers
        binding.apply {
            btn0?.setOnClickListener {view->
                numberAction(view)
            }

            btn1?.setOnClickListener {view->
                numberAction(view)
            }
            btn2?.setOnClickListener {view->
                numberAction(view)
            }
            btn3?.setOnClickListener {view->
                numberAction(view)
            }
            btn4?.setOnClickListener {view->
                numberAction(view)
            }
            btn5?.setOnClickListener {view->
                numberAction(view)
            }
            btn6?.setOnClickListener {view->
                numberAction(view)
            }
            btn7?.setOnClickListener {view->
                numberAction(view)
            }
            btn8?.setOnClickListener {view->
                numberAction(view)
            }
            btn9?.setOnClickListener {view->
                numberAction(view)
            }
            btnDot?.setOnClickListener{view->
                numberAction(view)
            }

        }
        //For operators
        binding.apply {
            btnMinus?.setOnClickListener {view->
                operatorsAction(view)
            }
            btnDivide?.setOnClickListener {view->
                operatorsAction(view)
            }
            btnPlus?.setOnClickListener {view->
                operatorsAction(view)
            }
            btnMultiply?.setOnClickListener {view->
                operatorsAction(view)
            }
        }
        binding.btnAC?.setOnClickListener {
            onClearAction()
        }
        binding.btnBack?.setOnClickListener {
            onBackSpaceAction()
        }

    }
    private fun numberAction(view: View){
        if(view is Button){
            if(view.text == "."){
                if(canAddDecimal){
                    binding.inputTxt?.append(view.text)
                    canAddDecimal = false
                }

            } else{
                binding.inputTxt?.append(view.text)
                canAddDecimal = true
            }
            canAddOperators  = true

        }

    }
    private fun operatorsAction(view: View){
        if(view is Button && canAddOperators){
            binding.inputTxt?.append(view.text)
            canAddOperators = false
            canAddDecimal = true
        }
    }
    private fun onClearAction(){
        binding.inputTxt?.text?.clear()
        binding.txtAnswer?.text?.subSequence(0,0)
    }
    private fun onBackSpaceAction(){
        val size  = binding.inputTxt?.text?.length ?:0
        if(size>0){
        binding.inputTxt?.text?.subSequence(0,size - 1)
        }
    }
    private fun onEqualsAction(){
        binding.txtAnswer?.text = calculateResults()
    }

    private fun calculateResults(): String {
        val digitOperators = digitOperators()
        if(digitOperators.isEmpty()) return ""
        val multiplydivision = multiplyDivision(digitOperators)
        if(multiplydivision.isEmpty()) return ""
        val result = addOrSubtract(multiplydivision)
        return result.toString()
    }

    private fun addOrSubtract(passedList: MutableList<Any>): Float {
        var result = passedList[0] as Float
        for(i in passedList.indices){
            if(passedList[i] is Char && i!=passedList.lastIndex){
                val op = passedList[i]
                val nextNum = passedList[i+1].toString().toFloat()
                if(op == '+'){
                    result +=nextNum
                }
                if(op == '-'){
                    result -=nextNum
                }
            }

        }
        return result
    }

    private fun multiplyDivision(passedList: MutableList<Any>): MutableList<Any> {
        var list = passedList
        while(list.contains('/')||list.contains('*')){
            list  = calcTimesDivision(list)
        }
        return list
    }

    private fun calcTimesDivision(passedList: MutableList<Any>): MutableList<Any> {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size
        for(i in passedList.indices){
            if(passedList[i] is Char && i != passedList.lastIndex && i < restartIndex){
                val ch = passedList[i]
                val num1 = passedList[i-1].toString().toFloat()
                val num2 = passedList[i+1].toString().toFloat()
                when(ch){
                    '*'->{
                        newList.add(num1*num2)
                        restartIndex = i+1
                    }
                    '/'->{
                        newList.add(num1/num2)
                        restartIndex = i+1
                    }
                    else->{
                        newList.add(num1)
                        newList.add(ch)
                    }
                }

            }
            if(i>restartIndex){
                newList.add(passedList[i])
            }
        }

            return newList
    }

    private fun digitOperators():MutableList<Any>{
        val lst = mutableListOf<Any>()
        var currentDigit = ""
        for(ch in binding.inputTxt?.text.toString()){
            if(ch.isDigit() || ch == '.'){
                currentDigit +=ch
            } else{
                lst.add(currentDigit.toFloat())
                currentDigit = ""
                lst.add(ch)
            }
            if(currentDigit!=""){
                lst.add(currentDigit)
            }

        }

        return lst
    }


}









