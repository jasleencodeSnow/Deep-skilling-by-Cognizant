# Java Testing Exercises — Complete Solutions

Covers: JUnit 5 Basics, JUnit 5 Advanced, Mockito, Mockito Advanced, Mockito Mock
Dependencies (Spring), Spring/JUnit Integration Testing, and SLF4J Logging.

All examples use **JUnit 5 (Jupiter)**, **Mockito**, and **Spring Boot Test** conventions.

---

## Table of Contents

1. [JUnit Basic Testing Exercises](#1-junit-basic-testing-exercises)
2. [Advanced JUnit Testing Exercises](#2-advanced-junit-testing-exercises)
3. [Mockito Exercises](#3-mockito-exercises)
4. [Advanced Mockito Exercises](#4-advanced-mockito-exercises)
5. [Mockito — Mocking Dependencies in Spring Tests](#5-mockito--mocking-dependencies-in-spring-tests)
6. [JUnit / Spring Testing Exercises](#6-junit--spring-testing-exercises)
7. [SLF4J Logging Exercises](#7-slf4j-logging-exercises)

---

## 1. JUnit Basic Testing Exercises

### Exercise 1: Setting Up JUnit

**Maven `pom.xml` (JUnit 5):**

```xml
<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.2.5</version>
        </plugin>
    </plugins>
</build>
```

**Empty starter test class:**

```java
import org.junit.jupiter.api.Test;

public class SetupTest {

    @Test
    void junitIsWorking() {
        System.out.println("JUnit is set up correctly!");
    }
}
```

### Exercise 2: Writing Basic JUnit Tests

**Class under test:**

```java
public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }
}
```

**Test class:**

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    void testAdd() {
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    void testSubtract() {
        assertEquals(1, calculator.subtract(3, 2));
    }

    @Test
    void testMultiply() {
        assertEquals(6, calculator.multiply(2, 3));
    }

    @Test
    void testDivide() {
        assertEquals(2, calculator.divide(6, 3));
    }

    @Test
    void testDivideByZeroThrows() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(1, 0));
    }
}
```

### Exercise 3: Assertions in JUnit

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AssertionsTest {

    @Test
    public void testAssertions() {
        assertEquals(5, 2 + 3);
        assertTrue(5 > 3);
        assertFalse(5 < 3);
        assertNull(null);
        assertNotNull(new Object());
        assertArrayEquals(new int[]{1, 2, 3}, new int[]{1, 2, 3});
        assertSame("same", "same"); // string pool - same reference
        assertNotSame(new Object(), new Object());
        assertThrows(ArithmeticException.class, () -> {
            int result = 1 / 0;
        });
        assertAll("grouped assertions",
                () -> assertEquals(4, 2 + 2),
                () -> assertTrue(10 > 1)
        );
    }
}
```

### Exercise 4: Arrange-Act-Assert (AAA), Fixtures, Setup/Teardown

```java
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartTest {

    private List<String> cart;

    @BeforeEach
    void setUp() {
        // Arrange (fixture) — runs before every test
        cart = new ArrayList<>();
        cart.add("Apple");
    }

    @AfterEach
    void tearDown() {
        // Teardown — runs after every test
        cart.clear();
    }

    @BeforeAll
    static void setUpOnce() {
        System.out.println("Runs once before all tests");
    }

    @AfterAll
    static void tearDownOnce() {
        System.out.println("Runs once after all tests");
    }

    @Test
    void testAddItem_AAAPattern() {
        // Arrange
        String newItem = "Banana";

        // Act
        cart.add(newItem);

        // Assert
        assertEquals(2, cart.size());
        assertTrue(cart.contains(newItem));
    }

    @Test
    void testRemoveItem_AAAPattern() {
        // Arrange
        String itemToRemove = "Apple";

        // Act
        cart.remove(itemToRemove);

        // Assert
        assertFalse(cart.contains(itemToRemove));
    }
}
```

---

## 2. Advanced JUnit Testing Exercises

### Exercise 1: Parameterized Tests

```java
public class EvenChecker {
    public boolean isEven(int number) {
        return number % 2 == 0;
    }
}
```

```java
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class EvenCheckerTest {

    private final EvenChecker evenChecker = new EvenChecker();

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, 8, 100})
    void testIsEven_true(int number) {
        assertTrue(evenChecker.isEven(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 7, 99})
    void testIsEven_false(int number) {
        assertFalse(evenChecker.isEven(number));
    }

    @ParameterizedTest
    @CsvSource({"2,true", "3,false", "0,true", "-4,true"})
    void testIsEven_withCsv(int number, boolean expected) {
        assertEquals(expected, evenChecker.isEven(number));
    }
}
```

### Exercise 2: Test Suites and Categories

```java
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        CalculatorTest.class,
        EvenCheckerTest.class,
        AssertionsTest.class
})
public class AllTests {
    // No body needed — annotations drive discovery.
    // Requires the junit-platform-suite dependency:
    // org.junit.platform:junit-platform-suite:1.10.2
}
```

Categorizing with tags:

```java
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("fast")
public class TaggedTest {

    @Test
    @Tag("smoke")
    void quickSmokeTest() {
        // ...
    }
}
```

### Exercise 3: Test Execution Order

```java
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderedTests {

    @Test
    @Order(1)
    void firstTest() {
        System.out.println("Running first");
        assertTrue(true);
    }

    @Test
    @Order(2)
    void secondTest() {
        System.out.println("Running second");
        assertTrue(true);
    }

    @Test
    @Order(3)
    void thirdTest() {
        System.out.println("Running third");
        assertTrue(true);
    }
}
```

### Exercise 4: Exception Testing

```java
public class ExceptionThrower {
    public void throwException() {
        throw new IllegalArgumentException("Invalid argument provided");
    }
}
```

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExceptionThrowerTest {

    @Test
    void testThrowException() {
        ExceptionThrower thrower = new ExceptionThrower();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                thrower::throwException
        );

        assertEquals("Invalid argument provided", exception.getMessage());
    }
}
```

### Exercise 5: Timeout and Performance Testing

```java
public class PerformanceTester {
    public void performTask() throws InterruptedException {
        Thread.sleep(100); // simulate work
    }
}
```

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class PerformanceTesterTest {

    private final PerformanceTester tester = new PerformanceTester();

    @Test
    @Timeout(value = 500, unit = MILLISECONDS)
    void testPerformTask_annotationTimeout() throws InterruptedException {
        tester.performTask();
    }

    @Test
    void testPerformTask_assertTimeout() {
        assertTimeout(Duration.ofMillis(500), () -> tester.performTask());
    }
}
```

---

## 3. Mockito Exercises

> Supporting class used across Exercises 1–2:
> ```java
> public interface ExternalApi {
>     String getData();
> }
>
> public class MyService {
>     private final ExternalApi externalApi;
>
>     public MyService(ExternalApi externalApi) {
>         this.externalApi = externalApi;
>     }
>
>     public String fetchData() {
>         return externalApi.getData();
>     }
> }
> ```

### Exercise 1: Mocking and Stubbing

```java
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MyServiceTest {

    @Test
    public void testExternalApi() {
        ExternalApi mockApi = mock(ExternalApi.class);
        when(mockApi.getData()).thenReturn("Mock Data");

        MyService service = new MyService(mockApi);
        String result = service.fetchData();

        assertEquals("Mock Data", result);
    }
}
```

### Exercise 2: Verifying Interactions

```java
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

public class MyServiceVerifyTest {

    @Test
    public void testVerifyInteraction() {
        ExternalApi mockApi = mock(ExternalApi.class);
        MyService service = new MyService(mockApi);

        service.fetchData();

        verify(mockApi).getData();
        verify(mockApi, times(1)).getData();
    }
}
```

### Exercise 3: Argument Matching

```java
public interface Calculator2 {
    int add(int a, int b);
}

public class MathService {
    private final Calculator2 calculator;

    public MathService(Calculator2 calculator) {
        this.calculator = calculator;
    }

    public int compute(int a, int b) {
        return calculator.add(a, b);
    }
}
```

```java
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArgumentMatchingTest {

    @Test
    void testArgumentMatchers() {
        Calculator2 mockCalculator = mock(Calculator2.class);
        when(mockCalculator.add(anyInt(), eq(5))).thenReturn(15);

        MathService service = new MathService(mockCalculator);
        int result = service.compute(10, 5);

        assertEquals(15, result);
        verify(mockCalculator).add(eq(10), eq(5));
    }
}
```

### Exercise 4: Handling Void Methods

```java
public interface NotificationSender {
    void sendNotification(String message);
}

public class AlertService {
    private final NotificationSender sender;

    public AlertService(NotificationSender sender) {
        this.sender = sender;
    }

    public void alert(String message) {
        sender.sendNotification(message);
    }
}
```

```java
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

public class VoidMethodTest {

    @Test
    void testVoidMethodStubbing() {
        NotificationSender mockSender = mock(NotificationSender.class);
        doNothing().when(mockSender).sendNotification(anyString());

        AlertService alertService = new AlertService(mockSender);
        alertService.alert("System down");

        verify(mockSender).sendNotification("System down");
    }
}
```

### Exercise 5: Multiple Return Values

```java
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MultipleReturnsTest {

    @Test
    void testConsecutiveCalls() {
        ExternalApi mockApi = mock(ExternalApi.class);
        when(mockApi.getData())
                .thenReturn("First Call")
                .thenReturn("Second Call")
                .thenReturn("Third Call");

        assertEquals("First Call", mockApi.getData());
        assertEquals("Second Call", mockApi.getData());
        assertEquals("Third Call", mockApi.getData());
        // Mockito repeats the last stub for further calls
        assertEquals("Third Call", mockApi.getData());
    }
}
```

### Exercise 6: Verifying Interaction Order

```java
public interface Step {
    void execute();
}

public class Workflow {
    private final Step step1;
    private final Step step2;

    public Workflow(Step step1, Step step2) {
        this.step1 = step1;
        this.step2 = step2;
    }

    public void run() {
        step1.execute();
        step2.execute();
    }
}
```

```java
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

public class InteractionOrderTest {

    @Test
    void testInteractionOrder() {
        Step step1 = mock(Step.class);
        Step step2 = mock(Step.class);
        Workflow workflow = new Workflow(step1, step2);

        workflow.run();

        InOrder inOrder = inOrder(step1, step2);
        inOrder.verify(step1).execute();
        inOrder.verify(step2).execute();
    }
}
```

### Exercise 7: Void Methods That Throw Exceptions

```java
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class VoidMethodExceptionTest {

    @Test
    void testVoidMethodThrowsException() {
        NotificationSender mockSender = mock(NotificationSender.class);
        doThrow(new RuntimeException("Send failed"))
                .when(mockSender).sendNotification(anyString());

        AlertService alertService = new AlertService(mockSender);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> alertService.alert("Critical error")
        );
        assertEquals("Send failed", exception.getMessage());
        verify(mockSender).sendNotification("Critical error");
    }
}
```

---

## 4. Advanced Mockito Exercises

> Supporting interfaces/classes referenced by the exercises:
> ```java
> public interface Repository {
>     String getData();
> }
>
> public class Service {
>     private final Repository repository;
>     public Service(Repository repository) { this.repository = repository; }
>     public String processData() { return "Processed " + repository.getData(); }
> }
>
> public interface RestClient {
>     String getResponse();
> }
>
> public class ApiService {
>     private final RestClient restClient;
>     public ApiService(RestClient restClient) { this.restClient = restClient; }
>     public String fetchData() { return "Fetched " + restClient.getResponse(); }
> }
>
> public interface FileReader {
>     String read();
> }
>
> public interface FileWriter {
>     void write(String content);
> }
>
> public class FileService {
>     private final FileReader fileReader;
>     private final FileWriter fileWriter;
>     public FileService(FileReader fileReader, FileWriter fileWriter) {
>         this.fileReader = fileReader;
>         this.fileWriter = fileWriter;
>     }
>     public String processFile() { return "Processed " + fileReader.read(); }
> }
>
> public interface NetworkClient {
>     String connect();
> }
>
> public class NetworkService {
>     private final NetworkClient networkClient;
>     public NetworkService(NetworkClient networkClient) { this.networkClient = networkClient; }
>     public String connectToServer() { return "Connected to " + networkClient.connect(); }
> }
> ```

### Exercise 1: Mocking Databases and Repositories

```java
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ServiceTest {

    @Test
    public void testServiceWithMockRepository() {
        Repository mockRepository = mock(Repository.class);
        when(mockRepository.getData()).thenReturn("Mock Data");

        Service service = new Service(mockRepository);
        String result = service.processData();

        assertEquals("Processed Mock Data", result);
    }
}
```

### Exercise 2: Mocking External Services (RESTful APIs)

```java
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ApiServiceTest {

    @Test
    public void testServiceWithMockRestClient() {
        RestClient mockRestClient = mock(RestClient.class);
        when(mockRestClient.getResponse()).thenReturn("Mock Response");

        ApiService apiService = new ApiService(mockRestClient);
        String result = apiService.fetchData();

        assertEquals("Fetched Mock Response", result);
    }
}
```

### Exercise 3: Mocking File I/O

```java
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FileServiceTest {

    @Test
    public void testServiceWithMockFileIO() {
        FileReader mockFileReader = mock(FileReader.class);
        FileWriter mockFileWriter = mock(FileWriter.class);
        when(mockFileReader.read()).thenReturn("Mock File Content");

        FileService fileService = new FileService(mockFileReader, mockFileWriter);
        String result = fileService.processFile();

        assertEquals("Processed Mock File Content", result);
        verifyNoInteractions(mockFileWriter);
    }
}
```

### Exercise 4: Mocking Network Interactions

```java
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class NetworkServiceTest {

    @Test
    public void testServiceWithMockNetworkClient() {
        NetworkClient mockNetworkClient = mock(NetworkClient.class);
        when(mockNetworkClient.connect()).thenReturn("Mock Connection");

        NetworkService networkService = new NetworkService(mockNetworkClient);
        String result = networkService.connectToServer();

        assertEquals("Connected to Mock Connection", result);
    }
}
```

### Exercise 5: Mocking Multiple Return Values

```java
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MultiReturnServiceTest {

    @Test
    public void testServiceWithMultipleReturnValues() {
        Repository mockRepository = mock(Repository.class);
        when(mockRepository.getData())
                .thenReturn("First Mock Data")
                .thenReturn("Second Mock Data");

        Service service = new Service(mockRepository);
        String firstResult = service.processData();
        String secondResult = service.processData();

        assertEquals("Processed First Mock Data", firstResult);
        assertEquals("Processed Second Mock Data", secondResult);
    }
}
```

---

## 5. Mockito — Mocking Dependencies in Spring Tests

> Supporting classes (shared across this section):
> ```java
> @Entity
> public class User {
>     @Id
>     private Long id;
>     private String name;
>
>     public User() {}
>     public User(Long id, String name) { this.id = id; this.name = name; }
>
>     public Long getId() { return id; }
>     public void setId(Long id) { this.id = id; }
>     public String getName() { return name; }
>     public void setName(String name) { this.name = name; }
> }
>
> public interface UserRepository extends JpaRepository<User, Long> { }
>
> @Service
> public class UserService {
>     @Autowired
>     private UserRepository userRepository;
>
>     public User getUserById(Long id) {
>         return userRepository.findById(id).orElse(null);
>     }
> }
>
> @RestController
> @RequestMapping("/users")
> public class UserController {
>     @Autowired
>     private UserService userService;
>
>     @GetMapping("/{id}")
>     public ResponseEntity<User> getUser(@PathVariable Long id) {
>         return ResponseEntity.ok(userService.getUserById(id));
>     }
> }
> ```

### Exercise 1: Mocking a Service Dependency in a Controller Test

```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testGetUser() throws Exception {
        User user = new User(1L, "Alice");
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alice"));
    }
}
```

### Exercise 2: Mocking a Repository in a Service Test

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetUserById_found() {
        User user = new User(1L, "Bob");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("Bob", result.getName());
    }

    @Test
    void testGetUserById_notFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        User result = userService.getUserById(99L);

        assertNull(result);
    }
}
```

### Exercise 3: Mocking a Service Dependency in an Integration Test

```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testGetUserEndToEnd() throws Exception {
        User user = new User(2L, "Carol");
        when(userService.getUserById(2L)).thenReturn(user);

        mockMvc.perform(get("/users/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Carol"));
    }
}
```

---

## 6. JUnit / Spring Testing Exercises

### Exercise 1: Basic Unit Test for a Service Method

```java
@Service
public class CalculatorService {
    public int add(int a, int b) {
        return a + b;
    }
}
```

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorServiceTest {

    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void testAdd() {
        assertEquals(7, calculatorService.add(3, 4));
    }
}
```

### Exercise 2: Mocking a Repository in a Service Test

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceRepoTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User(1L, "Dave")));

        User result = userService.getUserById(1L);

        assertEquals("Dave", result.getName());
    }
}
```

### Exercise 3: Testing a REST Controller with MockMvc

```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testGetUserEndpoint() throws Exception {
        when(userService.getUserById(1L)).thenReturn(new User(1L, "Eve"));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Eve"));
    }
}
```

### Exercise 4: Integration Test with Spring Boot

```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
@AutoConfigureMockMvc
public class UserFullFlowIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateThenFetchUser() throws Exception {
        String userJson = "{\"name\":\"Frank\"}";

        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Frank"));
    }
}
```

> Note: this test hits a real (typically in-memory H2) database, configured via
> `application-test.properties` and activated with `@ActiveProfiles("test")`.

### Exercise 5: Test Controller POST Endpoint

```java
@PostMapping
public ResponseEntity<User> createUser(@RequestBody User user) {
    return ResponseEntity.ok(userService.saveUser(user));
}
```

```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerPostTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testCreateUser() throws Exception {
        User savedUser = new User(1L, "Grace");
        when(userService.saveUser(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content("{\"name\":\"Grace\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Grace"));
    }
}
```

### Exercise 6: Test Service Exception Handling

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceExceptionTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetUserById_missingUser_returnsNull() {
        when(userRepository.findById(404L)).thenReturn(Optional.empty());

        assertNull(userService.getUserById(404L));
    }

    // If UserService is changed to throw instead of returning null:
    // public User getUserById(Long id) {
    //     return userRepository.findById(id)
    //             .orElseThrow(() -> new NoSuchElementException("User not found"));
    // }
    @Test
    void testGetUserById_missingUser_throwsException() {
        when(userRepository.findById(404L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            userRepository.findById(404L)
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
        });
    }
}
```

