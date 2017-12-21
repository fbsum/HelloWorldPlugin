# Intellij Plugin 开发 —— Hello World

## 配置开发环境
* [获取社区版 IntelliJ IDEA 的源码](http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started/setting_up_environment.html)

* 直接使用已安装的 IntelliJ IDEA／Android Studio 环境

## 新建项目

* 为了方便项目添加第三方依赖，项目采用 gradle 方式构建
* 使用 Kotlin 开发

### 过程：

通过 Gradle 构建项目，添加 Kotlin 支持（如下图）

![](http://7xsi11.com1.z0.glb.clouddn.com/kotlin-dev-plugin-1-001.png)

构建项目后，修改项目 build.gradle 文件，添加插件开发 SDK

```gradle
buildscript {
    ext.kotlin_version = '1.2.0'

    repositories {
        jcenter()
    }
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
}

plugins {
    id "org.jetbrains.intellij" version "0.2.17"
}

apply plugin: 'org.jetbrains.intellij'
apply plugin: 'kotlin'

// 详细配置参数请看：https://github.com/JetBrains/gradle-intellij-plugin#configuration
intellij {
    localPath '/Applications/IntelliJ IDEA CE.app'
    intellij.updateSinceUntilBuild false
}

compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}
compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
}

repositories {
    jcenter()
}

group 'com.fbsum.plugin'
version '0.0.1'

dependencies {
    compile "org.jetbrains.kotlin:kotlin-stdlib-jre8:$kotlin_version"
}
```

查看 External Libraries 确认配置成功

![](http://7xsi11.com1.z0.glb.clouddn.com/kotlin-dev-plugin-1-002.png)

在 src/main/resources/ 目录下添加 META-INF/plugin.xml，修改相关信息（重要，尤其是 id）。

![](http://7xsi11.com1.z0.glb.clouddn.com/kotlin-dev-plugin-1-003.png)

```xml
<idea-plugin>
  <id>com.fbsum.plugin.hello</id>
  <name>HelloWorld Plugin</name>
  <version>0.0.1</version>
  <vendor email="dummy" url="dummy">dummy</vendor>

  <description><![CDATA[
      Sample plugin.<br>
    ]]></description>

  <change-notes><![CDATA[
      Release 0.0.1: Initial release.<br>
    ]]>
  </change-notes>

  <!-- please see http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started/build_number_ranges.html for description -->
  <idea-version since-build="162"/>

  <!-- please see http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started/plugin_compatibility.html
       on how to target different products -->
  <!-- uncomment to enable plugin in all products
  <depends>com.intellij.modules.lang</depends>
  -->

  <extensions defaultExtensionNs="com.intellij">
  </extensions>

  <actions>

  </actions>

</idea-plugin>
```

创建 Action

![](http://7xsi11.com1.z0.glb.clouddn.com/kotlin-dev-plugin-1-010.png)

![](http://7xsi11.com1.z0.glb.clouddn.com/kotlin-dev-plugin-1-005.png)

将生成的 Action 转成 Kotlin 文件

![](http://7xsi11.com1.z0.glb.clouddn.com/kotlin-dev-plugin-1-006.png)

```kotlin
class HelloAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        val s = Messages.showInputDialog(project, "What's your name?", "Hello", Messages.getQuestionIcon())
        Messages.showMessageDialog(project, "Hello $s!", "Welcome", Messages.getInformationIcon())
    }
}
```

## 运行项目
运行 runIdea Task

![](http://7xsi11.com1.z0.glb.clouddn.com/kotlin-dev-plugin-1-007.png)

查看插件

![](http://7xsi11.com1.z0.glb.clouddn.com/kotlin-dev-plugin-1-008.png)

![](http://7xsi11.com1.z0.glb.clouddn.com/kotlin-dev-plugin-1-009.png)

## 显示 IDEA LOG
在需要的类里添加 Log 实例

```kotlin
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
```

编辑 runIdea 的运行配置，在 Logs 栏里，添加项目 build 目录下的 log 文件地址

![](http://7xsi11.com1.z0.glb.clouddn.com/kotlin-dev-plugin-1-011.png)

Log 结果如下图（PS：log.debug 不能显示，需要更改 Help > Debug Log Settings）

![](http://7xsi11.com1.z0.glb.clouddn.com/kotlin-dev-plugin-1-012.png)

## Build Plugin
修改 gradle 配置

```gradle
intellij {
    ......
    // 不更新 plugin.xml 里的 since-build 属性，即以 plugin.xml 的配置为准
    intellij.updateSinceUntilBuild false
    ......
}
```

运行 buildPlugin task

![](http://7xsi11.com1.z0.glb.clouddn.com/kotlin-dev-plugin-1-013.png)

## Thanks

* [项目添加 Gradle 支持](http://www.jetbrains.org/intellij/sdk/docs/tutorials/build_system/prerequisites.html#add-gradle-support-from-scratch)

* [JetBrains/gradle-intellij-plugin](https://github.com/JetBrains/gradle-intellij-plugin)

* [Intellij IDEA 插件开发快速入门](https://moxun.me/archives/28)


