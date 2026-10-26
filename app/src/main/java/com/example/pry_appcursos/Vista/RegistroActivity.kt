package com.example.pry_appcursos.Vista

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.savedstate.serialization.encodeToSavedState
import com.example.pry_appcursos.MainActivity
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

    //provisional
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
        //Inicializar presentador
        presentador= clsRegistroPresentador(this)
        btnConfirmar.setOnClickListener (this::ejecutarRegistro)
        btnCancelar.setOnClickListener (this::ejecutarCancelar)
        //Provisional
        idCurso= intent.getIntExtra("EXTRA_ID_CURSO", 3)

        val formato= SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaHoy = formato.format(Date())
        txtFecha.setText(fechaHoy)

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
    fun registroExitoso(mensaje: String){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        finish()
    }
    fun mostrarError(mensaje: String){
        Toast.makeText(this,mensaje, Toast.LENGTH_LONG).show()

    }
}