package com.example.practica2lrbl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.practica2lrbl.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listSneakers: ArrayList<Sneaker>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpInitialView()
    }



    private fun setUpInitialView() {
        val dataBaseManager = DataBaseManager(this)
        listSneakers = dataBaseManager.getSneakers()
        if(listSneakers.size == 0) binding.emptyLabel.visibility = View.VISIBLE
        else binding.emptyLabel.visibility = View.INVISIBLE
        val sneakersAdapter = SneakerAdapter(this, listSneakers)
        binding.sneakerListView.adapter = sneakersAdapter
        binding.sneakerListView.setOnItemClickListener { _, _, _, l ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("ID", l.toInt())
            startActivity(intent)
            finish()
        }
    }
    fun click(view: View) {
        startActivity(Intent(this, InsertActivity::class.java))
        finish()
    }
}