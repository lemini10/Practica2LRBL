package com.example.practica2lrbl

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.practica2lrbl.databinding.ListItemBinding

class SneakerAdapter(contexto: Context, sneakersList: ArrayList<Sneaker>): BaseAdapter() {

    private val sneakerList = sneakersList
    private val layoutInflater = LayoutInflater.from(contexto)

    override fun getCount(): Int {
        return sneakerList.size
    }

    override fun getItem(p0: Int): Any {
        return sneakerList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return sneakerList[p0].id.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val binding = ListItemBinding.inflate(layoutInflater)

        with(binding){
            modelLabel.text = sneakerList[p0].model
            brandLabel.text = sneakerList[p0].brand.toString()
            sizeLabel.text = sneakerList[p0].size.toString()
        }

        return binding.root
    }
}