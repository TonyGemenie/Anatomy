package prototype.anatomy.anatomy;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;


public class MainActivity extends AppCompatActivity {

    public String[] structures;
    @BindView(R.id.start_button) Button startBtn;
    @BindView(R.id.answer_button_1) Button answerBtn1;
    @BindView(R.id.answer_button_2) Button answerBtn2;
    @BindView(R.id.answer_button_3) Button answerBtn3;
    @BindView(R.id.answer_button_4) Button answerBtn4;
    @BindView(R.id.answer_text_1) TextView answerText1;
    @BindView(R.id.answer_text_2) TextView answerText2;
    @BindView(R.id.answer_text_3) TextView answerText3;
    @BindView(R.id.answer_text_4) TextView answerText4;
    @BindView(R.id.answer_text_5) TextView answerText5;
    @BindView(R.id.answer_text_6) TextView answerText6;
    @BindView(R.id.answer_text_7) TextView answerText7;
    @BindView(R.id.answer_text_8) TextView answerText8;
    @BindView(R.id.answer_text_9) TextView answerText9;
    @BindView(R.id.answer_text_10) TextView answerText10;
    @BindView(R.id.answer_text_11) TextView answerText11;
    @BindView(R.id.answer_text_12) TextView answerText12;
    public Button[] buttons = new Button[]{answerBtn1, answerBtn2, answerBtn3, answerBtn4};
    public TextView[] textViews = new TextView[]{answerText1, answerText2, answerText3, answerText4, answerText5, answerText6, answerText7,
            answerText8, answerText9, answerText10, answerText11, answerText12};
    public boolean[] answered = new boolean[textViews.length];
    @BindView(R.id.result_text)TextView result;
    @BindView(R.id.skin_image) ImageView image;
    Random mRnd;
    int aI;
    int correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRnd = new Random();
        structures = getResources().getStringArray(R.array.structures_array);
    }

    public void askQuestion() {
        startBtn.setVisibility(View.GONE);

        aI = mRnd.nextInt(structures.length);
        int[] iS = new int[3];
        int incorrectAnswerIndex = 0;
        for (int i = 0; i < iS.length; i++) {
            while (iS[0] == iS[1] || iS[1] == iS[2] || iS[0] == iS[2] ||
                    iS[0] == aI || iS[1] == aI || iS[2] == aI) {
                incorrectAnswerIndex = mRnd.nextInt(structures.length);
            }
            iS[i] = incorrectAnswerIndex;
        }
        correct = mRnd.nextInt(buttons.length);
        for (int i = 0; i < buttons.length; i++) {
            if(i == correct){
                buttons[i].setText(structures[aI]);
            }else {
                buttons[i].setText(structures[iS[i]]);
            }
        }

        int colorValue = getResources().getColor(R.color.colorAccent);
        textViews[aI].setTextColor(colorValue);
    }

    public void answerQuestion (View view) {
        result.setText("");
        result.setAlpha(1f);
        if (buttons[correct] == view) {
            textViews[aI].setText(structures[aI]);
            int colorValue = getResources().getColor(R.color.colorWhite);
            textViews[aI].setTextColor(colorValue);
            answered[aI] = true;
            result.setText(getString(R.string.right));
            if (checkStructures()){
                askQuestion();
            } else {
                startBtn.setText(getString(R.string.play_again_text));
                startBtn.setVisibility(View.VISIBLE);
            }
        } else {
            result.setText(getString(R.string.wrong));
        }
        ObjectAnimator fade = ObjectAnimator.ofFloat(result, "alpha", 0f);
        fade.setDuration(1000);
        fade.start();
    }

    public void playAgain(View view) {
        startBtn.setVisibility(View.GONE);
        setBlanks();
        answered = new boolean[textViews.length];

        askQuestion();
    }

    public void setBlanks(){
        for (int i = 0; i < textViews.length; i++) {
            int colorValue = getResources().getColor(R.color.colorWhite);
            textViews[i].setTextColor(colorValue);
            textViews[i].setText(getString(R.string.question));
        }
    }

    public boolean checkStructures(){
        for(boolean answer: answered){
            if(answer){
                return true;
            }
        }
        return false;
    }

}
