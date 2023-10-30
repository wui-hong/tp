---
layout: page
title: User Guide
---

## Welcome to Spend n Split!

***Taking care of your finances has never been easier!***

Spend n Split (SnS) is an application for managing transactions from your contacts list. Built for university students 
that reside on campus, it utilises your fast typing skills to help you maintain financial accountability between 
yourself and your peers. All you need to do, is to record your transactions in Spend n Split. Filtering, sorting, as
 well as the calculations of balances owed will be automatically handled by Spend n Split.
* Table of Contents

{:toc}

---

## Quick Start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `spendnsplit.jar` from [here](https://github.com). (Coming soon)

3. Copy the file to the folder you want to use as the _home folder_ for your Spend N Split.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar spendnsplit.jar`
   command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will
   open the help window.<br>
   Some example commands you can try:

    * `listPerson` : Lists all persons.

    * `listTransaction` : Lists all transactions.

    * `exit` : Exits the app.

6. Learn more about navigating the app in the [Navigating the App](#navigating-the-app) section below.
7. Learn more about the commands in the [Features](#features) section below.

---

## Navigating the App

Spend n Split has an intuitive Graphical User Interface (GUI) that allows you to navigate the app easily.

![](images/user-guide/labelledUi.png)

| Component                  | Description                                                                                                                |
|----------------------------|----------------------------------------------------------------------------------------------------------------------------|
| **Command Input Field**    | Type commands here and press `Enter` to execute them.                                                                      |
| **Command Output Display** | Shows the result of the command execution.                                                                                 |
| **Transaction List**       | Shows a list of transactions. <br/> The list can be filtered and sorted by the user.                                       |
| **Transaction Card**       | Shows the details of a transaction. <br/> Details include the description, date, payee, as well as the breakdown of costs. |
| **Person List**            | Shows a list of persons. <br/> The list can be filtered and sorted by the user.                                            |
| **Person Card**            | Shows the details of a person. <br/> Details include name, balance, phone number, etc.                                     |

---

## Features

### __v1.2__

### Adding a transaction: `addTransaction`

Adds a Transaction.
`addTransaction`
Creates a transaction for multiple people with customised split ratios.

Format: `addTransaction d=DETAILS n=NAME c=COST [ts=TIME] [n=NAME w=WEIGHT]...`
- Cost and weights have to be decimal numbers or fractions, and they must be positive.
- The first name refers to the payee (that is the person whom everyone else now owes).
- If the timestamp is not provided, the default time is the current system time.
- If you want to create a weight for yourself, include `n=Self` to refer to yourself.
- At least one pair of name and weight must be provided.
- The cost for each person is calculated as follows:
    - Individual cost = Total Cost * (Individual Weight / Total Weight)

Examples:
* `addTransaction d=Dinner n=Self c=100 n=John w=2 n=Mary w=2 n=Alice w=1`
    * Dinner costed $100 was first paid by self; now John and Mary each owe self $40 (2/5 of $100 each), Alice owes self $20 (1/5 of $100)
* `addTransaction d=Rent n=John c=600 ts=2020-10-10T12:00 n=Self w=1 n=John w=1 n=Mary =w1`
    * Rent costed $600 and was first paid by John at 12 o'clock on 10 October 2020; now self owes John $200 (1/3 of $600)

Sample execution:
```
$ addTransaction d=Dinner n=self c=100 n=John w=2 n=Mary w=2 n=Alice w=1

```
![addTransaction success](images/user-guide/addTransaction1.png)


```
$ addTransaction c=200 d=Textbooks

