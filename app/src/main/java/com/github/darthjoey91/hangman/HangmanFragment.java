package com.github.darthjoey91.hangman;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andreabaccega.widget.FormEditText;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by joe on 11/6/14.
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

        mWordList = new ArrayList<String>();

        //Filling mWordList
        Scanner listScanner = new Scanner(getResources().openRawResource(R.raw.wordlist));

        try {
            while (listScanner.hasNext()) {
                mWordList.add(listScanner.next());
            }
        } catch (Exception e){
            Log.e("Didn't create wordlist.", e.getMessage());
        } finally {
            listScanner.close();
        }

        //Initialize game
        mGallows = (ImageView) rootView.findViewById(R.id.gallows);
        mWrongGuesses = (TextView) rootView.findViewById(R.id.wrongletters);
        mInput = (FormEditText) rootView.findViewById(R.id.input);
        mTheWord = (TextView) rootView.findViewById(R.id.the_word);
        resetGame();



        mTheWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGuess=getLetter(mInput);
                if(mGuess!='\u0000'){
                String temp =mTheWordDashes;
                mTheWordDashes=findCharInWord(mWord,mGuess,mTheWordDashes);
                if(mTheWordDashes==temp){
                    if(mWrongGuessesData==""){
                        mWrongGuessesData=mWrongGuessesData+String.valueOf(mGuess);
                    }else {
                        mWrongGuessesData = mWrongGuessesData + ", " + String.valueOf(mGuess);
                    }
                    increaseError();
                }else{
                    mTheWord.setText(mTheWordDashes);
                    mInput.setText("");
                }}




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
        return word;
    }

    public String findCharInWord(String word, char letter, String returnable) {

            for (int index =0; index < word.length();index++) {
                if (word.charAt(index) == letter) {
                    char[] temp = returnable.toCharArray();
                    temp[2*index] = letter;
                    returnable = String.valueOf(temp);
                }
            }
        return returnable;
    }

    public String hideWord(String word){
        String dashes="";
        for(int i=0;i<word.length();i++){
                dashes=dashes+"_ ";
        }
        return dashes;
    }

    private void increaseError(){
        mWrongGuesses.setText(mWrongGuessesData);
        error+=1;
        if(error<7){
            String id = "error"+String.valueOf(error);
            mGallows.setImageResource(getResources().getIdentifier(id,"drawable","com.github.darthjoey91.hangman"));
            mInput.setText("");
            mTheWord.setText(mTheWordDashes);
            mInput.requestFocus();
        }
        else{mTheWord.setText(mWord);}
    }

}




