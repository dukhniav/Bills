package co.iamartem.billmanager

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        fun create(): MainActivity = MainActivity()
    }

    // Creates first instance of app?
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        add_new_bill.setOnClickListener{
            val intent = Intent(this, NewBill::class.java)
            startActivity(intent);
        }

        view_bills.setOnClickListener{
            val intent = Intent(this, ViewBills::class.java)
            startActivity(intent);
        }




        Log.i("Tag", "onCreate--------------------")


    }

    override fun onStart() {
        super.onStart()
        Log.i("Tag","onStart-------------------")
    }


}
