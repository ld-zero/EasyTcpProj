package ai.ldzero.easytcp.taskqueue.task

/** a task which can write data to server */
internal class WriteTask(CLIENT_ID: Int, val data: ByteArray) : BaseTask(CLIENT_ID) {
    override fun run() {
        val client = getClient() ?: return
        try {
            val outputStream = client.socket.getOutputStream()
            outputStream.write(data)
            outputStream.flush()
            client.writeListener.onWriteSuccess(data)
        } catch (e: Exception) {
            e.printStackTrace()
            client.writeListener.onWriteFail(e, data)
        }
    }
}