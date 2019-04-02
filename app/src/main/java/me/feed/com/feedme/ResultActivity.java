package me.feed.com.feedme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView mScoreLableTv;
    private TextView mHighScoreLableTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mScoreLableTv = findViewById(R.id.scoreTv);
        mHighScoreLableTv = findViewById(R.id.highScoreTv);

        int mScore = getIntent().getIntExtra("score", 0);
        mScoreLableTv.setText("Score: " + mScore);

        SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int mHighScore = sharedPreferences.getInt("HIGH_SCORE", 0);

        if (mScore > mHighScore){
            mHighScoreLableTv.setText("High Score: " + mScore);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("HIGH_SCORE", mScore);
            editor.apply();
        }

        else{
            mHighScoreLableTv.setText("High Score: " + mHighScore);
        }
    }

    public void tryAgain(View view) {
        Intent intent = new Intent(ResultActivity.this, StartActivity.class);
        startActivity(intent);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent){
        if (keyEvent.getAction() == keyEvent.ACTION_DOWN){
            switch (keyEvent.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(keyEvent);

    }
}
