package edu.ranken.kyoung.pixelblackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Constant
    final String RULES = "Rules: \n" +
                         "In this game of BlackJack, the object of the game is to attempt to beat the" +
                         " dealer by keeping a card value of 21 or lower while beating the total value" +
                         " of the dealer's hand. \n\n" +
                         "If your total card value goes over 21, you bust. If your total card value stays" +
                         " at 21 or lower and is higher than the dealers hand value, you win the round.";

    ImageView ivTitleImage;
    Button    btnPlayGame;
    Button    btnExit;
    Button    btnHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // References to widgets
        ivTitleImage = findViewById(R.id.ivTitleImage);
        btnPlayGame  = findViewById(R.id.btnPlayGame);
        btnExit      = findViewById(R.id.btnExit);
        btnHelp      = findViewById(R.id.btnHelp);

        btnPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayGame.class);
                startActivity(intent);
                finish();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HelpScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }
}