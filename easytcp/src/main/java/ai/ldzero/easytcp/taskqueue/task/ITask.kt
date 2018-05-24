package ai.ldzero.easytcp.taskqueue.task

/**
 * Class Description
 * Created on 2018/5/22.
 *
 * @author ldzero
 */
internal interface ITask {
    fun run()
}

internal class EmptyTask : ITask {
    override fun run() {}
}