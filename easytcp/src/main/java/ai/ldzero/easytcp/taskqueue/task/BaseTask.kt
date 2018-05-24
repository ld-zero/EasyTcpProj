package ai.ldzero.easytcp.taskqueue.task

import ai.ldzero.easytcp.TcpClientHolder
import ai.ldzero.easytcp.client.TcpClientImpl

/**
 * Class Description
 * Created on 2018/5/22.
 *
 * @author ldzero
 */
internal abstract class BaseTask(private val CLIENT_ID: Int) : ITask {
    fun getClient(): TcpClientImpl? = TcpClientHolder.getTcpClient(CLIENT_ID)
}