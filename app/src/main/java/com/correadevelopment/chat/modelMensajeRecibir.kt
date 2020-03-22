package com.correadevelopment.chat

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class modelMensajeRecibir(
    override var mensaje:String? = "",
    override var urlFoto:String? = "",
    override var nombre:String? = "",
    override var fotoPerfil:String? = "",
    override var type_mensaje:String? = "",
    var hora: Long? = null
): modelMensaje()