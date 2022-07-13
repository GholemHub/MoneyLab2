package com.gholem.moneylab.features.add.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gholem.moneylab.R
import com.gholem.moneylab.classes.Categori
import com.gholem.moneylab.databinding.ActivityAddBinding
import org.mariuszgromada.math.mxparser.Expression
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {

    private var idOfTipe: Int = 0
    private lateinit var categori: Categori

    lateinit var binding: ActivityAddBinding

    //private lateinit var bindingDialig: DialogTytleBinding
    //lateinit var adapter1: AdapterAddDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        binding = ActivityAddBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        //Log.d("TAG", "ID: " + userModel.idTocken)
        //Disable keyboard on editText
        binding.inputText.showSoftInputOnFocus = false

    }


    private var boolEquel = false

    private fun updateText(newStr: String) {
        var oldStr = binding.inputText.text.toString()
        var coursorPos = binding.inputText.selectionStart
        var leftStr = oldStr.substring(0, coursorPos)
        var rightStr = oldStr.substring(coursorPos)
        binding.inputText.setText(String.format("%s%s%s", leftStr, newStr, rightStr))
        binding.inputText.setSelection(coursorPos + 1)
    }

    fun btnListener_zero(View: View) {
        updateText("0")
    }

    fun btnListener_zero_zero(View: View) {

        var oldStr = binding.inputText.text.toString()
        var coursorPos = binding.inputText.selectionStart
        var leftStr = oldStr.substring(0, coursorPos)
        var rightStr = oldStr.substring(coursorPos)
        binding.inputText.setText(String.format("%s%s%s", leftStr, "00", rightStr))
        binding.inputText.setSelection(coursorPos + 2)

    }

    fun btnListener_one(View: View) {
        updateText("1")
    }

    fun btnListener_two(View: View) {
        updateText("2")
    }

    fun btnListener_three(View: View) {
        updateText("3")
    }

    fun btnListener_four(View: View) {
        updateText("4")
    }

    fun btnListener_five(View: View) {
        updateText("5")
    }

    fun btnListener_six(View: View) {
        updateText("6")
    }

    fun btnListener_seven(View: View) {
        updateText("7")
    }

    fun btnListener_eight(View: View) {
        updateText("8")
    }

    fun btnListener_nine(View: View) {
        updateText("9")
    }

    fun btnListener_clear(View: View) {
        binding.inputText.setText("")
        ChanheColour("#32A852")
    }

    fun btnListener_Plus(View: View) {
        updateText("+")
        boolEquel = true
        ChanheColour("#FFC107")

    }

    fun btnListener_Minuse(View: View) {
        updateText("-")
        boolEquel = true
        ChanheColour("#FFC107")
    }

    fun btnListener_Multiplication(View: View) {
        updateText("×")
        boolEquel = true
        ChanheColour("#FFC107")
    }

    fun btnListener_Division(View: View) {
        updateText("/")
        boolEquel = true
        ChanheColour("#FFC107")
    }

    fun btnListener_Dot(View: View) {
        updateText(".")
    }

    var expression = 0

    @SuppressLint("ResourceAsColor")
    fun btnListener_Equel(View: View) {
        var str = binding.inputText.text.toString()

        str = str.replace("×", "*")
        var exp = Expression(str)
        var res = exp.calculate().toString()

        expression = res.toDouble().toInt()

        binding.inputText.setText(expression.toString())
        binding.inputText.setSelection(expression.toString().length)

        if (boolEquel) {
            ChanheColour("#32A852")
            boolEquel = false
            Log.d("TAG", "ERROR1")
        } else {

            CreateTransaction()

        }
    }

    private fun CreateTransaction() {

        /*  if (binding.tytleImage.drawable.toString() != "android.graphics.drawable.VectorDrawable@ffe30f7") {

              CreateDate()

              var id = 0

              (if (categori.id == 3) {
                  id = 1
              } else {
                  id = categori.id
              })

              var newT = TransactionVM(categori.image, categori.title, id, expression, CreateDate())

              repository.ThrowTransactionToFirestore(newT)

              ChangeActivity()
          } else {
              val myToast = Toast.makeText(this, "Set title!", Toast.LENGTH_SHORT)
              myToast.setGravity(Gravity.END, 200, 200)
              myToast.show()
          }
  */
    }


    private fun CreateDate(): String {
        val sdf = SimpleDateFormat("yyyy.MM.dd")
        val date = Date()
        return sdf.format(date)
    }

    @SuppressLint("ResourceAsColor")
    fun ChanheColour(str: String) {
        binding.button22.setBackgroundColor(Color.parseColor(str))
    }

    fun btnListener_backspace(View: View) {

        var cursorPos = binding.inputText.selectionStart
        var textLen = binding.inputText.text.length

        if (cursorPos != 0 && textLen != 0) {

            val selection = binding.inputText.text as SpannableStringBuilder
            selection.replace(cursorPos - 1, cursorPos, "")
            binding.inputText.setText(selection)
            binding.inputText.setSelection(cursorPos - 1)
        }
    }
}
