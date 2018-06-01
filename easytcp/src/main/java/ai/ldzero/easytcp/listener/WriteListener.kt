package ai.ldzero.easytcp.listener

/** listener of tcpClient writing */
interface WriteListener {

    /** write data to server successfully */
    fun onWriteSuccess(data: ByteArray)

    /** failed to write data to server */
    fun onWriteFail(e: Exception, data: ByteArray)
}

/** empty implementation of [WriteListener] */
class EmptyWriteListener : WriteListener {
    override fun onWriteSuccess(data: ByteArray) {}

    override fun onWriteFail(e: Exception, data: ByteArray) {}
}