package com.example.pry_appcursos.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pry_appcursos.Adaptador.Cls_GaleriaAdaptador
import com.example.pry_appcursos.Modelo.Cls_DetalleCurso
import com.example.pry_appcursos.Modelo.RetrofitCliente
import com.example.pry_appcursos.Presentador.clsDetallePresentador
import com.example.pry_appcursos.R

class CursosDetalle : AppCompatActivity() {
    private lateinit var presentador: clsDetallePresentador
    private lateinit var btnAtras: TextView
    private lateinit var imgDetalle: ImageView
    private lateinit var txtNombre: TextView
    private lateinit var txtCodigo: TextView
    private lateinit var txtDescripcion: TextView
    private lateinit var txtEspacio: TextView
    private lateinit var txtHoras: TextView
    private lateinit var txtDias: TextView
    private lateinit var txtCupo: TextView
    private lateinit var lblGaleria: TextView
    private lateinit var rvGaleria: RecyclerView
    private lateinit var adaptadorGaleria: Cls_GaleriaAdaptador

    private var idCurso = 0
    //Curso cargado, para mandarlo a la pantalla de registro
    private var cursoActual: Cls_DetalleCurso? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cursos_detalle)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Enlazar componentes XML
        btnAtras = findViewById(R.id.btnAtras)
        imgDetalle = findViewById(R.id.imgDetalle)
        txtNombre = findViewById(R.id.txtNombreDetalle)
        txtCodigo = findViewById(R.id.txtCodigoDetalle)
        txtDescripcion = findViewById(R.id.txtDescripcionDetalle)
        txtEspacio = findViewById(R.id.txtEspacioDetalle)
        txtHoras = findViewById(R.id.txtHorasDetalle)
        txtDias = findViewById(R.id.txtDiasDetalle)
        txtCupo = findViewById(R.id.txtCupoDetalle)
        lblGaleria = findViewById(R.id.lblGaleria)
        rvGaleria = findViewById(R.id.rvGaleria)

        //Galeria horizontal
        adaptadorGaleria = Cls_GaleriaAdaptador(emptyList())
        rvGaleria.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvGaleria.adapter = adaptadorGaleria

        btnAtras.setOnClickListener(this::ejecutarVolver)

        //Id del curso que manda el catalogo
        idCurso = intent.getIntExtra("EXTRA_ID_CURSO", 0)

        presentador = clsDetallePresentador(this)
        presentador.cargarDetalle(idCurso)
    }

    fun mostrarDetalle(curso: Cls_DetalleCurso) {
        cursoActual = curso
        txtNombre.text = curso.nombre_curso
        txtCodigo.text = "Código: ${curso.codigo_curso}"
        txtDescripcion.text = curso.descripcion
        txtEspacio.text = curso.espacio
        txtHoras.text = "${curso.total_horas} horas"
        txtDias.text = "${curso.dias_atencion}\n${curso.horario}"
        txtCupo.text = "${curso.cupo} lugares"

        Glide.with(this)
            .load(RetrofitCliente.BASE_URL + curso.url_imagen_principal)
            .placeholder(R.drawable.bg_imagen_curso)
            .error(R.drawable.bg_imagen_curso)
            .centerCrop()
            .into(imgDetalle)

        //Si el curso no tiene galeria, se oculta la seccion
        if (curso.imagenes.isEmpty()) {
            lblGaleria.visibility = View.GONE
            rvGaleria.visibility = View.GONE
        } else {
            lblGaleria.visibility = View.VISIBLE
            rvGaleria.visibility = View.VISIBLE
            adaptadorGaleria.actualizarLista(curso.imagenes)
        }
    }

    fun ejecutarVolver(v: View) {
        finish()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}
