package co.iamartem.billmanager

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import co.iamartem.billmanager.R.id.bill_due_date
import co.iamartem.billmanager.R.id.bill_new_done
import kotlinx.android.synthetic.main.activity_new_bill.*
import kotlinx.android.synthetic.main.activity_update_bill.*
import kotlinx.android.synthetic.main.recyclerview_item_row.*
import java.lang.Double
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by dukhnia on 5/1/18.
 */
class UpdateBill() : AppCompatActivity() {

    var due_date_view: TextView? = null
    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_bill)


        due_date_view = this.u_bill_due_date

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        u_bill_due_date!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@UpdateBill, dateSetListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })

        val bun = intent.extras
        val idExtra : String

        idExtra = bun.getString("id")
        loadBill(idExtra)

        bill_update_done.setOnClickListener{
            updateBill()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }
    }


    fun loadBill(id : String) {
        val dbHandler = MyDBHandler(this, null, null, 1)
        Log.i("Tag","UpdateBill - loadBill: id -> ${id}")
        val bill : Bill? = dbHandler.findBill(id)

        u_bill_name.setText(bill?.company)
        u_bill_amt_due.setText(bill?.amtDue.toString())
        u_bill_amt_paid.setText(bill?.amtPaid.toString())
        u_bill_due_date.setText(bill?.dueDate)
        u_bill_type.setText(bill?.billType)
        u_bill_notes.setText(bill?.notes)
    }

    fun updateBill() {
        val dbHandler = MyDBHandler(this, null, null, 1)

        if (TextUtils.isEmpty(u_bill_name.text.toString())) {
            u_bill_name.setError("Payee field is required")
        } else if (TextUtils.isEmpty(u_bill_amt_due.text.toString())) {
            u_bill_amt_due.setError("Amount Due field is required")
        } else if (TextUtils.isEmpty(u_bill_due_date.text.toString())) {
            u_bill_due_date.setError("Due Date field is required")

        } else {
            if (TextUtils.isEmpty(u_bill_amt_paid.text.toString())) {
                u_bill_amt_paid.setText("0.00")
            }
            if (TextUtils.isEmpty(u_bill_type.text.toString())) {
                u_bill_type.setText("Uncategorized")
            }
            if (TextUtils.isEmpty(u_bill_notes.text.toString())) {
                u_bill_notes.setText(" ")
            }
            val amtDue = Double.parseDouble(u_bill_amt_due.text.toString())
            val amtPaid = Double.parseDouble(u_bill_amt_paid.text.toString())
            val dueDate = u_bill_due_date.text.toString()
            val billType = u_bill_type.text.toString()
            val billNotes = u_bill_notes.text.toString()

            val bill = Bill(u_bill_name.text.toString(), amtDue, amtPaid, dueDate, billType, billNotes)

            dbHandler.updateBill(bill)

            u_bill_name.setText("")
            u_bill_amt_due.setText("")
            u_bill_amt_paid.setText("")
            u_bill_due_date.setText("")
            u_bill_type.setText("")
            u_bill_notes.setText("")

            Toast.makeText(this, "New bill added", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        due_date_view!!.text = sdf.format(cal.getTime())
    }
}