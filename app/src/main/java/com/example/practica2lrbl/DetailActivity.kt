package com.example.practica2lrbl

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.practica2lrbl.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var dbManager: DataBaseManager
    var sneaker: Sneaker? = null
    var id:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
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

        if(sneaker != null){
            with(binding){
                brandTextfield.setText(sneaker?.brand.toString())
                sizeTextfield.setText(sneaker?.size.toString())
                modelTextfieldDetail.setText(sneaker?.model)
                when (sneaker!!.brand.toString()) {
                    "Adidas" -> binding.imageView2.setImageResource(R.mipmap.adidas)
                    "Nike" -> binding.imageView2.setImageResource(R.mipmap.nike)
                    "Jordan" -> binding.imageView2.setImageResource(R.mipmap.jordana)
                    "Yeezy" -> binding.imageView2.setImageResource(R.mipmap.yeezy)
                }


                brandTextfield.inputType = InputType.TYPE_NULL
                sizeTextfield.inputType = InputType.TYPE_NULL
                modelTextfieldDetail.inputType = InputType.TYPE_NULL

            }
        }
    }
    fun click(view: View) {
        when(view.id){
            R.id.updateButton -> {
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("ID", id)
                startActivity(intent)
                finish()
            }

            R.id.deleteButton -> {
                AlertDialog.Builder(this)
                    .setTitle("Confirmación")
                    .setMessage("¿Realmente deseas eliminar el juego ${sneaker?.brand.toString() +sneaker?.model}?")
                    .setPositiveButton("Sí", DialogInterface.OnClickListener { dialogInterface, i ->
                        if(dbManager.deleteSneaker(id)){
                            Toast.makeText(this, "Registro eliminado exitosamente", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    })
                    .show()
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
}