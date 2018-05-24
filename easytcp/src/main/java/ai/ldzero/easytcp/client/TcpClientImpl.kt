package ai.ldzero.easytcp.client

import ai.ldzero.easytcp.AppExecutors
import ai.ldzero.easytcp.TcpClientHolder
import ai.ldzero.easytcp.entity.TcpClientSetting
import ai.ldzero.easytcp.listener.ConnListener
import ai.ldzero.easytcp.listener.ReadListener
import ai.ldzero.easytcp.listener.WriteListener
import ai.ldzero.easytcp.listener.empty.EmptyConnListener
import ai.ldzero.easytcp.listener.empty.EmptyReadListener
import ai.ldzero.easytcp.listener.empty.EmptyWriteListener
import ai.ldzero.easytcp.taskqueue.TaskExecutor
import ai.ldzero.easytcp.taskqueue.task.CloseTask
import ai.ldzero.easytcp.taskqueue.task.ConnectTask
import ai.ldzero.easytcp.taskqueue.task.WriteTask
import java.net.Socket
import java.nio.charset.Charset

/**
 * Class Description
 * Created on 2018/5/22.
 *
 * @author ldzero
 */
internal class TcpClientImpl internal constructor(
        _setting: TcpClientSetting,
        _connListener: ConnListener?,
        _writeListener: WriteListener?,
        _readListener: ReadListener?) : TcpClient {

    val id = this.hashCode()

    val socket = Socket()

    val setting = _setting

    private val executors = AppExecutors()

    private val taskExecutor: TaskExecutor = TaskExecutor(_setting.taskQueueSizse)

    private val reader: Reader = Reader(id, _setting.readBuffSize)

    var connListener: ConnListener = _connListener ?: EmptyConnListener()

    var writeListener: WriteListener = _writeListener ?: EmptyWriteListener()

    var readListener: ReadListener = _readListener ?: EmptyReadListener()

    init {
        TcpClientHolder.addTcpClient(this)
        taskExecutor.startWorking()
    }

    override fun connect() = executors.networkIO.execute({ ConnectTask(id).run() })

    override fun sequentialConnect() = taskExecutor.addTask(ConnectTask(id))

    override fun write(data: String, charset: Charset) = write(data.toByteArray(charset))

    override fun write(data: ByteArray) = executors.networkIO.execute({ WriteTask(id, data).run() })

    override fun sequentialWrite(data: String, charset: Charset) = sequentialWrite(data.toByteArray(charset))

    override fun sequentialWrite(data: ByteArray) = taskExecutor.addTask(WriteTask(id, data))

    fun enableReaderIfAuto() {
        if (setting.autoEnableReader) {
            enableReader()
        }
    }

    override fun enableReader() = reader.startWorking()

    override fun disableReader() = reader.stopWorking()

    override fun close() = executors.networkIO.execute({ CloseTask(id).run() })

    override fun sequentialClose() = taskExecutor.addTask(CloseTask(id))

    override fun destroy() {
        taskExecutor.stopWorking()
        close()
        TcpClientHolder.removeTcpClient(id)
    }

    override fun resetConnListener(listener: ConnListener?) {
        connListener = listener ?: EmptyConnListener()
    }

    override fun resetWriteListener(listener: WriteListener?) {
        writeListener = listener ?: EmptyWriteListener()
    }

    override fun resetReadListener(listener: ReadListener?) {
        readListener = listener ?: EmptyReadListener()
    }
}