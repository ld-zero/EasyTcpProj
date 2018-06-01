package ai.ldzero.easytcp.entity

/** a class containing the setting of tcpClient */
internal class TcpClientSetting {

    var host: String = ""

    var port: Int = -1

    /** timeout of connecting, unit is ms */
    var connTimeout: Int = -1

    /** the maximum number of tasks that the queue can hold */
    var taskQueueSize: Int = 100

    /** timeout of socket reading data */
    var readTimeout: Int = 0

    /** control whether the reader starts automatically after the tcpClient connect successfully */
    var autoEnableReader: Boolean = true

    /** buffer size of reader */
    var readBuffSize: Int = 1024
}