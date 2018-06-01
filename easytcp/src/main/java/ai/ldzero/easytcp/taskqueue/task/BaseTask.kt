package ai.ldzero.easytcp.taskqueue.task

import ai.ldzero.easytcp.TcpClientHolder
import ai.ldzero.easytcp.client.TcpClientImpl

/** base implementation of [ITask] */
internal abstract class BaseTask(private val CLIENT_ID: Int) : ITask {

    /** get tcpClient from [TcpClientHolder] by id of client */
    fun getClient(): TcpClientImpl? = TcpClientHolder.getTcpClient(CLIENT_ID)
}