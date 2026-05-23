package com.doralice.minelauncher2

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject
import kotlin.concurrent.thread

class JavaVersionManager {

    // Link oficial da Mojang que contém todas as versões do Minecraft existentes
    private val manifestoUrl = "https://mojang.com"

    /**
     * Função que conecta na internet para buscar a lista de versões.
     * Como a internet pode demorar, usamos uma "thread" (um canal separado) para o app não travar.
     */
    fun buscarVersoesOficiais(aoTerminar: (List<String>) -> Unit) {
        thread {
            val listaDeVersoes = mutableListOf<String>()
            try {
                val url = URL(manifestoUrl)
                val conexao = url.openConnection() as HttpURLConnection
                conexao.requestMethod = "GET"

                // Lê o texto que o servidor da Mojang enviou
                val leitor = BufferedReader(InputStreamReader(conexao.inputStream))
                val respostaCompleta = StringBuilder()
                var linha: String?
                while (leitor.readLine().also { linha = it } != null) {
                    respostaCompleta.append(linha)
                }
                leitor.close()

                // Transforma o texto em um objeto JSON para podermos ler as versões
                val jsonPrincipal = JSONObject(respostaCompleta.toString())
                val versoes = jsonPrincipal.getJSONObject("versions")

                // Pega o nome de cada versão (Ex: 1.20, 23w14a, b1.7.3, a1.1.2)
                val chaves = versoes.keys()
                while (chaves.hasNext()) {
                    listaDeVersoes.add(chaves.next())
                }

            } catch (e: Exception) {
                e.printStackTrace()
                // Se der erro (como falta de internet), adiciona uma mensagem de aviso
                listaDeVersoes.add("Erro ao carregar versões da Mojang")
            }

            // Devolve a lista preenchida para a tela do aplicativo
            aoTerminar(listaDeVersoes)
        }
    }
}
