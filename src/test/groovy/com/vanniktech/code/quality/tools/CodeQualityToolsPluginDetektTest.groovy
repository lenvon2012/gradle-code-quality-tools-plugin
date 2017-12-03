package com.vanniktech.code.quality.tools

import org.gradle.api.Project
import org.junit.Test

import static com.vanniktech.code.quality.tools.CodeQualityToolsPlugin.addDetekt

class CodeQualityToolsPluginDetektTest extends CommonCodeQualityToolsTest {
  @Test void java() {
    assert !addDetekt(javaProject, rootProject, new CodeQualityToolsPluginExtensionForTests())
  }

  @Test void javaLibrary() {
    assert !addDetekt(javaLibraryProject, rootProject, new CodeQualityToolsPluginExtensionForTests())
  }

  @Test void kotlin() {
    assert addDetekt(kotlinProject, rootProject, new CodeQualityToolsPluginExtensionForTests())

    assertDetekt(kotlinProject, rootProject)
  }

  @Test void androidApp() {
    assert !addDetekt(androidAppProject, rootProject, new CodeQualityToolsPluginExtensionForTests())
  }

  @Test void androidLibrary() {
    assert !addDetekt(androidLibraryProject, rootProject, new CodeQualityToolsPluginExtensionForTests())
  }

  private static void assertDetekt(Project project, Project rootProject) {
    def dependencies = project.configurations.getByName('detektCheck').dependencies
    assert dependencies.size() == 1

    def detektCheck = dependencies[0]
    assert detektCheck.group == 'io.gitlab.arturbosch.detekt'
    assert detektCheck.name == 'detekt-cli'
    assert detektCheck.version == '1.0.0.M13.2'
    assert taskDependsOn(project.check, 'detektCheck')

    assert project.getTasksByName('detektCheck', false).size() == 1
    def detektCheckTask = project.getTasksByName('detektCheck', false)[0]
    assert detektCheckTask.group == 'verification'
    assert detektCheckTask.description == 'Runs detekt.'
    assert detektCheckTask.main == 'io.gitlab.arturbosch.detekt.cli.Main'
    assert detektCheckTask.args.size() == 4
    assert detektCheckTask.args[0] == '--config'
    assert detektCheckTask.args[1] == rootProject.file('code_quality_tools/detekt.yml').toString()
    assert detektCheckTask.args[2] == '--input'
    assert detektCheckTask.args[3] == project.file('.').toString()
  }

  @Test void configurations() {
    def extension = new CodeQualityToolsPluginExtensionForTests()
    extension.detekt.toolVersion = '1.0.0-RC5'

    assert addDetekt(kotlinProject, rootProject, extension)

    def detektCheck = kotlinProject.configurations.getByName('detektCheck').dependencies[0]
    assert detektCheck.group == 'io.gitlab.arturbosch.detekt'
    assert detektCheck.name == 'detekt-cli'
    assert detektCheck.version == '1.0.0-RC5'
  }

  @Test void enabled() {
    def extension = new CodeQualityToolsPluginExtensionForTests()
    extension.detekt.enabled = false

    for (def project : projects) {
      assert !addDetekt(project, rootProject, extension)
    }
  }
}
