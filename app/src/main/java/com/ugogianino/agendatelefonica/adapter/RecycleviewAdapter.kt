package com.ugogianino.agendatelefonica.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ugogianino.agendatelefonica.R
import com.ugogianino.agendatelefonica.UpdateActivity
import com.ugogianino.agendatelefonica.databinding.ItemAgendaBinding
import com.ugogianino.agendatelefonica.modelo.Agenda


class RecycleviewAdapter
    : RecyclerView.Adapter<RecycleviewAdapter.ViewHolder>()  {


    lateinit var context : Context
    lateinit var cursor : Cursor
    private lateinit var myDBOpenHelper: MyDBOpenHelper



    @SuppressLint("NotConstructor")
    fun RecycleviewAdapter(context: Context, cursor: Cursor){
        this.context = context
        this.cursor = cursor
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val tvName: TextView
        val tvSurName: TextView
        val tvPhone: TextView
        val tvEmail: TextView

        init {
            val bindingItemsRecyclerView = ItemAgendaBinding.bind(view)
            tvName = bindingItemsRecyclerView.tvName
            tvSurName = bindingItemsRecyclerView.tvSurName
            tvPhone = bindingItemsRecyclerView.editTextPhone
            tvEmail = bindingItemsRecyclerView.editTextTextEmailAddress
            myDBOpenHelper = MyDBOpenHelper(view.context, null)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_agenda,
        parent, false))
    }

    override fun getItemCount(): Int {
        if (cursor == null) {
            return 0
        } else {
            return cursor.count
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor.moveToPosition(position)


        holder.tvName.text = cursor.getString(1)
        holder.tvSurName.text = cursor.getString(2)
        holder.tvPhone.text = cursor.getString(3)
        holder.tvEmail.text = cursor.getString(4)

        //Borrar dejando pulsado
        holder.itemView.setOnLongClickListener {
            val id = cursor.getInt(0)

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar contacto")
            builder.setMessage("¿Está seguro que desea eliminar el contacto?")
            builder.setPositiveButton("Sí") { dialog, which ->

                val deletedRows = myDBOpenHelper.delAgenda(id)

                if (deletedRows >= 0) {

                    Toast.makeText(context, "Contacto Eliminado Correctamente", Toast.LENGTH_SHORT).show()

                    /* val snackbar = Snackbar.make(it, "Contacto eliminado", Snackbar.LENGTH_LONG)
                    snackbar.setAction("Deshacer") {
                        val agenda = Agenda(cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4))
                        myDBOpenHelper.upDateAgenda(id,agenda)

                        // Actualizar el cursor y el RecyclerView
                        cursor = myDBOpenHelper.getAllData()
                        notifyDataSetChanged()

                        Toast.makeText(context, "Contacto restaurado", Toast.LENGTH_SHORT).show()
                    }
                    snackbar.show()*/
                } else {
                    Toast.makeText(context, "Error al eliminar el contacto", Toast.LENGTH_SHORT).show()
                }

                cursor = myDBOpenHelper.getAllData()
                notifyDataSetChanged()
            }
            builder.setNegativeButton("No", null)

            val dialog = builder.create()
            dialog.show()

            true
        }

        //lleva la informacion al UpdateActivity
        holder.itemView.setOnClickListener {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val surName = cursor.getString(2)
            val phone = cursor.getString(3)
            val email = cursor.getString(4)

            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("name", name)
            intent.putExtra("surName", surName)
            intent.putExtra("phone", phone)
            intent.putExtra("email", email)
            context.startActivity(intent)
        }



    }


}