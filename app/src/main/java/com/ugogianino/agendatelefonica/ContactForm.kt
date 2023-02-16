package com.ugogianino.agendatelefonica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ugogianino.agendatelefonica.adapter.MyDBOpenHelper
import com.ugogianino.agendatelefonica.databinding.ActivityContactFormBinding
import com.ugogianino.agendatelefonica.modelo.Agenda

class ContactForm : AppCompatActivity() {


    private lateinit var binding: ActivityContactFormBinding
    private lateinit var myDBOpenHelper: MyDBOpenHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDBOpenHelper = MyDBOpenHelper(this, null)

        binding.btnSave.setOnClickListener{

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Nuevo Contacto")
            builder.setMessage("Quieres añadir este contacto?")
            builder.setPositiveButton(android.R.string.ok){
                    dialog, which ->
                crearContacto()

            }
            builder.setNegativeButton(android.R.string.cancel){
                    dialog, which -> Toast.makeText(this, "Has Cancelado", Toast.LENGTH_LONG).show()
            }
            builder.show()

        }

    }

    fun crearContacto() {
        val name = binding.tvName.text.toString().trim()
        val surName = binding.tvSurName.text.toString().trim()
        val phone = binding.editTextPhone.text.toString().trim()
        val email = binding.editTextTextEmailAddress.text.toString().trim()

        if (name.isEmpty() || surName.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_LONG).show()
        } else if (!Patterns.PHONE.matcher(phone).matches()) {
            Toast.makeText(this, "El número de teléfono no es válido", Toast.LENGTH_LONG).show()
        } else if (!isEmailValid(email)) {
            Toast.makeText(this, "La dirección de correo no es válida", Toast.LENGTH_LONG).show()
        }
        else {
            val contacto = Agenda(name, surName, phone, email)
            myDBOpenHelper.addAgenda(contacto)
            Toast.makeText(this, "Añadido", Toast.LENGTH_LONG).show()
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        }
    }

    fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailRegex.matches(email)
    }


}