Hangman
===============

A FOSS Hangman App.


Uses a word list modified from the 2of12 list from the [12dicts](http://wordlist.aspell.net/12dicts-readme/) word lists.

To Play:

> Pressing the gallows resets the game.

> Press the word you're guessing to guess a letter.

> Wrong letters will appear on the right.

> You lose when the hangman has a full body.

To Use a Custom Word List:

> Create file with the name `wordlist.dat` in the root of the sd card on your device. If you have more than one sd card, put it in the root of the internal one, or to cover your bases, both.

> The format should be lowercase words with one word per line like
    
    i
    am
    a
    word
    list
> App does support words with spaces and apostrophes and other non-alphabetic characters.

> Future stuff! If you want to use a wordlist for a different language, name it `wordlist-{language code}.dat`.
> An example would be for Spanish: `wordlist-es.dat`.


###TO-DO List:

* Add difficulty on word lists.
* ~~Allow for use of custom word lists.~~ Done
* Add support for multiple languages - Soon.

###Build Instructions for Linux in Terminal
* Clone repo using `git clone https://github.com/darthjoey91/Hangman-Android.git`
* `cd Hangman-Android`
* `./gradlew assembleRelease`
* Apk is then found in `./app/build/outputs/apk`


###Build Instructions for Windows
* Clone repo either using git bash or Github for Windows.
* Find the repository that you cloned.
* Run `gradlew.bat assembleRelease` from the command line.

Building requires Android SDK.
