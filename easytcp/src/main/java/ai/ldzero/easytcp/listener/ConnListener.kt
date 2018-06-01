package ai.ldzero.easytcp.listener

/** listener of tcpClient connecting */
interface ConnListener {

    /** client connects successfully */
    fun onConnect()

    /** client failed to connect */
    fun onConnFail(e: Exception)

    /** client is closed */
    fun onClose()
}

/** empty implementation of [ConnListener] */
class EmptyConnListener : ConnListener {
    override fun onConnect() {}

    override fun onConnFail(e: Exception) {}

    override fun onClose() {}
}