package com.correadevelopment.chat

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView

class HolderMensaje(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    private var nombre: TextView? = null
    private var mensaje: TextView? = null
    private var hora: TextView? = null
    private var fotoMensajePerfil: CircleImageView? = null
    private var fotoMensaje: ImageView? = null

    init {
        nombre = itemView?.findViewById(R.id.nombreMensaje)
        mensaje = itemView?.findViewById(R.id.mensajeMensaje)
        hora = itemView?.findViewById(R.id.horaMensaje)
        fotoMensajePerfil = itemView?.findViewById(R.id.fotoPerfilMensaje)
        fotoMensaje = itemView?.findViewById(R.id.mensajeFoto)
    }

    fun getNombre(): TextView? {
        return this.nombre
    }

    fun setNombre(nombre: TextView){
        this.nombre = nombre
    }

    fun getMensaje(): TextView? {
        return this.mensaje
    }

    fun setMensaje(mensaje: TextView){
        this.mensaje = mensaje
    }

    fun getHora(): TextView? {
        return this.hora
    }

    fun setHora(hora: TextView){
        this.hora = hora
    }

    fun getFotoMensajePerfil(): CircleImageView? {
        return this.fotoMensajePerfil
    }

    fun setFotoMansajePerfil(fotoMensajePerfil: CircleImageView){
        this.fotoMensajePerfil = fotoMensajePerfil
    }

    fun getFotoMensaje(): ImageView? {
        return this.fotoMensaje
    }

    fun setFotoMansaje(fotoMensaje: ImageView){
        this.fotoMensaje = fotoMensaje
    }

}