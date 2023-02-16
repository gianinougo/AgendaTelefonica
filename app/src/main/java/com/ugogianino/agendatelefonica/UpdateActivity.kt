package com.ugogianino.agendatelefonica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.ugogianino.agendatelefonica.adapter.MyDBOpenHelper
import com.ugogianino.agendatelefonica.databinding.ActivityUpdateBinding
import com.ugogianino.agendatelefonica.modelo.Agenda

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var myDBOpenHelper: MyDBOpenHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDBOpenHelper = MyDBOpenHelper(this, null)



        val id = intent.getIntExtra("id", 0)
        val name = intent.getStringExtra("name")
        val surName = intent.getStringExtra("surName")
        val phone = intent.getStringExtra("phone")
        val email = intent.getStringExtra("email")

        binding.tvName.setText(name)
        binding.tvSurName.setText(surName)
        binding.editTextPhone.setText(phone)
        binding.editTextTextEmailAddress.setText(email)


        binding.btnSave.setOnClickListener {
            val name = binding.tvName.text.toString()
            val surName = binding.tvSurName.text.toString()
            val phone = binding.editTextPhone.text.toString()
            val email = binding.editTextTextEmailAddress.text.toString()
            val agenda = Agenda(name, surName, phone, email)
            val id = intent.getIntExtra("id", 0)


            if (name.isEmpty() || surName.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_LONG).show()
            } else if (!Patterns.PHONE.matcher(phone).matches()) {
                Toast.makeText(this, "El número de teléfono no es válido", Toast.LENGTH_LONG).show()
            } else if (!isEmailValid(email)) {
                Toast.makeText(this, "La dirección de correo no es válida", Toast.LENGTH_LONG).show()
            }
            else {
                myDBOpenHelper.upDateAgenda(id, agenda)

                Toast.makeText(this, "Actualizado", Toast.LENGTH_LONG).show()
                val myIntent = Intent(this, MainActivity::class.java)
                startActivity(myIntent)

                finish()

            }

        }

    }

    fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailRegex.matches(email)
    }
}