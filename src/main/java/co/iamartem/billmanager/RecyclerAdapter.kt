package co.iamartem.billmanager

import android.app.PendingIntent.getActivity
import android.arch.persistence.room.Update
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.icu.util.ULocale
import android.os.Bundle
import android.support.constraint.Group
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.iamartem.billmanager.R.id.*
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*
import java.util.*

class RecyclerAdapter(val bills : List<Bill>) : RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder>(){


    override fun getItemCount() = bills.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerHolder{
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellRow = layoutInflater.inflate(R.layout.recyclerview_item_row, parent, false)

        return RecyclerHolder(cellRow)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        val bill = bills.get(position)

        holder.view.r_bill_name.text = bill.company
        if (bill.amtPaid >= bill.amtDue) {
            holder.view.r_amt_due?.text = ("Paid")
            holder.view.r_amt_due.setTextColor(Color.GREEN)
        } else {
            holder.view.r_amt_due?.text = ("Amount Due: $ ${bill.amtDue.toString()}")
        }
        holder.view.r_due_date?.text = ("Date Due: ${bill.dueDate}")
        holder.view.r_id?.text = (bill.id).toString()

        holder.view.full_row_id.setOnClickListener {
            Log.i("Tag","RecyclerView: Company A -> ${bill.company.toString()}")
            Log.i("Tag","RecyclerView: ExtraString A -> ${bill.id.toString()}")


            //fun onClick (view : View) {
            val intent = Intent(holder.view.full_row_id.getContext(), UpdateBill::class.java)
            intent.putExtra("id", bill.id.toString())

            var bun = Bundle()
            bun.putBoolean("isActive", true)
            intent.putExtras(bun)

            holder.view.full_row_id.getContext().startActivity(intent)
            //}
//
//            val intent = Intent(this, UpdateBill::class.java)
//            startActivity(intent)
//            //UpdateBill().loadBill(bill.id.toString())

            Log.i("Tag","RecyclerView: Company B -> ${bill.company.toString()}")

        }
    }

    inner class RecyclerHolder(val view : View) : RecyclerView.ViewHolder(view) {
    }
}

