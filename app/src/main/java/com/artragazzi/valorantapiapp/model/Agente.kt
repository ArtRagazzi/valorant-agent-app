package com.artragazzi.valorantapiapp.model

import com.google.gson.annotations.SerializedName

data class Agente (

    val nome:String?,
    val descricao:String?="",
    val classe: String? = "",
    val habilidades:List<String>? = mutableListOf<String>(),
    val imgClasse:String ?= "",
    val imgAgente:String? = "",





    )