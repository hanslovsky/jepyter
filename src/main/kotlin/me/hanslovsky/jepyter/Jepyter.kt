package me.hanslovsky.jepyter

import jep.SharedInterpreter
import org.jetbrains.kotlinx.jupyter.api.annotations.JupyterLibrary
import org.jetbrains.kotlinx.jupyter.api.declare
import org.jetbrains.kotlinx.jupyter.api.libraries.JupyterIntegration
import java.io.Closeable
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class Python : Closeable {
    private lateinit var _python: SharedInterpreter
    private val executor = Executors.newSingleThreadExecutor { Thread(it, "CPython").also { it.isDaemon = true } }
    fun exec(code: String) = exec { python.exec(code) }
    operator fun invoke(code: String) = exec(code)
    operator fun get(name: String) = exec { python.getValue(name) }
    operator fun <T> get(name: String, clazz: Class<T>) = exec { python.getValue(name, clazz) }
    operator fun set(name: String, value: Any?) = exec { python.set(name, value) }
    private val python: SharedInterpreter get() {
        if (!::_python.isInitialized) _python  = SharedInterpreter()
        return _python
    }
    fun <T> exec(task: Callable<T>) = executor.submit(task).get()
    override fun close() {
        exec { python.close() }
        executor.shutdown()
    }
}

@JupyterLibrary
class Jepyter : JupyterIntegration() {
    override fun Builder.onLoaded() {
        import("jep.DirectNDArray")

        val python = Python()

        preprocessCode { code ->
            if (code.startsWith("%%python")) { python(code.removePrefix("%%python")); "" }
            else code
        }

        onLoaded {
            declare("python" to python)
        }
    }
}
