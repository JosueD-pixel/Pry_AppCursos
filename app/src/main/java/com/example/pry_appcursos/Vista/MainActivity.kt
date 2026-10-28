package com.example.pry_appcursos.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pry_appcursos.Modelo.Cls_Carreras
import com.example.pry_appcursos.Modelo.Cls_Cursos
import com.example.pry_appcursos.Presentador.Cls_CursosPresentador
import com.example.pry_appcursos.R

class MainActivity : AppCompatActivity() {
    private lateinit var presentador: Cls_CursosPresentador
    private lateinit var spnCarreras: Spinner
    private lateinit var rvCursos: RecyclerView
    private lateinit var txtSinCursos: TextView
    private lateinit var btnVerRegistros: Button
    private lateinit var adaptador: Cls_CursosAdaptador

    private var listaCarreras: List<Cls_Carreras> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Enlazar componentes XML
        spnCarreras = findViewById(R.id.spnCarreras)
        rvCursos = findViewById(R.id.rvCursos)
        txtSinCursos = findViewById(R.id.txtSinCursos)
        btnVerRegistros = findViewById(R.id.btnVerRegistros)

        //RecyclerView en 2 columnas
        adaptador = Cls_CursosAdaptador(emptyList(), this::verDetalle, this::registrarse)
        rvCursos.layoutManager = GridLayoutManager(this, 2)
        rvCursos.adapter = adaptador

        //Inicializar presentador
        presentador = Cls_CursosPresentador(this)
        btnVerRegistros.setOnClickListener(this::ejecutarVerRegistros)

        spnCarreras.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presentador.cargarCursos(listaCarreras[position].id_carrera)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        presentador.cargarCarreras()
    }

    fun mostrarCarreras(carreras: List<Cls_Carreras>) {
        listaCarreras = carreras
        val nombres = carreras.map { it.nombre_carrera }
        val adaptadorSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombres)
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnCarreras.adapter = adaptadorSpinner
    }

    fun mostrarCursos(cursos: List<Cls_Cursos>) {
        txtSinCursos.visibility = View.GONE
        rvCursos.visibility = View.VISIBLE
        adaptador.actualizarLista(cursos)
    }

    fun mostrarSinCursos() {
        adaptador.actualizarLista(emptyList())
        rvCursos.visibility = View.GONE
        txtSinCursos.visibility = View.VISIBLE
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    //Pendiente: pantalla de Detalle (Christian)
    fun verDetalle(curso: Cls_Cursos) {
        Toast.makeText(this, "Detalle: ${curso.nombre_curso}", Toast.LENGTH_SHORT).show()
    }

    //Abre la pantalla de Registro (Nathan) con el id del curso
    fun registrarse(curso: Cls_Cursos) {
        val intent = Intent(this, RegistroActivity::class.java)
        intent.putExtra("EXTRA_ID_CURSO", curso.id_curso)
        startActivity(intent)
    }

    //Abre la pantalla de Ver registros (Luis)
    fun ejecutarVerRegistros(v: View) {
        startActivity(Intent(this, VerRegistroActivity::class.java))
    }
}