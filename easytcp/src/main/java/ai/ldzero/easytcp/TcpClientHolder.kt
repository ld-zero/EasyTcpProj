package ai.ldzero.easytcp

import ai.ldzero.easytcp.client.TcpClientImpl

/**
 * Class Description
 * Created on 2018/5/22.
 *
 * @author ldzero
 */
internal object TcpClientHolder {

    val ID_CLIENT_IMPL_MAP: MutableMap<Int, TcpClientImpl> = mutableMapOf()

    fun addTcpClient(client: TcpClientImpl) {
        ID_CLIENT_IMPL_MAP[client.id] = client
    }

    fun getTcpClient(id: Int): TcpClientImpl? = ID_CLIENT_IMPL_MAP.get(id)

    fun removeTcpClient(id: Int) = ID_CLIENT_IMPL_MAP.remove(id)
}