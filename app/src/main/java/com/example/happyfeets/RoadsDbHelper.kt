package com.example.happyfeets

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RoadsDbHelper(context: Context?): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 2
        private val DATABASE_NAME = "db"
        private val TABLE_NAME = "data"
        private val KEY_TITLE = "title"
        private val KEY_DESC = "descr"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($KEY_TITLE TEXT, $KEY_DESC TEXT)"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, old: Int, new: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addRoad(road: Road): Long? {
        println("trying to insert")
        val db = this.writableDatabase
        println(road.title)
        println(road.desc)
        val values = ContentValues().apply {
            put(KEY_TITLE, road.title)
            put(KEY_DESC, road.desc)
        }
        val success = db?.insert(TABLE_NAME,null, values)
        println("inserted")
        return success
    }

    fun viewData(): List<Road> {
        val db = this.readableDatabase
        val projection = arrayOf(KEY_TITLE, KEY_DESC)
        val cursor = db.query(
            TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val road = mutableListOf<Road>()
        with(cursor) {
            while (moveToNext()) {
                val road_title = getString(getColumnIndexOrThrow(KEY_TITLE))
                val road_desc = getString(getColumnIndexOrThrow(KEY_DESC))
                road.add(Road(road_title, road_desc))
            }
        }
        cursor.close()
        return road
    }
}