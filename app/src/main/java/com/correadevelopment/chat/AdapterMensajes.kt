package com.correadevelopment.chat

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterMensajes(c: Context): RecyclerView.Adapter<HolderMensaje>() {

    private var listMensajes: ArrayList<modelMensajeRecibir>? = arrayListOf()
    private var c: Context? = null

    init {
        this.c = c
    }

    fun addMensaje(m:modelMensajeRecibir){
        listMensajes?.add(m)
        notifyItemChanged(listMensajes?.size!!)
    }

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): HolderMensaje {
        var v: View = LayoutInflater.from(c).inflate(R.layout.card_view_mensajes,parent,false)
        return HolderMensaje(v)
    }

    override fun getItemCount(): Int {
        return this.listMensajes?.count()!!
    }

    override fun onBindViewHolder(@NonNull holder: HolderMensaje, position: Int) {
        holder.getNombre()?.text = listMensajes!![position].nombre
        holder.getMensaje()?.text = listMensajes!![position].mensaje
        if (listMensajes!![position].type_mensaje.equals("2")){
            holder.getFotoMensaje()?.visibility = View.VISIBLE
            holder.getMensaje()?.visibility = View.VISIBLE
            Glide.with(c).load(listMensajes!![position].urlFoto).into(holder.getFotoMensaje())
        } else{
            if (listMensajes!![position].type_mensaje.equals("1")){
                holder.getFotoMensaje()?.visibility = View.GONE
                holder.getMensaje()?.visibility = View.VISIBLE
            }
        }
        if(listMensajes!![position].fotoPerfil!!.isEmpty()){
            holder.getFotoMensajePerfil()!!.setImageResource(R.mipmap.ic_launcher)
        }else{
            Glide.with(c).load(listMensajes!![position].fotoPerfil).into(holder.getFotoMensajePerfil())
        }
        var codigoHora: Long? = listMensajes!![position].hora
        var d: Date? = Date(codigoHora!!)
        var sfd: SimpleDateFormat? = SimpleDateFormat("hh:mm:ss a") // a -> pm o am
        holder.getHora()?.text = sfd!!.format(d)
    }

}