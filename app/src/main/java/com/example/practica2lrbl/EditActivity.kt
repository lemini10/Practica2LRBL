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
import com.example.practica2lrbl.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, TextWatcher {

    private lateinit var binding: ActivityEditBinding
    private var selectedBrand: SneakerBrands = SneakerBrands.Adidas

    private lateinit var dbManager: DataBaseManager
    var sneaker: Sneaker? = null
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            val bundle = intent.extras
            if(bundle != null){
                id = bundle.getInt("ID", 0)
            }
        }else{
            id = savedInstanceState.getSerializable("ID") as Int
        }

        dbManager = DataBaseManager(this)

        sneaker = dbManager.getSneaker(id)
        adapt()

        if(sneaker != null){
            with(binding){
                var index = 0
                when (sneaker!!.brand) {
                    SneakerBrands.Adidas -> {
                        index = 0
                        binding.editImage.setImageResource(R.mipmap.adidas)
                    }
                    SneakerBrands.Jordan ->{
                        index = 1
                        binding.editImage.setImageResource(R.drawable.ic_launcher_foreground)
                    }
                    SneakerBrands.Nike -> {
                        index = 2
                        binding.editImage.setImageResource(R.mipmap.nike)

                    }
                    SneakerBrands.Yeezy -> {
                        index = 3
                        binding.editImage.setImageResource(R.mipmap.yeezy)
                    }
                }
                brandSpinnerEdit.setSelection(index)
                selectedBrand = sneaker!!.brand
                modelTextfieldEdit.setText(sneaker?.model)
                var sizeIndex = 0
                when (sneaker!!.size) {
                    5.0 -> sizeIndex = 0
                    5.5 -> sizeIndex = 1
                    6.0 -> sizeIndex = 2
                    6.5 -> sizeIndex = 3
                    7.0 -> sizeIndex = 4
                    7.5 -> sizeIndex = 5
                    8.0 -> sizeIndex = 6
                    8.5 -> sizeIndex = 7
                    9.0 -> sizeIndex = 8
                    9.5 -> sizeIndex = 9
                }
                sizeSpinnerEdit.setSelection(sizeIndex)
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    private fun adapt() {
        ArrayAdapter.createFromResource(
            this,
            R.array.sneakerBrands,
            android.R.layout.simple_spinner_dropdown_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.brandSpinnerEdit.adapter = it
        }
        binding.brandSpinnerEdit.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            this,
            R.array.sneakerSizes,
            android.R.layout.simple_spinner_dropdown_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.sizeSpinnerEdit.adapter = it
        }
        binding.sizeSpinnerEdit.onItemSelectedListener = this
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0 != null) {
            val selectedItemPosition = p0.selectedItemPosition
            if (selectedItemPosition == 0) {
                this.selectedBrand = SneakerBrands.Adidas
                binding.editImage.setImageResource(R.mipmap.adidas)
            } else if (selectedItemPosition == 1) {
                this.selectedBrand = SneakerBrands.Jordan
                binding.editImage.setImageResource(R.mipmap.jordana)
            } else if (selectedItemPosition == 2) {
                this.selectedBrand = SneakerBrands.Nike
                binding.editImage.setImageResource(R.mipmap.nike)
            }
            else if (selectedItemPosition == 3) {
                this.selectedBrand = SneakerBrands.Yeezy
                binding.editImage.setImageResource(R.mipmap.yeezy)
            }
        }
    }

    fun click(view: View) {
        with(binding){
            if(validSelection()){
                if(updatedSuccessfully()){
                    Toast.makeText(this@EditActivity, "Registro actualizado exitosamente", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@EditActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this@EditActivity, "Error al actualizar el registro", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this@EditActivity, "Ningún campo puede quedar vacío", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validSelection(): Boolean {
        var isValidToAdd: Boolean = false

        with(binding){
            val isBrandSelected: Boolean = brandSpinnerEdit.selectedItem != null
            val isModelWritten: Boolean = !modelTextfieldEdit.text.isEmpty()
            val isSizeSelected: Boolean = sizeSpinnerEdit.selectedItem != null
            if (isBrandSelected && isModelWritten && isSizeSelected) {
                isValidToAdd = true
            }
        }
        return isValidToAdd
    }

    private fun updatedSuccessfully(): Boolean {
        with(binding) {
            val brand = brandSpinnerEdit.selectedItem.toString()
            var sneakerBrand = SneakerBrands.Adidas
            when (brand) {
                "Adidas" -> sneakerBrand = SneakerBrands.Adidas
                "Nike" -> sneakerBrand = SneakerBrands.Nike
                "Yeezy" -> sneakerBrand = SneakerBrands.Yeezy
                "Jordan" -> sneakerBrand = SneakerBrands.Jordan
                else -> return false
            }
            val size = sizeSpinnerEdit.selectedItem.toString().toDouble()
            val model = modelTextfieldEdit.text.toString()
            return dbManager.updateSneaker(id,sneakerBrand, model ,size)
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
}