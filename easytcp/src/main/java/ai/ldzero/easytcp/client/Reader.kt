package ai.ldzero.easytcp.client

import ai.ldzero.easytcp.TcpClientHolder
import java.io.BufferedInputStream
import java.util.*


/**
 * Class Description
 * Created on 2018/5/23.
 *
 * @author ldzero
 */
internal class Reader(val clientId: Int, val bufferSize: Int): Thread() {

    private var isRunning = false

    private var hasStop = false

    override fun run() {
        if (bufferSize <= 0) throw IllegalArgumentException("buffer size must be greater than 0")
        val client = TcpClientHolder.getTcpClient(clientId) ?: return
        val socket = client.socket
        val inputStream = socket.getInputStream()
        val bufferedInputStream = BufferedInputStream(inputStream)
        val buffer = ByteArray(bufferSize)
        while (isRunning) {
            try {
                val readSize = bufferedInputStream.read(buffer, 0, bufferSize)
                if (readSize != -1) {
                    client.readListener.onRead(Arrays.copyOf(buffer, readSize))
                    Arrays.fill(buffer, 0)
                }
            } catch (e: Exception) {
                if (e.message == null || e.message != "Socket closed") {
                    client.readListener.onError(e)
                }
                stopWorking()
            }
        }
    }

    fun startWorking() {
        if (isRunning || hasStop) return
        isRunning = true
        start()
    }

    fun stopWorking() {
        if (!isRunning || hasStop) return
        isRunning = false
        val client = TcpClientHolder.getTcpClient(clientId) ?: return
        val socket = client.socket
        socket.getInputStream().close()
        socket.shutdownInput()
        hasStop = true
    }
}