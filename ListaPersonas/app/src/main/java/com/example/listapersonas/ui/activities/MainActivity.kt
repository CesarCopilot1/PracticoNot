package com.example.listapersonas.ui.activities

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listapersonas.R
import com.example.listapersonas.models.Nota
import com.example.listapersonas.ui.adapters.NotaAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), NotaAdapter.OnNotaClickListener {
    private val datalist = arrayListOf(
        Nota("Esta nota es 6", Color.BLUE)
    )
    private lateinit var rvNotaList: RecyclerView
    private lateinit var fabAddNota: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fabAddNota = findViewById(R.id.fabAddContact)
        rvNotaList = findViewById(R.id.rvContactList)
        setupRecyclerView()
        setupEventListeners()
    }

    private fun setupEventListeners() {
        fabAddNota.setOnClickListener {
            buildAlertDialog()
        }
    }

    private fun setupRecyclerView() {
        rvNotaList.adapter = NotaAdapter(datalist, this)
        rvNotaList.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
    }

    private fun buildAlertDialog(nota: Nota? = null) {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Formulario de notao")


        val viewInflated: View = LayoutInflater.from(this)
            .inflate(R.layout.form_nota_layout, null, false)

        val colorNames = listOf("Blue", "White", "Red", "Green", "Yellow", "Cyan", "Magenta", "Gray", "Black", "Orange")
        val colorValues = listOf(
            Color.BLUE,
            Color.WHITE,
            Color.RED,
            Color.GREEN,
            Color.YELLOW,
            Color.CYAN,
            Color.MAGENTA,
            Color.GRAY,
            Color.BLACK,
            Color.parseColor("#FFA500") // Naranja, usando parseColor
        )

        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colorNames) {
            override fun getView(position: Int, convertView: View?, parent: android.view.ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                // Cambiar el color del texto del elemento seleccionado
                view.setTextColor(colorValues[position])
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: android.view.ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                // Cambiar el color del texto de los elementos en el desplegable
                view.setTextColor(colorValues[position])
                return view
            }
        }

        // Especificar el layout a usar cuando se despliega el menú
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val txtNewNotaDescription: EditText = viewInflated.findViewById(R.id.txtDescripcion)
        txtNewNotaDescription.setText(nota?.descripcion)

        val spinnerNewColor : Spinner = viewInflated.findViewById(R.id.spinnerColor)
        spinnerNewColor.adapter = adapter
        var selectedColorValue: Int = Color.WHITE
        spinnerNewColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Guardar el valor del color seleccionado en la variable
                selectedColorValue = colorValues[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Acciones si no se selecciona nada
            }
        }
        builder.setView(viewInflated)

        builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            dialog.dismiss()
            val descripcion = txtNewNotaDescription.text.toString()



            if (nota != null) {
                nota.descripcion = descripcion
                nota.fondo = selectedColorValue

                editNotaFromList(nota)
            } else {
                addNotaToList(descripcion, selectedColorValue)
            }
        }
        builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun editNotaFromList(nota: Nota) {
        val adapter = rvNotaList.adapter as NotaAdapter
      adapter.itemUpdated(nota)
    }

    private fun addNotaToList(descripcion: String, color : Int) {
        val nota = Nota(descripcion, color)
        val adapter = rvNotaList.adapter as NotaAdapter
        adapter.itemAdded(nota)
    }

    override fun onNotaEditClickListener(nota: Nota) {
        buildAlertDialog(nota)
    }

    override fun onNotaDeleteClickListener(nota: Nota) {
        val adapter = rvNotaList.adapter as NotaAdapter
        adapter.itemDeleted(nota)
    }

}