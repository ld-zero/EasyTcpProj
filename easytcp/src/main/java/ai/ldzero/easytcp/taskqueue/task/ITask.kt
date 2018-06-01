package ai.ldzero.easytcp.taskqueue.task

/** interface of task */
internal interface ITask {

    /** the task logic */
    fun run()
}

/** empty implementation of [ITask] */
internal class EmptyTask : ITask {
    override fun run() {}
}