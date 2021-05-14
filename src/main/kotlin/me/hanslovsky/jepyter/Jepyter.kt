package me.hanslovsky.jepyter

import jep.SharedInterpreter
import org.jetbrains.kotlinx.jupyter.api.VariableDeclaration
import org.jetbrains.kotlinx.jupyter.api.annotations.JupyterLibrary
import org.jetbrains.kotlinx.jupyter.api.declare
import org.jetbrains.kotlinx.jupyter.api.libraries.JupyterIntegration
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.reflect.typeOf

interface Python {
    fun exec(code: String)
    operator fun invoke(code: String) = exec(code)
    operator fun get(name: String): Any?
    operator fun <T> get(name: String, clazz: Class<T>): T?
    operator fun set(name: String, value: Any?)
    fun <T> exec(task: Callable<T>): T
}

// TODO make this private
public class PythonImpl : Python {
    private lateinit var _python: SharedInterpreter
    private val executor = Executors.newSingleThreadExecutor { Thread(it, "CPython").also { it.isDaemon = true } }
    override fun exec(code: String) = exec { python.exec(code) }
    override operator fun get(name: String) = exec { python.getValue(name) }
    override operator fun <T> get(name: String, clazz: Class<T>) = exec { python.getValue(name, clazz) }
    override operator fun set(name: String, value: Any?) = exec { python.set(name, value) }
    private val python: SharedInterpreter get() {
        if (!::_python.isInitialized) _python  = SharedInterpreter()
        return _python
    }
    override fun <T> exec(task: Callable<T>) = executor.submit(task).get()
}

@JupyterLibrary
class Jepyter : JupyterIntegration() {
    override fun Builder.onLoaded() {
        import("jep.DirectNDArray")

        val python: Python = PythonImpl()

        preprocessCode { code ->
            if (code.startsWith("%%python")) { python(code.removePrefix("%%python")); "" }
            else code
        }

        onLoaded {
            declare("python" to python)
        }
    }
}
