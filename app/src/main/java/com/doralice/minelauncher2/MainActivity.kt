package com.doralice.minelauncher2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputNome = findViewById<EditText>(R.id.editUsername)
        val menuVersoes = findViewById<Spinner>(R.id.spinnerVersoes)
        val botaoEntrar = findViewById<Button>(R.id.btnJogar)

        // Criamos o gerenciador que busca as versões da internet
        val versionManager = JavaVersionManager()

        // Coloca um aviso temporário enquanto o app carrega da internet
        val listaTemporaria = listOf("Carregando versões...")
        val adapterTemp = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaTemporaria)
        menuVersoes.adapter = adapterTemp

        // Busca as versões oficiais da Mojang
        versionManager.buscarVersoesOficiais { listaDeVersoes ->
            // Como a busca roda em background, usamos runOnUiThread para atualizar a tela com segurança
            runOnUiThread {
                val adapterOficial = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listaDeVersoes)
                menuVersoes.adapter = adapterOficial
            }
        }

        botaoEntrar.setOnClickListener {
            val nome = inputNome.text.toString()
            val versaoSelecionada = menuVersoes.selectedItem.toString()

            if (nome.isEmpty()) {
                Toast.makeText(this, "Digite seu nick primeiro!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Iniciando Java Edition $versaoSelecionada para $nome!", Toast.LENGTH_LONG).show()
            }
        }
    }
}
