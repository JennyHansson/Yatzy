package com.example.first.yatzy;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

public class ScoreCardFragment extends Fragment implements View.OnClickListener {
    int currentPlayer = 0;
    int[] numbers;
    TextView[] textView = new TextView[18];
    int round = 0;
    int numbersOfPlayers = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score_card, container, false);
        return view;
    }

    private void setSumBonusTotalZero() {
        int[] f = {6, 7, 17};
        for (int i = 0; i < 4; i++) {
            String textViewID = "p" + i;
            int resID = getResources().getIdentifier(textViewID, "id", "com.example.first.yatzy");
            TextView textView2 = (TextView) getView().findViewById(resID);

            if (!textView2.getText().toString().equals("")) {
                numbersOfPlayers = i + 1;

                for (int j = 0; j < 3; j++) {
                    String textViewID2 = "p" + i + "f" + f[j];
                    resID = getResources().getIdentifier(textViewID2, "id", "com.example.first.yatzy");
                    textView[f[j]] = ((TextView) getView().findViewById(resID));
                    textView[f[j]].setText(valueOf(0));
                }
            } else break;
        }
    }

    public void selectScoreBox(int[] numbers) {
        this.numbers = numbers;
        if (round == 0)
            setSumBonusTotalZero();
        for (int i = 0; i < 18; i++) {
            String textViewID = "p" + currentPlayer + "f" + i;
            int resID = getResources().getIdentifier(textViewID, "id", "com.example.first.yatzy");
            textView[i] = ((TextView) getView().findViewById(resID));
            if (i != 6 && i != 7 && i != 17)
                textView[i].setOnClickListener(this);
        }
    }

    private void selection() {
        int min = 0, i, tmpInd, tmp;
        while (min < numbers.length) {
            i = min;
            tmpInd = min;
            while (i < numbers.length) {
                if (numbers[i] < numbers[tmpInd])
                    tmpInd = i;
                i++;
            }
            tmp = numbers[tmpInd];
            numbers[tmpInd] = numbers[min];
            numbers[min] = tmp;
            min++;
        }
    }

    private int searchScoreByOccurrence(int key) {
        selection();
        int integer = 6;
        int i = 4;
        while (i >= 0) {
            int occurrence = 0;
            while (numbers[i] == integer) {
                occurrence++;
                i--;
                if (occurrence == key) {
                    return integer * key;
                }
                if (i < 0) return 0;
            }
            integer--;
        }
        return 0;
    }

    private int calculateOccurrence(int key) {
        selection();
        int occurrence = 0;
        for (int i = 0; i < 5; i++) {
            if (numbers[i] == key) {
                occurrence++;
            } else if (numbers[i] > key) {
                return occurrence;
            }
        }
        return occurrence;
    }

    private int calculateStraight(int max) {
        selection();
        int i = 4;
        for (int integer = max; integer > max - 4; integer--) {
            if (numbers[i] == integer) {
                i--;
            } else {
                return 0;
            }
        }
        if (max == 6) {
            return 20;
        }
        return 15;
    }

    private int secondSearchScoreByOccurrenceIgnore(int key, int ignore) {
        selection();
        int integer = 6;
        int i = 4;
        while (i >= 0) {
            int occurrence = 0;

            while (numbers[i] == integer) {
                if (numbers[i] != ignore) {
                    occurrence++;
                    i--;
                    if (occurrence == key) {
                        return integer * key;
                    }
                } else i--;
                if (i < 0) return 0;
            }
            integer--;
        }
        return 0;
    }

    public void calculateSumBonus(int newScore) {
        //sum
        int sum = parseInt(textView[6].getText().toString());
        sum += newScore;
        textView[6].setText(valueOf(sum));

        //bonus
        if (parseInt(textView[7].getText().toString()) == 0) {
            if (sum >= 63) {
                textView[7].setText(valueOf(50));
                newScore += 50;
            }
        }
        calculateTotal(newScore);
    }

    public void calculateTotal(int newScore) {
        int total = parseInt(textView[17].getText().toString());
        total += newScore;
        textView[17].setText(valueOf(total));
    }

    private void changePlayer() {
        round++;
        DiceFragment diceFragment = (DiceFragment) getActivity().getFragmentManager().findFragmentById(R.id.fragmentDice);
        YatzyActivity yatzyActivity = (YatzyActivity) getActivity();
        if (round < (numbersOfPlayers * 15)) {
            if (currentPlayer < numbersOfPlayers - 1) {
                currentPlayer++;
            } else {
                currentPlayer = 0;
            }
            for (int i = 0; i < 18; i++) {
                if (i != 6 && i != 7 && i != 17)
                    textView[i].setOnClickListener(null);
            }
            diceFragment.startNewRound();
            yatzyActivity.startNewRound(currentPlayer);

        } else {
            numbers = new int[numbersOfPlayers];
            int max = 0;
            for (int i = 0; i < numbersOfPlayers; i++) {
                String textViewID = "p" + i + "f17";
                int resID = getResources().getIdentifier(textViewID, "id", "com.example.first.yatzy");
                textView[i] = ((TextView) getView().findViewById(resID));
                int temp = parseInt(textView[i].getText().toString());
                if(temp > max)
                    max = temp;
            }
            Integer[] winnerId = new Integer[numbersOfPlayers];
            int x = 0;
            for (int i = 0; i<numbersOfPlayers; i++){
                int temp = parseInt(textView[i].getText().toString());
                if(temp == max) {
                    winnerId[x] = i;
                    x++;
                }
            }
            diceFragment.gameOver();
            yatzyActivity.gameOver(winnerId);
        }
    }

    public void onClick(View view) {
        int score;
        String[] textViewID = new String[18];
        int[] resID = new int[18];
        for (int i = 0; i < 18; i++) {
            textViewID[i] = "p" + currentPlayer + "f" + i;
            resID[i] = getResources().getIdentifier(textViewID[i], "id", "com.example.first.yatzy");
        }
        int viewId = view.getId();
        if (viewId == resID[0]) {
            if (textView[0].getText().equals("")) {
                score = calculateOccurrence(1);
                textView[0].setText(valueOf(score));
                calculateSumBonus(score);
                changePlayer();
            }
        } else if (viewId == resID[1]) {
            if (textView[1].getText().equals("")) {
                score = calculateOccurrence(2) * 2;
                textView[1].setText(valueOf(score));
                calculateSumBonus(score);
                changePlayer();
            }
        } else if (viewId == resID[2]) {
            if (textView[2].getText().equals("")) {
                score = calculateOccurrence(3) * 3;
                textView[2].setText(valueOf(score));
                calculateSumBonus(score);
                changePlayer();
            }
        } else if (viewId == resID[3]) {
            if (textView[3].getText().equals("")) {
                score = calculateOccurrence(4) * 4;
                textView[3].setText(valueOf(score));
                calculateSumBonus(score);
                changePlayer();
            }
        } else if (viewId == resID[4]) {
            if (textView[4].getText().equals("")) {
                score = calculateOccurrence(5) * 5;
                textView[4].setText(valueOf(score));
                calculateSumBonus(score);
                changePlayer();
            }
        } else if (viewId == resID[5]) {
            if (textView[5].getText().equals("")) {
                score = calculateOccurrence(6) * 6;
                textView[5].setText(valueOf(score));
                calculateSumBonus(score);
                changePlayer();
            }
        } else if (viewId == resID[8]) {
            if (textView[8].getText().equals("")) {
                score = searchScoreByOccurrence(2);
                textView[8].setText(valueOf(score));
                calculateTotal(score);
                changePlayer();
            }
        } else if (viewId == resID[9]) {
            if (textView[9].getText().equals("")) {
                int secondSearch = 0;
                int firstSearch = searchScoreByOccurrence(2);
                if (firstSearch > 0) {
                    secondSearch = secondSearchScoreByOccurrenceIgnore(2, firstSearch / 2);
                }
                if (firstSearch != 0 && secondSearch != 0) {
                    score = firstSearch + secondSearch;
                } else {
                    score = 0;
                }
                textView[9].setText(valueOf(score));
                calculateTotal(score);
                changePlayer();
            }
        } else if (viewId == resID[10]) {
            if (textView[10].getText().equals("")) {
                score = searchScoreByOccurrence(3);
                textView[10].setText(valueOf(score));
                calculateTotal(score);
                changePlayer();
            }
        } else if (viewId == resID[11]) {
            if (textView[11].getText().equals("")) {
                score = searchScoreByOccurrence(4);
                textView[11].setText(valueOf(score));
                calculateTotal(score);
                changePlayer();
            }

        } else if (viewId == resID[12]) {
            if (textView[12].getText().equals("")) {
                score = calculateStraight(5);
                textView[12].setText(valueOf(score));
                calculateTotal(score);
                changePlayer();
            }
        } else if (viewId == resID[13]) {
            if (textView[13].getText().equals("")) {
                score = calculateStraight(6);
                textView[13].setText(valueOf(score));
                calculateTotal(score);
                changePlayer();
            }
        } else if (viewId == resID[14]) {
            if (textView[14].getText().equals("")) {
                int secondSearch = 0;
                int firstSearch = searchScoreByOccurrence(3);
                if (firstSearch > 0) {
                    secondSearch = secondSearchScoreByOccurrenceIgnore(2, firstSearch / 3);
                }
                if (firstSearch != 0 && secondSearch != 0) {
                    score = (firstSearch + secondSearch);
                } else {
                    score = 0;
                }
                textView[14].setText(valueOf(score));
                calculateTotal(score);
                changePlayer();
            }

        } else if (viewId == resID[15]) {
            if (textView[15].getText().equals("")) {
                score = numbers[0] + numbers[1] + numbers[2] + numbers[3] + numbers[4];
                textView[15].setText(valueOf(score));
                calculateTotal(score);
                changePlayer();
            }
        } else if (viewId == resID[16]) {
            if (textView[16].getText().equals("")) {
                if (searchScoreByOccurrence(5) > 0)
                    score = 50;
                else
                    score = 0;
                textView[16].setText(valueOf(score));
                calculateTotal(score);
                changePlayer();
            }
        }
    }
}
