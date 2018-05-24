package ai.ldzero.easytcp.taskqueue.task

import ai.ldzero.easytcp.TcpClientHolder
import java.net.InetSocketAddress

/**
 * Class Description
 * Created on 2018/5/22.
 *
 * @author ldzero
 */
internal class ConnectTask(clientId: Int) : BaseTask(clientId) {

    override fun run() {
        val client = getClient() ?: return
        val setting = client.setting
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