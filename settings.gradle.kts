rootProject.name = "glsp-server"

pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.aliyun.com/repository/gradle-plugin")
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

include(":org.eclipse.glsp.graph")
project(":org.eclipse.glsp.graph").projectDir = file("plugins/org.eclipse.glsp.graph")
include(":org.eclipse.glsp.layout")
project(":org.eclipse.glsp.layout").projectDir = file("plugins/org.eclipse.glsp.layout")
include(":org.eclipse.glsp.server")
project(":org.eclipse.glsp.server").projectDir = file("plugins/org.eclipse.glsp.server")
include(":org.eclipse.glsp.server.emf")
project(":org.eclipse.glsp.server.emf").projectDir = file("plugins/org.eclipse.glsp.server.emf")
include(":org.eclipse.glsp.server.websocket")
project(":org.eclipse.glsp.server.websocket").projectDir = file("plugins/org.eclipse.glsp.server.websocket")

include(":org.eclipse.glsp.example.workflow")
project(":org.eclipse.glsp.example.workflow").projectDir = file("examples/org.eclipse.glsp.example.workflow")