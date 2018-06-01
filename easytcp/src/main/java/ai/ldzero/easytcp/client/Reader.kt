package ai.ldzero.easytcp.client

import ai.ldzero.easytcp.TcpClientHolder
import java.io.BufferedInputStream
import java.util.*

/**
 * reader of tcpClient, it inherits from thread,
 * while the thread running, it will continue to receive data from server,
 * blocking if there is no data
 */
internal class Reader(private val CLIENT_ID: Int, private val BUFFER_SIZE: Int) : Thread() {

    /** controlling whether the thread continue to run */
    private var isRunning = false

    /* indicates if the reader has stopped */
    private var hasStop = false

    override fun run() {
        if (BUFFER_SIZE <= 0) throw IllegalArgumentException("buffer size must be greater than 0")
        /** get tcp client from [TcpClientHolder] */
        val client = TcpClientHolder.getTcpClient(CLIENT_ID) ?: return
        val socket = client.socket
        val inputStream = socket.getInputStream()
        val bufferedInputStream = BufferedInputStream(inputStream)
        val buffer = ByteArray(BUFFER_SIZE)
        while (isRunning) {
            try {
                /** read data from inputStream and callback to outside */
                val readSize = bufferedInputStream.read(buffer, 0, BUFFER_SIZE)
                if (readSize != -1) {
                    client.readListener.onRead(Arrays.copyOf(buffer, readSize))
                    Arrays.fill(buffer, 0)
                }
            } catch (e: Exception) {
                /** if exception occurred, stop the reader */
                if (e.message == null || e.message != "Socket closed") {
                    client.readListener.onError(e)
                }
                stopWorking()
            }
        }
    }

    /** setup some flag and start the reading thread */
    fun startWorking() {
        if (isRunning || hasStop) return
        isRunning = true
        start()
    }

    /**
     * stop the reading thread and setup flags,
     * after stopping the reader, it cannot be started again
     */
    fun stopWorking() {
        if (!isRunning || hasStop) return
        isRunning = false
        val client = TcpClientHolder.getTcpClient(CLIENT_ID) ?: return
        val socket = client.socket
        socket.getInputStream().close()
        socket.shutdownInput()
        hasStop = true
    }
}