### Exercise 7: Test Custom Repository Query

```java
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
}
```

```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByName() {
        userRepository.save(new User(null, "Henry"));
        userRepository.save(new User(null, "Henry"));
        userRepository.save(new User(null, "Ivy"));

        List<User> results = userRepository.findByName("Henry");

        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(u -> u.getName().equals("Henry")));
    }
}
```

### Exercise 8: Test Controller Exception Handling

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}
```

```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testUserNotFound_returns404() throws Exception {
        when(userService.getUserById(999L))
                .thenThrow(new NoSuchElementException("User not found"));

        mockMvc.perform(get("/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }
}
```

> This assumes `UserController.getUser` propagates the exception from the service
> layer (i.e. it isn't caught locally), so `GlobalExceptionHandler` handles it.

### Exercise 9: Parameterized Test with JUnit

```java
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorServiceParameterizedTest {

    private final CalculatorService calculatorService = new CalculatorService();

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,3,5",
            "0,0,0",
            "-1,1,0",
            "100,200,300"
    })
    void testAdd(int a, int b, int expected) {
        assertEquals(expected, calculatorService.add(a, b));
    }
}
```

---

## 7. SLF4J Logging Exercises

### Exercise 1: Logging Error Messages and Warning Levels

**`pom.xml`:**

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.13</version>
</dependency>
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.5.6</version>
</dependency>
```

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingExample {
    private static final Logger logger = LoggerFactory.getLogger(LoggingExample.class);

    public static void main(String[] args) {
        logger.info("Application started");
        logger.debug("Debugging details here");
        logger.warn("This is a warning message");
        logger.error("This is an error message");
    }
}
```

### Exercise 2: Parameterized Logging

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParameterizedLoggingExample {
    private static final Logger logger = LoggerFactory.getLogger(ParameterizedLoggingExample.class);

    public static void main(String[] args) {
        String username = "alice";
        int loginAttempts = 3;

        // Single placeholder
        logger.info("User {} logged in", username);

        // Multiple placeholders — avoids string concatenation cost
        // when the log level is disabled
        logger.warn("User {} failed login {} times", username, loginAttempts);

        // Logging an exception with a message
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            logger.error("Failed to compute result for user {}", username, e);
        }
    }
}
```

