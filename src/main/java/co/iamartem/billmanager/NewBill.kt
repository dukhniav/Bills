package co.iamartem.billmanager

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_new_bill.*
import java.lang.Double.parseDouble
import java.text.SimpleDateFormat
import java.util.*

class NewBill : AppCompatActivity() {

    var due_date_view: TextView? = null
    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_bill)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        val backButton = supportActionBar
        backButton!!.title = "New Bill"

//        bill_new_cancel.setOnClickListener{
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }

//        new_done.setOnClickListener{
//            Toast.makeText(this, "Functionality not part of assignment :)", Toast.LENGTH_LONG).show()
//        }

        due_date_view = this.bill_due_date

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }


        bill_due_date!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@NewBill, dateSetListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })


        // Database for recycler view
        val dbHandler = MyDBHandler(this, null, null, 1)
        //val bill : List<Bill> = dbHandler.getAllUnpaidBills()


        bill_new_done.setOnClickListener{
            newBill()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }
    }

    @SuppressLint("SetTextI18n")
    fun newBill() {
        val dbHandler = MyDBHandler(this, null, null, 1)

        if (TextUtils.isEmpty(bill_name.text.toString())) {
            bill_name.setError("Payee field is required")
        } else if (TextUtils.isEmpty(bill_amt_due.text.toString())) {
            bill_amt_due.setError("Amount Due field is required")
        } else if (TextUtils.isEmpty(bill_due_date.text.toString())) {
            bill_due_date.setError("Due Date field is required")

        } else {
            if (TextUtils.isEmpty(bill_amt_paid.text.toString())) {
                bill_amt_paid.setText("0.00")
            }
            if (TextUtils.isEmpty(bill_type.text.toString())) {
                bill_type.setText("Uncategorized")
            }
            if (TextUtils.isEmpty(bill_notes.text.toString())) {
                bill_notes.setText(" ")
            }
            val amtDue = parseDouble(bill_amt_due.text.toString())
            val amtPaid = parseDouble(bill_amt_paid.text.toString())
            val dueDate = bill_due_date.text.toString()
            val billType = bill_type.text.toString()
            val billNotes = bill_notes.text.toString()

            val bill = Bill(bill_name.text.toString(), amtDue, amtPaid, dueDate, billType, billNotes)

            dbHandler.addBill(bill)


            bill_name.setText("")
            bill_amt_due.setText("")
            bill_amt_paid.setText("")
            bill_due_date.setText("")
            bill_type.setText("")
            bill_notes.setText("")

            Toast.makeText(this, "New bill added", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        due_date_view!!.text = sdf.format(cal.getTime())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
