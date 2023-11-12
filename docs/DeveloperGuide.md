---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

We would like to acknowledge the following libraries for use in Spend n Split:

* **[JavaFX](https://openjfx.io/)**: The GUI framework of Spend n Split.

* **[Jackson](https://github.com/FasterXML/jackson)**: The Java JSON library for parsing and creating JSON for Spend n Split.

* **[JUnit 5](https://junit.org/junit5/)**: The Java testing framework of Spend n Split.

* **[Checkstyle](https://docs.gradle.org/current/userguide/checkstyle_plugin.html)**: The Gradle plugin that ensures consistent and appropriate code style.

* **[Shadow](https://github.com/johnrengelman/shadow)**: The Gradle plugin for creating fat JARs for Spend n Split.

* **[Poppins Font](https://fonts.google.com/specimen/Poppins)**: The font used in Spend n Split.



--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object) and all `Transaction` objects (which are contained in a `UniqueTransactionList` object).
* stores the currently 'selected' `Person` and `Transaction` objects (e.g., results of a search query) as separate _filtered_ lists which are exposed to outsiders as unmodifiable `ObservableList<Person>` and `ObservableList<Transaction>` lists that can be 'observed' e.g. the UI can be bound to these lists so that the UI automatically updates when the data in the lists change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Person-related Features

#### Adding Persons

#### Editing Persons

#### Deleting Persons

#### Filtering Persons

#### Sorting Persons

### Transaction-related Features

#### Adding Transactions

##### Settling Balances

#### Editing Transactions

Editing transactions mechanism is facilitated by `EditTransactionCommand`. It extends `Command` with the ability to edit a transaction.

It consists of the following classes:

* `EditTransactionCommand`— Represents a command to edit a transaction.
* `EditTransactionCommandParser`— Parses user input into a `EditTransactionCommand`.
* `EditTransactionDescriptor`— Stores the details to edit the transaction with. Each non-empty field value will replace the corresponding field value of the transaction.

<img src="images/EditTransactionCommandDiagram.png" width="550" />

**Note**: The above class diagram is to be updated to reflect the new implementation, with the addition of `UpdateExpenseCommand`.

Upon execution, `EditTransactionCommand` will retrieve the transaction to be edited from `filteredTransactionList` from `Model`, create a copy of the `Transaction` object with the new details, then replace the old `Transaction` object with the new one in `filteredTransactionList`.

We chose this method of execution instead of directly editing the `Transaction` object in `filteredTransactionList` because `Model` re-renders the UI only when `filteredTransactionList` is updated. If we were to edit the `Transaction` object directly, `Model` would not be able to detect the change and re-render the UI.

##### Updating Portions

#### Deleting Transactions

#### Duplicating Transactions

#### Filtering Transactions

### Other Features

#### Setting Shorthands

#### Clearing App Data

#### Accessing Help

#### Exiting the App

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope


**Target User Profile**: University students who live in hall
* Needs to manage a significant number of contacts
* Often splits expenses with other hallmates (for groceries, meals etc.)
* Have friend groups with whom they hang out more frequently
* Often has their laptop on hand and prefer desktop apps
* Used to typing fast on their laptop
* Is reasonably comfortable using CLI apps

**Value Proposition**: The app will allow users to seamlessly keep track of the money they owe/are owed, with regards to borrowers/lenders and their expenses. It is CLI-based and manages contacts faster than a typical mouse/GUI driven app.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                                    | So that I can…​                                                        |
|----------|--------------------------------------------|-------------------------------------------------|------------------------------------------------------------------------|
| `* * *`  | new user                                   | see usage instructions                          | refer to instructions when I forget how to use the app                 |
| `* * *`  | user                                       | add a new person                                |                                                                        |
| `* * *`  | user                                       | delete a person                                 | remove entries that I no longer need                                   |
| `* * *`  | user                                       | find a person by name                           | locate details of persons without having to go through the entire list |
| `* * *`  | user                                       | add transactions under a specific person        |                                                                        |
| `* * *`  | user                                       | add group transactions                          | easily add transactions that are split among a group                   |
| `* *`    | user                                       | edit transactions                               | change incorrectly added entries                                       |
| `* * *`  | user                                       | delete transactions                             | remove incorrectly added entries                                       |
| `* * *`  | user                                       | view balance with each person                   | know how much they owe me                                              |
| `* *`    | user                                       | settle balances                                 | simulate clearing of debt                                              |
| `* * *`  | user                                       | list all my transactions with a specific person | remind myself of why a specific person's balance is the way it is      |
| `* *`    | user with many persons in the address book | sort persons by balance                         | know from whom I should collect money                                  |
| `*`      | user                                       | hide private contact details                    | minimise chance of someone else seeing them by accident                |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `Spend n Split` and the **Actor** is the `user`, unless specified otherwise)

---

**Use Case: UC1 - Listing all Persons**

**MSS**

1. User requests to view all contacts in the contact list.
2. Spend n Split shows a list of contacts.

Use case ends.

Extensions:
* 1a. The user can include a flag to sort the list by name.
  * 1a1. Spend n Split shows the list of contacts now sorted by name.
  * 1a2. Use case resumes at step 4.

---

**Use Case: UC2 - Adding a New Person**

**MSS**

1. User requests to add a new person.
2. Spend n Split informs the user that the new person has been added.
3. Spend n Split shows the updated contact list.

Use case ends.

Extensions:
* 1a. Spend n Split detects that the person already exists in the contact list.
  * 1a1. Spend n Split informs the user that the person already exists in the contact list.
  * 1a2. Use case resumes at step 1.
* 1b. Spend n Split detects an error in the entered data for the new person.
  * 1b1. Spend n Split informs the user that the data entered is invalid.
  * 1b2. Use case resumes at step 1.

---

**Use Case: UC3 - Editing a Person**

**MSS**

1. User requests to edit a person.
2. Spend n Split informs the user that the person has been edited.
3. Spend n Split shows the updated contact list.

Use case ends.

Extensions:
* 1a. Spend n Split detects that the person does not exist in the contact list.
  * 1a1. Spend n Split informs the user that the person does not exist in the contact list.
  * 1a2. Use case resumes at step 1.
* 1b. Spend n Split detects an error in the entered data for the person.
  * 1b1. Spend n Split informs the user that the data entered is invalid.
  * 1b2. Use case resumes at step 1.

---

**Use Case: UC4 - Deleting a Person**

**MSS**

1. User requests to delete a person.
2. Spend n Split informs the user that the person has been deleted.
3. Spend n Split shows the updated contact list.

Use case ends.

Extensions:
* 1a. Spend n Split detects that the person does not exist in the contact list.
  * 1a1. Spend n Split informs the user that the person does not exist in the contact list.
  * 1a2. Use case resumes at step 1.

---

**Use Case: UC5 - Finding a Person**

**MSS**

1. User requests to find a person.
2. Spend n Split shows the list of persons that match the search query.

Use case ends.

**Use Case: UC# - Clear All Persons**

TODO: Internal note: let's remove / amend this feature, this command is too powerful and destructive.

---

**Use Case: UC6 - Listing all Transactions with a Person**

Preconditions: Person exists in the contact list.

**MSS**

1. User requests to view the transaction list involving a person.
2. Spend n Split shows the list of transactions involving that person.

Use case ends.

Extensions:
* 1a. Person does not exist in the contact list.
  * 1a1. Spend n Split informs the user that the person does not exist in the contact list.
  * 1a2. Use case resumes at step 1.

---

**Use Case: UC7 - Add a New Transaction**

Preconditions: Person exists in the contact list.

**MSS**

1. User requests to add a new transaction.
2. Spend n Split informs the user that the new transaction has been added.
3. Spend n Split shows the updated transaction list.

Use case ends.

Extensions:
* 1a. Person does not exist in the contact list.
  * 1a1. Spend n Split informs the user that the person does not exist in the contact list.
  * 1a2. Use case resumes at step 1.

* 1b. Spend n Split detects an error in the request for the new person.
  * 1b1. Spend n Split informs the user that the request is invalid.
  * 1b2. Use case resumes at step 1.

---

**Use Case: UC8 - Edit a Transaction**

Preconditions: Transaction exists in the transaction list.

**MSS**

1. User requests to view the transaction list.
2. Spend n Split shows a list of transaction.
3. User requests to edit a transaction.
4. Spend n Split informs the user that the transaction has been edited.
5. Spend n Split shows the updated transaction list.

Use case ends.

Extensions:
* 4a. Transaction does not exist in the portion list.
  * 4a1. Spend n Split informs the user that the transaction does not exist in the transaction list.
  * 4a2. Use case resumes at step 3.

* 4b. Spend n Split detects an error in the request.
  * 4b1. Spend n Split informs the user that request is invalid.
  * 4b2. Use case resumes at step 3.

---

**Use Case: UC9 - Settle with a person**

Preconditions: Person exists in the contact list.

**MSS**

1. User requests to settle with a person.
2. Spend n Split informs the user that all the outstanding balance with the person have been settled.
3. Spend n Split shows the list of contacts.

Use case ends.

Extensions:
* 1a. Person does not exist in the contact list.
  * 1a1. Spend n Split informs the user that the person does not exist in the contact list.
  * 1a2. Use case resumes at step 2.
* 1b. User does not have an outstanding balance with the person.
  * 1b1. Spend n Split informs the user that there is no outstanding balance with that person.
  * 1b2. Use case resumes at step 3.
---

**Use Case: UC10 - Delete a Transaction**

Preconditions: Transaction exists in the transaction list.

**MSS**

1. User requests to view the transaction list.
2. Spend n Split shows a list of transactions.
3. User requests to delete a transaction.
4. Spend n Split informs the user that the transaction has been deleted.
5. Spend n Split shows the updated transaction list.

Use case ends.

Extensions:
* 3a. Transaction does not exist in the portion list.
    * 3a1. Spend n Split informs the user that the transaction does not exist in the transaction list.
    * 3a2. Use case resumes at step 2.

---

### Non-Functional Requirements

1.  The application should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  The application should be able to hold up to 1000 people without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e, not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The application should work without internet access.
5.  The application should not take more than 50MB of space.
6.  The user interface should be intuitive to new users. From the interface, users should easily find out how to write input, view output, and find the help guide.
7.  The application should be accurate when calculating portions, being able to handle the division of costs with precision.
8.  The application should store data to the hard disk consistently so that loading the data on a different device leads to the same application state.
9.  The GUI should organise and present data clearly so that users are able to read application details easily.

### Glossary

**Application**

#### Address
The particulars of the place where a person lives.
#### Alphanumeric
Consisting of only letters (a-z, A-Z) or numbers or both.
#### Amount
The total value of the transaction.
#### Balance
The amount of money that you owe a person or the amount of money that they owe you. A positive balance under a person
means that they owe you money, whereas a negative balance means that you owe them money.
#### Card
A rectangular area in our application that either describes the full details of a transaction or person.
#### Command
To use and control the application, commands are necessary. To utilise a feature, a command has to be typed into the Command Input Field and executed.
#### Cost
The total value of the transaction.
#### Description
A written account of a transaction, used to provide context and details about the transaction.
#### Email address
A unique identifier for an email account. It identifies an email box to which messages and emails are delivered.
#### Field
An area where text can be input.
#### Flag
Flags are used to modify the operation of a command.
#### Gui
Gui stands for graphical user interface. A graphical user interface uses icons and mouse inputs from users to allow them to interact with the application.
#### Index
A number representing the position of an item in a list.
#### Integer
A number that is a not a fraction. E.g. whole numbers such as -10, 0, 1, 5/
#### Keyword
An important word that is used by the application to process various commands.
#### Name
A word or set of words that are used to address or refer to a person.
#### Negative
A numerical value that is less than 0.
#### Parameter
An additional input that provides further details on a command that a user is executing.
#### Payee
Refers to the person that paid for the transaction.
#### Payer
Refers to a person that owes the payee money for the transaction.
#### Person
A human being.
#### Phone number
A sequence of digits that is dialled on a telephone to contact a person.
#### Portion
A subset of a transaction. A transaction is split into multiple portions.
#### Positive
A numerical value that is greater than 0.
#### Settle
Refers to the act of two people exchanging money, such that their respective balances with each other equal zero after the transaction.
#### Shorthand
A shortened version of a command.
#### Tag
A label that can be applied to a person to provide additional details about them.
#### Telegram handle
A unique identifier for a telegram account.
#### Timestamp
Refers to the time the transaction occurred. Timestamps in our application are displayed in the "DD/MM/YYYY HH:MM" format. DD refers to Day, MM refers to Month,
YYYY refers to Year, HH refers to Hour, and MM refers to Minute.
#### Transaction
An interaction of buying or selling something where the exchange of money occurs.
#### Weight
A numerical value assigned to each person that is involved in a transaction. It is used to calculate the amount of money that
a person should pay for their share of the transaction.

**General**
#### Mainstream OS
Refers to operating systems such as Windows, Linux, Unix, OS-X.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Deleting a transaction

1. Deleting a transaction while all transactions are being shown

    1. Prerequisites: There already exist some transactions in the application.

    1. Test case: `deleteTransaction 1`<br>
       Expected: First transaction is deleted from the list. Details of the deleted transaction shown in the status message. Timestamp in the status bar is updated.

    1. Test case: `delete 0`<br>
       Expected: No transaction is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `deleteTransaction`, `deleteTransaction x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

### Stronger List Command Input Validation

- **Background**: Currently, the `Name` of our `Person` is currently validated.
For example, it cannot contain symbols and cannot begin with a whitespace.
However, the `listPerson` validation on input arguments is insufficient.

- **Issue**: Executing `listPerson $$$` shows "0 person(s) listed" with no error.
This is not the right behaviour as `$$$` will never be included in a `Name`, and an
error message should be shown instead.

- **Enhancement**: We plan on ensuring `listPerson` performs stronger validation on
its input, invalidating any arguments cannot exist in a `Name`. For instance, each
command argument in `listPerson` should be validated to be alphanumeric. Similarly,
`listTransaction` should also validate the arguments in a similar fashion. Should
the input arguments fail validation, we plan to show an error message as shown below.

Sample Execution:
```
$ listPerson $$$

Names in should only contain alphanumeric characters
Example: listPerson Alex David
```

### Improved Space Sensitivity in Names

- **Background**: Currently, the `Name` of our `Person` is currently able to trim
leading and trailing spaces, allowing us to treat `Alex Yeoh` and `&nbsp;&nbsp;Alex Yeoh&nbsp;&nbsp;`
as the same `Name`. However, the `Name` is sensitive to spaces in between. This results
in `Alex Yeoh` and `Alex&nbsp;&nbsp;&nbsp;Yeoh` being treated as different names.

- **Issue**: Suppose a `Person` named `Alex Yeoh` exists in our application
  (and no one is named `Alex&nbsp;&nbsp;&nbsp;Yeoh` with the additional spaces in between).
Executing `addTransaction d= n=Alex&nbsp;&nbsp;&nbsp;Yeoh ...` will show an error of
"This transaction involves unknown parties; please set them to 'Others'" even if all
other names correctly refer to people in the application. This is not the right behaviour
as our application should be able to detect that `Alex Yeoh` and `Alex&nbsp;&nbsp;&nbsp;Yeoh`
are equivalent.

- **Enhancement**: We plan on ensuring that our application collapses multiple spaces
between individual names such that names like `Alex Yeoh` and `Alex&nbsp;&nbsp;&nbsp;Yeoh`
can be considered to refer to the same `Name`.

### Enhanced Timestamp Validation Messages
- **Background**: Many of the transaction-related commands, such as `addTransaction` and `editTransaction`
may involve a `Timestamp` input. Currently, we validate our `Timestamp` through a combination of regex
and checking of `DateTimeParseException` from `LocalDateTime.parse(datetime)`. Currently, if
any validation fails, we show the following error message.

> Date must be in DD/MM/YYYY format and time must be in HH:MM format; date should
come before time with a single space separating them if both are provided

- **Issue**: This error message is too general, and does not effectively communicate
to the user why the date is invalid, making it difficult for the user to correct
the `Timestamp` input in the transaction-related commands.

- **Enhancement**: We plan to make our `Timestamp` validation error messages
more specific in order to let the user know how they can correct their input. Specifically,
we plan on covering these 5 cases:
1. Incorrect Date Format
2. Incorrect Time Format
3. Incorrect Date and Time Format (e.g. `25/06/2023 + 11:30` didn't separate the date and time
with a single space)
4. Invalid Date (e.g. `32/13/2023` has an invalid day and month)
5. Invalid Time (e.g. `25/06/2023 25:61` has an invalid time)

If there are multiple issues in the input, the error message will be prioritised based
on the order above.

Below are examples of the enhanced error messages in commands with `Timestamp` input.
The `Timestamp` input comes after `ts=` in the `addTransaction` command.

Example 1: Incorrect Date Format
```
$ addTransaction d=Bread n=John c=10 ts=abcd n=Self w=1

Date must be in DD/MM/YYYY format with leading zeroes in the date, month and year,
and forward slashes as separators (e.g. 25/06/2020).
Example: addTransaction d=Bread n=John c=10 ts=25/06/2020 n=Self w=1
```

Example 2: Incorrect Time Format
```
$ addTransaction d=Bread n=John c=10 ts=25/06/2023 abcd n=Self w=1

Time must be in HH:MM format with leading zeroes in the hours and minutes,
and a colon separating the hours and minutes (e.g. 09:05).
Example: addTransaction d=Bread n=John c=10 ts=25/06/2020 09:05 n=Self w=1
```

Example 3: Incorrect Date and Time Format
```
$ addTransaction d=Bread n=John c=10 ts=25/06/2023 + 11:30 n=Self w=1

The date and time must be in the format 'DD/MM/YYYY HH:MM' with a single space
between the date and time (e.g. 25/06/2020 09:05).
Example: addTransaction d=Bread n=John c=10 ts=25/06/2020 09:05 n=Self w=1
```

Example 4: Invalid Date
```
$ addTransaction d=Bread n=John c=10 ts=32/13/2023 n=Self w=1

The date entered does not exist. Please use a valid date in DD/MM/YYYY format.
Example: addTransaction d=Bread n=John c=10 ts=25/06/2020 n=Self w=1
```

Example 5: Invalid Time
```
$ addTransaction d=Bread n=John c=10 ts=25/06/2023 25:61 n=Self w=1

The time you entered does not exist. Please use a valid time in HH:MM format between
00:00 to 23:59.
Example: addTransaction d=Bread n=John c=10 ts=25/06/2020 09:05 n=Self w=1
```