Invalid command format! 
addTransaction: Adds a transaction to the address book. 
Parameters: d=DESCRIPTION n=NAME c=COST [n=NAME w=WEIGHT] Example: addTransaction d=bread n=John Doe c=25.00 n=Self w=1.5 n=John Doe w=1
```

### Editing a Transaction: `editTransaction`

Edits the transaction at the specified `INDEX`. The index refers to the index number when viewing the TransactionList.
The index **must be a positive integer** 1, 2, 3, ...

Transaction details that can be edited:

* Description
* Cost
* Payee

Format: `editTransaction INDEX [d=DESCRIPTION] [c=COST] [n=PAYEE]`

Examples:

* `editTransaction 1 c=12.12`
* `editTransaction 2 d=Potato n=Bob`
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* A transaction cannot be edited to be "irrelevant".

Sample Execution:

``` 
editTransaction 1 n=Bob c=12.12
```

![editTransaction success](images/user-guide/editTransactionSuccess.png)

### Updating a Portion of a Transaction: `updatePortion`

Updates the portion of a transaction at the specified `INDEX`. The index refers to the index number when viewing the
TransactionList. The index **must be a positive integer** 1, 2, 3, ...

Portion refers to the amount of money that a person owes you for a transaction. \
The portion is calculated based on the cost of the transaction and the proportion of the transaction that the person has
to pay for, which is determined by the `WEIGHT` of the person.

Format: `updatePortion INDEX n=NAME w=WEIGHT`

Examples:

* To add a new person (e.g. Alice) to the transaction:
    * `updatePortion 1 n=Alice w=0.5`


* To edit the weight of an existing person (e.g. Bob) in the transaction:
    * `updatePortion 1 n=Bob w=0.5`


* To remove an existing person (e.g. Bob) from the transaction, set the weight to 0:
    * `updatePortion 1 n=Bob w=0`

Sample Execution:

```
updatePortion 1 n=Alice w=0.5
```

![](images/user-guide/updatePortionSuccess.png)

### Deleting a transaction: `deleteTransaction`

Deletes the specified transaction based on index.

Format: `deleteTransaction INDEX`

* The index refers to the index number shown in the displayed transaction list. The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `deleteTransaction 1`
    * Deletes the first transaction in the list

Sample Execution:

```
$ deleteTransaction 4

Deleted Transaction: Group Project Lunch; Timestamp: 2023-10-13T12:34:56.789; Amount: 60.00; Paid by: Self; Portions: [name: Benson Meier, weight: 4.00][name: Alice Pauline, weight: 2.00]
```

![](images/user-guide/deleteTransaction.png)

### Listing transactions: `listTransaction`

Shows a list of transactions that includes the specified people. If no people are specified, all transactions will be shown.

Format: `listTransaction [n=NAME]`

* The name refers to the name of the person in the transaction (either as a payee or a payer).
* The name must contain only alphabets, numbers, and spaces. It cannot be empty and is case-insensitive.

Examples:

* `listTransaction`
    * Shows all transactions
* `listTransaction n=Alice Pauline n=Carl Kurz`
    * Shows all transactions that include Alice Pauline or Carl Kurz

Sample Execution:

```
$ listTransaction

5 transactions listed!
```

![listTransaction1 success](images/user-guide/listTransaction1.png)

```
$ listTransaction n=Alice Pauline n=Carl Kurz

2 transactions listed!
```

![listTransaction2 success](images/user-guide/listTransaction2.png)

### Settling transactions: `settlePerson`

Fully settles the outstanding balance with the specified person.
After settling, outstanding balance with the specified person will be 0.

Format: `settlePerson INDEX`

Example:

*  `settlePerson 1` settles the outstanding balance with the 2nd person in the displayed list.

Sample Execution:

```
$ settlePerson 

Invalid command format! 
settlePerson: Settle any outstanding balance with another person. Parameters: INDEX (must be a positive integer)
Example: settlePerson 1

$ settlePerson 1

Balance settled: Alex Yeoh 
```

![settle error](images/user-guide/settle1.jpeg)

![settle success](images/user-guide/settle2.jpeg)

### Listing people: `listPerson`

Shows the outstanding balances for each person, along with their contact information.

Format: `listPerson`
* The outstanding balance is calculated as follows:
    * Outstanding balance = Total amount owed to you - Total amount you owe
* The list is sorted by the outstanding balance in descending order:
    * The person who owes you the most money will be shown first.
    * The person who you owe the most money to will be shown last.

Sample Execution:

```
$ listPerson

