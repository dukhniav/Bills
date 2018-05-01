package co.iamartem.billmanager

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*

class RecyclerAdapter(val bills : List<Bill>) : RecyclerView.Adapter<RecyclerHolder>(){
    override fun getItemCount() = bills.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerHolder{
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellRow = layoutInflater.inflate(R.layout.recyclerview_item_row, parent, false)

        return RecyclerHolder(cellRow)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        val bill = bills.get(position)

        holder.view.r_bill_name.text = bill.company
        holder.view.r_amt_due?.text = ("Amount Due: $ ${bill.amtDue.toString()}")
        holder.view.r_due_date?.text = ("Date Due: ${bill.dueDate}")
    }
}

class RecyclerHolder(val view : View) : RecyclerView.ViewHolder(view) {}