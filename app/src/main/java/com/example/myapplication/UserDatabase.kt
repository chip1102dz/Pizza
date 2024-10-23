package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build.VERSION

class UserDatabase(context: Context) : SQLiteOpenHelper(context,DATABASE,null, VERSION) {
    companion object{
        const val DATABASE = "SQL9.db"
        const val VERSION = 1
        const val TABLE = "object1234"
        const val ID = "id"
        const val NAME = "name"
        const val TUONG_OT = "tuong_ot"
        const val PHO_MAI = "pho_mai"
        const val DE = "de"
        const val CREATE_TABLE = " CREATE TABLE " + TABLE + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                TUONG_OT + " INTEGER, " +
                PHO_MAI + " INTEGER, " +
                DE + " TEXT " + ");"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    fun getAllData(): MutableList<User>{
        val list = mutableListOf<User>()
        val db = readableDatabase
        val cursor = db.query(TABLE, null,null,null,null,null,null)
        if(cursor.moveToFirst()){
            do {
                val user = User()
                user.id = cursor.getInt(0)
                user.name = cursor.getString(1)
                user.pho_mai = cursor.getInt(2) == 1
                user.tuong_ot = cursor.getInt(3) == 1
                user.de = cursor.getString(4)
                list.add(user)
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }
    fun insertData(user: User){
        val db = writableDatabase
        val values = ContentValues()
        values.put(NAME, user.name)
        values.put(TUONG_OT, if(user.tuong_ot) 1 else 0)
        values.put(PHO_MAI, if(user.pho_mai) 1 else 0)
        values.put(DE, user.de)
        db.insert(TABLE, null, values)
    }
    fun deleteData(id: Int){
        val db = writableDatabase
        db.delete(TABLE, ID + " = ? ", arrayOf(id.toString()))
        db.close()
    }
}