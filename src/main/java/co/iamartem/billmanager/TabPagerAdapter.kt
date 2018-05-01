package co.iamartem.billmanager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by dukhnia on 4/30/18.
 */

class TabPagerAdapter(fm: FragmentManager, private var tabCount: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return ViewCurrentBills()
            1 -> return ViewPaidBills()
            else -> return null
        }
    }
    override fun getCount(): Int {
        return tabCount
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Current"
            else -> {
                return "Paid"
            }
        }
    }
}