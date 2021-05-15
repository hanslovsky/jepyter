[![](https://jitpack.io/v/me.hanslovsky/jepyter.svg)](https://jitpack.io/#me.hanslovsky/jepyter)

# Jepyter

[Jep](https://github.com/ninia/jep) integration for the [Kotlin Jupyter kernel](https://github.com/Kotlin/kotlin-jupyter).

## Installation

 1. Install [Jupyter notebook](https://github.com/jupyter/notebook#installation)
 2. Install [Jep](https://github.com/ninia/jep/wiki/Getting-Started#installing-jep) and make sure that the shared library is accessible by the Java process. In my experience, it is sufficient to
    ```shell
    python -m pip install jep
    ```
    install Jep into the `site-packages` of the Python interpreter used for the notebook. Make sure to check out the [Jep faq for troubleshooting](https://github.com/ninia/jep/wiki/FAQ#how-do-i-fix-unsatisfied-link-error-no-jep-in-javalibrarypath) if needed. Note: You can use the [`PYTHONHOME`](https://github.com/ninia/jep/issues/92) environment variable to specify the Python interpreter that Jep is going to use but I have tested only with my system interpreter.
 3. Install the [Kotlin Jupyter kernel](https://github.com/Kotlin/kotlin-jupyter). Note that Jepyter requires the latest master and installation [from sources](https://github.com/Kotlin/kotlin-jupyter#from-sources).


## Usage

Inside a Jupyter cell with Kotlin kernel, use [line magics](https://github.com/Kotlin/kotlin-jupyter#line-magics) to import the Jep library definitions. You can include the library definition as a path to a local file, e.g.

```
%use jep@file[jep.json]
```
if the Jupyter started the parent directory of  `jep.json`, or by specifying a remote url, e.g.
```
%use jep@url[https://raw.githubusercontent.com/hanslovsky/jepyter/main/jep.json]
```
the latest development version on the Jepyter `main` branch.


## Examples

Usage examples are provided in
 - [`jupyter.ipynb`](jupyter.ipynb): General usage example
 - [`jepyter-numpy.ipynb`](jepyter-numpy.ipynb): Usage example with [NumPy](https://numpy.org/). Requires NumPy installation into the interpreter that is used for Jep.
 - [`jepyter-ntakt.ipynb`](jepyter-ntakt.ipynb): Usage example for shared memory between [nta.kt](https://github.com/saalfeldlab/ntakt) and NumPy. Requires NumPy installation into the interpreter that is used for Jep.
