package com.ugogianino.agendatelefonica.adapter

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.ugogianino.agendatelefonica.modelo.Agenda

class MyDBOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION)  {

        companion object {
            val DATABASE_NAME = "AgendaTelefonica"
            val DATABASE_VERSION = 1

            val TABLA_NAME = "agenda"
            val COLUMNA_ID = "_id"
            val COLUNMA_NAME = "name"
            val COLUMNA_APELLIDO = "surname"
            val COLUMNA_TEL = "telefono"
            val COLUMNA_EMAIL = "email"
        }

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            val tablaAgenda = "CREATE TABLE $TABLA_NAME " +
                    "($COLUMNA_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUNMA_NAME TEXT, " +
                    "$COLUMNA_APELLIDO TEXT, " +
                    "$COLUMNA_TEL TEXT, " +
                    "$COLUMNA_EMAIL TEXT)"

            db!!.execSQL(tablaAgenda)

        } catch (e: SQLiteException) {
            Log.e("{$TAG} (onCreate)", e.message.toString())
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            val deleteAgenta = "DROP TABLE IF EXISTS $TABLA_NAME "

            db!!.execSQL(deleteAgenta)

            onCreate(db)
        } catch (e: SQLiteException) {
            Log.e("{$TAG} (onUpgrade)", e.message.toString())
        }
    }

    fun addAgenda(agenda : Agenda) {
        val datos = ContentValues()

        datos.put(COLUNMA_NAME, agenda.name)
        datos.put(COLUMNA_APELLIDO, agenda.surName)
        datos.put(COLUMNA_TEL, agenda.telNumber)
        datos.put(COLUMNA_EMAIL, agenda.email)

        val db = this.writableDatabase
        db.insert(TABLA_NAME, null, datos)
        db.close()
    }

    fun upDateAgenda(id : Int, agenda : Agenda) {

        val args = arrayOf(id.toString())

        val datos = ContentValues()

        datos.put(COLUNMA_NAME, agenda.name)
        datos.put(COLUMNA_APELLIDO, agenda.surName)
        datos.put(COLUMNA_TEL, agenda.telNumber)
        datos.put(COLUMNA_EMAIL, agenda.email)

        val db = this.writableDatabase
        db.update(TABLA_NAME, datos, "$COLUMNA_ID = ?", args)
        db.close()

    }


    fun delAgenda(id : Int): Int {

        val args = arrayOf(id.toString())

        val db = this.writableDatabase
        val borrados = db.delete(TABLA_NAME, "$COLUMNA_ID = ?", args)
        db.close()

        return borrados
    }

    fun getAllData(): Cursor {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLA_NAME"
        return db.rawQuery(query, null)
    }





}