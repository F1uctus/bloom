dependencies {
    implementation(project(":bloom-plugins:interface"))
    implementation(project(":bloom-plugins:interface-fx"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.core:jackson-databind")

    runtimeOnly("com.h2database:h2")
    runtimeOnly("io.r2dbc:r2dbc-h2")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}
