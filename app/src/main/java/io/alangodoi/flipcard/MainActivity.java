package io.alangodoi.flipcard;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ConstraintLayout clCardFront, clCardBack;
    ImageView cardFlag;
    TextView tvCardNumber, tvCardNumber2, tvCardNumber3, tvCardNumber4,
            tvCardName, tvCardValidThru, tvCardCvv;
    EditText etCardNumber, etCardName, etCardValidThru, etCardCVV;
    StringBuilder sb;

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clCardFront = findViewById(R.id.clCardFront);
        clCardBack = findViewById(R.id.clCardBack);
        cardFlag = findViewById(R.id.cardFlag);
        tvCardNumber = findViewById(R.id.tvCardNumber);
        tvCardNumber2 = findViewById(R.id.tvCardNumber2);
        tvCardNumber3 = findViewById(R.id.tvCardNumber3);
        tvCardNumber4 = findViewById(R.id.tvCardNumber4);
        tvCardName = findViewById(R.id.tvCardName);
        tvCardValidThru = findViewById(R.id.tvCardValidThru);
        tvCardCvv = findViewById(R.id.tvCardCvv);
        etCardNumber = findViewById(R.id.etCardNumber);
        etCardName = findViewById(R.id.etCardName);
        etCardValidThru = findViewById(R.id.etCardValidThru);
        etCardCVV = findViewById(R.id.etCardCvv);

        loadAnimations();
        changeCameraDistance();

        etCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    if (start == 0 && s.charAt(0) == '4') {
                        cardFlag.setImageDrawable(
                                getResources().getDrawable(R.drawable.visa));
                    } else if (s.length() > 1) {
                        if ((s.charAt(0) == '5') &&
                                (s.charAt(1) == '1' || s.charAt(1) == '2') ) {
                            cardFlag.setImageDrawable(
                                    getResources().getDrawable(R.drawable.mastercard));
                        } else if (s.charAt(0) == '3' && s.charAt(1) == '7') {
                            cardFlag.setImageDrawable(
                                    getResources().getDrawable(R.drawable.americanexpress));
                        }
                    }
                    cardFlag.setVisibility(View.VISIBLE);
                } else {
                    cardFlag.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() <= 4) {
                    tvCardNumber.setText(s);
                } else if ((s.length() >= 5) && (s.length() <= 8)) {
                    tvCardNumber2.setText(s.subSequence(4, s.length()));
                } else if ((s.length() >= 9) && (s.length() <= 12)) {
                    tvCardNumber3.setText(s.subSequence(8, s.length()));
                } else if (s.length() >= 13) {
                    tvCardNumber4.setText(s.subSequence(12, s.length()));
                }
            }
        });

        etCardName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvCardName.setText(s);
            }
        });

        etCardValidThru.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 2) {
                    etCardValidThru.setText(s + "/");
                    etCardValidThru.setSelection(etCardValidThru.getText().length());
                }
                tvCardValidThru.setText(s);
            }
        });

        etCardCVV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    flipCard(clCardBack, clCardFront);
                } else {
                    flipCard(clCardFront, clCardBack);
                }
            }
        });

        etCardCVV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvCardCvv.setText(s);
            }
        });

    }

    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        clCardFront.setCameraDistance(scale);
        clCardBack.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(
                this, R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(
                this, R.animator.in_animation);
    }

    public void flipCard(View show, View hide) {
        mSetRightOut.setTarget(hide);
        mSetLeftIn.setTarget(show);
        mSetRightOut.start();
        mSetLeftIn.start();

        show.setVisibility(View.VISIBLE);
        hide.setVisibility(View.INVISIBLE);
    }

}
