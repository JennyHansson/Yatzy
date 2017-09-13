package com.example.first.yatzy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText[] editTexts = new EditText[4];
    RadioButton[] radio = new RadioButton[4];
    int numberOfPlayers = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i = 0; i< 4; i++){
            String editTextID = "edit_player"+ (i+1);
            int resID = getResources().getIdentifier(editTextID, "id", "com.example.first.yatzy");
            editTexts[i] = (EditText) findViewById(resID);

            String radioID = "radio_"+ (i+1) +"_player";
            int resID2 = getResources().getIdentifier(radioID, "id", "com.example.first.yatzy");
            radio[i] = (RadioButton) findViewById(resID2);
            radio[i].setOnClickListener(this);
        }
    }

    public void goToActivity_yatzy(View view) {
        String[] playerNames = new String[numberOfPlayers];

        boolean flag = false;
        for(int i = 0; i < numberOfPlayers && !flag; i++){
            if (editTexts[i].getText().toString().equals("")){
                flag = true;
            }
        }
        if (flag){
            Toast.makeText(this, "You have to enter name for all players", Toast.LENGTH_LONG).show();
        }else {
            for(int i = 0; i < numberOfPlayers; i++){
                playerNames[i] = editTexts[i].getText().toString();
            }
            Intent intent = new Intent(MainActivity.this, YatzyActivity.class);
            intent.putExtra("playerNames", playerNames);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        if(radio[0].isChecked())
            numberOfPlayers = 1;
        else if(radio[1].isChecked())
            numberOfPlayers = 2;
        else if(radio[2].isChecked())
            numberOfPlayers = 3;
        else if(radio[3].isChecked())
            numberOfPlayers = 4;

        for (int i = 0; i<4; i++){
            if(i >= numberOfPlayers)
                editTexts[i].setVisibility(View.GONE);
            else
                editTexts[i].setVisibility(View.VISIBLE);
        }
    }
}