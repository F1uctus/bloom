dependencies {
    implementation(project(":bloom-plugins:interface"))
    implementation(project(":bloom-plugins:interface-fx"))

    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.jackson.databind)

    runtimeOnly(libs.h2)
    runtimeOnly(libs.h2.r2dbc)

    annotationProcessor(libs.spring.boot.configuration.processor)
}
