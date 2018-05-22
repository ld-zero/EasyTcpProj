package ai.ldzero.easytcp

import android.os.Handler
import java.util.concurrent.Executor
import java.util.concurrent.Executors

internal class AppExecutors {

    val diskIO: Executor = Executors.newSingleThreadExecutor()

    val networkIO: Executor = Executors.newFixedThreadPool(3)

    val mainThread: Executor = MainThreadExecutor()

    class MainThreadExecutor: Executor {
        private val handler = Handler()

        override fun execute(runnable: Runnable?) {
            handler.post(runnable)
        }
    }
}