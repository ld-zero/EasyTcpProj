package ai.ldzero.easytcp.client

import ai.ldzero.easytcp.entity.TcpClientSetting
import ai.ldzero.easytcp.listener.ConnListener

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

        fun setConnListener(listener: ConnListener?): Builder {
            connListener = listener
            return this
        }

        fun build(): TcpClient {
            return TcpClientImpl(setting, connListener)
        }
    }

    fun resetConnListener(listener: ConnListener?)

    fun connect()

    fun sequentialConnect()

    fun close()

    fun sequentialClose()

    fun destroy()
}