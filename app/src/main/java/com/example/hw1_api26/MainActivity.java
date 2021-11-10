package com.example.hw1_api26;

import static android.widget.ImageView.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Vibrator;

public class MainActivity extends AppCompatActivity {

    public ImageView car;
    public ImageView stone;
    public LinearLayout leftTrack;
    public LinearLayout middleTrack;
    public LinearLayout rightTrack;
    public LinearLayout carTrack;
    public LinearLayout leftCarTrack;
    public LinearLayout middleCarTrack;
    public LinearLayout rightCarTrack;
    public ImageButton rightBtn;
    public ImageButton leftBtn;
    public LinearLayout topBar;
    public ImageView heart1;
    public ImageView heart2;
    public ImageView heart3;
    public int chosenTrack;
    public int wastedLives;
    public List<LinearLayout> tracks;
    public List<ImageView> lives;

    public void moveRight(){ // Function that is responsible about moving the car right
        rightBtn = findViewById(R.id.right_btn);
        rightBtn.setOnClickListener(e->{
            if(leftCarTrack.findViewById(car.getId()) != null){ // if the car is in the left track then move it to the middle track
                leftCarTrack.removeView(car);
                middleCarTrack.addView(car);
            }
            else if(middleCarTrack.findViewById(car.getId()) != null){ // if the car is in the middle track then move it to the right track
                middleCarTrack.removeView(car);
                rightCarTrack.addView(car);
            }

        });
    }

    public void moveLeft(){ // Function that is responsible about moving the car left
        leftBtn = findViewById(R.id.left_btn);
        leftBtn.setOnClickListener(e -> {
            if(rightCarTrack.findViewById(car.getId()) != null){ // if the car is in the left track then move it to the middle track
                rightCarTrack.removeView(car);
                middleCarTrack.addView(car);
            }
            else if(middleCarTrack.findViewById(car.getId()) != null){ // if the car is in the middle track then move it to the right track
                middleCarTrack.removeView(car);
                leftCarTrack.addView(car);
            }
        });
    }

    public void startGame(){ // Function that is responsible about moving the stone in the tracks
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
                                      @Override
                                      public void run() {
                                          runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {
                                                  if (stone.getY() == (carTrack.getY()-60) && stone.getY() != 0.0) { // If the stone reached the car track on the y axis then move the stone to the next track
                                                      if((leftTrack.findViewById(stone.getId()) != null) && (leftCarTrack.findViewById(car.getId()) != null)||
                                                              (middleTrack.findViewById(stone.getId()) != null) && (middleCarTrack.findViewById(car.getId()) != null)||
                                                              (rightTrack.findViewById(stone.getId()) != null) && (rightCarTrack.findViewById(car.getId()) != null)){ // If the stone at the same side of track with the car then remove on live
                                                          vibrate(); // Make the phone vibrate
                                                          if(wastedLives == 2){ // If you failed 3 times then start again with another 3 lives
                                                              for(ImageView live : lives)
                                                                  live.setVisibility(VISIBLE);
                                                              wastedLives = 0;
                                                          }
                                                          else{
                                                              lives.get(wastedLives).setVisibility(INVISIBLE);
                                                              wastedLives++;
                                                          }
                                                          Toast.makeText(MainActivity.this,"oops! watch out!",Toast.LENGTH_SHORT).show();
                                                      }
                                                      for(LinearLayout track : tracks) {
                                                          track.removeView(stone);
                                                      }
                                                      chosenTrack++;
                                                      tracks.get(chosenTrack%3).addView(stone);
                                                      stone.setY(0);
                                                  }
                                                  else{
                                                      stone.setY(stone.getY() + 1); // Move the stone in the y axis
                                                  }
                                              }
                                          });
                                      }
                                  },
                0,
                3);
    }

    private void vibrate(){
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        car = findViewById(R.id.car);
        leftTrack = findViewById(R.id.left_track);
        middleTrack = findViewById(R.id.middle_track);
        rightTrack = findViewById(R.id.right_track);
        carTrack = findViewById(R.id.carTrack);
        leftCarTrack = findViewById(R.id.left_car_track);
        middleCarTrack = findViewById(R.id.middle_car_track);
        rightCarTrack = findViewById(R.id.right_car_track);
        stone = findViewById(R.id.stone);
        topBar = findViewById(R.id.top_horizontal_linear_layout);
        heart1 = findViewById(R.id.heart1);
        heart2 = findViewById(R.id.heart2);
        heart3 = findViewById(R.id.heart3);

        tracks = new ArrayList<>();
        tracks.add(leftTrack);
        tracks.add(middleTrack);
        tracks.add(rightTrack);

        lives = new ArrayList<>();
        lives.add(heart1);
        lives.add(heart2);
        lives.add(heart3);

        wastedLives = 0;
        chosenTrack = 0;

        moveRight();
        moveLeft();
        startGame();
    }
}