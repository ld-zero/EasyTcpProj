package ai.ldzero.easytcp.listener

/**
 * Class Description
 * Created on 2018/5/24.
 *
 * @author ldzero
 */
interface ReadListener {

    fun onRead(data: ByteArray)

    fun onError(e: Exception)

}