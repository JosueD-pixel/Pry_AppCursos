package com.example.pry_appcursos.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pry_appcursos.Modelo.Cls_ImagenCurso
import com.example.pry_appcursos.Modelo.RetrofitCliente
import com.example.pry_appcursos.R

class Cls_GaleriaAdaptador(
    private var listaImagenes: List<Cls_ImagenCurso>
) : RecyclerView.Adapter<Cls_GaleriaAdaptador.GaleriaViewHolder>() {

    class GaleriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgGaleria: ImageView = itemView.findViewById(R.id.imgGaleria)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GaleriaViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_galeria, parent, false)
        return GaleriaViewHolder(vista)
    }

    override fun onBindViewHolder(holder: GaleriaViewHolder, position: Int) {
        val imagen = listaImagenes[position]
        //La BD guarda la ruta relativa: upload/cursos/galeria/...
        Glide.with(holder.itemView.context)
            .load(RetrofitCliente.BASE_URL + imagen.url_imagen)
            .placeholder(R.drawable.bg_imagen_curso)
            .error(R.drawable.bg_imagen_curso)
            .centerCrop()
            .into(holder.imgGaleria)
    }

    override fun getItemCount(): Int = listaImagenes.size

    fun actualizarLista(nuevaLista: List<Cls_ImagenCurso>) {
        listaImagenes = nuevaLista
        notifyDataSetChanged()
    }
}
