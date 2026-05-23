package com.doralice.minelauncher2

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipFile
import kotlin.concurrent.thread

class BedrockImportManager(private val context: Context) {

    /**
     * Função que pega o APK selecionado pelo usuário e extrai a biblioteca "libminecraftpe.so"
     * que é o verdadeiro coração do Minecraft Bedrock no Android.
     */
    fun importarApkBedrock(caminhoDoApk: String, aoTerminar: (Boolean, String) -> Unit) {
        thread {
            try {
                val arquivoApk = File(caminhoDoApk)
                if (!arquivoApk.exists()) {
                    aoTerminar(false, "Arquivo APK não foi encontrado!")
                    return@thread
                }

                // Como o APK é um arquivo compactado (tipo .zip), nós abrimos ele
                val zip = ZipFile(arquivoApk)
                
                // Procuramos pela biblioteca do Bedrock específica para celulares de 32-bits (armeabi-v7a)
                val caminhoInternoLib = "lib/armeabi-v7a/libminecraftpe.so"
                val entradaZip = zip.getEntry(caminhoInternoLib)

                if (entradaZip == null) {
                    aoTerminar(false, "Este APK não serve! Ele não tem a versão para armeabi-v7a.")
                    zip.close()
                    return@thread
                }

                // Criamos uma pasta segura dentro do nosso launcher para guardar essa biblioteca
                val pastaDestino = File(context.filesDir, "bedrock_core")
                if (!pastaDestino.exists()) { pastaDestino.mkdirs() }
                
                val arquivoInjetável = File(pastaDestino, "libminecraftpe.so")

                // Copiamos o cérebro do Bedrock de dentro do APK para a nossa pasta segura
                zip.getInputStream(entradaZip).use { entrada ->
                    FileOutputStream(arquivoInjetável).use { saida ->
                        entrada.copyTo(saida)
                    }
                }

                zip.close()
                // Se tudo deu certo, avisamos que a extração funcionou e enviamos o caminho do arquivo
                aoTerminar(true, arquivoInjetável.absolutePath)

            } catch (e: Exception) {
                e.printStackTrace()
                aoTerminar(false, "Erro ao extrair o APK: ${e.message}")
            }
        }
    }
}
