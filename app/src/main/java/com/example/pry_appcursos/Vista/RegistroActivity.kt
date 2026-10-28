package com.example.pry_appcursos.Vista

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.pry_appcursos.Modelo.RetrofitCliente
import com.example.pry_appcursos.Presentador.clsRegistroPresentador
import com.example.pry_appcursos.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegistroActivity : AppCompatActivity() {
    private lateinit var presentador: clsRegistroPresentador
    private lateinit var txtMatricula: EditText
    private lateinit var txtFecha: EditText
    private lateinit var txtCorreo: EditText
    private lateinit var btnConfirmar: Button
    private lateinit var btnCancelar: Button
    private lateinit var btnAtras: TextView

    private lateinit var txtCursoRegistro: TextView
    private lateinit var imgCursoRegistro: ImageView

    private var idCurso = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Enlazar componentes XML
        txtMatricula= findViewById(R.id.txtMatricula)
        txtFecha=findViewById(R.id.txtFecha)
        txtCorreo=findViewById(R.id.txtCorreo)
        btnConfirmar= findViewById(R.id.btnConfirmar)
        btnCancelar=findViewById(R.id.btnCancelar)
        txtCursoRegistro=findViewById(R.id.txtCursoRegistro)
        imgCursoRegistro=findViewById(R.id.imgCursoRegistro)
        btnAtras=findViewById(R.id.btnAtras)
        //Inicializar presentador
        presentador= clsRegistroPresentador(this)
        btnConfirmar.setOnClickListener (this::ejecutarRegistro)
        btnCancelar.setOnClickListener (this::ejecutarCancelar)
        btnAtras.setOnClickListener (this::ejecutarCancelar)

        //Datos del curso que manda el catalogo
        idCurso= intent.getIntExtra("EXTRA_ID_CURSO", 0)
        val nombreCurso = intent.getStringExtra("EXTRA_NOMBRE_CURSO") ?: ""
        val imagenCurso = intent.getStringExtra("EXTRA_IMAGEN_CURSO") ?: ""

        txtCursoRegistro.text = nombreCurso
        Glide.with(this)
            .load(RetrofitCliente.BASE_URL + imagenCurso)
            .placeholder(R.drawable.bg_imagen_curso)
            .error(R.drawable.bg_imagen_curso)
            .centerCrop()
            .into(imgCursoRegistro)

        //Fecha de Hoy
        val formato= SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaHoy = formato.format(Date())
        txtFecha.setText(fechaHoy)

        //Al presionar Enter en la matricula, busca el correo
        txtMatricula.setOnEditorActionListener(this::ejecutarBuscarCorreo)

    }
    fun ejecutarRegistro(v: View){
        val matricula = txtMatricula.text.toString().trim()
        val fecha= txtFecha.text.toString().trim()
        val correo= txtCorreo.text.toString().trim()
        presentador.realizarRegistro(idCurso,matricula,correo,fecha)
    }
    fun ejecutarCancelar(v: View){
        finish()
    }
    fun ejecutarBuscarCorreo(v: TextView?, i: Int, event: KeyEvent?): Boolean {
        if (i == EditorInfo.IME_ACTION_DONE) {

            presentador.buscarCorreo(txtMatricula.text.toString().trim())
        }
        return false
    }
    fun mostrarCorreo(correo: String, nombre: String) {
        txtCorreo.setText(correo)
        Toast.makeText(this, "Alumno: $nombre", Toast.LENGTH_LONG).show()
    }
    fun registroExitoso(mensaje: String){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        finish()
    }
    fun mostrarError(mensaje: String){
        Toast.makeText(this,mensaje, Toast.LENGTH_LONG).show()

    }
}