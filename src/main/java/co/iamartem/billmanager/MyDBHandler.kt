package co.iamartem.billmanager

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Double.parseDouble
import kotlin.collections.ArrayList

/**
 * Created by dukhnia on 4/27/18.
 */

class MyDBHandler(context: Context, company: String?,
                  factory: SQLiteDatabase.CursorFactory?, version: Int) :
                        SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {






    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_BILLS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BILLS")
        onCreate(db)
    }

    fun addBill(bill: Bill) {
        val values = ContentValues()

        values.put(COLUMN_BILLNAME, bill.company)
        values.put(COLUMN_AMTDUE, bill.amtDue)
        values.put(COLUMN_AMTPAID, bill.amtPaid)
        values.put(COLUMN_DUEDATE, bill.dueDate)
        values.put(COLUMN_BILLTYPE, bill.billType)
        values.put(COLUMN_NOTES, bill.notes)

        val db = this.writableDatabase

        db.insert(TABLE_BILLS, null, values)
        db.close()
    }

    fun getAllBills(): ArrayList<Bill> {
        //var billList : ArrayList<Bill> = ArrayList()
        val cursor : Cursor = this.writableDatabase.query(TABLE_BILLS, arrayOf(COLUMN_ID,
                COLUMN_BILLNAME, COLUMN_AMTDUE, COLUMN_AMTPAID, COLUMN_DUEDATE, COLUMN_BILLTYPE, COLUMN_NOTES),
                null, null, null, null, null)

        //val cursor = db.rawQuery(query, null)

        //val db = this.writableDatabase
        //val query = "SELECT  * FROM $TABLE_BILLS"


        if (cursor.moveToFirst()) {
            //cursor.moveToFirst()

            do  {
                //val bills = Bill()
                var bill = Bill()//id, company, amtDue, amtPaid, dueDate, billType, notes)

                //                bills.id =       Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID)))
                //                bills.company =  cursor.getString(cursor.getColumnIndex(COLUMN_BILLNAME))
                //                bills.amtDue =   parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_AMTDUE)))
                //                bills.amtPaid =  parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_AMTPAID)))
                //                bills.dueDate =  cursor.getString(cursor.getColumnIndex(COLUMN_DUEDATE))
                //                bills.billType = cursor.getString(cursor.getColumnIndex(COLUMN_BILLTYPE))
                //                bills.notes =    cursor.getString(cursor.getColumnIndex(COLUMN_NOTES))

                bill.id = Integer.parseInt(cursor.getString(0))
                bill.company = cursor.getString(1)
                bill.amtDue = parseDouble(cursor.getString(2))
                bill.amtPaid = parseDouble(cursor.getString(3))
                bill.dueDate = cursor.getString(4)
                bill.billType = cursor.getString(5)
                bill.notes = cursor.getString(6)
                //billList.add(bills)

                billList.add(bill)
            } while (cursor.moveToNext())
        }

        cursor.close()
        //db.close()
        return billList
    }

    fun deleteBill(_id : Int): Boolean {
        var result = false
        val query = ("Select * FROM " + TABLE_BILLS + " WHERE "
                + COLUMN_ID + " =  \"" + _id + "\"")
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            val id = Integer.parseInt(cursor.getString(0))
            db.delete(TABLE_BILLS, COLUMN_ID + " = ?",
                    arrayOf(id.toString()))
            cursor.close()
            result = true
        }
        db.close()
        return result
    }

    companion object {
        private var instance : MyDBHandler? = null

        fun getInstance(context: Context) : MyDBHandler {
            if (instance == null) {
                instance = MyDBHandler(context, DATABASE_NAME, null, DATABASE_VERSION)
            }
            return instance!!
        }

        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "billsDB.db"
        val TABLE_BILLS = "bills"

        val COLUMN_ID = "_id"
        val COLUMN_BILLNAME = "db_billname"
        val COLUMN_AMTDUE = "db_amtdue"
        val COLUMN_AMTPAID = "db_amtpaid"
        val COLUMN_DUEDATE = "db_duedate"
        val COLUMN_BILLTYPE = "db_billtype"
        val COLUMN_NOTES = "db_notes"

        val ADD = "add"
        val WHAT = "what"
        val UPDATE = "update"

        var billList : ArrayList<Bill> = ArrayList()

        // Create
        val CREATE_BILLS_TABLE = ("CREATE TABLE "
                + TABLE_BILLS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_BILLNAME + " TEXT, "
                + COLUMN_AMTDUE + " DOUBLE, "
                + COLUMN_AMTPAID + " DOUBLE, "
                + COLUMN_DUEDATE + " TEXT, "
                + COLUMN_BILLTYPE + " TEXT, "
                + COLUMN_NOTES + " TEXT" + ")")


    }

}
