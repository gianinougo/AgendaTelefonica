package com.ugogianino.agendatelefonica

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.ugogianino.agendatelefonica.adapter.MyDBOpenHelper
import com.ugogianino.agendatelefonica.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var myDBOpenHelper: MyDBOpenHelper
    private lateinit var cursor: Cursor

    private var contactId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDBOpenHelper = MyDBOpenHelper(this, null)


        // Obtener los datos del contacto del intent
        val intent = intent
        contactId = intent.getIntExtra("contactId", -1)
        val name = intent.getStringExtra("name")
        val surname = intent.getStringExtra("surname")
        val phone = intent.getStringExtra("phone")
        val email = intent.getStringExtra("email")

        // Mostrar los detalles del contacto en la vista
        binding.tvName.text = name
        binding.tvSurname.text = surname
        binding.tvPhone.text = phone
        binding.tvEmail.text = email

        // Configurar los botones
        binding.btnCall.setOnClickListener {
            val phoneNumber = binding.tvPhone.text.toString()
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            startActivity(intent)
        }

        binding.btnEmail.setOnClickListener {
            val email = binding.tvEmail.text.toString()
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
            startActivity(intent)
        }

        binding.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("¿Está seguro de que desea eliminar el contacto?")
            builder.setPositiveButton("Sí") { _, _ ->
                myDBOpenHelper.delAgenda(contactId)
                finish()
            }
            builder.setNegativeButton("No", null)
            builder.show()
        }
    }
}
