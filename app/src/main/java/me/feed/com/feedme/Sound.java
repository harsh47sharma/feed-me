package me.feed.com.feedme;


import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class Sound {

    private static SoundPool soundPool;
    private static int hitSound;
    private static int overSound;

    public Sound(Context context){

        //SoundPool(int maxStreams, int streamType, int srcQuality)
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        hitSound = soundPool.load(context, R.raw.hit, 1);
        overSound = soundPool.load(context, R.raw.over, 1);
    }

    public void playHitSound(){

        soundPool.play(hitSound, 1.0f, 1.0f, 1, 0, 1.0f);

    }

    public void playOverSound(){

        soundPool.play(overSound, 1.0f, 1.0f, 1, 0, 1.0f);

    }

}
