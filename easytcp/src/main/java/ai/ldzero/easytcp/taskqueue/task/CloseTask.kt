package ai.ldzero.easytcp.taskqueue.task

/**
 * Class Description
 * Created on 2018/5/22.
 *
 * @author ldzero
 */
internal class CloseTask(clientId: Int): BaseTask(clientId) {

    override fun run() {
        val client = getClient() ?: return
        val socket = client.socket
        try {
            socket.shutdownOutput()
            socket.shutdownInput()
            socket.getOutputStream().close()
            socket.getInputStream().close()
            socket.close()
        } catch (e: Exception) {
            socket.close()
        }
        client.connListener.onClose()
    }
}