package com.example.pry_appcursos.Vista

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pry_appcursos.Modelo.Cls_Carreras
import com.example.pry_appcursos.Modelo.Cls_CursoInscritos
import com.example.pry_appcursos.Presentador.Cls_VerRegistrosPresentador
import com.example.pry_appcursos.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pry_appcursos.Adaptador.Cls_InscritosAdaptador

class VerRegistroActivity : AppCompatActivity() {
    private lateinit var presentador: Cls_VerRegistrosPresentador
    private lateinit var spnCarreras: Spinner
    private lateinit var txtTotal: TextView
    private lateinit var btnAtras: TextView
    private lateinit var btnVolverCatalogo: Button

    private lateinit var rvInscritos: RecyclerView
    private lateinit var adaptador: Cls_InscritosAdaptador

    private var listaCarreras: List<Cls_Carreras> = emptyList()
    private var idCarreraActual = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ver_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Enlazar componentes XML
        spnCarreras = findViewById(R.id.spnCarreras)
        txtTotal = findViewById(R.id.txtTotal)
        btnAtras = findViewById(R.id.btnAtras)
        btnVolverCatalogo = findViewById(R.id.btnVolverCatalogo)

        rvInscritos = findViewById(R.id.rvInscritos)

        //RecyclerView en una columna
        adaptador = Cls_InscritosAdaptador(emptyList())
        rvInscritos.layoutManager = LinearLayoutManager(this)
        rvInscritos.adapter = adaptador

        //Inicializar presentador
        presentador = Cls_VerRegistrosPresentador(this)
        btnAtras.setOnClickListener(this::ejecutarVolver)
        btnVolverCatalogo.setOnClickListener(this::ejecutarVolver)

        spnCarreras.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                idCarreraActual = listaCarreras[position].id_carrera
                presentador.cargarResumen(idCarreraActual)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        presentador.cargarCarreras()
    }

    override fun onResume() {
        super.onResume()
        //Actualiza los inscritos al regresar a la pantalla
        if (idCarreraActual != -1) {
            presentador.cargarResumen(idCarreraActual)
        }
    }

    fun mostrarCarreras(carreras: List<Cls_Carreras>) {
        listaCarreras = carreras
        val nombres = carreras.map { it.nombre_carrera }
        val adaptadorSpinner = ArrayAdapter(this, R.layout.item_spinner, nombres)
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnCarreras.adapter = adaptadorSpinner
    }

    fun mostrarResumen(cursos: List<Cls_CursoInscritos>, total: Int) {
        adaptador.actualizarLista(cursos)
        txtTotal.text = "Total: $total"
    }
    fun ejecutarVolver(v: View) {
        finish()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}