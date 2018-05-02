package co.iamartem.billmanager

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recyclerview_item_row.*

/**
 * Created by dukhnia on 4/30/18.
 */


class ViewCurrentBills : Fragment() {
    private var recyclerView : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.activity_view_current_bills, container, false)
        recyclerView = view!!.findViewById<View>(R.id.recycler_bill_view) as RecyclerView

        // Database for recycler view
        val dbHandler = MyDBHandler(activity, null, null, 1)
        val bills : List<Bill> = dbHandler.getAllUnpaidBills()

        recyclerView!!.adapter = RecyclerAdapter(bills)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)

        //emptyText =  as EditText

        if (bills.size > 0) {
            view.findViewById<android.view.View>(co.iamartem.billmanager.R.id.bill_view_nothing_show).visibility = View.INVISIBLE
        }


        return view
    }
}