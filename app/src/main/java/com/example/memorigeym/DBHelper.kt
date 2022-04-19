package com.example.memorigeym
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
val DATABASENAME = "Oyunjuk"
val TABLENAME = "Skorlar"
val COL_PUAN = "Puan"
val COL_TIME = "Süre"
val COL_ID = "id"
val COL_LEVEL = "Seviye"
class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
    1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLENAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_PUAN + " INTEGER,"  + COL_TIME + " INTEGER," + COL_LEVEL + " VARCHAR(256))"
        db?.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onCreate(db);
    }
    fun insertData(skor: Skor) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_PUAN, skor.puan)
        contentValues.put(COL_TIME, skor.time)
        contentValues.put(COL_LEVEL, skor.seviye)
        val result = database.insert(TABLENAME, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    @SuppressLint("Range")
    fun readData(): MutableList<Skor> {
        val list: MutableList<Skor> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val skor = Skor()
                skor.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                skor.puan = result.getString(result.getColumnIndex(COL_PUAN)).toInt()
                skor.time = result.getString(result.getColumnIndex(COL_TIME)).toInt()
                skor.seviye = result.getString(result.getColumnIndex(COL_LEVEL))
                list.add(skor)
            }
            while (result.moveToNext())
        }
        return list
    }
 /*   fun deleteAllData(){
        val db = this.writableDatabase
        db.delete(TABLENAME,null,null)
        db.close()

    }

*/
}