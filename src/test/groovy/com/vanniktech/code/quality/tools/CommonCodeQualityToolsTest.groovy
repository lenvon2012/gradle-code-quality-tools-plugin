package com.vanniktech.code.quality.tools

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before

abstract class CommonCodeQualityToolsTest {
  Project rootProject

  Project javaProject
  Project javaLibraryProject
  Project javaGradlePluginProject // TODO: use for testing

  Project kotlinProject
  Project kotlinAndroidProject // TODO: use for testing
  Project kotlinPlatformCommonProject // TODO: use for testing
  Project kotlinPlatformJvmProject // TODO: use for testing
  Project kotlinPlatformJsProject // TODO: use for testing

  Project androidAppProject
  Project androidLibraryProject
  Project androidTestProject // TODO: use for testing
  Project androidFeatureProject // TODO: use for testing
  Project androidInstantAppProject // TODO: use for testing

  Project[] projects

  @Before void setUp() {
    rootProject = ProjectBuilder.builder().withName('root').build()

    javaProject = ProjectBuilder.builder().withName('java').withParent(rootProject).build()
    javaProject.plugins.apply('java')
    javaProject.repositories { jcenter() } // TODO: is this needed?!

    javaLibraryProject = ProjectBuilder.builder().withName('java-library').withParent(rootProject).build()
    javaLibraryProject.plugins.apply('java-library')
    javaLibraryProject.repositories { jcenter() } // TODO: is this needed?!

    javaGradlePluginProject = ProjectBuilder.builder().withName('java-library').withParent(rootProject).build()
    javaGradlePluginProject.plugins.apply('java-gradle-plugin')
    javaGradlePluginProject.repositories { jcenter() } // TODO: is this needed?!

    kotlinProject = ProjectBuilder.builder().withName('kotlin').withParent(rootProject).build()
    kotlinProject.plugins.apply('kotlin')
    kotlinProject.repositories { jcenter() } // TODO: is this needed?!

    kotlinAndroidProject = ProjectBuilder.builder().withName('kotlin-android').withParent(rootProject).build()
    kotlinAndroidProject.plugins.apply('kotlin-android')
    kotlinAndroidProject.repositories { jcenter() } // TODO: is this needed?!

    kotlinPlatformCommonProject = ProjectBuilder.builder().withName('kotlin-platform-common').withParent(rootProject).build()
    kotlinPlatformCommonProject.plugins.apply('kotlin-platform-common')
    kotlinPlatformCommonProject.repositories { jcenter() } // TODO: is this needed?!

    kotlinPlatformJvmProject = ProjectBuilder.builder().withName('kotlin-platform-jvm').withParent(rootProject).build()
    kotlinPlatformJvmProject.plugins.apply('kotlin-platform-jvm')
    kotlinPlatformJvmProject.repositories { jcenter() } // TODO: is this needed?!

    kotlinPlatformJsProject = ProjectBuilder.builder().withName('kotlin-platform-js').withParent(rootProject).build()
    kotlinPlatformJsProject.plugins.apply('kotlin-platform-js')
    kotlinPlatformJsProject.repositories { jcenter() } // TODO: is this needed?!

    androidAppProject = ProjectBuilder.builder().withName('android app').build()
    androidAppProject.plugins.apply('com.android.application')
    androidAppProject.repositories { jcenter() } // TODO: is this needed?!

    androidLibraryProject = ProjectBuilder.builder().withName('android library').build()
    androidLibraryProject.plugins.apply('com.android.library')
    androidLibraryProject.repositories { jcenter() } // TODO: is this needed?!

    androidTestProject = ProjectBuilder.builder().withName('android test').build()
    androidTestProject.plugins.apply('com.android.test')
    androidTestProject.repositories { jcenter() } // TODO: is this needed?!

    androidFeatureProject = ProjectBuilder.builder().withName('android feature').build()
    androidFeatureProject.plugins.apply('com.android.feature')
    androidFeatureProject.repositories { jcenter() } // TODO: is this needed?!

    androidInstantAppProject = ProjectBuilder.builder().withName('android instant app').build()
    androidInstantAppProject.plugins.apply('com.android.instantapp')
    androidInstantAppProject.repositories { jcenter() } // TODO: is this needed?!

    projects = [
        rootProject,
        javaProject,
        javaLibraryProject,
        javaGradlePluginProject,
        kotlinProject,
        kotlinAndroidProject,
        kotlinPlatformCommonProject,
        kotlinPlatformJvmProject,
        kotlinPlatformJsProject,
        androidAppProject,
        androidLibraryProject,
        androidTestProject,
        androidFeatureProject,
        androidInstantAppProject
    ]
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
