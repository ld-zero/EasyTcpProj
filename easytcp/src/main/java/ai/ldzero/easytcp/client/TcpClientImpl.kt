package ai.ldzero.easytcp.client

import ai.ldzero.easytcp.AppExecutors
import ai.ldzero.easytcp.TcpClientHolder
import ai.ldzero.easytcp.entity.TcpClientSetting
import ai.ldzero.easytcp.listener.*
import ai.ldzero.easytcp.taskqueue.TaskExecutor
import ai.ldzero.easytcp.taskqueue.task.CloseTask
import ai.ldzero.easytcp.taskqueue.task.ConnectTask
import ai.ldzero.easytcp.taskqueue.task.WriteTask
import java.net.Socket
import java.nio.charset.Charset

/**
 * an implementation of [TcpClient]
 *
 * @constructor build a tcpClient with [SETTING], and setup listeners
 */
internal class TcpClientImpl internal constructor(
    _setting: TcpClientSetting,
    _connListener: ConnListener?,
    _writeListener: WriteListener?,
    _readListener: ReadListener?
) : TcpClient {

    /** id of this client */
    val ID = this.hashCode()

    val socket = Socket()

    val SETTING = _setting

    private val executors = AppExecutors()

    /** a task executor which containing a task queue, it will execute task sequentially */
    private val taskExecutor: TaskExecutor = TaskExecutor(_setting.taskQueueSize)

    private val reader: Reader = Reader(ID, _setting.readBuffSize)

    var connListener: ConnListener = _connListener ?: EmptyConnListener()

    var writeListener: WriteListener = _writeListener ?: EmptyWriteListener()

    var readListener: ReadListener = _readListener ?: EmptyReadListener()

    init {
        TcpClientHolder.addTcpClient(this)
        taskExecutor.startWorking()
    }

    override fun connect() = executors.networkIO.execute({ ConnectTask(ID).run() })

    override fun sequentialConnect() = taskExecutor.addTask(ConnectTask(ID))

    override fun close() = executors.networkIO.execute({ CloseTask(ID).run() })

    override fun sequentialClose() = taskExecutor.addTask(CloseTask(ID))

    override fun destroy() {
        taskExecutor.stopWorking()
        close()
        TcpClientHolder.removeTcpClient(ID)
    }

    override fun write(data: String, charset: Charset) = write(data.toByteArray(charset))

    override fun write(data: ByteArray) = executors.networkIO.execute({ WriteTask(ID, data).run() })

    override fun sequentialWrite(data: String, charset: Charset) =
        sequentialWrite(data.toByteArray(charset))

    override fun sequentialWrite(data: ByteArray) = taskExecutor.addTask(WriteTask(ID, data))

    fun enableReaderIfAuto() {
        if (SETTING.autoEnableReader) {
            enableReader()
        }
    }

    override fun enableReader() = reader.startWorking()

    override fun disableReader() = reader.stopWorking()

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