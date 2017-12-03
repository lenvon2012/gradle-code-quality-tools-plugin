package com.vanniktech.code.quality.tools

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before

abstract class CommonCodeQualityToolsTest {
  Project rootProject
  Project javaProject
  Project javaLibraryProject
  Project kotlinProject
  Project androidAppProject
  Project androidLibraryProject

  Project[] projects

  @Before void setUp() {
    rootProject = ProjectBuilder.builder().withName('root').build()

    javaProject = ProjectBuilder.builder().withName('java').withParent(rootProject).build()
    javaProject.plugins.apply('java')
    javaProject.repositories {
      jcenter()
    }

    javaLibraryProject = ProjectBuilder.builder().withName('java-library').withParent(rootProject).build()
    javaLibraryProject.plugins.apply('java-library')
    javaLibraryProject.repositories {
      jcenter()
    }


    kotlinProject = ProjectBuilder.builder().withName('kotlin').withParent(rootProject).build()
    kotlinProject.plugins.apply('kotlin')
    kotlinProject.repositories {
      jcenter()
    }

    androidAppProject = ProjectBuilder.builder().withName('android app').build()
    androidAppProject.plugins.apply('com.android.application')
    androidAppProject.repositories {
      jcenter()
    }

    androidLibraryProject = ProjectBuilder.builder().withName('android library').build()
    androidLibraryProject.plugins.apply('com.android.library')
    androidLibraryProject.repositories {
      jcenter()
    }

    projects = [javaProject, javaLibraryProject, kotlinProject, androidAppProject, androidLibraryProject]
  }

  static boolean taskDependsOn(final Task task, final String taskName) {
    def it = task.dependsOn.iterator()

    while (it.hasNext()) {
      def item = it.next()

      if (item.toString().equals(taskName)) {
        return true
      }
    }

    return false
  }
}
