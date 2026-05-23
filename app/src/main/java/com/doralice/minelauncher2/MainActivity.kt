package com.doralice.minelauncher2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputNome = findViewById<EditText>(R.id.editUsername)
        val botaoEntrar = findViewById<Button>(R.id.btnJogar)

        botaoEntrar.setOnClickListener {
            val nome = inputNome.text.toString()
            if (nome.isEmpty()) {
                Toast.makeText(this, "Digite seu nick primeiro!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Bem-vindo ao Minelauncher2, $nome!", Toast.LENGTH_LONG).show()
            }
        }
    }
}
