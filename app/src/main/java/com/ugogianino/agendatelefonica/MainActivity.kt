package com.ugogianino.agendatelefonica

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ugogianino.agendatelefonica.adapter.MyDBOpenHelper
import com.ugogianino.agendatelefonica.adapter.RecycleviewAdapter
import com.ugogianino.agendatelefonica.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myDBOpenHelper: MyDBOpenHelper
    private lateinit var db: SQLiteDatabase
    private lateinit var recycleviewAdapter: RecycleviewAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDBOpenHelper = MyDBOpenHelper(this, null)
        db = myDBOpenHelper.readableDatabase


        initRecyclerView()


        binding.button.setOnClickListener {

            val intentAddActivity = Intent(this, ContactForm::class.java)
            startActivity(intentAddActivity)
        }

        binding.button2.setOnClickListener {

            val intentAddActivity = Intent(this, FragmentActivity::class.java)
            startActivity(intentAddActivity)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.opcion1 -> {
                Toast.makeText(this, "opcion 1 escogida", Toast.LENGTH_LONG).show()
                true
            }
            R.id.opcion2 -> {
                Toast.makeText(this, "opcion 2 escogida", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }

    private fun initRecyclerView(){

        val cursor : Cursor = db.rawQuery(
            "SELECT * FROM agenda", null)
        val adaptador = RecycleviewAdapter()
        adaptador.RecycleviewAdapter(this, cursor)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adaptador
    }



}