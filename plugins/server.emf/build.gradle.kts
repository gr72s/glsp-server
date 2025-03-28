version = "0.1.0-SNAPSHOT"

extra["name"] = "Charon Activity Diagram Module"
extra["description"] = "Diagram sub module"

dependencies {
    api(project(":server"))
    api(project(":graph"))
    api("com.google.inject:guice:7.0.0")
    api(libs.emf.ecore.change)
    api("org.eclipse.emf:org.eclipse.emf.ecore.edit:2.15.0")
    api(libs.emf.mapping.ecore2xml)
    api("commons-cli:commons-cli:1.9.0")
}

tasks.test {
    useJUnitPlatform()
}

sourceSets {
    main {
        java {
            srcDirs("src/main/java", "src/main/notation-gen")
        }
        kotlin {
            srcDirs("src/main/java", "src/main/notation-gen")
        }
    }
}