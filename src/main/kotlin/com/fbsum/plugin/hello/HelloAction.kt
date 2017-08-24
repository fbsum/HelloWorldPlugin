package com.fbsum.plugin.hello

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class HelloAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        val s = Messages.showInputDialog(project, "What's your name?", "Hello", Messages.getQuestionIcon())
        Messages.showMessageDialog(project, "Hello $s!", "Welcome", Messages.getInformationIcon())
    }
}
