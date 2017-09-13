package com.example.first.yatzy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jenny on 2017-04-04.
 */

public class YatzyActivity extends Activity{
    TextView status;
    String[] playerNames;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yatzy);
        playerNames = getIntent().getStringArrayExtra("playerNames");
        for (int i = 0; i<playerNames.length; i++) {
            String textViewID= "p"+ i;
            int resID = getResources().getIdentifier(textViewID, "id", "com.example.first.yatzy");
            TextView p0 = (TextView) findViewById(resID);
            p0.setText(playerNames[i].substring(0, 2));
        }
        status = (TextView)findViewById(R.id.status);
        status.setText(playerNames[0] + " " + getString(R.string.roll_dice));
    }

    public void startNewRound(int currentPlayer) {
        status.setText(playerNames[currentPlayer] + " " + getString(R.string.roll_dice));
    }

    public void gameOver(Integer[] winnerId) {
        String toast = "Congratulations ";

        int i = 0;
        while (i<playerNames.length && winnerId[i] != null){
            if (i == 0)
                toast += playerNames[winnerId[0]];
            else
                toast += " and " + playerNames[winnerId[i]];
            i++;
        }
        toast += " you won!";
        Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
    }
}
