import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

plugins {
    kotlin("jvm") version "1.9.21" apply false
}

subprojects {
    group = "com.gr72s.glsp"

    apply(plugin = "java-library")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")

    val javaPluginExtension = extensions.getByType(JavaPluginExtension::class.java)
    javaPluginExtension.targetCompatibility = JavaVersion.VERSION_17

    val kotlinProjectExtension = extensions.getByType(KotlinProjectExtension::class.java)
    kotlinProjectExtension.jvmToolchain(17)

    tasks.withType<JavaCompile> {
        options.compilerArgs = listOf("-Xlint:none", "-nowarn", "-Xlint:-deprecation")
        options.encoding = "UTF-8"
    }

    tasks.withType<Javadoc> {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
        (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
        options.encoding = "UTF-8"
    }

}
