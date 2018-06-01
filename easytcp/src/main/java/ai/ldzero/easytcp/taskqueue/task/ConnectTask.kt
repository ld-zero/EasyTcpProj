package ai.ldzero.easytcp.taskqueue.task

import java.net.InetSocketAddress

/** a task which can connect to server */
internal class ConnectTask(CLIENT_ID: Int) : BaseTask(CLIENT_ID) {
    override fun run() {
        val client = getClient() ?: return
        val setting = client.SETTING
        if (setting.host.isEmpty() || setting.port <= 0 || setting.port > 65535) {
            throw IllegalArgumentException("host or port is illegal")
        }
        val address = InetSocketAddress(setting.host, setting.port)
        val socket = client.socket
        if (setting.readTimeout > 0) {
            socket.soTimeout = setting.readTimeout
        }
        try {
            when {
                setting.connTimeout > 0 -> socket.connect(address, setting.connTimeout)
                else -> socket.connect(address)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            client.connListener.onConnFail(e)
            return
        }
        client.connListener.onConnect()
        client.enableReaderIfAuto()
    }
}