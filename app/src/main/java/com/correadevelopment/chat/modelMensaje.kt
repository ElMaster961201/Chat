package com.correadevelopment.chat

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
abstract class modelMensaje(
    open var mensaje:String? = "",
    open var urlFoto:String? = "",
    open var nombre:String? = "",
    open var fotoPerfil:String? = "",
    open var type_mensaje:String? = ""
)
