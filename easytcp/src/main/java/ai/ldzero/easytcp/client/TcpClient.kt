package ai.ldzero.easytcp.client

import ai.ldzero.easytcp.entity.TcpClientSetting
import ai.ldzero.easytcp.listener.ConnListener
import ai.ldzero.easytcp.listener.ReadListener
import ai.ldzero.easytcp.listener.WriteListener
import java.nio.charset.Charset

/**
 * Class Description
 * Created on 2018/5/22.
 *
 * @author ldzero
 */
interface TcpClient {

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

        fun setTaskQueueSize(taskQueueSize: Int): Builder {
            setting.taskQueueSizse = taskQueueSize
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

    fun connect()

    fun sequentialConnect()

    fun write(data: String, charset: Charset = Charsets.UTF_8)

    fun write(data: ByteArray)

    fun sequentialWrite(data: String, charset: Charset = Charsets.UTF_8)

    fun sequentialWrite(data: ByteArray)

    fun enableReader()

    fun disableReader()

    fun close()

    fun sequentialClose()

    fun destroy()

    fun resetConnListener(listener: ConnListener?)

    fun resetWriteListener(listener: WriteListener?)

    fun resetReadListener(listener: ReadListener?)
}