package prototype.anatomy.anatomy;

import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    public String[] structures;
    @BindView(R.id.start_button) Button startBtn;
    @BindViews({R.id.answer_text_1, R.id.answer_text_2, R.id.answer_text_3, R.id.answer_text_4, R.id.answer_text_5, R.id.answer_text_6, R.id.answer_text_7,
            R.id.answer_text_8, R.id.answer_text_9, R.id.answer_text_10, R.id.answer_text_11, R.id.answer_text_12}) TextView[] answerTexts;
    @BindViews({R.id.answer_button_1, R.id.answer_button_2, R.id.answer_button_3, R.id.answer_button_4}) Button[] buttons;
    public boolean[] answered;
    @BindView(R.id.result_text)TextView result;
    @BindView(R.id.skin_image) ImageView image;
    Random mRnd;
    int aI;
    int correct;
    int colorValue;

    static final ButterKnife.Setter<TextView, Integer> BLANKS = new ButterKnife.Setter<TextView, Integer>() {
        @Override
        public void set(@NonNull TextView view, Integer value, int index) {
            view.setTextColor(value);
        }
    };

    static final ButterKnife.Setter<TextView, String> STRINGS = new ButterKnife.Setter<TextView, String>() {
        @Override
        public void set(@NonNull TextView view, String question, int index) {
            view.setText(question);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        colorValue = getResources().getColor(R.color.colorWhite);
        mRnd = new Random();
        structures = getResources().getStringArray(R.array.structures_array);
    }

    public void askQuestion() {
        startBtn.setVisibility(View.GONE);
        aI = mRnd.nextInt(structures.length);
        while(answered[aI]) {
            aI = mRnd.nextInt(structures.length);
        }
        int[] iS = new int[]{1000, 2000, 3000, 4000};
        for (int i = 0; i < iS.length; i++) {
            do{
                int incorrectAnswerIndex = mRnd.nextInt(structures.length);
                iS[i] = incorrectAnswerIndex;
                }while (iS[0] == iS[1] || iS[1] == iS[2] || iS[0] == iS[2] || iS[0] == iS[3] || iS[1] == iS [3] || iS[2] == iS[3] ||
                    iS[0] == aI || iS[1] == aI || iS[2] == aI || iS[3] == aI);
        }
        correct = mRnd.nextInt(buttons.length);
        iS[correct] = aI;
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(structures[iS[i]]);
        }
        int colorValue = getResources().getColor(R.color.colorAccent);
        answerTexts[aI].setTextColor(colorValue);
    }

    public void answerQuestion (View view) {
        result.setText("");
        result.setAlpha(1f);
        if (String.valueOf(correct).equals(view.getTag().toString())) {
            answerTexts[aI].setText(structures[aI]);
            answerTexts[aI].setTextColor(colorValue);
            result.setText(getString(R.string.right));
            answered[aI] = true;
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
        answered = new boolean[structures.length];
        askQuestion();
    }

    public void setBlanks(){
        String question = getString(R.string.question);
        ButterKnife.apply(answerTexts, BLANKS, colorValue);
        ButterKnife.apply(answerTexts, STRINGS, question);
    }

    public boolean checkStructures(){
        for(boolean answer: answered){
            if(!answer){
                return true;
            }
        }
        return false;
    }

}
