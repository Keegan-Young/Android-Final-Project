package edu.ranken.kyoung.pixelblackjack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Scoreboard extends AppCompatActivity {

    Button btnBackToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard);

        btnBackToMenu = findViewById(R.id.btnBackToMenu);


        btnBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Scoreboard.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
