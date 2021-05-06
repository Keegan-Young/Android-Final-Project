package edu.ranken.kyoung.pixelblackjack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HelpScreen extends AppCompatActivity {
    TextView tvHelp;
    Button btnBackToTheMenu;

    final String HELP = "Rules of the Game: \n " +
            "In this game of BlackJack, the rules are simple; " +
            "The object of the game is to defeat the dealer by having a higher card value " +
            "than the dealer but maintaining a value of 21 or lower. " +
            "If your total hand value goes over 21, you bust and the dealer obtains the " +
            "bet you made at the beginning of the round. " +
            "These same rules apply to the dealer. " +
            "If you run out of funds, you lose the game.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_screen);

        tvHelp = findViewById(R.id.tvHelp);
        btnBackToTheMenu = findViewById(R.id.btnBackToTheMenu);

        tvHelp.setText(HELP);

        btnBackToTheMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
