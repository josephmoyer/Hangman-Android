package com.github.darthjoey91.hangman;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andreabaccega.widget.FormEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Joseph Moyer on 11/6/14.
 *
 */
public class HangmanFragment extends Fragment {
    //Declaring variables
    public ArrayList<String> mWordList;
    private ImageView mGallows;
    private TextView mWrongGuesses;
    private FormEditText mInput;
    private String mWord;
    private char mGuess;
    private TextView mTheWord;    //May or may not be the bird.
    private String mWrongGuessesData;
    private String mTheWordDashes;
    private int error;
    private String LOG_TAG = "HangmanFragment";
    private Scanner listScanner;

    private void resetGame(){
        mInput.requestFocus();
        mGallows.setImageResource(R.drawable.error0);
        mWrongGuessesData="";
        mWrongGuesses.setText(mWrongGuessesData);
        mWord = pickRandomWord(mWordList);
        mTheWordDashes=hideWord(mWord);
        mTheWord.setText(mTheWordDashes);
        error=0;

    }

    public HangmanFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);





        //Initialize game
        fillWordList();
        mGallows = (ImageView) rootView.findViewById(R.id.gallows);
        mWrongGuesses = (TextView) rootView.findViewById(R.id.wrongletters);
        mInput = (FormEditText) rootView.findViewById(R.id.input);
        mTheWord = (TextView) rootView.findViewById(R.id.the_word);
        resetGame();


        //Main Flow of Game

        //Listener listens for a click.
        mTheWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Takes inputted letter.
                mGuess=getLetter(mInput);
                //Provided good input
                if(mGuess!='\u0000') {
                    //Find every instance of letter
                    String temp = mTheWordDashes;
                    mTheWordDashes = findCharInWord(mWord, mGuess, mTheWordDashes);
                    //If letter is not in word
                    if (mTheWordDashes.equals(temp)) {
                        //Add letter to list of wrong letters appropriately
                        if (mWrongGuessesData.equals("")) {
                            mWrongGuessesData = mWrongGuessesData + String.valueOf(mGuess);
                        } else {
                            mWrongGuessesData = mWrongGuessesData + ", " + String.valueOf(mGuess);
                        }
                        mWrongGuesses.setText(mWrongGuessesData);
                        //Then add new stuff to hangman
                        increaseError();
                    } else {
                        //Reset the input and show that the letter was right.
                        mTheWord.setText(mTheWordDashes);
                        mInput.setText("");
                    }
                    mInput.requestFocus();
                    endGame();
                }
            }
        });



        mGallows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });






        return rootView;
    }

    public char getLetter(FormEditText input)
    {
        boolean valid=input.testValidity();
        if (valid)
        {
            return input.getText().toString().toLowerCase().charAt(0);
        }
        else
            return '\u0000';
    }

    public String pickRandomWord(ArrayList<String> list) {

        int length = list.size();
        Random random = new Random();
        int index = random.nextInt(length);
        String word = list.get(index);
        String temp = "";
        // add spaces in between letter to make it look pretty
        for (int i = 0; i < word.length(); i++) {
            temp = temp + word.charAt(i) + " ";
        }
        return temp;
    }

    public String findCharInWord(String word, char letter, String returnable) {

            for (int index =0; index < word.length();index++) {
                if (word.charAt(index) == letter) {
                    char[] temp = returnable.toCharArray();
                    temp[index] = letter;
                    returnable = String.valueOf(temp);
                }
            }
        return returnable;
    }

    public String hideWord(String word){
        String dashes="";
        for(int i=0;i<word.length();i++){
            if(word.charAt(i)==' '||word.charAt(i)=='\'')
                dashes=dashes+word.charAt(i);
            else
                dashes=dashes+"_";
        }
        return dashes;
    }

    private void increaseError(){


        error+=1;
        if(error<6){
            String id = "error"+String.valueOf(error);
            mGallows.setImageResource(getResources().getIdentifier(id,"drawable","com.github.darthjoey91.hangman"));
            mInput.setText("");
            mTheWord.setText(mTheWordDashes);
        }
    }

    public void endGame(){
        if(mWord.equals(mTheWordDashes))
        {
            mWrongGuesses.setText(R.string.game_over);
            mTheWord.setText(R.string.game_win);
            mGallows.setImageResource(R.drawable.win);
        }else if (error>=6){
            mTheWord.setText(mWord);
            mWrongGuesses.setText(R.string.game_over);
            mGallows.setImageResource(R.drawable.error6);
        }
    }

    public void fillWordList(){

        mWordList = new ArrayList<String>();



        //Filling mWordList
        if(isExternalStorageReadable()) {
            File path = Environment.getExternalStorageDirectory();
            File file = new File(path, "wordlist.dat");

            try {
                if (file.exists()) {
                    listScanner = new Scanner(file);
                    try {
                        while (listScanner.hasNext()) {
                            mWordList.add(listScanner.next());
                        }
                    } catch (Exception e) {
                        Log.e(LOG_TAG, e.getMessage());
                        useDefaultWordlist();
                    } finally {
                        listScanner.close();
                    }

                } else {
                    useDefaultWordlist();
                }

            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
                useDefaultWordlist();
            }
        }

        //Handles if file was empty.
        if(mWordList.isEmpty()){
            useDefaultWordlist();
        }

    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void useDefaultWordlist(){
        listScanner = new Scanner(getResources().openRawResource(R.raw.wordlist));


        try {
            while (listScanner.hasNext()) {
                mWordList.add(listScanner.next());
            }
        } catch (Exception e){
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            listScanner.close();
        }
    }

}




