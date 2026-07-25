# Java Hands-On Solutions: Algorithms & Data Structures + Design Patterns & Principles

> Cognizant DN5 Java Placement Program
> Complete, compilable Java solutions for every exercise in both hands-on documents.
> Each exercise includes the conceptual explanation asked for in the "Understand/Analyze" steps, followed by full working code with a `Main`/test class.

---

## Table of Contents

### Part A — Algorithms & Data Structures
1. [Exercise 1: Inventory Management System](#exercise-1-inventory-management-system)
2. [Exercise 2: E-commerce Platform Search Function](#exercise-2-e-commerce-platform-search-function)
3. [Exercise 3: Sorting Customer Orders](#exercise-3-sorting-customer-orders)
4. [Exercise 4: Employee Management System](#exercise-4-employee-management-system)
5. [Exercise 5: Task Management System](#exercise-5-task-management-system)
6. [Exercise 6: Library Management System](#exercise-6-library-management-system)
7. [Exercise 7: Financial Forecasting](#exercise-7-financial-forecasting)

### Part B — Design Patterns & Principles
1. [Exercise 1: Singleton Pattern](#exercise-1-singleton-pattern)
2. [Exercise 2: Factory Method Pattern](#exercise-2-factory-method-pattern)
3. [Exercise 3: Builder Pattern](#exercise-3-builder-pattern)
4. [Exercise 4: Adapter Pattern](#exercise-4-adapter-pattern)
5. [Exercise 5: Decorator Pattern](#exercise-5-decorator-pattern)
6. [Exercise 6: Proxy Pattern](#exercise-6-proxy-pattern)
7. [Exercise 7: Observer Pattern](#exercise-7-observer-pattern)
8. [Exercise 8: Strategy Pattern](#exercise-8-strategy-pattern)
9. [Exercise 9: Command Pattern](#exercise-9-command-pattern)
10. [Exercise 10: MVC Pattern](#exercise-10-mvc-pattern)
11. [Exercise 11: Dependency Injection](#exercise-11-dependency-injection)

---

# Part A — Algorithms & Data Structures

## Exercise 1: Inventory Management System

**Why data structures/algorithms matter here:** A warehouse inventory can hold thousands of SKUs with constant additions, price/quantity updates, and deletions. Picking the right structure determines whether these operations are instant or painfully slow at scale. A plain list needs a full scan (O(n)) to find a product by ID, while a hash-based map gives near-instant (O(1) average) lookup, insert, and delete by key — critical when the system is queried continuously by staff and automated reordering jobs.

**Suitable structures:** `ArrayList` is fine for simple sequential storage/iteration (e.g., generating a full stock report), but for frequent lookups/updates by `productId`, a `HashMap<Integer, Product>` is far more efficient. That's the structure used below.

### `Product.java`

```java
public class Product {
    private int productId;
    private String productName;
    private int quantity;
    private double price;

    public Product(int productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "Product{id=" + productId + ", name='" + productName +
                "', qty=" + quantity + ", price=" + price + "}";
    }
}
```

### `Inventory.java`

```java
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    // HashMap chosen for O(1) average add/update/delete/search by productId
    private Map<Integer, Product> products = new HashMap<>();

    public void addProduct(Product product) {
        products.put(product.getProductId(), product);
        System.out.println("Added: " + product);
    }

    public void updateProduct(int productId, int newQuantity, double newPrice) {
        Product product = products.get(productId);
        if (product != null) {
            product.setQuantity(newQuantity);
            product.setPrice(newPrice);
            System.out.println("Updated: " + product);
        } else {
            System.out.println("Product ID " + productId + " not found.");
        }
    }

    public void deleteProduct(int productId) {
        Product removed = products.remove(productId);
        if (removed != null) {
            System.out.println("Deleted: " + removed);
        } else {
            System.out.println("Product ID " + productId + " not found.");
        }
    }

    public Product searchProduct(int productId) {
        return products.get(productId);
    }

    public void printAll() {
        products.values().forEach(System.out::println);
    }
}
```

### `InventoryMain.java`

```java
public class InventoryMain {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        inventory.addProduct(new Product(101, "Wireless Mouse", 50, 499.0));
        inventory.addProduct(new Product(102, "Mechanical Keyboard", 30, 2499.0));
        inventory.addProduct(new Product(103, "USB-C Hub", 75, 999.0));

        System.out.println("\n--- All Products ---");
        inventory.printAll();

        System.out.println("\n--- Update Product 102 ---");
        inventory.updateProduct(102, 25, 2299.0);

        System.out.println("\n--- Delete Product 101 ---");
        inventory.deleteProduct(101);

        System.out.println("\n--- Search Product 103 ---");
        System.out.println(inventory.searchProduct(103));
    }
}
```

**Time complexity analysis:**
| Operation | HashMap (used here) | ArrayList (alternative) |
|---|---|---|
| Add | O(1) average | O(1) amortized (append) |
| Update (by ID) | O(1) average | O(n) — must scan to find |
| Delete (by ID) | O(1) average | O(n) — must scan + shift |
| Search (by ID) | O(1) average | O(n) |

**Optimization discussion:** HashMap already gives amortized constant time for key-based operations, so the main risks are hash collisions (mitigated by a good `hashCode()`/`equals()` on the key, which `Integer` already provides) and resizing overhead (mitigated by pre-sizing the map with an expected capacity, e.g. `new HashMap<>(1024)`, to avoid repeated rehashing as inventory grows).

---

## Exercise 2: E-commerce Platform Search Function

**Big O notation:** Big O describes how an algorithm's running time (or memory) grows relative to input size `n`, ignoring constant factors — it tells you the *worst-case growth trend*, which is what matters as the product catalog scales from hundreds to millions of items.

- **Best case:** the fastest an algorithm can run for the "friendliest" input (e.g., binary search finding the target on its first probe → O(1)).
- **Average case:** expected time across typical inputs (linear search ≈ O(n/2) → still O(n)).
- **Worst case:** the upper bound guarantee, used for capacity planning (linear search misses entirely → O(n); binary search misses entirely → O(log n)).

### `Product.java`

```java
public class Product {
    private int productId;
    private String productName;
    private String category;

    public Product(int productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getCategory() { return category; }

    @Override
    public String toString() {
        return "Product{id=" + productId + ", name='" + productName + "', category='" + category + "'}";
    }
}
```

### `SearchService.java`

```java
import java.util.Arrays;
import java.util.Comparator;

public class SearchService {

    // Linear Search: O(n) — works on unsorted array
    public static Product linearSearch(Product[] products, String name) {
        for (Product p : products) {
            if (p.getProductName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    // Binary Search: O(log n) — requires array sorted by productName
    public static Product binarySearch(Product[] sortedProducts, String name) {
        int low = 0, high = sortedProducts.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = sortedProducts[mid].getProductName().compareToIgnoreCase(name);

            if (cmp == 0) {
                return sortedProducts[mid];
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }

    public static Product[] sortByName(Product[] products) {
        Product[] sorted = Arrays.copyOf(products, products.length);
        Arrays.sort(sorted, Comparator.comparing(Product::getProductName, String.CASE_INSENSITIVE_ORDER));
        return sorted;
    }
}
```

### `SearchMain.java`

```java
public class SearchMain {
    public static void main(String[] args) {
        Product[] products = {
                new Product(1, "Wireless Mouse", "Electronics"),
                new Product(2, "Office Chair", "Furniture"),
                new Product(3, "Bluetooth Speaker", "Electronics"),
                new Product(4, "Standing Desk", "Furniture"),
                new Product(5, "Gaming Laptop", "Electronics")
        };

        System.out.println("--- Linear Search for 'Standing Desk' ---");
        System.out.println(SearchService.linearSearch(products, "Standing Desk"));

        Product[] sorted = SearchService.sortByName(products);
        System.out.println("\n--- Binary Search for 'Gaming Laptop' (on sorted array) ---");
        System.out.println(SearchService.binarySearch(sorted, "Gaming Laptop"));
    }
}
```

**Comparison & recommendation:** Linear search is O(n) but needs no pre-sorting, so it suits small or frequently-changing catalogs. Binary search is O(log n) — dramatically faster on large catalogs (a million-item catalog takes ~20 comparisons vs. up to a million) — but requires the data to stay sorted, which costs O(n log n) whenever the data changes. For a large e-commerce catalog with far more reads (searches) than writes (new products), pre-sorting once (or maintaining a sorted index/database index) and using binary search — or better, a proper search index like Elasticsearch/DB index (effectively O(log n) or better) — is the right choice.

---

## Exercise 3: Sorting Customer Orders

**Sorting algorithms overview:**
- **Bubble Sort:** repeatedly swaps adjacent out-of-order elements; simple but O(n²) — inefficient on large lists.
- **Insertion Sort:** builds a sorted section one element at a time by inserting into its correct position; O(n²) worst case but fast on nearly-sorted data.
- **Quick Sort:** divide-and-conquer using a pivot to partition the array; O(n log n) average, O(n²) worst case (rare with good pivot choice).
- **Merge Sort:** divide-and-conquer that splits, sorts, and merges; guaranteed O(n log n), stable, but uses O(n) extra space.

### `Order.java`

```java
public class Order {
    private int orderId;
    private String customerName;
    private double totalPrice;

    public Order(int orderId, String customerName, double totalPrice) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public double getTotalPrice() { return totalPrice; }

    @Override
    public String toString() {
        return "Order{id=" + orderId + ", customer='" + customerName + "', total=" + totalPrice + "}";
    }
}
```

### `OrderSorter.java`

```java
public class OrderSorter {

    // Bubble Sort by totalPrice — O(n^2)
    public static void bubbleSort(Order[] orders) {
        int n = orders.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (orders[j].getTotalPrice() > orders[j + 1].getTotalPrice()) {
                    Order temp = orders[j];
                    orders[j] = orders[j + 1];
                    orders[j + 1] = temp;
                }
            }
        }
    }

    // Quick Sort by totalPrice — O(n log n) average
    public static void quickSort(Order[] orders, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(orders, low, high);
            quickSort(orders, low, pivotIndex - 1);
            quickSort(orders, pivotIndex + 1, high);
        }
    }

    private static int partition(Order[] orders, int low, int high) {
        double pivot = orders[high].getTotalPrice();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (orders[j].getTotalPrice() <= pivot) {
                i++;
                Order temp = orders[i];
                orders[i] = orders[j];
                orders[j] = temp;
            }
        }
        Order temp = orders[i + 1];
        orders[i + 1] = orders[high];
        orders[high] = temp;

        return i + 1;
    }
}
```

### `OrderSortMain.java`

```java
import java.util.Arrays;

public class OrderSortMain {
    public static void main(String[] args) {
        Order[] bubbleOrders = {
                new Order(1, "Ananya", 2500.0),
                new Order(2, "Rahul", 800.0),
                new Order(3, "Priya", 4200.0),
                new Order(4, "Vikram", 1200.0),
                new Order(5, "Sneha", 300.0)
        };

        Order[] quickOrders = Arrays.copyOf(bubbleOrders, bubbleOrders.length);

        OrderSorter.bubbleSort(bubbleOrders);
        System.out.println("--- Sorted by Bubble Sort ---");
        for (Order o : bubbleOrders) System.out.println(o);

        OrderSorter.quickSort(quickOrders, 0, quickOrders.length - 1);
        System.out.println("\n--- Sorted by Quick Sort ---");
        for (Order o : quickOrders) System.out.println(o);
    }
}
```

**Performance comparison:** Bubble Sort is O(n²) in average and worst case — every pair is compared repeatedly, making it impractical beyond small order batches. Quick Sort averages O(n log n), which scales far better as order volume grows (e.g., daily sales during a flash sale). Quick Sort is generally preferred because it sorts large datasets much faster in practice, sorts in-place (low memory overhead compared to Merge Sort), and its worst case (O(n²), only with poor pivot choices on already-sorted/adversarial data) can be avoided with randomized or median-of-three pivot selection.

---

## Exercise 4: Employee Management System

**Array representation in memory:** An array stores elements in contiguous memory locations, indexed from 0. Because the address of any element can be computed directly as `baseAddress + index * elementSize`, arrays give O(1) constant-time random access — a major advantage for read-heavy, index-based access patterns. The trade-off is a fixed size (in raw arrays) and costly insert/delete in the middle since subsequent elements must shift.

### `Employee.java`

```java
public class Employee {
    private int employeeId;
    private String name;
    private String position;
    private double salary;

    public Employee(int employeeId, String name, String position, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public int getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public String getPosition() { return position; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return "Employee{id=" + employeeId + ", name='" + name + "', position='" + position + "', salary=" + salary + "}";
    }
}
```

### `EmployeeArrayManager.java`

```java
import java.util.Arrays;

public class EmployeeArrayManager {
    private Employee[] employees;
    private int size;

    public EmployeeArrayManager(int capacity) {
        employees = new Employee[capacity];
        size = 0;
    }

    // Add — O(1) amortized (assuming capacity available)
    public void addEmployee(Employee employee) {
        if (size == employees.length) {
            employees = Arrays.copyOf(employees, employees.length * 2); // grow array — O(n) when triggered
        }
        employees[size++] = employee;
    }

    // Search by ID — O(n)
    public Employee searchEmployee(int employeeId) {
        for (int i = 0; i < size; i++) {
            if (employees[i].getEmployeeId() == employeeId) {
                return employees[i];
            }
        }
        return null;
    }

    // Traverse — O(n)
    public void traverse() {
        for (int i = 0; i < size; i++) {
            System.out.println(employees[i]);
        }
    }

    // Delete by ID — O(n) (find + shift)
    public boolean deleteEmployee(int employeeId) {
        for (int i = 0; i < size; i++) {
            if (employees[i].getEmployeeId() == employeeId) {
                for (int j = i; j < size - 1; j++) {
                    employees[j] = employees[j + 1];
                }
                employees[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }
}
```

### `EmployeeMain.java`

```java
public class EmployeeMain {
    public static void main(String[] args) {
        EmployeeArrayManager manager = new EmployeeArrayManager(5);

        manager.addEmployee(new Employee(1, "Arjun Mehta", "Software Engineer", 65000));
        manager.addEmployee(new Employee(2, "Neha Kapoor", "QA Engineer", 55000));
        manager.addEmployee(new Employee(3, "Rohan Das", "Team Lead", 95000));

        System.out.println("--- All Employees ---");
        manager.traverse();

        System.out.println("\n--- Search Employee ID 2 ---");
        System.out.println(manager.searchEmployee(2));

        System.out.println("\n--- Delete Employee ID 1 ---");
        manager.deleteEmployee(1);
        manager.traverse();
    }
}
```

**Time complexity summary:** Add O(1) amortized (O(n) when resize is triggered), Search O(n), Traverse O(n), Delete O(n).

**Limitations of arrays:** Fixed initial capacity (resizing requires copying, which is expensive), costly insert/delete in the middle (elements must shift), and no built-in key-based lookup. Arrays are best when the dataset size is largely known upfront, access is mostly by index, and reads vastly outnumber structural changes; for a frequently-changing employee roster, a `HashMap<Integer, Employee>` or `ArrayList`/`LinkedList` may be more appropriate.

---

## Exercise 5: Task Management System

**Linked list types:**
- **Singly Linked List:** each node holds data + a reference to the next node only; traversal is one-directional.
- **Doubly Linked List:** each node holds references to both the next *and* previous nodes, enabling bidirectional traversal at the cost of extra memory per node.

### `Task.java`

```java
public class Task {
    private int taskId;
    private String taskName;
    private String status;

    public Task(int taskId, String taskName, String status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
    }

    public int getTaskId() { return taskId; }
    public String getTaskName() { return taskName; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return "Task{id=" + taskId + ", name='" + taskName + "', status='" + status + "'}";
    }
}
```

### `TaskLinkedList.java`

```java
public class TaskLinkedList {

    private static class Node {
        Task task;
        Node next;

        Node(Task task) {
            this.task = task;
        }
    }

    private Node head;
    private int size = 0;

    // Add — O(1) if appending at head, O(n) if appending at tail (used here to preserve order)
    public void add(Task task) {
        Node newNode = new Node(task);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // Search — O(n)
    public Task search(int taskId) {
        Node current = head;
        while (current != null) {
            if (current.task.getTaskId() == taskId) {
                return current.task;
            }
            current = current.next;
        }
        return null;
    }

    // Traverse — O(n)
    public void traverse() {
        Node current = head;
        while (current != null) {
            System.out.println(current.task);
            current = current.next;
        }
    }

    // Delete — O(n)
    public boolean delete(int taskId) {
        if (head == null) return false;

        if (head.task.getTaskId() == taskId) {
            head = head.next;
            size--;
            return true;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.task.getTaskId() == taskId) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public int size() { return size; }
}
```

### `TaskMain.java`

```java
public class TaskMain {
    public static void main(String[] args) {
        TaskLinkedList taskList = new TaskLinkedList();

        taskList.add(new Task(1, "Design database schema", "In Progress"));
        taskList.add(new Task(2, "Set up CI/CD pipeline", "Pending"));
        taskList.add(new Task(3, "Write unit tests", "Pending"));

        System.out.println("--- All Tasks ---");
        taskList.traverse();

        System.out.println("\n--- Search Task ID 2 ---");
        System.out.println(taskList.search(2));

        System.out.println("\n--- Delete Task ID 1 ---");
        taskList.delete(1);
        taskList.traverse();
    }
}
```

**Time complexity:** Add at tail O(n) (O(1) if a tail pointer is maintained), Search O(n), Traverse O(n), Delete O(n).

**Linked lists vs. arrays for dynamic data:** Linked lists grow and shrink without reallocation/copying (no fixed capacity), and insertion/deletion at a known node is O(1) (no shifting of subsequent elements required, unlike arrays). This makes them well suited for task queues where tasks are added and removed frequently and unpredictably, at the cost of O(n) random access (no direct indexing) and extra memory for the `next`/`prev` pointers.

---

## Exercise 6: Library Management System

### `Book.java`

```java
public class Book {
    private int bookId;
    private String title;
    private String author;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }

    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }

    @Override
    public String toString() {
        return "Book{id=" + bookId + ", title='" + title + "', author='" + author + "'}";
    }
}
```

### `BookSearchService.java`

```java
import java.util.Arrays;
import java.util.Comparator;

public class BookSearchService {

    // Linear search by title — O(n), works on unsorted list
    public static Book linearSearchByTitle(Book[] books, String title) {
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title)) {
                return b;
            }
        }
        return null;
    }

    // Binary search by title — O(log n), requires sorted array
    public static Book binarySearchByTitle(Book[] sortedBooks, String title) {
        int low = 0, high = sortedBooks.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = sortedBooks[mid].getTitle().compareToIgnoreCase(title);

            if (cmp == 0) return sortedBooks[mid];
            else if (cmp < 0) low = mid + 1;
            else high = mid - 1;
        }
        return null;
    }

    public static Book[] sortByTitle(Book[] books) {
        Book[] sorted = Arrays.copyOf(books, books.length);
        Arrays.sort(sorted, Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER));
        return sorted;
    }
}
```

### `LibraryMain.java`

```java
public class LibraryMain {
    public static void main(String[] args) {
        Book[] books = {
                new Book(1, "Clean Code", "Robert C. Martin"),
                new Book(2, "Effective Java", "Joshua Bloch"),
                new Book(3, "Design Patterns", "Gang of Four"),
                new Book(4, "Head First Java", "Kathy Sierra"),
                new Book(5, "Refactoring", "Martin Fowler")
        };

        System.out.println("--- Linear Search for 'Refactoring' ---");
        System.out.println(BookSearchService.linearSearchByTitle(books, "Refactoring"));

        Book[] sorted = BookSearchService.sortByTitle(books);
        System.out.println("\n--- Binary Search for 'Effective Java' (sorted) ---");
        System.out.println(BookSearchService.binarySearchByTitle(sorted, "Effective Java"));
    }
}
```

**Comparison:** Linear search is O(n) and requires no ordering, fine for a small library or a card catalog that changes constantly. Binary search is O(log n) but requires the catalog to be kept sorted (O(n log n) each time it needs re-sorting after bulk changes). **Recommendation:** for a small, frequently-updated collection, use linear search; for a large, mostly-static or periodically-reindexed catalog (as most library systems are, since sorting happens once per data refresh, and searches vastly outnumber updates), binary search — or a database index — is far more efficient.

---

## Exercise 7: Financial Forecasting

**Recursion:** A recursive algorithm solves a problem by calling itself on a smaller sub-problem until it reaches a base case. It's a natural fit for financial forecasting because a future value naturally decomposes: `FV(n) = FV(n-1) * (1 + growthRate)`, with `FV(0) = presentValue` as the base case — this mirrors how compounding actually works year over year.

### `FinancialForecast.java`

```java
public class FinancialForecast {

    // Naive recursive future value calculation — O(n) time, O(n) call-stack space
    public static double futureValueRecursive(double presentValue, double growthRate, int years) {
        if (years == 0) {
            return presentValue; // base case
        }
        return futureValueRecursive(presentValue, growthRate, years - 1) * (1 + growthRate);
    }

    // Optimized version using memoization to avoid recomputation across repeated calls
    private static java.util.Map<String, Double> memo = new java.util.HashMap<>();

    public static double futureValueMemoized(double presentValue, double growthRate, int years) {
        if (years == 0) {
            return presentValue;
        }
        String key = presentValue + "-" + growthRate + "-" + years;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        double result = futureValueMemoized(presentValue, growthRate, years - 1) * (1 + growthRate);
        memo.put(key, result);
        return result;
    }

    // Iterative version — O(n) time, O(1) space; best in production for large 'years'
    public static double futureValueIterative(double presentValue, double growthRate, int years) {
        double value = presentValue;
        for (int i = 0; i < years; i++) {
            value *= (1 + growthRate);
        }
        return value;
    }
}
```

### `FinancialForecastMain.java`

```java
public class FinancialForecastMain {
    public static void main(String[] args) {
        double presentValue = 100000;
        double growthRate = 0.08; // 8% annual growth
        int years = 10;

        System.out.println("Recursive Future Value: " + FinancialForecast.futureValueRecursive(presentValue, growthRate, years));
        System.out.println("Memoized Future Value: " + FinancialForecast.futureValueMemoized(presentValue, growthRate, years));
        System.out.println("Iterative Future Value: " + FinancialForecast.futureValueIterative(presentValue, growthRate, years));
    }
}
```

**Time complexity:** The naive recursive solution makes exactly `n` recursive calls, so it's O(n) time and O(n) space (call stack depth) — each call also depends only on the previous year's value, so there's no redundant branching here (unlike, say, naive Fibonacci).

**Optimization discussion:** Because each recursive call directly depends on only the previous result (linear recursion, not tree recursion), the biggest real risk is stack depth for very large `years` values (e.g., forecasting hundreds of years causes `StackOverflowError`). The fixes shown above are: (1) **memoization**, useful when the same `(presentValue, growthRate, years)` combination is requested repeatedly across many forecasts, and (2) converting to an **iterative loop**, which achieves the same O(n) time with O(1) space and no stack-depth risk — generally the preferred approach for this kind of tail-recursive calculation in production code.

---

# Part B — Design Patterns & Principles

## Exercise 1: Singleton Pattern

**Scenario:** A single, shared `Logger` instance across the whole application.

### `Logger.java`

```java
public class Logger {
    private static Logger instance;

    private Logger() {
        // private constructor prevents external instantiation
    }

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}
```

### `SingletonTest.java`

```java
public class SingletonTest {
    public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        logger1.log("Application started");
        logger2.log("Fetching data...");

        System.out.println("Same instance? " + (logger1 == logger2));
    }
}
```

---

## Exercise 2: Factory Method Pattern

### `Document.java`, concrete documents, and the factory

```java
public interface Document {
    void open();
}
```

```java
public class WordDocument implements Document {
    public void open() { System.out.println("Opening Word document..."); }
}
```

```java
public class PdfDocument implements Document {
    public void open() { System.out.println("Opening PDF document..."); }
}
```

```java
public class ExcelDocument implements Document {
    public void open() { System.out.println("Opening Excel document..."); }
}
```

### `DocumentFactory.java`

```java
public abstract class DocumentFactory {
    public abstract Document createDocument();
}
```

```java
public class WordDocumentFactory extends DocumentFactory {
    public Document createDocument() { return new WordDocument(); }
}
```

```java
public class PdfDocumentFactory extends DocumentFactory {
    public Document createDocument() { return new PdfDocument(); }
}
```

```java
public class ExcelDocumentFactory extends DocumentFactory {
    public Document createDocument() { return new ExcelDocument(); }
}
```

### `FactoryMethodTest.java`

```java
public class FactoryMethodTest {
    public static void main(String[] args) {
        DocumentFactory wordFactory = new WordDocumentFactory();
        Document word = wordFactory.createDocument();
        word.open();

        DocumentFactory pdfFactory = new PdfDocumentFactory();
        Document pdf = pdfFactory.createDocument();
        pdf.open();

        DocumentFactory excelFactory = new ExcelDocumentFactory();
        Document excel = excelFactory.createDocument();
        excel.open();
    }
}
```

---

## Exercise 3: Builder Pattern

### `Computer.java`

```java
public class Computer {
    private final String cpu;
    private final String ram;
    private final String storage;
    private final String gpu; // optional

    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.gpu = builder.gpu;
    }

    @Override
    public String toString() {
        return "Computer{cpu='" + cpu + "', ram='" + ram + "', storage='" + storage + "', gpu='" + gpu + "'}";
    }

    public static class Builder {
        private String cpu;
        private String ram;
        private String storage;
        private String gpu = "Integrated Graphics"; // default

        public Builder setCPU(String cpu) {
            this.cpu = cpu;
            return this;
        }

        public Builder setRAM(String ram) {
            this.ram = ram;
            return this;
        }

        public Builder setStorage(String storage) {
            this.storage = storage;
            return this;
        }

        public Builder setGPU(String gpu) {
            this.gpu = gpu;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }
}
```

### `BuilderTest.java`

```java
public class BuilderTest {
    public static void main(String[] args) {
        Computer officePC = new Computer.Builder()
                .setCPU("Intel i5")
                .setRAM("16GB")
                .setStorage("512GB SSD")
                .build();

        Computer gamingPC = new Computer.Builder()
                .setCPU("Intel i9")
                .setRAM("32GB")
                .setStorage("2TB SSD")
                .setGPU("NVIDIA RTX 4080")
                .build();

        System.out.println(officePC);
        System.out.println(gamingPC);
    }
}
```

---

## Exercise 4: Adapter Pattern

### `PaymentProcessor.java`

```java
public interface PaymentProcessor {
    void processPayment(double amount);
}
```

### Adaptee classes (third-party gateways with incompatible interfaces)

```java
public class StripeGateway {
    public void makeStripePayment(double amountInUSD) {
        System.out.println("Processing payment of $" + amountInUSD + " via Stripe");
    }
}
```

```java
public class PaypalGateway {
    public void sendPaypalPayment(double amount) {
        System.out.println("Processing payment of $" + amount + " via PayPal");
    }
}
```

### Adapters

```java
public class StripeAdapter implements PaymentProcessor {
    private StripeGateway stripeGateway;

    public StripeAdapter(StripeGateway stripeGateway) {
        this.stripeGateway = stripeGateway;
    }

    public void processPayment(double amount) {
        stripeGateway.makeStripePayment(amount);
    }
}
```

```java
public class PaypalAdapter implements PaymentProcessor {
    private PaypalGateway paypalGateway;

    public PaypalAdapter(PaypalGateway paypalGateway) {
        this.paypalGateway = paypalGateway;
    }

    public void processPayment(double amount) {
        paypalGateway.sendPaypalPayment(amount);
    }
}
```

### `AdapterTest.java`

```java
public class AdapterTest {
    public static void main(String[] args) {
        PaymentProcessor stripe = new StripeAdapter(new StripeGateway());
        PaymentProcessor paypal = new PaypalAdapter(new PaypalGateway());

        stripe.processPayment(150.00);
        paypal.processPayment(75.50);
    }
}
```

---

## Exercise 5: Decorator Pattern

### `Notifier.java`

```java
public interface Notifier {
    void send(String message);
}
```

### `EmailNotifier.java`

```java
public class EmailNotifier implements Notifier {
    public void send(String message) {
        System.out.println("Sending EMAIL notification: " + message);
    }
}
```

### `NotifierDecorator.java`

```java
public abstract class NotifierDecorator implements Notifier {
    protected Notifier wrappedNotifier;

    public NotifierDecorator(Notifier notifier) {
        this.wrappedNotifier = notifier;
    }

    public void send(String message) {
        wrappedNotifier.send(message);
    }
}
```

### `SMSNotifierDecorator.java`

```java
public class SMSNotifierDecorator extends NotifierDecorator {
    public SMSNotifierDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("Sending SMS notification: " + message);
    }
}
```

### `SlackNotifierDecorator.java`

```java
public class SlackNotifierDecorator extends NotifierDecorator {
    public SlackNotifierDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("Sending SLACK notification: " + message);
    }
}
```

### `DecoratorTest.java`

```java
public class DecoratorTest {
    public static void main(String[] args) {
        Notifier notifier = new EmailNotifier();
        notifier = new SMSNotifierDecorator(notifier);
        notifier = new SlackNotifierDecorator(notifier);

        // Sends via Email + SMS + Slack, all dynamically stacked
        notifier.send("Server deployment completed successfully.");
    }
}
```

---

## Exercise 6: Proxy Pattern

### `Image.java`

```java
public interface Image {
    void display();
}
```

### `RealImage.java`

```java
public class RealImage implements Image {
    private String fileName;

    public RealImage(String fileName) {
        this.fileName = fileName;
        loadFromRemoteServer(fileName); // expensive operation happens on construction
    }

    private void loadFromRemoteServer(String fileName) {
        System.out.println("Loading " + fileName + " from remote server...");
    }

    public void display() {
        System.out.println("Displaying " + fileName);
    }
}
```

### `ProxyImage.java`

```java
public class ProxyImage implements Image {
    private RealImage realImage; // lazily initialized
    private String fileName;

    public ProxyImage(String fileName) {
        this.fileName = fileName;
    }

    public void display() {
        if (realImage == null) {
            realImage = new RealImage(fileName); // load + cache only on first use
        }
        realImage.display(); // subsequent calls reuse the cached instance
    }
}
```

### `ProxyTest.java`

```java
public class ProxyTest {
    public static void main(String[] args) {
        Image image = new ProxyImage("landscape.png");

        System.out.println("Image object created, but not loaded yet.");

        image.display(); // triggers remote load, then displays
        image.display(); // uses cached image, no reload
    }
}
```

---

## Exercise 7: Observer Pattern

### `Stock.java`

```java
public interface Stock {
    void registerObserver(Observer observer);
    void deregisterObserver(Observer observer);
    void notifyObservers();
}
```

### `Observer.java`

```java
public interface Observer {
    void update(String symbol, double price);
}
```

### `StockMarket.java`

```java
import java.util.ArrayList;
import java.util.List;

public class StockMarket implements Stock {
    private List<Observer> observers = new ArrayList<>();
    private String symbol;
    private double price;

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void deregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(symbol, price);
        }
    }

    public void setStockPrice(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
        notifyObservers();
    }
}
```

### `MobileApp.java` / `WebApp.java`

```java
public class MobileApp implements Observer {
    public void update(String symbol, double price) {
        System.out.println("[Mobile App] " + symbol + " is now $" + price);
    }
}
```

```java
public class WebApp implements Observer {
    public void update(String symbol, double price) {
        System.out.println("[Web App] " + symbol + " is now $" + price);
    }
}
```

### `ObserverTest.java`

```java
public class ObserverTest {
    public static void main(String[] args) {
        StockMarket stockMarket = new StockMarket();

        Observer mobileApp = new MobileApp();
        Observer webApp = new WebApp();

        stockMarket.registerObserver(mobileApp);
        stockMarket.registerObserver(webApp);

        stockMarket.setStockPrice("INFY", 1520.75);

        stockMarket.deregisterObserver(webApp);
        stockMarket.setStockPrice("INFY", 1535.10); // only mobileApp is notified now
    }
}
```

---

## Exercise 8: Strategy Pattern

### `PaymentStrategy.java`

```java
public interface PaymentStrategy {
    void pay(double amount);
}
```

### `CreditCardPayment.java` / `PayPalPayment.java`

```java
public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using Credit Card ending in " + cardNumber.substring(cardNumber.length() - 4));
    }
}
```

```java
public class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using PayPal account " + email);
    }
}
```

### `PaymentContext.java`

```java
public class PaymentContext {
    private PaymentStrategy strategy;

    public PaymentContext(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void executePayment(double amount) {
        strategy.pay(amount);
    }
}
```

### `StrategyTest.java`

```java
public class StrategyTest {
    public static void main(String[] args) {
        PaymentContext context = new PaymentContext(new CreditCardPayment("4111111111111234"));
        context.executePayment(2500.00);

        context.setStrategy(new PayPalPayment("jasleen@example.com"));
        context.executePayment(999.99);
    }
}
```

---

## Exercise 9: Command Pattern

### `Command.java`

```java
public interface Command {
    void execute();
}
```

### `Light.java` (Receiver)

```java
public class Light {
    public void on() {
        System.out.println("Light is ON");
    }

    public void off() {
        System.out.println("Light is OFF");
    }
}
```

### `LightOnCommand.java` / `LightOffCommand.java`

```java
public class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.on();
    }
}
```

```java
public class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.off();
    }
}
```

### `RemoteControl.java` (Invoker)

```java
public class RemoteControl {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        command.execute();
    }
}
```

### `CommandTest.java`

```java
public class CommandTest {
    public static void main(String[] args) {
        Light livingRoomLight = new Light();
        Command lightOn = new LightOnCommand(livingRoomLight);
        Command lightOff = new LightOffCommand(livingRoomLight);

        RemoteControl remote = new RemoteControl();

        remote.setCommand(lightOn);
        remote.pressButton();

        remote.setCommand(lightOff);
        remote.pressButton();
    }
}
```

---

## Exercise 10: MVC Pattern

### `Student.java` (Model)

```java
public class Student {
    private String name;
    private String id;
    private String grade;

    public Student(String name, String id, String grade) {
        this.name = name;
        this.id = id;
        this.grade = grade;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getId() { return id; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}
```

### `StudentView.java` (View)

```java
public class StudentView {
    public void displayStudentDetails(String name, String id, String grade) {
        System.out.println("Student Details:");
        System.out.println("  ID: " + id);
        System.out.println("  Name: " + name);
        System.out.println("  Grade: " + grade);
    }
}
```

### `StudentController.java` (Controller)

```java
public class StudentController {
    private Student model;
    private StudentView view;

    public StudentController(Student model, StudentView view) {
        this.model = model;
        this.view = view;
    }

    public void setStudentName(String name) {
        model.setName(name);
    }

    public void setStudentGrade(String grade) {
        model.setGrade(grade);
    }

    public void updateView() {
        view.displayStudentDetails(model.getName(), model.getId(), model.getGrade());
    }
}
```

### `MVCTest.java`

```java
public class MVCTest {
    public static void main(String[] args) {
        Student model = new Student("Jasleen Singh", "STU2026001", "A");
        StudentView view = new StudentView();
        StudentController controller = new StudentController(model, view);

        controller.updateView();

        controller.setStudentGrade("A+");
        controller.updateView();
    }
}
```

---

## Exercise 11: Dependency Injection

### `CustomerRepository.java`

```java
public interface CustomerRepository {
    String findCustomerById(int id);
}
```

### `CustomerRepositoryImpl.java`

```java
import java.util.HashMap;
import java.util.Map;

public class CustomerRepositoryImpl implements CustomerRepository {
    private Map<Integer, String> customers = new HashMap<>();

    public CustomerRepositoryImpl() {
        customers.put(1, "Jasleen Singh");
        customers.put(2, "Riddhi Raj Singh");
        customers.put(3, "Karan Verma");
    }

    public String findCustomerById(int id) {
        return customers.getOrDefault(id, "Customer not found");
    }
}
```

### `CustomerService.java`

```java
public class CustomerService {
    private final CustomerRepository customerRepository;

    // Constructor injection — dependency is provided from outside, not created internally
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public String getCustomerName(int id) {
        return customerRepository.findCustomerById(id);
    }
}
```

### `DependencyInjectionTest.java`

```java
public class DependencyInjectionTest {
    public static void main(String[] args) {
        CustomerRepository repository = new CustomerRepositoryImpl();
        CustomerService service = new CustomerService(repository); // injected here

        System.out.println("Customer 1: " + service.getCustomerName(1));
        System.out.println("Customer 2: " + service.getCustomerName(2));
        System.out.println("Customer 5: " + service.getCustomerName(5));
    }
}
```

**Why this matters:** `CustomerService` depends only on the `CustomerRepository` interface, not the concrete `CustomerRepositoryImpl`. Because the implementation is passed in (injected) rather than instantiated with `new` inside the service, you can swap in a different implementation (e.g., a mock for unit testing, or a database-backed repository) without changing `CustomerService` at all — this is the core benefit of Dependency Injection and the Dependency Inversion Principle.

---

## Suggested Repository Structure

```
java-dsa-and-design-patterns/
├── README.md                          <- this file
├── algorithms-data-structures/
│   ├── ex1-inventory-management/
│   ├── ex2-ecommerce-search/
│   ├── ex3-sorting-orders/
│   ├── ex4-employee-management/
│   ├── ex5-task-management/
│   ├── ex6-library-management/
│   └── ex7-financial-forecasting/
└── design-patterns/
    ├── ex1-singleton/
    ├── ex2-factory-method/
    ├── ex3-builder/
    ├── ex4-adapter/
    ├── ex5-decorator/
    ├── ex6-proxy/
    ├── ex7-observer/
    ├── ex8-strategy/
    ├── ex9-command/
    ├── ex10-mvc/
    └── ex11-dependency-injection/
```

Push to GitHub:

```bash
git init
git add .
git commit -m "Add DSA and Design Patterns exercise solutions"
git branch -M main
git remote add origin https://github.com/jasleencodeSnow/java-dsa-and-design-patterns.git
git push -u origin main
```
