package ai.ldzero.easytcp.listener

/**
 * Class Description
 * Created on 2018/5/23.
 *
 * @author ldzero
 */
interface WriteListener {
    fun onWriteSuccess(data: ByteArray)

    fun onWriteFail(e: Exception, data: ByteArray)
}

class EmptyWriteListener : WriteListener {
    override fun onWriteSuccess(data: ByteArray) {}

    override fun onWriteFail(e: Exception, data: ByteArray) {}
}