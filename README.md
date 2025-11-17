# Lab 3: Android Product Manager with SQLite

This is an Android application developed for SEG2105 Lab 3. The app serves as a simple product management system that uses a local SQLite database to store and manage product information.

## Features

This application allows users to perform the following CRUD (Create, Read, Update, Delete) operations for products:

*   **View All Products**: On startup, the app displays a list of all products currently stored in the database.
*   **Add a Product**: Users can add a new product by providing its name and price.
*   **Find a Product**: The app provides flexible search functionality:
    *   Find by **Product Name**: Filters the list to show products whose names start with the entered text.
    *   Find by **Product Price**: Filters the list to show products with an exact price match.
    *   Find by **Name and Price**: Combines the two filters to find products that match both the name (starts with) and the exact price.
*   **Delete a Product**: Users can delete a product by entering its exact name. The deletion is case-sensitive.
*   **Persistent Storage**: The app comes pre-populated with two items ("Laptop" and "Wireless Mouse") to demonstrate that the data persists between app launches.

## Technologies Used

*   **Language**: Java
*   **Framework**: Android SDK
*   **Database**: SQLite (using Android's built-in `SQLiteOpenHelper`)
*   **IDE**: Android Studio

## Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

*   Java JDK 11 or newer
*   Android Studio with Android SDK installed
*   An Android Emulator or a physical Android device

### Installation

1.  Clone the repo:
    ```sh
    git clone https://github.com/TheSai9/SEG2105C_Group6_Lab3.git
    ```
2.  Open the project in Android Studio.
3.  Build the project and run it on an emulator or a physical device.

## Testing

This project includes unit tests to ensure the reliability of the core database logic found in the `MyDBHandler` class. The tests are written using JUnit and the Robolectric framework, which allows Android-specific code to be tested on a local JVM without needing an emulator.

### Test Cases

Two main test cases have been implemented in `MyDBHandlerTest.java`:

1.  **`testAddProduct_Success`**: This test verifies that a new product can be successfully added to the database. It adds a product and then immediately tries to find it, asserting that the product exists.
2.  **`testDeleteProduct_Success`**: This test confirms that the delete functionality works as expected. It first adds a product, then deletes it, and finally asserts that the delete operation was successful.

### How to Run the Tests

1.  Open the project in Android Studio.
2.  Navigate to `app/src/test/java/com/example/lab3/MyDBHandlerTest.java`.
3.  Right-click on the `MyDBHandlerTest` class and select **Run 'MyDBHandlerTest'**.
4.  The test results will be displayed in the run terminal. All tests should pass.

## Deliverables

This project fulfills the requirements for Lab 3 and the testing lab, including a video demonstration of the following actions:
- Viewing pre-populated items on launch.
- Adding a new item.
- Finding an item by name only, by price only, and by both.
- Deleting an existing item.
- Explaining and running the JUnit tests, showing both passing and failing cases.

### Demo Video
https://github.com/user-attachments/assets/9fd50cc4-d203-400a-989e-3c225e0490b5
