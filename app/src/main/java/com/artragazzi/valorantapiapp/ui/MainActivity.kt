package com.artragazzi.valorantapiapp.ui

import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
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

        binding.fabBuscar.setOnClickListener {
            //Adicionando animação ao clicar em buscar (Aparecer apenas dps de carregado a chamada API)
            val slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in)
            binding.layoutInfo.startAnimation(slideIn)
            binding.layoutInfo.isVisible = true
        }


        binding.btnRecuar.setOnClickListener {
            val slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out)
            binding.layoutInfo.startAnimation(slideOut)
            binding.layoutInfo.isVisible = false
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