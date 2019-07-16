package com.gentrio.runscriptplugin.file

import com.gentrio.runscriptplugin.Constants
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiManager

/**
 * 提取JavaFile的导包和代码块
 */
class JavaFileExtract(private val project: Project, val file: VirtualFile?) {

    @Throws(Exception::class)
    fun getScript(): String? {

        var script: String? = null
        if (file != null) {
            val javaPsiFile = PsiManager.getInstance(project).findFile(file) as? PsiJavaFile
            if (javaPsiFile == null) {
                throw Exception("this is not a java file")
            } else {
                script = scriptExtract(javaPsiFile)
            }
        }

        return script
    }

    private fun scriptExtract(javaFile: PsiJavaFile): String? {
        var script: String? = null
        val importState = javaFile.importList?.allImportStatements?.joinToString("\n") {
            it.text
        } ?: ""
        val bodyState = javaFile.classes.filter { psiClass ->
            psiClass.annotations.any {
                Constants.CLASS_ANNOTATION == it.text
            }
        }.joinToString("\n") { psiClass ->
            psiClass.allMethods.filter { psiMethod ->
                psiMethod.annotations.any {
                    Constants.METHOD_ANNOTATION == it.text
                }
            }.map { psiMethod ->
                psiMethod.body?.statements?.joinToString("\n") { psiStatement ->
                    psiStatement.text.replace("@Override", "")
                }
            }.joinToString("\n")
        }

        if (bodyState.isNotEmpty()) {
            script = importState.plus(bodyState)
        }

        return script
    }
}