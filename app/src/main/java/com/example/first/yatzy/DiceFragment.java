package com.example.first.yatzy;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jenny on 2017-04-12.
 */

public class DiceFragment extends Fragment implements View.OnClickListener {
    CheckBox saveDie1, saveDie2, saveDie3, saveDie4, saveDie5;
    ImageView die1, die2, die3, die4, die5;
    TextView status;
    int [] numbers = new int[5];
    int quantity = 0;
    Button btn_rollDice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_dice, container, false);
        saveDie1 = (CheckBox)view.findViewById(R.id.save1);
        saveDie2 = (CheckBox)view.findViewById(R.id.save2);
        saveDie3 = (CheckBox)view.findViewById(R.id.save3);
        saveDie4 = (CheckBox)view.findViewById(R.id.save4);
        saveDie5 = (CheckBox)view.findViewById(R.id.save5);

        die1 = (ImageView)view.findViewById(R.id.die1);
        die2 = (ImageView) view.findViewById(R.id.die2);
        die3 = (ImageView) view.findViewById(R.id.die3);
        die4 = (ImageView) view.findViewById(R.id.die4);
        die5 = (ImageView) view.findViewById(R.id.die5);

        btn_rollDice = (Button)view.findViewById(R.id.btn_rollDice);
        btn_rollDice.setOnClickListener(this);
        startNewRound();

        status = (TextView)view.findViewById(R.id.status);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (!saveDie1.isChecked()) {
            int d1 = (int)(Math.random() * 6) + 1;
            setImage(die1, d1);
            numbers[0] = d1;
        }
        if (!saveDie2.isChecked()) {
            int d2 = (int)(Math.random() * 6) + 1;
            setImage(die2, d2);
            numbers[1] = d2;
        }
        if (!saveDie3.isChecked()) {
            int d3 = (int)(Math.random() * 6) + 1;
            setImage(die3, d3);
            numbers[2] = d3;
        }
        if (!saveDie4.isChecked()) {
            int d4 = (int)(Math.random() * 6) + 1;
            setImage(die4, d4);
            numbers[3] = d4;
        }
        if (!saveDie5.isChecked()) {
            int d5 = (int)(Math.random() * 6) + 1;
            setImage(die5, d5);
            numbers[4] = d5;
        }

        quantity++;
        status.setText("You rolled " + quantity + " times.");

        if (quantity == 3) {
            btn_rollDice.setClickable(false);
        }
        ScoreCardFragment scoreCardFragment = (ScoreCardFragment) getActivity().getFragmentManager().findFragmentById(R.id.fragmentScoreCard);
        if(scoreCardFragment!=null)
            scoreCardFragment.selectScoreBox(numbers);

        saveDie1.setClickable(true);
        saveDie2.setClickable(true);
        saveDie3.setClickable(true);
        saveDie4.setClickable(true);
        saveDie5.setClickable(true);
    }

    private void setImage(ImageView imageView, int number) {
        if(number == 1)
            imageView.setImageResource(R.drawable.die1);
        else if(number == 2)
            imageView.setImageResource(R.drawable.die2);
        else if(number == 3)
            imageView.setImageResource(R.drawable.die3);
        else if(number == 4)
            imageView.setImageResource(R.drawable.die4);
        else if(number == 5)
            imageView.setImageResource(R.drawable.die5);
        else if(number == 6)
            imageView.setImageResource(R.drawable.die6);
    }

    public void startNewRound() {
        saveDie1.setChecked(false);
        saveDie2.setChecked(false);
        saveDie3.setChecked(false);
        saveDie4.setChecked(false);
        saveDie5.setChecked(false);
        saveDie1.setClickable(false);
        saveDie2.setClickable(false);
        saveDie3.setClickable(false);
        saveDie4.setClickable(false);
        saveDie5.setClickable(false);
        quantity = 0;
        btn_rollDice.setClickable(true);
    }

    public void gameOver() {
        btn_rollDice.setClickable(false);
        saveDie1.setChecked(false);
        saveDie2.setChecked(false);
        saveDie3.setChecked(false);
        saveDie4.setChecked(false);
        saveDie5.setChecked(false);
        saveDie1.setClickable(false);
        saveDie2.setClickable(false);
        saveDie3.setClickable(false);
        saveDie4.setClickable(false);
        saveDie5.setClickable(false);
    }
}
