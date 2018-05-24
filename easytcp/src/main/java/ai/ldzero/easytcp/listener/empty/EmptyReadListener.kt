package ai.ldzero.easytcp.listener.empty

import ai.ldzero.easytcp.listener.ReadListener

/**
 * Class Description
 * Created on 2018/5/24.
 *
 * @author ldzero
 */
class EmptyReadListener: ReadListener {
    override fun onRead(data: ByteArray) {

    }

    override fun onError(e: Exception) {

    }
}