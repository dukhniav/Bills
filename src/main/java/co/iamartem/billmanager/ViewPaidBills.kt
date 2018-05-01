package co.iamartem.billmanager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by dukhnia on 4/30/18.
 */
class ViewPaidBills : Fragment() {
    private var recyclerView : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.activity_view_paid_bills, container, false)
        recyclerView = view!!.findViewById<View>(R.id.recycler_bill_view) as RecyclerView

        // Database for recycler view
        val dbHandler = MyDBHandler(activity, null, null, 1)
        val paidBills : List<Bill> = dbHandler.getAllPaidBills()

        recyclerView!!.adapter = RecyclerAdapter(paidBills)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)


        if (paidBills.size > 0) {
            view.findViewById<android.view.View>(co.iamartem.billmanager.R.id.bill_view_nothing_show).visibility = View.INVISIBLE
        }

        return view
    }
}