### Exercise 3: Using Different Appenders

**`logback.xml`:**

```xml
<configuration>
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="file" class="ch.qos.logback.core.FileAppender">
        <file>app.log</file>
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="debug">
        <appender-ref ref="console" />
        <appender-ref ref="file" />
    </root>
</configuration>
```

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppenderExample {
    private static final Logger logger = LoggerFactory.getLogger(AppenderExample.class);

    public static void main(String[] args) {
        logger.debug("Debug message — goes to console and file");
        logger.info("Info message — goes to console and file");
        logger.warn("Warning message — goes to console and file");
        logger.error("Error message — goes to console and file");
        // Check the project root for app.log to confirm the file appender worked.
    }
}
```

---

## Suggested Repository Layout

```
testing-exercises/
├── src/
│   ├── main/java/com/example/
│   │   ├── Calculator.java
│   │   ├── CalculatorService.java
│   │   ├── EvenChecker.java
│   │   ├── User.java
│   │   ├── UserRepository.java
│   │   ├── UserService.java
│   │   ├── UserController.java
│   │   └── GlobalExceptionHandler.java
│   └── test/java/com/example/
│       ├── CalculatorTest.java
│       ├── AssertionsTest.java
│       ├── EvenCheckerTest.java
│       ├── UserServiceTest.java
│       └── UserControllerTest.java
├── src/main/resources/logback.xml
├── pom.xml
└── README.md   <-- this file
```

