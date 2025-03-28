version = "0.1.0-SNAPSHOT"

extra["name"] = "Charon Activity Diagram Module"
extra["description"] = "Diagram sub module"

dependencies {
    api("org.eclipse.emf:org.eclipse.emf.common:2.41.0")
    api("org.eclipse.emf:org.eclipse.emf.ecore:2.38.0")
    api("com.google.code.gson:gson:2.10.1")
    api("org.slf4j:slf4j-api:2.0.9")
}

tasks.test {
    useJUnitPlatform()
}

sourceSets {
    main {
        java {
            srcDirs("src/main/java", "src/main/graph-gen")
        }
        kotlin {
            srcDirs("src/main/java", "src/main/graph-gen")
        }
    }
}