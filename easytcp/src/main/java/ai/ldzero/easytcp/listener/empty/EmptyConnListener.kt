package ai.ldzero.easytcp.listener.empty

import ai.ldzero.easytcp.listener.ConnListener

/**
 * Class Description
 * Created on 2018/5/22.
 *
 * @author ldzero
 */
class EmptyConnListener: ConnListener {
    override fun onConnect() {

    }

    override fun onConnFail(e: Exception) {

    }

    override fun onClose() {

    }
}