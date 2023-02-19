# Bloom

Making a fully modular project using combo of:

- JavaFX
- Spring Boot
- Project Reactor + RxJava
- PF4J
- Lombok
- TestFX

### Building

Target JDK version: 17 \
Requires Gradle 7.4+

```shell
./gradlew assemblePlugins bootJar
```

### Running

```shell
./gradlew assemblePlugins bootRun
# or
./gradlew assemblePlugins bootJar
java -jar ./bloom-fx/build/libs/bloom-fx-boot-0.0.1-SNAPSHOT.jar
```

### Tests

```shell
./gradlew :bloom-fx:test
```
