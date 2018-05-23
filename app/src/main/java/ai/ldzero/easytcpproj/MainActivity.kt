package ai.ldzero.easytcpproj

import ai.ldzero.easytcp.client.TcpClient
import ai.ldzero.easytcp.listener.ConnListener
import ai.ldzero.easytcp.listener.WriteListener
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val tcpClient = TcpClient.Builder
                .setHost("host")
                .setPort(50001)
                .setConnListener(object : ConnListener {
                    override fun onConnect() {
                        Log.d("tag", "onConnect")
                    }

                    override fun onConnFail(e: Exception) {
                        Log.d("tag", "onConnFail")
                    }

                    override fun onClose() {
                        Log.d("tag", "onClose")
                    }
                })
                .setWriteListener(object : WriteListener {
                    override fun onWriteSuccess(data: ByteArray) {
                        Log.d("tag", "onWriteSuccess " + String(data))
                    }

                    override fun onWriteFail(e: Exception, data: ByteArray) {
                        e.printStackTrace()
                        Log.d("tag", "onWriteFail " + String(data))
                    }
                })
                .build()

        btnConnect.setOnClickListener({
            tcpClient.connect()
        })

        btnWrite.setOnClickListener({
            tcpClient.write("123")
        })

        btnClose.setOnClickListener({
            tcpClient.close()
        })

        btnDestroy.setOnClickListener({
            tcpClient.destroy()
        })
    }
}
