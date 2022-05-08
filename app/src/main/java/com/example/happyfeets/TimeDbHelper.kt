package com.example.happyfeets

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TimeDbHelper(context: Context?): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 2
        private val DATABASE_NAME = "db"
        private val TABLE_NAME = "times"
        private val KEY_ROAD = "road"
        private val KEY_TIME = "time"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($KEY_ROAD TEXT, $KEY_TIME INT)"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, old: Int, new: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addTime(time: Time): Long? {
        println("trying to insert")
        val db = this.writableDatabase
        println(time.road)
        println(time.time)
        val values = ContentValues().apply {
            put(KEY_ROAD, time.road)
            put(KEY_TIME, time.time)
        }
        val success = db?.insert(TABLE_NAME,null, values)
        println("inserted")
        return success
    }

    fun viewData(): List<Time> {
        val db = this.readableDatabase
        val projection = arrayOf(KEY_ROAD, KEY_TIME)
        val cursor = db.query(
            TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val times = mutableListOf<Time>()
        with(cursor) {
            while (moveToNext()) {
                val time_road = getString(getColumnIndexOrThrow(KEY_ROAD))
                val time_time = getInt(getColumnIndexOrThrow(KEY_TIME))
                times.add(Time(time_road, time_time))
            }
        }
        cursor.close()
        return times
    }
}