package com.example.cocoping

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class CocoPingViewModel : ViewModel() {
    var ipOrHostname by mutableStateOf("")
    var pingResults by mutableStateOf(emptyList<String>())

    suspend fun performPing(count: Int, packetSize: Int) {
        try {
            val command = "ping -c $count -s $packetSize $ipOrHostname"
            val process = withContext(Dispatchers.IO) {
                Runtime.getRuntime().exec(command)
            }

            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val errorReader = BufferedReader(InputStreamReader(process.errorStream))
            val stringBuilder = StringBuilder()
            var line: String?

            while (withContext(Dispatchers.IO) { reader.readLine() }.also { line = it } != null) {
                stringBuilder.append(line).append('\n')
            }

            while (withContext(Dispatchers.IO) { errorReader.readLine() }.also { line = it } != null) {
                stringBuilder.append(line).append('\n')
            }

            val exitValue = withContext(Dispatchers.IO) { process.waitFor() }

            val result = if (exitValue == 0) {
                "Ping successful to $ipOrHostname\n$stringBuilder"
            } else {
                "Ping failed for $ipOrHostname\n$stringBuilder"
            }

            pingResults = pingResults + result
        } catch (e: Exception) {
            pingResults = pingResults + "Ping failed for $ipOrHostname: ${e.message}"
        }
    }
}
