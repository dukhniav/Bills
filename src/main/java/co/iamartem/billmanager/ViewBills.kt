package co.iamartem.billmanager

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import co.iamartem.billmanager.MyDBHandler.Companion.COLUMN_ID
import co.iamartem.billmanager.MyDBHandler.Companion.UPDATE
import co.iamartem.billmanager.MyDBHandler.Companion.WHAT
import co.iamartem.billmanager.R.layout.activity_view_bills
import kotlinx.android.synthetic.main.activity_view_bills.*

class ViewBills : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_bills)

        // To show back arrow
        if(supportActionBar != null){
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        }
        


        // Creates a back arrow button
        val backButton = supportActionBar
        backButton!!.title = "View Bills"

//        var bills : ArrayList<Bill> = MyDBHandler.billList

//        recycler_bill_view.layoutManager = LinearLayoutManager(this)
//        recycler_bill_view.adapter = RecyclerAdapter(bills)

        activity_view_bills.adapter = RecyclerAdapter(this@ViewBills)
    }


    class BillHandler() : BaseAdapter() {
        var billList : ArrayList<Bill>? = null
        var context : Context? = null

        constructor(context: Context) : this() {
            this.billList = MyDBHandler.getInstance(context!!).getAllBills()
            this.context = context
        }

        fun updateBills() {
            this.billList = MyDBHandler.getInstance(context!!).getAllBills()
            notifyDataSetChanged()
        }

        override fun getView(position: Int, view: View?, p2: ViewGroup?): View {
            var convertView : View? = view
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.recyclerview_item_row, null)
            }
            var company : TextView = convertView?.findViewById(R.id.r_bill_name) as TextView
            var amtDue : TextView = convertView?.findViewById(R.id.r_due_date) as TextView
            var dueDate : TextView = convertView?.findViewById(R.id.r_due_date) as TextView
            var imgEdit : ImageView = convertView.findViewById(R.id.r_img_edit) as ImageView

            company.text = billList?.get(position)?.company
            amtDue.text = billList?.get(position)?.amtDue.toString()
            dueDate.text = billList?.get(position)?.dueDate

            imgEdit.setOnClickListener {
                var intent = Intent(context, activity_view_bills::class.java)
                intent.putExtra(WHAT,UPDATE)
                intent.putExtra(COLUMN_ID, (billList?.get(position)) as Parcelable)
                context?.startActivity(intent)
            }

            return convertView
        }

        override fun getItem(p0: Int): Bill? {
            return billList?.get(p0)
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return billList!!.size
        }

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
