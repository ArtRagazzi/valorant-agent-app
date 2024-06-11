package com.artragazzi.valorantapiapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.artragazzi.valorantapiapp.R
import com.artragazzi.valorantapiapp.databinding.ActivityMainBinding
import com.artragazzi.valorantapiapp.model.Agente
import com.artragazzi.valorantapiapp.service.RetrofitHelper
import com.artragazzi.valorantapiapp.service.RetrofitService
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val retrofit by lazy{
        RetrofitHelper.retrofit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }





    }
    private suspend fun recuperarAgente(nome:String){

        var retorno: Response<Agente>? = null

        try {
            val servico = retrofit.create(RetrofitService::class.java)
            retorno = servico.getAgent(nome)
        }catch (e: Exception){
            Log.i("info_rest", "${e.printStackTrace()}")
        }
        if(retorno!=null && retorno.isSuccessful){
            val agente = retorno.body()
        }


    }
}