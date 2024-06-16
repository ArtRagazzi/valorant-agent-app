package com.artragazzi.valorantapiapp.model

import com.google.gson.annotations.SerializedName

data class Agente (
    @SerializedName("title")
    val nome:String="",
    @SerializedName("description")
    val descricao:String="",
    @SerializedName("role")
    val classes: String = "",
    val imgClasse:String = "",
    val imgAgente:String = "",


)