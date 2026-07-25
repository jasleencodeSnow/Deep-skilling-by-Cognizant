# Document 1 — Hands on 2, 3 and 4 (concept notes)

These three hands-on sections are SME-led walkthroughs of external reference
material rather than build tasks, so there's no project code for them.
Notes below summarize what each one covers; the runnable hands-on (1, 5–9)
live in `../orm-learn`.

## Hands on 2 — Hibernate XML Config implementation walk through

Reference: https://www.tutorialspoint.com/hibernate/hibernate_examples.htm

- Object-to-relational mapping is defined in a `<classname>.hbm.xml` mapping
  file (table name, column names, primary key) instead of annotations.
- Core objects used end-to-end:
  - `SessionFactory` — built once from `hibernate.cfg.xml`; thread-safe, expensive to create.
  - `Session` — a single unit-of-work / first-level cache, obtained from the `SessionFactory`.
  - `Transaction` — demarcates the unit of work: `beginTransaction()`, `commit()`, `rollback()`.
  - `session.save(obj)` — persists a new entity.
  - `session.createQuery(hql).list()` — HQL query returning a list.
  - `session.get(Class, id)` — fetch by primary key (returns `null` if not found).
  - `session.delete(obj)` — removes an entity.
- Typical flow: `openSession()` → `beginTransaction()` → do work → `commit()`
  (or `rollback()` in a `catch` block) → `close()` the session in `finally`.

## Hands on 3 — Hibernate Annotation Config implementation walk through

Reference: https://www.tutorialspoint.com/hibernate/hibernate_annotations.htm

- Same end-to-end flow as Hands on 2, but the mapping lives on the entity
  class itself instead of an XML mapping file:
  - `@Entity` — marks the class as a persistent entity.
  - `@Table(name = "...")` — maps it to a specific table.
  - `@Id` — marks the primary key field.
  - `@GeneratedValue` — delegates primary key generation to the database/strategy.
  - `@Column(name = "...")` — maps a field to a specific column.
- `hibernate.cfg.xml` still supplies the connection-level configuration:
  dialect, JDBC driver class, connection URL, username, password.

## Hands on 4 — Difference between JPA, Hibernate and Spring Data JPA

| | What it is |
|---|---|
| **JPA** | A specification (JSR 338) for persisting, reading and managing Java objects. It has *no* implementation of its own. |
| **Hibernate** | An ORM tool that *implements* the JPA specification (one of several possible providers). |
| **Spring Data JPA** | Sits *above* a JPA provider (typically Hibernate). It doesn't implement persistence itself — it removes boilerplate code (no manual `Session`/`EntityManager` handling) and manages transactions declaratively via `@Transactional`. |

Code comparison (see the document for the full snippets): a raw Hibernate
`addEmployee()` method manually opens a `Session`, begins/commits/rolls back
a `Transaction`, and closes the session in a `finally` block. The Spring
Data JPA equivalent is just:

```java
public interface EmployeeRepository extends JpaRepository<Employee, Integer> { }

@Transactional
public void addEmployee(Employee employee) {
    employeeRepository.save(employee);
}
```

Spring Data JPA + Hibernate is exactly what `../orm-learn` and
`../employee-management-system` are built with.

References:
- https://dzone.com/articles/what-is-the-difference-between-hibernate-and-sprin-1
- https://www.javaworld.com/article/3379043/what-is-jpa-introduction-to-the-java-persistence-api.html
