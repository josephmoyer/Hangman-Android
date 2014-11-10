Hangman
===============

A FOSS Hangman App.


Uses a word list modified from the 2of12 list from the [12dicts](http://wordlist.aspell.net/12dicts-readme/) word lists.

To Play:

Pressing the gallows resets the game.

Press the word you're guessing to guess a letter.

Wrong letters will appear on the right.

You lose when the hangman has a full body.



###TO-DO List:

* Add difficulty on word lists.
* Allow for use of custom word lists.
* Add support for Spanish

###Build Instructions for Linux in Terminal
* Clone repo using `git clone https://github.com/darthjoey91/Hangman-Android.git`
* `cd Hangman-Android`
* `./gradlew assembleRelease`
* Apk is then found in `./app/build/outputs/apk`
