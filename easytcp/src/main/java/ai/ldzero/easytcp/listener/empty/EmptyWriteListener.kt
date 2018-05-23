package ai.ldzero.easytcp.listener.empty

import ai.ldzero.easytcp.listener.WriteListener

/**
 * Class Description
 * Created on 2018/5/23.
 *
 * @author ldzero
 */
class EmptyWriteListener: WriteListener {

    override fun onWriteSuccess(data: ByteArray) {

    }

    override fun onWriteFail(e: Exception, data: ByteArray) {

    }

}