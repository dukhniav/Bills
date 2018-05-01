package co.iamartem.billmanager

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View

/**
 * Created by dukhnia on 4/30/18.
 */

// From ViewBills ----------------------------------------------------------------------------------

//override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    setContentView(R.layout.activity_view_bills)
//
//    // To show back arrow
//    if(supportActionBar != null){
//        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
//        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
//    }
//
//    // Creates a back arrow button
//    val backButton = supportActionBar
//    backButton!!.title = "View Bills"
//
//    // Database for recycler view
//    val dbHandler = MyDBHandler(this, null, null, 1)
//    val bills : List<Bill> = dbHandler.getAllBills()
//
//    R.id.recycler_bill_view.layoutManager = LinearLayoutManager(this)
//    R.id.recycler_bill_view.adapter = RecyclerAdapter(bills)
//
//    if (bills.size > 0) {
//        R.id.bill_view_nothing_show.visibility = View.INVISIBLE
//    }
//}
//
//override fun onSupportNavigateUp(): Boolean {
//    onBackPressed()
//    return true
//}
//
//override fun onBackPressed() {
//    super.onBackPressed()
//    var intent = Intent(this, MainActivity::class.java)
//    startActivity(intent)
//}