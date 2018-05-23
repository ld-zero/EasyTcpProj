package ai.ldzero.easytcp.taskqueue.task

/**
 * Class Description
 * Created on 2018/5/23.
 *
 * @author ldzero
 */
internal class WriteTask(clientId: Int, val data: ByteArray): BaseTask(clientId) {

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