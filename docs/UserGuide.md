# Spend n Split
## Description
Spend n Split (SnS) is a **desktop app for managing expense from contacts, optimized for use via a Command Line 
Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, SnS can 
get your contact expense management tasks done faster than traditional GUI apps.

## Setup

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

    * `list` : Lists all expenses.

    * `clear` : Deletes all content.

    * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

## Features

### __v1.2__

### Adding an expense: `addExpense`
Adds an expense.
Format: `addExpense n/NAME c/COST d/DETAILS`

Examples:
* `addExpense n/John Doe c/25 d/Sourdough bread`
* `addExpense n/Sir Bobby c/1759 d/Iphone 20`

Sample Execution:

```bash
$ add n/John Doe c/25 d/Sourdough bread 

Added an expense for John Doe. Sourdough bread, $25.00.

$ add n/Ryan d/Sourdough bread 

Error. Expense cost was not provided with a c/ flag.
```
![addExpense success](images/user-guide/addExpense1.jpg)

![addExpense error](images/user-guide/addExpense2.jpg)


### Editing an expense: `editExpense`
Edits the expense for the person at the specified `INDEX`. The index refers to the index number when viewing a 
specific person's expenses. The index **must be a positive integer** 1, 2, 3, …​
Format: `editExpense n/NAME i/INDEX [c/COST] [d/DETAILS]​`

Examples:
* `editExpense n/John Doe i/1 c/35`
* `editExpense n/Sir Bobby i/4 d/iPhone 30`
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Sample Execution:
```
Suppose this is the list of expenses for Bob:

$ log n/Bob

1.            eat                  $1.00    Bob
2.            Pokemon Cards        $15.12   Bob

$ editExpense n/Bob 2 c/12.12

I have edited Bob's expense to be Pokemon Cards, $12.12.

$ editExpense n/Bob 3 d/Potato

Error! There is no such expense for Bob at that index.
```
![editExpense success](images/user-guide/editExpense1.jpg)


![editExpense error](images/user-guide/editExpense2.jpg)



### Deleting an expense: `deleteExpense`

Deletes the specified expense based on index. Must be in an expense log view when entering this command.

Format: `deleteExpense i/INDEX`

Parameters:
- `i/INDEX`: The index of the expense to be deleted.

Examples:

* `deleteExpense i/2`
    * Deletes the second expense in the list
* `deleteExpense i/1`
    * Deletes the first expense in the list

![](images/user-guide/deleteExpense1.png)

![](images/user-guide/deleteExpense2.png)

![](images/user-guide/deleteExpense3.png)

### Viewing my log with a person: `log`

Shows a list of the expenses with the specified person.

Format: `log p/PERSON`

Parameters:
- `p/PERSON`: Specifies the name of the person whose log we want to check.

Examples:

* `log p/Bob`
    * Shows log with Bob
* `log p/Alice`
    * Shows log with Alice

![log success](images/user-guide/log1.png)

![](images/user-guide/log2.png)

![](images/user-guide/log3.png)

![](images/user-guide/log4.png)


### Settling expenses: `settle`
Fully settles the outstanding balance with the specified person.
After settling, outstanding balance with the specified person will be 0.

Format: `settle n/NAME`

Example:
- `settle n/Bob`
    - settles the outstanding balance with contact Bob.

Sample Execution:
```
$ settle 

Error: Please indicate the person you would like to settle expenses with.

$ settle n/Bob

Confirm settle expense with Bob? [Y/N]
    Bob owes you $50. 
    
$ Y

Successfully settled expense with Bob.  
    No outstanding balance with Bob.

$ settle n/Mary

Confirm settle expense with Mary? [Y/N]
    You owe Mary $30.

$ N
```

![settle error](images/user-guide/settle1.jpg)

![settle prompt confirm](images/user-guide/settle2.jpg)

![settle success](images/user-guide/settle3.jpg)


### Listing balances of all persons : `list`


Shows the outstanding balances for each person, along with their contact information.

Format: `list`

Sample Execution:

