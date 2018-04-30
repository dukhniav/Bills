package co.iamartem.billmanager

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*

class RecyclerAdapter(val bills: ArrayList<Bill>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    
    override fun getItemCount() = bills.size
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val cellRow: View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_row, parent, false)
        return ViewHolder(cellRow)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bill = bills.get(position)

        holder.company.text = bills[position].company
        holder.amt_due.text = bills[position].amtDue.toString()
        holder.due_date.text = bills[position].dueDate
//        holder.view.r_amt_due?.text = bill.amtDue.toString()
//        holder.view.r_due_date?.text = bill.dueDate
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val company : TextView = itemView.findViewById(R.id.r_bill_name)
        val amt_due : TextView = itemView.findViewById(R.id.r_amt_due)
        val due_date : TextView = itemView.findViewById(R.id.r_due_date)
    }
}
