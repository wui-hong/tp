---
layout: page
title: Jeffrey Jian's Project Portfolio Page
---

### Project: Spend n Split

### Overview
Spend n Split (SnS) is a **desktop app for managing expense from contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI).

If you can type fast, SnS can get your contact expense management tasks done faster than traditional GUI apps.


### Summary of Contributions
Given below are my contributions to the project.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=jeffrey-jian&breakdown=false&sort=groupTitle%20dsc&sortWithin=title&since=2023-09-22&timeframe=commit&mergegroup=&groupSelect=groupByRepos)
* **Enhancements implemented**: Added the ability to use hotkeys to navigate through the app.
* **Contributions to the UG**: to be added soon
* **Contributions to the DG**: to be added soon
* **Contributions to team-based tasks**: to be added soon
* **Review/mentoring contributions**: to be added soon
* **Contributions beyond the project team**: to be added soon

* **New Feature**: Added TransactionList to store transactions.
* **What it does**: Allows the user to store transactions in a list.
* **Justification**: This feature allows the user to view the transactions they have made.
* **Highlights**: This enhancement affects transaction-related commands. It required an in-depth analysis of design alternatives, where we considered the need for a unique or non-unique list.

* **New Feature**: Added new command `editTransaction`.
* **What it does**: Allows the user to edit an existing transaction in the transaction list.
* **Justification**: This feature allows the user to edit the transactions they have made, in case they made a mistake or would like to update the transaction.
* **Highlights**: This enhancement affects transaction-related commands. It required an in-depth analysis of design alternatives, whereby we considered what should we allow the user to edit, and how should we allow the user to edit.

* **New Feature**: Added new command `updatePortion`.
* **What it does**: Allows the user to update the portions of a transaction.
* **Justification**: This feature allows the user to update the portions of a transaction, in case they made a mistake or would like to update the portions of a transaction.
* **Highlights**: This enhancement affects transaction-related commands. It required an in-depth judgement about the splitting of the commands for editing transactions, and how to allow the user to update the portions of a transaction. It also required the designing of a 3-in-1 command, which is a command that can be used to add, edit, and delete portions.

* **New Feature**: Added hotkeys to navigate through the app.
* **What it does**: Allows the user to use pre-defined hotkeys to navigate through the app.
* **Justification**: This feature allows the user to navigate through the app faster using the keyboard only, and is especially useful for users who are familiar with the app.
* **Highlights**: This enhancement requires the use of JavaFX and keyCombination to implement the hotkeys. It also required an in-depth analysis of design alternatives, whereby we considered the need for hotkeys, and what hotkeys should we implement.

* User Guide:
* Added documentation for the features `editTransaction` and `updatePortion`
* Did cosmetic tweaks to include tips and notes for features regarding "relevant" transactions.

* Developer Guide:
* Added implementation details of the `editTransaction` and `updatePortion` features.

[//]: # (    * Added documentation for the features `delete` and `find` [\#72]&#40;&#41;)

[//]: # (    * Did cosmetic tweaks to existing documentation of features `clear`, `exit`: [\#74]&#40;&#41;)

[//]: # (  * Developer Guide:)

[//]: # (    * Added implementation details of the `delete` feature.)

[//]: # (  * What it does: allows the user to undo all previous commands one at a time. Preceding undo commands can be reversed by using the redo command.)

[//]: # (  * Justification: This feature improves the product significantly because a user can make mistakes in commands and the app should provide a convenient way to rectify them.)

[//]: # (  * Highlights: This enhancement affects existing commands and commands to be added in future. It required an in-depth analysis of design alternatives. The implementation too was challenging as it required changes to existing commands.)

[//]: # (  * Credits: *{mention here if you reused any code/ideas from elsewhere or if a third-party library is heavily used in the feature so that a reader can make a more accurate judgement of how much effort went into the feature}*)

[//]: # ()
[//]: # (* **New Feature**: Added a history command that allows the user to navigate to previous commands using up/down keys.)

[//]: # ()
[//]: # (* **Code contributed**: [RepoSense link]&#40;&#41;)

[//]: # ()
[//]: # (* **Project management**:)

[//]: # (  * Managed releases `v1.3` - `v1.5rc` &#40;3 releases&#41; on GitHub)

[//]: # ()
[//]: # (* **Enhancements to existing features**:)

[//]: # (  * Updated the GUI color scheme &#40;Pull requests [\#33]&#40;&#41;, [\#34]&#40;&#41;&#41;)

[//]: # (  * Wrote additional tests for existing features to increase coverage from 88% to 92% &#40;Pull requests [\#36]&#40;&#41;, [\#38]&#40;&#41;&#41;)

[//]: # ()
[//]: # (* **Documentation**:)

[//]: # (  * User Guide:)

[//]: # (    * Added documentation for the features `delete` and `find` [\#72]&#40;&#41;)

[//]: # (    * Did cosmetic tweaks to existing documentation of features `clear`, `exit`: [\#74]&#40;&#41;)

[//]: # (  * Developer Guide:)

[//]: # (    * Added implementation details of the `delete` feature.)

[//]: # ()
[//]: # (* **Community**:)

[//]: # (  * PRs reviewed &#40;with non-trivial review comments&#41;: [\#12]&#40;&#41;, [\#32]&#40;&#41;, [\#19]&#40;&#41;, [\#42]&#40;&#41;)

[//]: # (  * Contributed to forum discussions &#40;examples: [1]&#40;&#41;, [2]&#40;&#41;, [3]&#40;&#41;, [4]&#40;&#41;&#41;)

[//]: # (  * Reported bugs and suggestions for other teams in the class &#40;examples: [1]&#40;&#41;, [2]&#40;&#41;, [3]&#40;&#41;&#41;)

[//]: # (  * Some parts of the history feature I added was adopted by several other class mates &#40;[1]&#40;&#41;, [2]&#40;&#41;&#41;)

[//]: # ()
[//]: # (* **Tools**:)

[//]: # (  * Integrated a third party library &#40;Natty&#41; to the project &#40;[\#42]&#40;&#41;&#41;)

[//]: # (  * Integrated a new Github plugin &#40;CircleCI&#41; to the team repo)

[//]: # ()
[//]: # (* _{you can add/remove categories in the list above}_)
