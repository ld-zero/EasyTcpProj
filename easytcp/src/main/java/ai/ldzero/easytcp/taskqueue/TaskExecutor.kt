package ai.ldzero.easytcp.taskqueue

import ai.ldzero.easytcp.taskqueue.task.EmptyTask
import ai.ldzero.easytcp.taskqueue.task.ITask
import java.util.concurrent.ArrayBlockingQueue

/** [TaskExecutor] contain a task queue, it can implement the sequential execution of tasks */
internal class TaskExecutor(TASK_COUNT: Int) : Thread() {

    /** taks queue */
    private val taskQueue: ArrayBlockingQueue<ITask>

    /** control the running status of [TaskExecutor] */
    private var isRunning = false

    init {
        if (TASK_COUNT <= 0) throw IllegalArgumentException("size of task queue must be greater than 0")
        taskQueue = ArrayBlockingQueue(TASK_COUNT)
    }

    override fun run() {
        while (isRunning) {
            val task = taskQueue.take()
            task.run()
        }
    }

    /** add a task to task queue */
    fun addTask(task: ITask) {
        taskQueue.offer(task)
    }

    /** add a task to task queue, it will block until successfully add */
    fun blockingAddTask(task: ITask) {
        taskQueue.put(task)
    }

    /** start the executing thread */
    fun startWorking() {
        isRunning = true
        start()
    }

    /** stop the executing thread */
    fun stopWorking() {
        if (!isRunning) return
        taskQueue.clear()
        isRunning = false
        addTask(EmptyTask())
    }
}

