package me.feed.com.feedme;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView mScoreLable;
    private TextView mStartLable;

    private ImageView mBoxImageView;
    private ImageView mOrangeImageView;
    private ImageView mPinkImageView;
    private ImageView mBlackImageView;
    private ImageView mBlackSecondImageView;

    private int mBoxY;
    private int orangeX;
    private int orangeY;
    private int pinkX;
    private int pinkY;
    private int blackX;
    private int blackY;
    private int blackSecondX;
    private int blackSecondY;

   /* private int orangeSpeed;
    private int pinkSpeed;
    private int blackSpeed;
    private int blackSecondSpeed;
    private int boxSpeed;*/

    private int speed = 0;

    private int mScore = 0;

    private Handler handler = new Handler();
    private Timer timer = new Timer();

    private boolean action_Flag = false;
    private boolean start_Flag = false;

    private boolean timerX = false;

    private int mFrameHeight;
    private int mBoxSize;

    private int screenWidth;
    private int screenHeight;

    private Sound sound;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScoreLable = findViewById(R.id.scoreLable);
        mStartLable = findViewById(R.id.startLable);

        mBoxImageView = findViewById(R.id.boxImgView);

        mOrangeImageView = findViewById(R.id.orangeImgView);
        mPinkImageView = findViewById(R.id.pinkImgView);
        mBlackImageView = findViewById(R.id.blackImgView);
        mBlackSecondImageView = findViewById(R.id.blackSecImgView);

        sound = new Sound(this);

        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

       /* boxSpeed = Math.round(screenHeight / 60f);
        orangeSpeed = Math.round(screenHeight / 60f);
        pinkSpeed = Math.round(screenHeight / 36f);
        blackSpeed = Math.round(screenHeight / 45f);
        blackSecondSpeed = Math.round(screenHeight / 45f);*/

        screenWidth = size.x;
        screenHeight = size.y;


        mOrangeImageView.setX(-80);
        mOrangeImageView.setY(-80);

        mPinkImageView.setX(-80);
        mPinkImageView.setY(-80);

        mBlackImageView.setX(-80);
        mBlackImageView.setY(-80);

        mBlackSecondImageView.setX(-80);
        mBlackSecondImageView.setY(-80);

        mScoreLable.setText("Score : 0");


    }

    public void changePos(){


        if(action_Flag){

            boxUpSpeed();

        }
        else{

            boxDownSpeed();

        }

        if (mBoxY < 0){
            mBoxY = 0;
        }

        if (mBoxY > mFrameHeight - mBoxSize){
            mBoxY = mFrameHeight - mBoxSize;
        }
        mBoxImageView.setY(mBoxY);

        mScoreLable.setText("Score : " + mScore);

        hitCheck();

        showOrange();
        showPink();
        showBlack();
        showSecondBlack();


    }

    public boolean onTouchEvent(MotionEvent me) {

        if (!start_Flag){

            start_Flag = true;
            mStartLable.setVisibility(View.GONE);

            FrameLayout mFrame = findViewById(R.id.frameLayout);
            mFrameHeight = mFrame.getHeight();
            mBoxY = (int) mBoxImageView.getHeight();

            mBoxSize = mBoxImageView.getHeight();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 20);

        }
        else {
            if (me.getAction() == MotionEvent.ACTION_DOWN){
                action_Flag = true;
            }
            else if(me.getAction() == MotionEvent.ACTION_UP){
                action_Flag = false;
            }
        }


        return true;
    }

    public void hitCheck(){

        hitOrange();
        hitPink();
        hitBlack();
        hitSecondBlack();


    }

    public void hitOrange(){
        int orangeCenterX = orangeX + mOrangeImageView.getWidth() / 2;
        int orangeCenterY = orangeY + mOrangeImageView.getHeight() / 2;

        if(0 <= orangeCenterX && orangeCenterX <= mBoxSize &&
                mBoxY <= orangeCenterY && orangeCenterY <= mBoxSize + mBoxY ){

            mScore += 5;
            orangeX = -10;
            sound.playHitSound();

        }
    }

    public void hitPink(){
        int pinkCenterX = pinkX + mPinkImageView.getWidth() / 2;
        int pinkCenterY = pinkY + mPinkImageView.getHeight() / 2;

        if(0 <= pinkCenterX && pinkCenterX <= mBoxSize &&
                mBoxY <= pinkCenterY && pinkCenterY <= mBoxSize + mBoxY ){

            mScore += 10;
            pinkX = -10;
            sound.playHitSound();

        }
    }

    public void hitBlack(){
        int blackCenterX = blackX + mBlackImageView.getWidth() / 2;
        int blackCenterY = blackY + mBlackImageView.getHeight() / 2;

        if(0 <= blackCenterX && blackCenterX <= mBoxSize &&
                mBoxY <= blackCenterY && blackCenterY <= mBoxY + mBoxSize ){

            timer.cancel();
            timerX = true;
            timer = null;

            sound.playOverSound();

            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("score", mScore);
            startActivity(intent);
        }

    }

    public void hitSecondBlack(){
        int blackSecondCenterX = blackSecondX + mBlackSecondImageView.getWidth() / 2;
        int blackSecondCenterY = blackSecondY + mBlackSecondImageView.getHeight() / 2;

        if(0 <= blackSecondCenterX && blackSecondCenterX <= mBoxSize &&
                mBoxY <= blackSecondCenterY && blackSecondCenterY <= mBoxY + mBoxSize ){

            timer.cancel();
            timerX = true;
            timer = null;

            sound.playOverSound();

            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("score", mScore);
            startActivity(intent);
        }

    }

    public void showOrange(){

        changeOrangeSpeed();

        if (orangeX < 0) {
            orangeX = screenWidth + 20;
            orangeY = (int) Math.floor(Math.random() * (mFrameHeight - mOrangeImageView.getHeight()));
        }
        mOrangeImageView.setX(orangeX);
        mOrangeImageView.setY(orangeY);


    }



    public void showPink(){

        changePinkSpeed();

        if (pinkX < 0){
            pinkX = screenWidth + 1000;
            pinkY = (int) Math.floor(Math.random() * (mFrameHeight - mPinkImageView.getHeight()));

        }
        mPinkImageView.setX(pinkX);
        mPinkImageView.setY(pinkY);

    }

    public void showBlack(){

        changeBlackSpeed();

        if (blackX < 0){
            blackX = screenWidth + 1000;
            blackY = (int) Math.floor(Math.random() * (mFrameHeight - mBlackImageView.getHeight()));


        }
        mBlackImageView.setX(blackX);
        mBlackImageView.setY(blackY);

    }

    public void showSecondBlack(){

        changeSecondBlackSpeed();

        if (blackSecondX < 0){
            blackSecondX = screenWidth + 10;
            blackSecondY = (int) Math.floor(Math.random() * (mFrameHeight - mBlackSecondImageView.getHeight()));

        }
        mBlackSecondImageView.setX(blackSecondX);
        mBlackSecondImageView.setY(blackSecondY);

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This Game seems to be hard for you, want to quit?")
                .setCancelable(false)
                .setTitle("Quit Game")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void changeOrangeSpeed() {

        if (mScore >= 0 && mScore <=60){
            orangeX -=19;

        }

        if (mScore  >= 61 && mScore <= 100){
            orangeX -=20;

        }

        if (mScore  >= 101 && mScore <= 160){
            orangeX -=22;


        }

        if (mScore  >= 161 && mScore <= 200){
            orangeX -=26;


        }

        if (mScore  >= 201 && mScore <= 240){
            orangeX -=30;


        }

        if (mScore  >= 241 && mScore <= 300){
            orangeX -=32;


        }

        if (mScore  >= 301 && mScore <= 360){
            orangeX -=35;


        }

        if (mScore  >= 361 && mScore <= 400){
            orangeX -=38;


        }

        if (mScore  >= 401 && mScore <= 460){
            orangeX -=41;


        }

        if (mScore  >= 461 && mScore <= 500){
            orangeX -=44;


        }

        if (mScore  >= 501 && mScore <= 540){
            orangeX -=47;


        }

        if (mScore  >= 541 && mScore <= 600){
            orangeX -=50;


        }

        if (mScore > 601){
            orangeX -=53;


        }
    }

    private void changePinkSpeed() {

        if (mScore >= 0 && mScore <=60){
            pinkX -=21;
            ;

        }

        if (mScore  >= 61 && mScore<= 100){
            pinkX -=22;
        }

        if (mScore  >= 101 && mScore <= 160){
            pinkX -=24;

        }

        if (mScore  >= 161 && mScore <= 200){
            pinkX -=26;

        }

        if (mScore  >= 201 && mScore <= 240){
            pinkX -=28;

        }

        if (mScore  >= 241 && mScore <= 300){
            pinkX -=30;

        }

        if (mScore  >= 301 && mScore <= 340){
            pinkX -=32;

        }

        if (mScore  >= 341 && mScore <= 400){
            pinkX -=34;

        }

        if (mScore  >= 401 && mScore <= 460){
            pinkX -=36;

        }

        if (mScore  >= 460 && mScore <= 500){
            pinkX -=38;

        }

        if (mScore  >= 501 && mScore <= 540){
            pinkX -=40;

        }

        if (mScore  >= 541 && mScore <= 600){
            pinkX -=42;

        }

        if (mScore > 601){
            pinkX -=44;

        }
    }

    private void changeBlackSpeed() {

        if (mScore >= 0 && mScore <=60){

            blackX -=17;
        }

        if (mScore  >= 61 && mScore <= 100){

            blackX -=18;
        }

        if (mScore  >= 101 && mScore <= 140){

            blackX -=19;

        }

        if (mScore  >= 141 && mScore <= 200){

            blackX -=21;

        }

        if (mScore  >= 201 && mScore <= 260){

            blackX -=23;

        }

        if (mScore  >= 261 && mScore <= 300){

            blackX -=25;

        }

        if (mScore  >= 301 && mScore <= 340){

            blackX -=26;

        }

        if (mScore  >= 341 && mScore <= 400){

            blackX -=27;

        }

        if (mScore  >= 401 && mScore <= 460){

            blackX -=29;

        }

        if (mScore  >= 461 && mScore <= 500){

            blackX -=31;

        }

        if (mScore  >= 501 && mScore <= 540){

            blackX -=33;

        }

        if (mScore  >= 541 && mScore <= 600){

            blackX -=35;

        }

        if (mScore > 601){

            blackX -=37;

        }
    }

    private void changeSecondBlackSpeed() {

        if (mScore >= 0 && mScore <=60){

            blackSecondX -=17;
        }

        if (mScore  >= 61 && mScore <= 100){

            blackSecondX -=18;
        }

        if (mScore  >= 101 && mScore <= 140){

            blackSecondX -=19;

        }

        if (mScore  >= 141 && mScore <= 200){

            blackSecondX -=21;

        }

        if (mScore  >= 201 && mScore <= 260){

            blackSecondX -=23;

        }

        if (mScore  >= 261 && mScore <= 300){

            blackSecondX -=26;

        }

        if (mScore  >= 301 && mScore <= 340){

            blackSecondX -=28;

        }

        if (mScore  >= 341 && mScore <= 400){

            blackSecondX -=30;

        }

        if (mScore  >= 401 && mScore <= 460){

            blackSecondX -=33;

        }

        if (mScore  >= 460 && mScore <= 500){

            blackSecondX -=36;

        }

        if (mScore  >= 501 && mScore <= 540){

            blackSecondX -=39;

        }

        if (mScore  >= 541 && mScore <= 600){

            blackSecondX -=42;

        }

        if (mScore > 601){

            blackSecondX -=45;

        }
    }

    public void boxUpSpeed(){

        if (mScore >= 0 && mScore <=80){
            mBoxY -=22;

        }

        if (mScore  >= 81 && mScore <= 90){
            mBoxY -=25;

        }

        if (mScore  >= 91 && mScore <= 100){
            mBoxY -=28;


        }

        if (mScore  >= 101 && mScore <= 110){
            mBoxY -=30;


        }

        if (mScore  >= 111 && mScore <= 120){
            mBoxY -=32;


        }

        if (mScore  >= 121 && mScore <= 130){
            mBoxY -=34;


        }

        if (mScore  >= 131 && mScore <= 140){
            mBoxY -=37;


        }

        if (mScore  >= 141 && mScore <= 150){
            mBoxY -=40;


        }

        if (mScore  >= 151 && mScore <= 190){
            mBoxY -=43;


        }

        if (mScore  >= 191 && mScore <= 250){
            mBoxY -=47;


        }

        if (mScore  >= 251 && mScore <= 330){
            mBoxY -=50;


        }

        if (mScore  >= 331 && mScore <= 360){
            mBoxY -=53;


        }

        if (mScore > 361){
            mBoxY -=60;


        }

    }

    public void boxDownSpeed(){

        if (mScore >= 0 && mScore <=80){
            mBoxY +=22;

        }

        if (mScore  >= 81 && mScore <= 90){
            mBoxY +=25;

        }

        if (mScore  >= 91 && mScore <= 100){
            mBoxY +=28;


        }

        if (mScore  >= 101 && mScore <= 110){
            mBoxY +=30;


        }

        if (mScore  >= 111 && mScore <= 120){
            mBoxY +=32;


        }

        if (mScore  >= 121 && mScore <= 130){
            mBoxY +=34;


        }

        if (mScore  >= 131 && mScore <= 140){
            mBoxY +=37;


        }

        if (mScore  >= 141 && mScore <= 150){
            mBoxY +=40;


        }

        if (mScore  >= 151 && mScore <= 190){
            mBoxY +=43;


        }

        if (mScore  >= 191 && mScore <= 250){
            mBoxY +=47;


        }

        if (mScore  >= 251 && mScore <= 330){
            mBoxY +=50;


        }

        if (mScore  >= 331 && mScore <= 360){
            mBoxY +=53;


        }

        if (mScore > 361){
            mBoxY +=60;


        }

    }
}
