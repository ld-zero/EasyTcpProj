package ai.ldzero.easytcp.listener

/**
 * Class Description
 * Created on 2018/5/22.
 *
 * @author ldzero
 */
interface ConnListener {

    fun onConnect()

    fun onConnFail()

    fun onClose()

}