```
$ list

| S/N | Name | Contact  | Balance |
| --- | ---  | -------- | ------- |
|  1  | Amy  | 95382713 |   $50   |
|  2  | John | 82347185 |  -$14   |

```
![list success](images/user-guide/list1.png)


### Sorting people by balance: `sortBalance`

Sorts the list of people in your address book based on their outstanding balances in either ascending or descending 
order. This allows you to quickly identify who owes the most or the least amount of money. Negative balance means you 
own them money.

Format: `sortBalance o/ORDER`

Parameters:
- `o/ORDER`: Specifies the order in which to sort the balances. Use `asc` for ascending order and `desc` for 
descending order. Raise error for missing or unknown parameters.

Examples:
* `sortBalance o/asc`
    * This command will rearrange the list to show the person with the lowest outstanding balance at the top, followed 
        by others in increasing order of their outstanding balances.
* `sortBalance o/desc`
    * This command will rearrange the list to show the person with the highest outstanding balance at the top, 
        followed by others in decreasing order of their outstanding balances.

Sample execution:

```
$ sortBalance o/asc
All contacts balance in ascending order. Negative balance means you own them money.
1. Alex Yeoh, -$15
2. Bernice Yu, -$12
3. Charlotte Oliveiro, $23
```
![sortBalance success](images/user-guide/sortBalance1.png)

```
$ sortBalance
Invalid sorting order, must be `o/asc` or `o/desc`

$ sortBalance o/increasing
Invalid sorting order, must be `o/asc` or `o/desc`
```
![sortBalance error](images/user-guide/sortBalance2.png)

### Creating shared expenses: `createGroupExpense`
Creates an expense for multiple people with customised split ratios.

Format: `createGroupExpense c/COST d/DETAILS [n/NAME w/WEIGHT]...`
- Cost has to be a number.
- Positive cost means that the person owes you.
- Negative cost means that you owe the person.
- If you want to create a weight for yourself, include `n/Self` to refer to yourself.
- At least one pair of name and weight must be provided.
- Weight must be an integer.
- The cost for each person is calculated as follows:
    - Individual cost = Total Cost * (Individual Weight / Total Weight)

Examples:
* `createGroupExpense c/100 d/Dinner n/John w/2 n/Mary w/2 n/Alice w/1`
    * creates 3 expenses: two expenses of $40 for John and Mary (2/5 of $100 each), and one expense of $20 for 
        Alice (1/5 of $100)
* `createGroupExpense c/600 d/Rent n/Self w/1 n/John w/1 n/Mary /w1`
    * creates 2 expenses: $200 each for John and Mary (since you incurred 1/3 of the cost, which is $200)

Sample execution:
```
$ createGroupExpense c/100 d/Dinner n/John w/2 n/Mary w/2 n/Alice w/1

Successfully created 3 expenses totalling $100:    
    John owes you $40
    Mary owes you $40
    Alice owes you $20
```
![createGroupExpense success](images/user-guide/createGroupExpense1.png)


```
$ createGroupExpense c/200 d/Textbooks

Error: At least one other person must be included in a shared expense.

For example,
c/30 d/Lunch n/John w/1 n/Mary w/1
```

![createGroupExpense error](images/user-guide/createGroupExpense2.png)


### __v1.1__
### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all persons : `list` (deprecated)
**NOTE: The latest version of this command is in v1.2**
Shows a list of all persons in the address book.

Format: `list`

![list](images/user-guide/listOld.png)


### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. 
    The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com`
    *  Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` 
        respectively.
*  `edit 2 n/Betsy Crower t/`
    *  Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

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
  ![find success](images/user-guide/find1.png)


### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

![](images/user-guide/delete1.png)


### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`
![](images/user-guide/clear1.png)


### Exiting the program : `exit`

Exits the program.

Format: `exit`
![](images/user-guide/exit1.png)

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need 
to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users 
are welcome to update data directly by editing that data file.
## FAQ
### Why do the expenses in the log not add up exactly to the balance?
The expenses displayed are rounded to a fixed number of decimal places. This means there may be fractional differences 
between the actual expenses and what is displayed.

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
