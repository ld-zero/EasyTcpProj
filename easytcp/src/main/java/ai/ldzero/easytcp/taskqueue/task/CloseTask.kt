package ai.ldzero.easytcp.taskqueue.task

/** a task which can close the socket */
internal class CloseTask(CLIENT_ID: Int) : BaseTask(CLIENT_ID) {
    override fun run() {
        val client = getClient() ?: return
        val socket = client.socket
        try {
            client.disableReader()
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