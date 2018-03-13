# javafx-springboot-boilerplate-hibernate-lifecycles-1nn1
A boilerplate project for JavaFX development with Spring Boot, JPA/Hibernate. It shows some real world use cases relevant for `association lifecycle`. I felt most tutorials on the web lacking this complete lifecycle and are almost useless therefore. Features:

- **spring integration**
- a sample for communication between two controllers
- **MVVM framework**
- simple sync of elements in `ListView` with the help of `Bean Properties` and `Observable` (changes are synced across different views)
- a simple window manager concept
- jpa with hibernate
- h2 database is created, schema is created automatically. See `persistence.xml`
- Lifecycle of association in real world use cases: GUI
- 1nn1 Lifecycle
- Observable

This project is not meant to be a full fledged template. Rather it is a snapshot of a bigger project I'm working on. The key-features are ported into this template. It contains bugs and misconceptions that should solved.

There are a vast of libraries included:

| Library | Description |
|-------|-------------|
| org.springframework.boot:spring-boot-starter-data-jpa       | Spring boot data starter jpa          |
| org.springframework.boot:spring-boot-starter-test       | Spring boot starter test         |
| com.h2database:h2:1.4.193       | H2 Database Driver           |
| com.google.code.findbugs       | For static code analysis with IntelliJ           |
| com.google.guava       | Common lang Utilities           |
| de.saxsys:mvvmfx:1.6.0       | MVVM Framework           |
| de.saxsys:mvvmfx-validation:1.6.0       | MVVM Framework validation support           |
| org.kohsuke:wordnet-random-name:1.3       | Random name generator           |
| com.jfoenix:jfoenix:1.4.0       | JavaFX material design           |


See [javafx-boilerplate-hibernate-lifecycles-1nn1](https://github.com/No3x/javafx-boilerplate-hibernate-lifecycles-1nn1) for an example with JPA and hibernate and JavaFX.
