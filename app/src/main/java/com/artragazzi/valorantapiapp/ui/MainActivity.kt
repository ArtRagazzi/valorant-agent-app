package com.artragazzi.valorantapiapp.ui

import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.artragazzi.valorantapiapp.R
import com.artragazzi.valorantapiapp.databinding.ActivityMainBinding
import com.artragazzi.valorantapiapp.model.Agente
import com.artragazzi.valorantapiapp.service.RetrofitHelper
import com.artragazzi.valorantapiapp.service.ValorantAPI
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val retrofit by lazy {
        RetrofitHelper.retrofit
    }
    private lateinit var meuAgente:Agente;

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
            val nomeAgente = binding.editBuscar.text.toString().replaceFirstChar { char-> char.uppercase() }

            CoroutineScope(Dispatchers.IO).launch {
                if(recuperarAgente(nomeAgente)){
                    withContext(Dispatchers.Main) {
                        val slideIn =
                            AnimationUtils.loadAnimation(applicationContext, R.anim.slide_in)
                        binding.layoutInfo.startAnimation(slideIn)
                        binding.layoutInfo.isVisible = true

                        Glide.with(applicationContext)
                            .load(meuAgente.imgAgente)
                            .apply(RequestOptions.circleCropTransform())
                            .into(binding.imgAgent)

                        binding.nomeAgent.setText(meuAgente.nome)
                        binding.classeAgent.setText(meuAgente.classe)
                        binding.txtDescricao.setText(meuAgente.descricao)

                        Picasso.get().load(meuAgente.habilidades!!.get(0)).into(binding.imgHabQ)
                        Picasso.get().load(meuAgente.habilidades!!.get(1)).into(binding.imgHabE)
                        Picasso.get().load(meuAgente.habilidades!!.get(2)).into(binding.imgHabC)
                        Picasso.get().load(meuAgente.habilidades!!.get(3)).into(binding.imgHabX)
                        Picasso.get().load(meuAgente.imgClasse).into(binding.imgClasse)
                    }
                }else{
                    withContext(Dispatchers.Main) {
                        Toast.makeText(applicationContext, "Agente não Encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        binding.btnRecuar.setOnClickListener {
            val slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out)
            binding.layoutInfo.startAnimation(slideOut)
            binding.layoutInfo.isVisible = false
        }


    }

    private suspend fun recuperarAgente(nomeAgente:String):Boolean {
        var response: Response<Any>? = null
        try {
            val service = retrofit.create(ValorantAPI::class.java)
            response = service.getAllAgents()
        }catch (e:Exception){
            Log.i("info-rest", "${e.printStackTrace()}")
        }
        if(response != null && response.isSuccessful){
            //Gera um map String E valor
            val responseBody = response.body() as Map<String,Any>
            // Pega a chave Data (Dentro de Data agora ta todos agentes Numa lista, e essa lista é um map dos atributos e seus valores)
            val agentes = responseBody["data"] as List<Map<String, String>>

            for (agente in agentes) {
                if(agente["displayName"] == nomeAgente.trim() && agente["isPlayableCharacter"].toString() == "true"){
                    val classe = agente["role"] as Map<String, Any>
                    val classeNome = classe["displayName"]
                    val imgClasse = classe["displayIcon"]

                    var listHabilidades = mutableListOf<String>()
                    val habilidades = agente["abilities"] as List<Map<String, String>>
                    for(habilidade in habilidades){
                        listHabilidades.add(habilidade["displayIcon"].toString())
                    }
                    meuAgente = Agente(
                        nome = agente["displayName"],
                        descricao = agente["description"],
                        classe = classeNome.toString(),
                        habilidades = listHabilidades,
                        imgClasse = imgClasse.toString(),
                        imgAgente = agente["displayIcon"].toString()
                    )
                    return true
                }
            }
        }
        return false
    }



}