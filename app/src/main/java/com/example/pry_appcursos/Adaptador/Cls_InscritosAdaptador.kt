package com.example.pry_appcursos.Adaptador

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pry_appcursos.Modelo.Cls_CursoInscritos
import com.example.pry_appcursos.Modelo.RetrofitCliente
import com.example.pry_appcursos.R

class Cls_InscritosAdaptador(
    private var listaCursos: List<Cls_CursoInscritos>
) : RecyclerView.Adapter<Cls_InscritosAdaptador.InscritosViewHolder>() {

    class InscritosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCurso: ImageView = itemView.findViewById(R.id.imgCursoInscritos)
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombreCursoInscritos)
        val pbInscritos: ProgressBar = itemView.findViewById(R.id.pbInscritos)
        val txtInscritos: TextView = itemView.findViewById(R.id.txtInscritos)
        val txtCupo: TextView = itemView.findViewById(R.id.txtCupo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InscritosViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_curso_inscritos, parent, false)
        return InscritosViewHolder(vista)
    }

    override fun onBindViewHolder(holder: InscritosViewHolder, position: Int) {
        val curso = listaCursos[position]
        val contexto = holder.itemView.context

        holder.txtNombre.text = curso.nombre_curso
        holder.txtInscritos.text = "${curso.inscritos} inscritos"
        holder.txtCupo.text = "${curso.cupo} total"

        //Barra de inscritos: verde si ya se lleno el cupo
        holder.pbInscritos.max = curso.cupo
        holder.pbInscritos.progress = curso.inscritos
        val color = if (curso.inscritos >= curso.cupo) R.color.verdeRegistro else R.color.azulPrincipal
        holder.pbInscritos.progressTintList = ColorStateList.valueOf(contexto.getColor(color))

        //La BD guarda la ruta relativa: upload/cursos/principales/...
        Glide.with(contexto)
            .load(RetrofitCliente.BASE_URL + curso.url_imagen_principal)
            .placeholder(R.drawable.bg_imagen_curso)
            .error(R.drawable.bg_imagen_curso)
            .centerCrop()
            .into(holder.imgCurso)
    }

    override fun getItemCount(): Int = listaCursos.size

    fun actualizarLista(nuevaLista: List<Cls_CursoInscritos>) {
        listaCursos = nuevaLista
        notifyDataSetChanged()
    }
}