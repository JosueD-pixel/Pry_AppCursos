package com.example.pry_appcursos.Vista

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pry_appcursos.Modelo.Cls_Cursos
import com.example.pry_appcursos.Modelo.RetrofitCliente
import com.example.pry_appcursos.R

class Cls_CursosAdaptador(
    private var listaCursos: List<Cls_Cursos>,
    private val onVerDetalle: (Cls_Cursos) -> Unit,
    private val onRegistrarse: (Cls_Cursos) -> Unit
) : RecyclerView.Adapter<Cls_CursosAdaptador.CursoViewHolder>() {

    class CursoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCurso: ImageView = itemView.findViewById(R.id.imgCurso)
        val txtNombreCurso: TextView = itemView.findViewById(R.id.txtNombreCurso)
        val txtEspacio: TextView = itemView.findViewById(R.id.txtEspacio)
        val btnVerDetalle: Button = itemView.findViewById(R.id.btnVerDetalle)
        val btnRegistrarse: Button = itemView.findViewById(R.id.btnRegistrarse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursoViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_curso, parent, false)
        return CursoViewHolder(vista)
    }

    override fun onBindViewHolder(holder: CursoViewHolder, position: Int) {
        val curso = listaCursos[position]
        holder.txtNombreCurso.text = curso.nombre_curso
        holder.txtEspacio.text = curso.espacio

        //La BD guarda la ruta relativa: upload/cursos/principales/...
        Glide.with(holder.itemView.context)
            .load(RetrofitCliente.BASE_URL + curso.url_imagen_principal)
            .placeholder(R.drawable.bg_imagen_curso)
            .error(R.drawable.bg_imagen_curso)
            .centerCrop()
            .into(holder.imgCurso)

        holder.btnVerDetalle.setOnClickListener { onVerDetalle(curso) }
        holder.btnRegistrarse.setOnClickListener { onRegistrarse(curso) }
    }

    override fun getItemCount(): Int = listaCursos.size

    fun actualizarLista(nuevaLista: List<Cls_Cursos>) {
        listaCursos = nuevaLista
        notifyDataSetChanged()
    }
}