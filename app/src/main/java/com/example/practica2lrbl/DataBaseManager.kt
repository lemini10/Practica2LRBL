package com.example.practica2lrbl

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class DataBaseManager(context: Context?) : DBHelper(context) {

    val context = context

    fun insertGame(brand: SneakerBrands, model: String, size: Double): Long{

        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase
        var id: Long = 0

        try{

            val values = ContentValues()
            val sneakerBrand: String = brand.toString()
            val sneakerSize: String = size.toString()

            values.put("brand", sneakerBrand)
            values.put("model", model)
            values.put("size", sneakerSize)

            id = db.insert(TABLE_GAMES, null, values)

        }catch(e: Exception){
        }finally {
            db.close()
        }

        return id
    }

    fun getSneakers(): ArrayList<Sneaker>{
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        var sneakersList = ArrayList<Sneaker>()
        var fetchingItem: Sneaker? = null
        var cursorSneakers: Cursor? = null

        cursorSneakers = db.rawQuery("SELECT * FROM $TABLE_GAMES", null)

        if(cursorSneakers.moveToFirst()){
            do{
                var sneakerBrandValue = SneakerBrands.Yeezy
                when (cursorSneakers.getString(1)) {
                    "Jordan" -> sneakerBrandValue = SneakerBrands.Jordan
                    "Yeezy" -> sneakerBrandValue = SneakerBrands.Yeezy
                    "Nike" -> sneakerBrandValue = SneakerBrands.Nike
                    "Adidas" -> sneakerBrandValue = SneakerBrands.Adidas
                }
                fetchingItem = Sneaker(sneakerBrandValue,cursorSneakers.getDouble(3),cursorSneakers.getString(2),cursorSneakers.getString(0))
                sneakersList.add(fetchingItem)
            }while(cursorSneakers.moveToNext())
        }

        cursorSneakers.close()

        return sneakersList
    }

    fun getSneaker(id: Int): Sneaker?{
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        var sneaker: Sneaker? = null
        var cursorSneakers: Cursor? = null

        cursorSneakers = db.rawQuery("SELECT * FROM $TABLE_GAMES WHERE id = $id LIMIT 1", null)

        if(cursorSneakers.moveToFirst()){
            var sneakerBrandValue = SneakerBrands.Yeezy
            when (cursorSneakers.getString(1)) {
                "Jordan" -> sneakerBrandValue = SneakerBrands.Jordan
                "Yeezy" -> sneakerBrandValue = SneakerBrands.Yeezy
                "Nike" -> sneakerBrandValue = SneakerBrands.Nike
                "Adidas" -> sneakerBrandValue = SneakerBrands.Adidas
            }
            sneaker = Sneaker(sneakerBrandValue,cursorSneakers.getDouble(3),cursorSneakers.getString(2),cursorSneakers.getString(0))
        }

        cursorSneakers.close()

        return sneaker
    }

    fun updateSneaker(id: Int, brand: SneakerBrands, model: String, size: Double): Boolean {

        var succesfullyUpdated = false
        val sneakerBrandString = brand.toString()
        val sneakerSizeString = size.toString()

        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        try{
            db.execSQL("UPDATE $TABLE_GAMES SET brand = '$sneakerBrandString', model = '$model', size = '$sneakerSizeString' WHERE id = $id")
            succesfullyUpdated = true
        }catch(e: Exception){

        }finally {
            db.close()
        }

        return succesfullyUpdated
    }

    fun deleteSneaker(id: Int): Boolean{

        var succesfullyDeleted = false

        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        try{
            db.execSQL("DELETE FROM $TABLE_GAMES WHERE id = $id")
            succesfullyDeleted = true
        }catch(e: Exception){

        }finally {
            db.close()
        }
        return succesfullyDeleted
    }
}