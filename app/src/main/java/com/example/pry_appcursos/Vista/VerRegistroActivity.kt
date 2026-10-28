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

class VerRegistroActivity : AppCompatActivity() {
    private lateinit var presentador: Cls_VerRegistrosPresentador
    private lateinit var spnCarreras: Spinner
    private lateinit var txtTotal: TextView
    private lateinit var btnAtras: TextView
    private lateinit var btnVolverCatalogo: Button

    private var listaCarreras: List<Cls_Carreras> = emptyList()
    private var idCarreraActual = -1

    //Tarjetas de los cursos (id_curso 1 al 4)
    private val tarjetas = listOf(R.id.cardCurso1, R.id.cardCurso2, R.id.cardCurso3, R.id.cardCurso4)
    private val textosInscritos = listOf(R.id.txtInscritos1, R.id.txtInscritos2, R.id.txtInscritos3, R.id.txtInscritos4)
    private val textosCupo = listOf(R.id.txtCupo1, R.id.txtCupo2, R.id.txtCupo3, R.id.txtCupo4)
    private val barras = listOf(R.id.pbInscritos1, R.id.pbInscritos2, R.id.pbInscritos3, R.id.pbInscritos4)

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
        for (i in tarjetas.indices) {
            val tarjeta = findViewById<View>(tarjetas[i])
            val curso = cursos.find { it.id_curso == i + 1 }

            if (curso == null) {
                //El curso no pertenece a la carrera seleccionada
                tarjeta.visibility = View.GONE
            } else {
                tarjeta.visibility = View.VISIBLE
                findViewById<TextView>(textosInscritos[i]).text = "${curso.inscritos} inscritos"
                findViewById<TextView>(textosCupo[i]).text = "${curso.cupo} total"

                val barra = findViewById<ProgressBar>(barras[i])
                barra.max = curso.cupo
                barra.progress = curso.inscritos
                val color = if (curso.inscritos >= curso.cupo) R.color.verdeRegistro else R.color.azulPrincipal
                barra.progressTintList = ColorStateList.valueOf(getColor(color))
            }
        }
        txtTotal.text = "Total: $total"
    }
    fun ejecutarVolver(v: View) {
        finish()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}