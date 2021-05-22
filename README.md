[![](https://jitpack.io/v/me.hanslovsky/jepyter.svg)](https://jitpack.io/#me.hanslovsky/jepyter)
[![](http://github.com/hanslovsky/jepyter/actions/workflows/build.yaml/badge.svg)](http://github.com/hanslovsky/jepyter/actions/workflows/build.yaml)

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
 - [`jepyter.ipynb`](jepyter.ipynb): General usage example
 - [`jepyter-numpy.ipynb`](jepyter-numpy.ipynb): Usage example with [NumPy](https://numpy.org/). Requires NumPy installation into the interpreter that is used for Jep.
 - [`jepyter-ntakt.ipynb`](jepyter-ntakt.ipynb): Usage example for shared memory between [nta.kt](https://github.com/saalfeldlab/ntakt) and NumPy. Requires NumPy installation into the interpreter that is used for Jep.

## Contribute

Jepyter follows [conventional commits](https://www.conventionalcommits.org/) to auto-generate a meaningful changelog.


### Releases
Jepyter uses [GitHub Actions](https://github.com/features/actions) for CI/CD.
This allows for a stream-lined release process with the `gradle.properties` file as single source of truth for the release version. Most of the release process is automated:
 1. Create a [release request issue](https://github.com/hanslovsky/jepyter/issues/new?assignees=&labels=release+request&template=request-release.md&title=%5BRELEASE%5D), e.g. [hanslovsky/jepyter#34](https://github.com/hanslovsky/jepyter/issues/34)
 2. The issue triggers a pull request (PR) with two commits, e.g. [hanslovsky/jepyter#35](https://github.com/hanslovsky/jepyter/issues/35), and is closed right after creation:
    1. Set version in `gradle.properties` to non-`SNAPSHOT` (currently, it just removes `-SNAPSHOT` but it should not be too hard to infer new version from commit history or have an optional parameter for the release request issue)
    2. Bump to next development cycle: Increment patch version and add `-SNAPSHOT`.
 3. **Rebase merge the PR into the main branch to trigger release. Automatic releases will not work with any other merge options than rebase merge** (see the following steps for details).
 4. On any push (that includes PR merge) to main branch, a GitHub action checks
    - if the commit message indicates bump to next development cycle, and
    - if the parent commit (`HEAD^`) has a non-`SNAPSHOT` version in `gradle.properties`.
    If both conditions are fulfilled, a release is created for `HEAD^` with the version in `gradle.properties`.

There are two major issues that I see here:
 1. There is no way to restrict the merge option of a PR to only rebase based on the tag or some other information.
    It is thus the responsibility of the maintainer to be diligent and pick the right option
    if the repository allows for other merge options than rebase merge.
 2. How to handle changes to main branch after release request has been created? Probably one of those two options:
    - Close the PR with GitHub actions
    - Re-generate the PR commits from current main on request in a comment in the PR
