package com.fbsum.plugin.hello

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.ui.Messages

class HelloAction : AnAction() {

    private val log = Logger.getInstance(HelloAction::class.java)

    override fun actionPerformed(e: AnActionEvent) {

        log.info("actionPerformed()")
        log.warn("actionPerformed()")
        log.debug("actionPerformed()")
        log.error("actionPerformed()")

        val project = e.project
        val s = Messages.showInputDialog(project, "What's your name?", "Hello", Messages.getQuestionIcon())
        Messages.showMessageDialog(project, "Hello $s!", "Welcome", Messages.getInformationIcon())
    }
}
