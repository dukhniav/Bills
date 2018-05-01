package co.iamartem.billmanager

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
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
        Log.v("Tag", " Record Inserted Sucessfully")
    }

    fun getAllUnpaidBills(): List<Bill> {
            val db = this.writableDatabase
            val list = ArrayList<Bill>()
            val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_BILLS WHERE $COLUMN_AMTPAID < $COLUMN_AMTDUE", null)

            if (cursor != null) {
                if (cursor.count > 0) {
                    cursor.moveToFirst()
                    do {
                        val billID = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                        val billCompany = cursor.getString(cursor.getColumnIndex(COLUMN_BILLNAME))
                        val billDue = parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_AMTDUE)))
                        val billPaid = parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_AMTPAID)))
                        val billDate = cursor.getString(cursor.getColumnIndex(COLUMN_DUEDATE))
                        val billType = cursor.getString(cursor.getColumnIndex(COLUMN_BILLTYPE))
                        val billNotes = cursor.getString(cursor.getColumnIndex(COLUMN_NOTES))
                        val bill = Bill(billID, billCompany, billDue, billPaid, billDate, billType, billNotes)
                        list.add(bill)
                    } while (cursor.moveToNext())
                }
            }
        cursor.close()
        return list
    }

    fun getAllPaidBills(): List<Bill> {
        val db = this.writableDatabase
        val list = ArrayList<Bill>()
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_BILLS WHERE $COLUMN_AMTPAID >= $COLUMN_AMTDUE", null)

        if (cursor != null) {
            if (cursor.count > 0) {
                cursor.moveToFirst()
                do {
                    val billID = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                    val billCompany = cursor.getString(cursor.getColumnIndex(COLUMN_BILLNAME))
                    val billDue = parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_AMTDUE)))
                    val billPaid = parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_AMTPAID)))
                    val billDate = cursor.getString(cursor.getColumnIndex(COLUMN_DUEDATE))
                    val billType = cursor.getString(cursor.getColumnIndex(COLUMN_BILLTYPE))
                    val billNotes = cursor.getString(cursor.getColumnIndex(COLUMN_NOTES))
                    val bill = Bill(billID, billCompany, billDue, billPaid, billDate, billType, billNotes)
                    list.add(bill)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return list
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
