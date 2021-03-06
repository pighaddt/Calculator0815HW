package com.angus.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_caculator.*
import kotlinx.android.synthetic.main.single_button.view.*
import java.util.ArrayList

class CaculatorActivity : AppCompatActivity(){

    private var isOperation: Boolean = false
    private var operation: String = ""
    private var outputNumber1: Int = 0
    private var outputNumber2: Int = 0
    private lateinit var buttons: MutableList<CalculatorButton>
    private lateinit var numberIndex: ArrayList<Int>
    private lateinit var calculatorContent: ArrayList<String>
    var msg = StringBuilder()

    companion object{
        val TAG = "CaculatorActivity.kt"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caculator)
         calculatorContent =
            arrayListOf<String>(
                "AC", "Clear", "%", "/",
                "1", "2", "3", "*",
                "4", "5", "6", "-",
                "7", "8", "9", "+",
                "0", "00", ".", "=")
        numberIndex = arrayListOf<Int>(4, 5, 6, 8, 9, 10, 12, 13, 14, 16, 17, 18)
        //setting content
        buttons = mutableListOf<CalculatorButton>()
        for (i in 0..19){
            if (numberIndex.contains(i)){
                val button = OperandButton(this)
                button.content = calculatorContent.get(i)
                button.position = i
                buttons.add(button)
                Log.d(TAG, "isNumber: ${button.isNumber}")
            }else{
                val button = OperatorButton(this)
                button.content = calculatorContent.get(i)
                button.position = i
                buttons.add(button)
            }

//            Log.d(TAG, "buttons: ${buttons[i].position}")
        }
        recycler.layoutManager = GridLayoutManager(this@CaculatorActivity, 4)
        recycler.setHasFixedSize(true)

        var adapter = object : RecyclerView.Adapter<CalculatorHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorHolder {
                val view = LayoutInflater.from(this@CaculatorActivity)
                    .inflate(R.layout.single_button2, parent, false)
                return CalculatorHolder(view)
            }

            override fun onBindViewHolder(holder: CalculatorHolder, position: Int) {
                holder.button.text = buttons.get(position).content
//                Log.d(TAG, "content: ${buttons.get(position).content}")
                holder.button.isSelected = !buttons.get(position).isNumber
                holder.button.position = buttons.get(position).position
                holder.button.setOnClickListener{
                    var number  = (it as CalculatorButton)
                    Log.d(TAG, "number: ${number.isNumber} ${number.text} ${number.position}")
                    when ((it as CalculatorButton).position){
                        in 0..1 -> {
                            msg.clear()
                            displayView.text = "0"
                        }
                        2 -> {
                          outputNumber1 = msg.toString().toInt()
                            msg.clear()
                            operation = "%"
                            displayView.text = "%"
//                            isOperation = true
                        }
                        3 -> {
                            outputNumber1 = msg.toString().toInt()
                            msg.clear()
                            operation = "/"
                            displayView.text = "/"

//                            isOperation = true
                        }
                        in 4..6 -> {
                            msg.append(it.text.toString())
                            displayView.text = msg.toString()
                        }
                        7 -> {
                            outputNumber1 = msg.toString().toInt()
                            msg.clear()
                            operation = "*"
                            displayView.text = "*"
//                            isOperation = true
                        }
                        in 8..10-> {
                            msg.append(it.text.toString())
                            displayView.text = msg.toString()
                        }
                        11 -> {
                            outputNumber1 = msg.toString().toInt()
                            msg.clear()
                            operation = "-"
                            displayView.text = "-"
//                            isOperation = true
                        }
                        in 12..14-> {
                            msg.append(it.text.toString())
                            displayView.text = msg.toString()
                        }
                        15 -> {
                            outputNumber1 = msg.toString().toInt()
                            msg.clear()
                            operation = "+"
                            displayView.text = "+"

//                            isOperation = true
                        }
                        in 16..18->{
                            msg.append(it.text.toString())
                            displayView.text = msg.toString()
                        }
                        19 -> {
                            outputNumber2 = msg.toString().toInt()
                            msg.clear()
                            Log.d(TAG, "output:${outputNumber1} ${outputNumber2}");
                            var result = 0
                            if (operation == "%"){
                                result = outputNumber1 % outputNumber2
                            }else if (operation == "/"){
                                result = outputNumber1 / outputNumber2
                            }else if (operation == "*"){
                                result = outputNumber1 * outputNumber2
                            }else if (operation == "-"){
                                result = outputNumber1 - outputNumber2
                            }else if (operation == "+"){
                                result = outputNumber1 + outputNumber2
                            }
                            displayView.text = result.toString()
                        }
                    }
                }
//                Log.d(TAG, "buttons: ${holder.button.isNumber}")
            }

            override fun getItemCount(): Int {
                return calculatorContent.size
            }

        }
        recycler.adapter = adapter
    }

    class CalculatorHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        lateinit var button : CalculatorButton
        init {
            button = itemView.findViewById(R.id.button2)
        }
    }

}