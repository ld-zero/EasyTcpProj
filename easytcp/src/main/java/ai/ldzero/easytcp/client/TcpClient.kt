package ai.ldzero.easytcp.client

import ai.ldzero.easytcp.entity.TcpClientSetting
import ai.ldzero.easytcp.listener.ConnListener
import ai.ldzero.easytcp.listener.ReadListener
import ai.ldzero.easytcp.listener.WriteListener
import java.nio.charset.Charset

/**
 * an interface containing exposure to external methods of tcp client,
 * using this client, you can easily use the features commonly used in tcp
 */
interface TcpClient {

    /** connect to server immediately */
    fun connect()

    /** add a connect task to task queue, when it comes to this task it will be executed */
    fun sequentialConnect()

    /** close the tcpClient immediately, and this client cannot be used again */
    fun close()

    /** add a close tcpClient task to task queue, when it comes to this task it will be executed */
    fun sequentialClose()

    /** destroy the tcpClient immediately, used to release the resource of tcpClient */
    fun destroy()

    /**
     * [data] will be encoded into a byte array by [charset],
     * then wrote to server immediately
     */
    fun write(data: String, charset: Charset = Charsets.UTF_8)

    /**
     * write [data] to server
     */
    fun write(data: ByteArray)

    /**
     * add a write data task to task queue,
     * when it comes to this task it will be executed
     * while executing, [data] will be encoded into a byte array by [charset],
     * then wrote to server
     */
    fun sequentialWrite(data: String, charset: Charset = Charsets.UTF_8)

    /**
     * add a write data task to task queue,
     * when it comes to this task it will be executed
     * while executing, [data] will be wrote to server
     */
    fun sequentialWrite(data: ByteArray)

    /** after enabling reader, tcpClient can receive data from server */
    fun enableReader()

    /**
     * after disabling reader, tcpClient will no longer receive data from server,
     * and reader cannot be enabled again after disabled
     */
    fun disableReader()

    fun resetConnListener(listener: ConnListener?)

    fun resetWriteListener(listener: WriteListener?)

    fun resetReadListener(listener: ReadListener?)

    /**
     * a builder of tcpClient which can set several attributes and build a tcpClient,
     * the specific meaning of each attribute can refer to the [TcpClientSetting] description
     */
    object Builder {

        private val setting = TcpClientSetting()

        private var connListener: ConnListener? = null

        private var writeListener: WriteListener? = null

        private var readListener: ReadListener? = null

        fun setHost(host: String): Builder {
            setting.host = host
            return this
        }

        fun setPort(port: Int): Builder {
            setting.port = port
            return this
        }

        fun setConnTimeout(connTimeout: Int): Builder {
            setting.connTimeout = connTimeout
            return this
        }

        fun setTaskQueueSize(taskQueueSize: Int): Builder {
            setting.taskQueueSize = taskQueueSize
            return this
        }

        fun setReadTimeout(readTimeout: Int): Builder {
            setting.readTimeout = readTimeout
            return this
        }

        fun setAutoEnableReader(autoEnableReader: Boolean): Builder {
            setting.autoEnableReader = autoEnableReader
            return this
        }

        fun setReadBuffSize(readBuffSize: Int): Builder {
            setting.readBuffSize = readBuffSize
            return this
        }

        fun setConnListener(listener: ConnListener?): Builder {
            connListener = listener
            return this
        }

        fun setWriteListener(listener: WriteListener?): Builder {
            writeListener = listener
            return this
        }

        fun setReadListener(listener: ReadListener?): Builder {
            readListener = listener
            return this
        }

        fun build(): TcpClient {
            return TcpClientImpl(setting, connListener, writeListener, readListener)
        }
    }
}