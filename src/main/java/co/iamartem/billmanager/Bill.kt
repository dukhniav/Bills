package co.iamartem.billmanager

/**
 * Created by dukhnia on 4/27/18.
 */

class Bill {
    var id: Int = 0
    var company: String? = ""
    var amtDue: Double = 0.00
    var amtPaid: Double = 0.00
    var dueDate: String? = "MM/dd/yyyy"
    var billType: String? = ""
    var notes: String? = ""

    constructor(id: Int, company: String, amtDue: Double, amtPaid: Double,
                dueDate: String, billType: String, notes: String){
        this.id = id
        this.company = company
        this.amtDue = amtDue
        this.amtPaid = amtPaid
        this.dueDate = dueDate
        this.billType = billType
        this.notes = notes
    }

    constructor(company: String, amtDue: Double, amtPaid: Double,
                dueDate: String, billType: String, notes: String){
        this.company = company
        this.amtDue = amtDue
        this.amtPaid = amtPaid
        this.dueDate = dueDate
        this.billType = billType
        this.notes = notes
    }
}