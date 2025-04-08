version = "0.1.0-SNAPSHOT"

extra["name"] = "Charon Activity Diagram Module"
extra["description"] = "Diagram sub module"

dependencies {
    api(libs.emf.ecore.change)
    api("com.google.code.gson:gson:2.10.1")
    api("org.eclipse.lsp4j:org.eclipse.lsp4j:0.24.0")
    api("org.eclipse.lsp4j:org.eclipse.lsp4j.jsonrpc:0.24.0")
    api("commons-cli:commons-cli:1.9.0")
    api("com.google.inject:guice:7.0.0")
    api("org.eclipse.emf:org.eclipse.emf.common:2.41.0")
    api("org.eclipse.emf:org.eclipse.emf.ecore:2.38.0")
    api(project(":graph"))
    api(libs.log4j.core)
    api(libs.log4j.slf4j2.impl)

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.1")
}

tasks.test {
    useJUnitPlatform()
}