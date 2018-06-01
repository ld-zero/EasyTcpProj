package ai.ldzero.easytcp

import ai.ldzero.easytcp.client.TcpClientImpl

/** [TcpClientHolder] holds all clients that has not closed or destroyed */
internal object TcpClientHolder {

    /** key is the id of client, value is tcpClientImpl */
    val ID_CLIENT_IMPL_MAP: MutableMap<Int, TcpClientImpl> = mutableMapOf()

    fun addTcpClient(client: TcpClientImpl) {
        ID_CLIENT_IMPL_MAP[client.ID] = client
    }

    fun getTcpClient(id: Int): TcpClientImpl? = ID_CLIENT_IMPL_MAP.get(id)

    fun removeTcpClient(id: Int) = ID_CLIENT_IMPL_MAP.remove(id)
}