package ai.ldzero.easytcp.entity

/**
 * Class Description
 * Created on 2018/5/22.
 *
 * @author ldzero
 */
internal class TcpClientSetting {

    var host: String = ""

    var port: Int = -1

    var connTimeout: Int = -1

    var taskQueueSizse: Int = 100
}