package ai.ldzero.easytcp.listener

/** listener of tcpClient reading */
interface ReadListener {

    /** read data from server */
    fun onRead(data: ByteArray)

    /** error occurred */
    fun onError(e: Exception)
}

/** empty implementation of [ReadListener] */
class EmptyReadListener : ReadListener {
    override fun onRead(data: ByteArray) {}

    override fun onError(e: Exception) {}
}