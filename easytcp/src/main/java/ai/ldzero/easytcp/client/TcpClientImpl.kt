package ai.ldzero.easytcp.client

import ai.ldzero.easytcp.AppExecutors
import ai.ldzero.easytcp.TcpClientHolder
import ai.ldzero.easytcp.entity.TcpClientSetting
import ai.ldzero.easytcp.listener.ConnListener
import ai.ldzero.easytcp.listener.empty.EmptyConnListener
import ai.ldzero.easytcp.taskqueue.TaskExecutor
import ai.ldzero.easytcp.taskqueue.task.CloseTask
import ai.ldzero.easytcp.taskqueue.task.ConnectTask
import java.net.Socket

/**
 * Class Description
 * Created on 2018/5/22.
 *
 * @author ldzero
 */
internal class TcpClientImpl internal constructor(
        _setting: TcpClientSetting,
        _connListener: ConnListener?): TcpClient {

    val id = this.hashCode()

    val socket = Socket()

    val setting = _setting

    var connListener: ConnListener = _connListener ?: EmptyConnListener()

    private val executors = AppExecutors()

    private val taskExecutor: TaskExecutor = TaskExecutor(_setting.taskQueueSizse)

    init {
        TcpClientHolder.addTcpClient(this)
    }

    override fun connect() {
        executors.networkIO.execute({
            ConnectTask(id).run()
        })
    }

    override fun sequentialConnect() {
        taskExecutor.addTask(ConnectTask(id))
    }

    override fun resetConnListener(listener: ConnListener?) {
        connListener = listener ?: EmptyConnListener()
    }

    override fun close() {
        executors.networkIO.execute({
            CloseTask(id).run()
        })
    }

    override fun sequentialClose() {
        taskExecutor.addTask(CloseTask(id))
    }

    override fun destroy() {
        taskExecutor.stopWorking()
        close()
        TcpClientHolder.removeTcpClient(id)
    }
}