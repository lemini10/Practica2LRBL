package com.example.practica2lrbl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.practica2lrbl.databinding.ActivityInsertBinding

class InsertActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, TextWatcher {

    private lateinit var binding: ActivityInsertBinding
    private var selectedBrand: SneakerBrands = SneakerBrands.Adidas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.modelTextfield.text.clear()
        adapt()
    }

    fun addRegister() {
        val managerDB = DataBaseManager(this)
        with(binding) {
            if (modelTextfield.text.isEmpty()) {
                Toast.makeText(this@InsertActivity, "Por favor ingresa un modelo", Toast.LENGTH_LONG).show()
            } else {
                val sneakerBrand = brandSpinner.selectedItem.toString()
                val sneaker = SneakerBrands.Adidas
                when(sneakerBrand) {
                    "Adidas" -> SneakerBrands.Adidas
                    "Nike" -> SneakerBrands.Nike
                    "Jordan" -> SneakerBrands.Jordan
                    "Yeezy" -> SneakerBrands.Yeezy
                }
                val model = binding.modelTextfield.text.toString()
                val size = binding.sizeSpinner.selectedItem.toString().toDouble()
                val id = managerDB.insertGame(sneaker,model,size)
                if(id > 0) {
                    Toast.makeText(this@InsertActivity, "Registro guardado exitosamente", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun validSelection(): Boolean {
        var isValidToAdd: Boolean = false

        with(binding){
            val isBrandSelected: Boolean = brandSpinner.selectedItem != null
            val isModelWritten: Boolean = !modelTextfield.text.isEmpty()
            val isSizeSelected: Boolean = sizeSpinner.selectedItem != null
            if (isBrandSelected && isModelWritten && isSizeSelected) {
                isValidToAdd = true
            }
        }
        return isValidToAdd
    }

    private fun adapt() {
        ArrayAdapter.createFromResource(
            this,
            R.array.sneakerBrands,
            android.R.layout.simple_spinner_dropdown_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.brandSpinner.adapter = it
        }
        binding.brandSpinner.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            this,
            R.array.sneakerSizes,
            android.R.layout.simple_spinner_dropdown_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.sizeSpinner.adapter = it
        }
        binding.sizeSpinner.onItemSelectedListener = this
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0 != null) {
            val selectedItemPosition = p0.selectedItemPosition
            if (selectedItemPosition == 0) {
                this.selectedBrand = SneakerBrands.Adidas
                binding.brandImage.setImageResource(R.mipmap.adidas)
            } else if (selectedItemPosition == 1) {
                this.selectedBrand = SneakerBrands.Jordan
                binding.brandImage.setImageResource(R.mipmap.jordana)
            } else if (selectedItemPosition == 2) {
                this.selectedBrand = SneakerBrands.Nike
                binding.brandImage.setImageResource(R.mipmap.nike)
            }
            else if (selectedItemPosition == 2) {
                this.selectedBrand = SneakerBrands.Yeezy
                binding.brandImage.setImageResource(R.mipmap.yeezy)
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        binding.addButton.isEnabled = false
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    fun click(view: View) {
        addRegister()
    }
}