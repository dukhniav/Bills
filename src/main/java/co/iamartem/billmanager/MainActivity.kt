package co.iamartem.billmanager

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        fun create(): MainActivity = MainActivity()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Tabs
        configureTabLayout()

        // New Bill
        add_new_bill.setOnClickListener{
            val intent = Intent(this, NewBill::class.java)
            startActivity(intent)
        }

    }

    private fun configureTabLayout() {

        tab_layout.addTab(tab_layout.newTab().setText("Current"))
        tab_layout.addTab(tab_layout.newTab().setText("Paid"))

        val adapter = TabPagerAdapter(supportFragmentManager,tab_layout.tabCount)
        pager.adapter = adapter

        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }



//
//        view_bills.setOnClickListener{
//            val intent = Intent(this, ViewBills::class.java)
//            startActivity(intent);
//        }
//
//        Log.i("Tag", "onCreate--------------------")
//    }
//
//    override fun onStart() {
//        super.onStart()
//        Log.i("Tag","onStart-------------------")
//    }
}
