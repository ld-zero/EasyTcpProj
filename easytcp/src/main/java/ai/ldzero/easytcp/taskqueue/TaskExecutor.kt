package ai.ldzero.easytcp.taskqueue

import ai.ldzero.easytcp.taskqueue.task.EmptyTask
import ai.ldzero.easytcp.taskqueue.task.ITask
import java.util.concurrent.ArrayBlockingQueue
/**
 * Class Description
 * Created on 2018/5/22.
 *
 * @author ldzero
 */
internal class TaskExecutor(taskCount: Int) : Thread() {

    private val taskQueue: ArrayBlockingQueue<ITask>

    private var isRunning = false

    init {
        if (taskCount <= 0) {
            throw IllegalArgumentException("size of task queue must be greater than 0")
        }
        taskQueue = ArrayBlockingQueue(taskCount)
    }

    override fun run() {
        while (isRunning) {
            val task = taskQueue.take()
            task.run()
        }
    }

    fun addTask(task: ITask) {
        taskQueue.offer(task)
    }

    fun blockingAddTask(task: ITask) = taskQueue.put(task)

    fun startWorking() {
        isRunning = true
        start()
    }

    fun stopWorking() {
        if (!isRunning) return
        taskQueue.clear()
        isRunning = false
        addTask(EmptyTask())
    }
}