Listed all persons
```

![listPerson success](images/user-guide/listPerson.png)

### Sorting people by balance: `sortPerson`

Sorts the list of people in your address book based on their outstanding balances in either ascending or descending
order. This allows you to quickly identify who owes the most or the least amount of money. Negative balance means you
own them money.

Format: `sortPerson ORDER`

Parameters:
- `ORDER`: Specifies the order in which to sort the balances. Use `-` for ascending order (or most negative balance at the top) and `+` for descending order (or most positive balance at the top). Raise error for missing or unknown parameters.

Examples:
* `sortPerson -`
    * This command will rearrange the list to show the person with the lowest outstanding balance at the top, followed 
        by others in increasing order of their outstanding balances.
* `sortPerson +`
    * This command will rearrange the list to show the person with the highest outstanding balance at the top, 
        followed by others in decreasing order of their outstanding balances.

Sample execution:

```
$ sortPerson +
All contacts balance in descending order. Negative balance means you own them money.
1. John, +40.00
2. Mary, +40.00
3. Alice, +20.00
```
![sortPerson success](images/user-guide/sortPerson1.png)


### __v1.1__

### Adding a person: `addPerson`

Adds a person to the address book.

Format: `addPerson n=NAME p=PHONE_NUMBER e=EMAIL a=ADDRESS [t=TAG]…​`

Examples:

* `addPerson  n=John Doe p=98765432 e=johnd@example.com a=John street, block 123, #01-01`
* `addPerson  n=Betsy Crowe t=friend e=betsycrowe@example.com a=London Block 55 p=1234567 t=London`

### Listing all persons : `list` (deprecated)

**NOTE: The latest version of this command is in v1.2**
Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `editPerson`

Edits an existing person in the address book.

Format: `editPerson INDEX [n=NAME] [p=PHONE] [e=EMAIL] [a=ADDRESS] [t=TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list.
  The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t=` without
  specifying any tags after it.

Examples:

* `editPerson 1 p=91234567 e=johndoe@example.com`
    * Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com`
      respectively.
* `editPerson 2 n=Betsy Crower t=`
    * Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

  ![editPerson_success](C:\Users\khoow\Documents\tp\docs\images\user-guide\editPerson.PNG)

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:

* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`
  ![find success](C:\Users\khoow\Documents\tp\docs\images\user-guide\find.PNG)

### Deleting a person : `deletePerson`

Deletes the specified person from the address book.

Format: `deletePerson INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:

* `list` followed by `deletePerson 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `deletePerson 1` deletes the 1st person in the results of the `find` command.

![](C:\Users\khoow\Documents\tp\docs\images\user-guide\deletePerson.PNG)

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`
![](C:\Users\khoow\Documents\tp\docs\images\user-guide\clear.PNG)

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need
to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users
are welcome to update data directly by editing that data file.

## FAQ

### 1) Why do the portions in the log not add up exactly to the balance?

The portions displayed are rounded to a fixed number of decimal places. This means there may be fractional differences
between the actual portions and what is displayed.

Example:

```
If these are the logs stored:
A:      0.122
B:      0.223
Total:  0.345

This is displayed instead when the app is set to show 2 decimal places:
A:      0.12
B:      0.22
Total:  0.35
```
### 2) What happens when I enter an invalid command?

When an invalid command is input, an error message will be reflected at the 
output panel at the top of Spend n Split. The error message will vary depending on the type of error.

* Invalid command format. This occurs when the command word 
is recognised but there are missing fields or the values provided in the fields are 
not supported. 
The error message reflected
will state `Invalid command format!`, 
before giving details on the command and 
the fields required, along with an example of a correct command
input with the fields required.
    * Example: `settlePerson -1`
      * Error message: 
    ```
    Invalid command format!
    settlePerson: Settle any outstanding balance with another person. Parameters: INDEX (must be a positive integer)
    Example: settlePerson 1
    ```
* Unknown command. This occurs when the command word is not recognised.
The error message reflected will state `Unknown command`.
  * Example: `settleTransaction n=Ryan tg=@ryanzzzzz`
    * Error message:
  ```
  Unknown command
  ```
* Invalid command fields. This occurs when the invalid fields are provided
for the valid command word. The error message reflected is dependent on the 
valid command word.
  * Example: `settlePerson 6` when there are only 5 people in the Persons List.
    * Error message:
  ```
  The person index provided is invalid
  ```
  
