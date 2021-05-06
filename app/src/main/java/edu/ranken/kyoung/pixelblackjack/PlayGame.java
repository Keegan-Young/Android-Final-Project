package edu.ranken.kyoung.pixelblackjack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class PlayGame extends AppCompatActivity {

    TextView tvPlayerBet;
    TextView tvDealerBet;
    TextView tvPlayerCardValue;
    TextView tvDealerCardValue;
    TextView tvDefaultPlayerScore;
    TextView tvDefaultDealerScore;
    TextView tvDefaultPlayerCardValue;
    TextView tvDefaultDealerCardValue;

    EditText etEnterBet;
    Button   btnBet;
    Button   btnHit;
    Button   btnStand;
    Button   btnEndGame;
    Button   btnReset;

    ImageView ivDealerCard;
    ImageView ivPlayerCard;
    ImageView ivPokerChip1;
    ImageView ivPokerChip2;
    ImageView ivPokerChip3;
    ImageView ivPokerChip4;
    ImageView ivPokerChip5;
    ImageView ivPokerChip6;

    // Program Constants
    final int MINBET = 1;
    final int MAXBET = 100;
    final int PLAYERSTARTBANKVALUE = 500;
    final int DEALERSTARTBANKVALUE = 500;
    final String PBI = "Player Bet Invalid. Bet must be greater than $0 or $100 or less.";
    final String IF = "Insufficient Funds";
    final String PLAYERBUST = "You Bust!";
    final String DEALERBUST = "Dealer Bust!";
    final String DEALERSTAND = "Dealer Chooses To Stand!";
    final String PLAYERWIN = "Player Wins Round!";
    final String DEALERWIN = "Dealer Wins Round!";
    final String TIEBUST   = "Tie bust! You will retain your bet.";
    final String TIESTAND  = "Tie stand! You will retain your bet.";

    // Program Variables
    int playerBet = 0;
    int cardValue = 0;
    int dealerCardValue = 0;
    int playerTotalCardValue = 0;
    int dealerTotalCardValue = 0;
    int playerTotalScore = 0;
    int dealerTotalScore = 0;
    int randomCard = 0;
    int randomDealerBet = 0;
    static int playerTotalBankValue = 0;
    static int dealerTotalBankValue = 0;
    boolean firstRound;

    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);

        // References to widgets
        tvPlayerBet              = findViewById(R.id.tvPlayerBet);
        tvDealerBet              = findViewById(R.id.tvDealerBet);
        tvPlayerCardValue        = findViewById(R.id.tvPlayerCardValue);
        tvDealerCardValue        = findViewById(R.id.tvDealerCardValue);
        tvDefaultPlayerScore     = findViewById(R.id.tvDefaultPlayerScore);
        tvDefaultDealerScore     = findViewById(R.id.tvDefaultDealerScore);
        tvDefaultPlayerCardValue = findViewById(R.id.tvDefaultPlayerCardValue);
        tvDefaultDealerCardValue = findViewById(R.id.tvDefaultDealerCardValue);

        etEnterBet               = findViewById(R.id.etEnterBet);
        btnBet                   = findViewById(R.id.btnBet);
        btnHit                   = findViewById(R.id.btnHit);
        btnStand                 = findViewById(R.id.btnStand);
        btnEndGame               = findViewById(R.id.btnEndGame);
        btnReset                 = findViewById(R.id.btnReset);

        ivDealerCard             = findViewById(R.id.ivDealerCard);
        ivPlayerCard             = findViewById(R.id.ivPlayerCard);
        ivPokerChip1             = findViewById(R.id.ivPokerChip1);
        ivPokerChip2             = findViewById(R.id.ivPokerChip2);
        ivPokerChip3             = findViewById(R.id.ivPokerChip3);
        ivPokerChip4             = findViewById(R.id.ivPokerChip4);
        ivPokerChip5             = findViewById(R.id.ivPokerChip5);
        ivPokerChip6             = findViewById(R.id.ivPokerChip6);

        // Set buttons to disabled at start of the first round
        btnHit.setEnabled(false);
        btnStand.setEnabled(false);
        btnReset.setEnabled(false);

        firstRound = true;

            // The player starts with $500 at the beginning of the game
            Toast.makeText(getApplicationContext(), "You have $" + PLAYERSTARTBANKVALUE + " available.",
                    Toast.LENGTH_LONG).show();

        btnBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set these buttons to disabled when betting
                btnHit.setEnabled(false);
                btnStand.setEnabled(false);
                btnReset.setEnabled(false);

                // Player makes a bet which will be subtracted from their current score
                playerBet = Integer.parseInt(etEnterBet.getText().toString());

                if(firstRound)
                {
                    playerTotalBankValue = PLAYERSTARTBANKVALUE;
                    dealerTotalBankValue = DEALERSTARTBANKVALUE;
                }

                // Dealer makes random bet
                randomDealerBet = rand.nextInt(100) + 1;

                if(randomDealerBet > dealerTotalBankValue)
                {
                    randomDealerBet = rand.nextInt(100) + 1;
                }

                //Check for valid bet
                if((playerBet < MINBET) || (playerBet > MAXBET))
                {
                    Toast.makeText(getApplicationContext(), PBI, Toast.LENGTH_LONG).show();
                    btnHit.setEnabled(false);
                    btnStand.setEnabled(false);
                }
                else if(playerBet > playerTotalBankValue)
                {
                    Toast.makeText(getApplicationContext(), IF, Toast.LENGTH_LONG).show();
                }
                else if(playerBet < playerTotalBankValue)
                {
                    playerTotalBankValue = playerTotalBankValue - playerBet;
                    dealerTotalBankValue = dealerTotalBankValue - randomDealerBet;
                    generateRandomPlayerCard();
                    generateRandomDealerCard();
                    etEnterBet.setText("");
                    btnBet.setEnabled(false);
                    btnHit.setEnabled(true);
                    btnStand.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "You have $" + playerTotalBankValue +
                            " available.", Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Dealer has $" + dealerTotalBankValue +
                            " available.", Toast.LENGTH_LONG).show();
                    dealerTotalScore = randomDealerBet;
                    dealerTotalCardValue = dealerCardValue;
                    playerTotalCardValue = cardValue;
                    tvDefaultPlayerCardValue.setText(" " + cardValue);
                    tvDefaultPlayerScore.setText("$" + playerBet);
                    tvDefaultDealerCardValue.setText(" " + dealerCardValue);
                    tvDefaultDealerScore.setText("$" + randomDealerBet);
                    chips();
                }
            }
        });

        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Generate a new random card for the player and dealer
                generateRandomPlayerCard();

                // If the dealer's current total card value is less than 18, add the new card value to
                // the total card value.
                if (dealerTotalCardValue <= 18)
                {
                    generateRandomDealerCard();
                    dealerTotalCardValue = dealerCardValue + dealerTotalCardValue;
                }
                // If the dealer's card value reaches 19, 20 or 21, the dealer stands
                if ((dealerTotalCardValue == 19) || ((dealerTotalCardValue == 20) ||
                        (dealerTotalCardValue == 21)))
                {
                    Toast.makeText(getApplicationContext(), DEALERSTAND, Toast.LENGTH_LONG).show();
                    tvDefaultDealerCardValue.setText("" + dealerTotalCardValue);
                }

                // If the dealer's new card value is higher than 22, display a toast
                if(dealerTotalCardValue >= 22)
                {
                    firstRound = false;
                    Toast.makeText(getApplicationContext(), DEALERBUST, Toast.LENGTH_LONG).show();
                    playerTotalBankValue = randomDealerBet + playerBet + playerTotalBankValue;
                    btnBet.setEnabled(false);
                    btnHit.setEnabled(false);
                    btnStand.setEnabled(false);
                    btnReset.setEnabled(true);
                }

                // If the player's current total card value is less than 21, add the new card value to
                // the total card value.
                if (playerTotalCardValue <= 21)
                {
                    playerTotalCardValue = cardValue + playerTotalCardValue;
                }
                // If the player's new card value is higher than 22, display a toast
                if (playerTotalCardValue >= 22)
                {
                    firstRound = false;
                    Toast.makeText(getApplicationContext(), PLAYERBUST, Toast.LENGTH_LONG).show();
                    dealerTotalBankValue = randomDealerBet + playerBet + dealerTotalBankValue;
                    btnBet.setEnabled(false);
                    btnHit.setEnabled(false);
                    btnStand.setEnabled(false);
                    btnReset.setEnabled(true);
                }
                tvDefaultPlayerCardValue.setText("" + playerTotalCardValue);
                tvDefaultDealerCardValue.setText("" + dealerTotalCardValue);

                // This is hit when the round is a tie bust, the player and dealer will retain
                // their bet
                if ((playerTotalCardValue > 21) && (dealerTotalCardValue > 21))
                {
                    Toast.makeText(getApplicationContext(), TIEBUST, Toast.LENGTH_LONG).show();
                    playerTotalBankValue = playerTotalBankValue + playerBet - playerBet;
                    dealerTotalBankValue = dealerTotalBankValue + randomDealerBet - randomDealerBet;
                }

                if(playerTotalBankValue < 2)
                {
                    btnBet.setEnabled(false);
                    btnHit.setEnabled(false);
                    btnStand.setEnabled(false);
                    btnReset.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "You are out of money. You Lose!", Toast.LENGTH_LONG
                    ).show();
                }

                if(dealerTotalBankValue < 2)
                {
                    btnBet.setEnabled(false);
                    btnHit.setEnabled(false);
                    btnStand.setEnabled(false);
                    btnReset.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "Dealer is out of money. You Win!", Toast.LENGTH_LONG
                    ).show();
                }
            }
        });

        btnStand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEnterBet.setEnabled(false);
                btnBet.setEnabled(false);
                btnHit.setEnabled(false);
                // Player has decided to stand, meaning their turn for the round is over
                // and the dealer will continue. They will play until they have a higher
                // hand value than the player but a score lower than 21 or until they bust.

                Toast.makeText(getApplicationContext(), "You chose to stand!", Toast.LENGTH_LONG).show();

                // If the dealer's card value reaches 20 or 21, the dealer stands
                if ((dealerTotalCardValue == 20) || (dealerTotalCardValue == 21))
                {
                    Toast.makeText(getApplicationContext(), DEALERSTAND, Toast.LENGTH_LONG).show();
                    tvDefaultDealerCardValue.setText("" + dealerTotalCardValue);
                }

                // If the players hit "Stand" and the dealer's hand is lower than 18, generate
                // another card for the dealer
                if (dealerTotalCardValue <= 18)
                {
                    generateRandomDealerCard();
                    dealerTotalCardValue = dealerCardValue + dealerTotalCardValue;
                    tvDefaultDealerCardValue.setText("" + dealerTotalCardValue);
                }

                if (dealerTotalCardValue > 21)
                {
                    Toast.makeText(getApplicationContext(), DEALERBUST, Toast.LENGTH_LONG).show();
                    playerTotalBankValue = randomDealerBet + playerBet + playerTotalBankValue;
                    btnReset.setEnabled(true);
                    btnStand.setEnabled(false);
                }

                // If the dealer's hand is equal to 20 and more than the player's hand
                // the dealer wins the round.
                if (dealerTotalCardValue > playerTotalCardValue)
                {
                    Toast.makeText(getApplicationContext(), DEALERSTAND, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), DEALERWIN, Toast.LENGTH_LONG).show();
                    dealerTotalBankValue = randomDealerBet + playerBet + dealerTotalBankValue;
                    btnReset.setEnabled(true);
                    btnStand.setEnabled(false);
                }

                if (playerTotalCardValue > dealerTotalCardValue)
                {
                    Toast.makeText(getApplicationContext(), DEALERSTAND, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), PLAYERWIN, Toast.LENGTH_LONG).show();
                    playerTotalBankValue = randomDealerBet + playerBet + playerTotalBankValue;
                    btnReset.setEnabled(true);
                    btnStand.setEnabled(false);
                }

                // If the player has hit "Stand" and the dealer has a higher card value
                //if ((dealerTotalCardValue > playerTotalCardValue) && (dealerTotalCardValue < 21))
                //{
                //    Toast.makeText(getApplicationContext(), DEALERWIN, Toast.LENGTH_LONG).show();
                //    dealerTotalBankValue = randomDealerBet + playerBet + dealerTotalBankValue;
                //}

                // This is hit when the player hits the "Stand" button, and a tie stand occurs.
                if (playerTotalCardValue == dealerTotalCardValue)
                {
                    Toast.makeText(getApplicationContext(), TIESTAND, Toast.LENGTH_LONG).show();
                    playerTotalBankValue = playerTotalBankValue + playerBet;
                    dealerTotalBankValue = dealerTotalBankValue + randomDealerBet;
                    btnStand.setEnabled(false);
                    btnReset.setEnabled(true);
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                etEnterBet.setEnabled(true);
                btnBet.setEnabled(true);
                tvDefaultPlayerCardValue.setText("" + 0);
                tvDefaultDealerCardValue.setText("" + 0);
                tvDefaultPlayerScore.setText("$" + 0);
                tvDefaultDealerScore.setText("$" + 0);
                ivPlayerCard.setImageResource(R.drawable.starter_card);
                ivDealerCard.setImageResource(R.drawable.starter_card);
                Toast.makeText(getApplicationContext(), "You have $" + playerTotalBankValue + " available.",
                        Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Dealer has $" + dealerTotalBankValue +
                        " available.", Toast.LENGTH_LONG).show();
                btnReset.setEnabled(false);
            }
        });

        btnEndGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayGame.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void generateRandomPlayerCard()
    {
        randomCard = rand.nextInt(14) + 1;

        switch(randomCard)
        {
            case 1:
                ivPlayerCard.setImageResource(R.drawable.two_of_hearts);
                cardValue = 2;
                Toast.makeText(getApplicationContext(), "Two of Hearts", Toast.LENGTH_SHORT).show();
                break;

            case 2:
                ivPlayerCard.setImageResource(R.drawable.three_of_clubs);
                cardValue = 3;
                Toast.makeText(getApplicationContext(), "Three of Clubs", Toast.LENGTH_SHORT).show();
                break;

            case 3:
                ivPlayerCard.setImageResource(R.drawable.four_of_diamonds);
                cardValue = 4;
                Toast.makeText(getApplicationContext(), "Four of Diamonds", Toast.LENGTH_SHORT).show();
                break;

            case 4:
                ivPlayerCard.setImageResource(R.drawable.five_of_spades);
                cardValue = 5;
                Toast.makeText(getApplicationContext(), "Five of Spades", Toast.LENGTH_SHORT).show();
                break;

            case 5:
                ivPlayerCard.setImageResource(R.drawable.six_of_hearts);
                cardValue = 6;
                Toast.makeText(getApplicationContext(), "Six of Hearts", Toast.LENGTH_SHORT).show();
                break;

            case 6:
                ivPlayerCard.setImageResource(R.drawable.seven_of_clubs);
                cardValue = 7;
                Toast.makeText(getApplicationContext(), "Seven of Clubs", Toast.LENGTH_SHORT).show();
                break;

            case 7:
                ivPlayerCard.setImageResource(R.drawable.eight_of_diamonds);
                cardValue = 8;
                Toast.makeText(getApplicationContext(), "Eight of Diamonds", Toast.LENGTH_SHORT).show();
                break;

            case 8:
                ivPlayerCard.setImageResource(R.drawable.nine_of_clubs);
                cardValue = 9;
                Toast.makeText(getApplicationContext(), "Nine of Clubs", Toast.LENGTH_SHORT).show();
                break;

            case 9:
                ivPlayerCard.setImageResource(R.drawable.ten_of_hearts);
                cardValue = 10;
                Toast.makeText(getApplicationContext(), "Ten of Hearts", Toast.LENGTH_SHORT).show();
                break;

            case 10:
                ivPlayerCard.setImageResource(R.drawable.jack_of_diamonds);
                cardValue = 10;
                Toast.makeText(getApplicationContext(), "Jack of Diamonds", Toast.LENGTH_SHORT).show();
                break;

            case 11:
                ivPlayerCard.setImageResource(R.drawable.queen_of_spades);
                cardValue = 10;
                Toast.makeText(getApplicationContext(), "Queen of Spades", Toast.LENGTH_SHORT).show();
                break;

            case 12:
                ivPlayerCard.setImageResource(R.drawable.king_of_hearts);
                cardValue = 10;
                Toast.makeText(getApplicationContext(), "King of Hearts", Toast.LENGTH_SHORT).show();
                break;

            case 13:
                ivPlayerCard.setImageResource(R.drawable.ace_of_clubs_value_one);
                cardValue = 1;
                Toast.makeText(getApplicationContext(), "Ace of Clubs, Value of 1", Toast.LENGTH_SHORT).show();
                break;

            case 14:
                ivPlayerCard.setImageResource(R.drawable.ace_of_spades_value_eleven);
                cardValue = 11;
                Toast.makeText(getApplicationContext(), "Ace of Spades, Value of 11", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void generateRandomDealerCard()
    {
        randomCard = rand.nextInt(14) + 1;

        switch(randomCard)
        {
            case 1:
                ivDealerCard.setImageResource(R.drawable.two_of_hearts);
                dealerCardValue = 2;
                break;

            case 2:
                ivDealerCard.setImageResource(R.drawable.three_of_clubs);
                dealerCardValue = 3;
                break;

            case 3:
                ivDealerCard.setImageResource(R.drawable.four_of_diamonds);
                dealerCardValue = 4;
                break;

            case 4:
                ivDealerCard.setImageResource(R.drawable.five_of_spades);
                dealerCardValue = 5;
                break;

            case 5:
                ivDealerCard.setImageResource(R.drawable.six_of_hearts);
                dealerCardValue = 6;
                break;

            case 6:
                ivDealerCard.setImageResource(R.drawable.seven_of_clubs);
                dealerCardValue = 7;
                break;

            case 7:
                ivDealerCard.setImageResource(R.drawable.eight_of_diamonds);
                dealerCardValue = 8;
                break;

            case 8:
                ivDealerCard.setImageResource(R.drawable.nine_of_clubs);
                dealerCardValue = 9;
                break;

            case 9:
                ivDealerCard.setImageResource(R.drawable.ten_of_hearts);
                dealerCardValue = 10;
                break;

            case 10:
                ivDealerCard.setImageResource(R.drawable.jack_of_diamonds);
                dealerCardValue = 10;
                break;

            case 11:
                ivDealerCard.setImageResource(R.drawable.queen_of_spades);
                dealerCardValue = 10;
                break;

            case 12:
                ivDealerCard.setImageResource(R.drawable.king_of_hearts);
                dealerCardValue = 10;
                break;

            case 13:
                ivDealerCard.setImageResource(R.drawable.ace_of_clubs_value_one);
                dealerCardValue = 1;
                break;

            case 14:
                ivDealerCard.setImageResource(R.drawable.ace_of_spades_value_eleven);
                dealerCardValue = 11;
                break;
        }
    }

    public void chips()
    {
        // Player's chip count
        if(playerTotalBankValue < 400)
        {
            ivPokerChip1.setImageResource(R.drawable.splash_screen_image);
        }

        if(playerTotalBankValue < 300)
        {
            ivPokerChip2.setImageResource(R.drawable.splash_screen_image);
        }

        if(playerTotalBankValue < 50)
        {
            ivPokerChip3.setImageResource(R.drawable.splash_screen_image);
        }

        if(playerTotalBankValue > 400)
        {
            ivPokerChip1.setImageResource(R.drawable.chips);
        }

        if(playerTotalBankValue > 300)
        {
            ivPokerChip2.setImageResource(R.drawable.chips);
        }

        if(playerTotalBankValue > 50)
        {
            ivPokerChip3.setImageResource(R.drawable.chips);
        }



        // Dealer's chip count
        if(dealerTotalBankValue < 400)
        {
            ivPokerChip4.setImageResource(R.drawable.splash_screen_image);
        }

        if(dealerTotalBankValue < 300)
        {
            ivPokerChip5.setImageResource(R.drawable.splash_screen_image);
        }

        if(dealerTotalBankValue < 50)
        {
            ivPokerChip6.setImageResource(R.drawable.splash_screen_image);
        }

        if(dealerTotalBankValue > 400)
        {
            ivPokerChip4.setImageResource(R.drawable.chips);
        }

        if(dealerTotalBankValue > 300)
        {
            ivPokerChip5.setImageResource(R.drawable.chips);
        }

        if(dealerTotalBankValue > 50)
        {
            ivPokerChip6.setImageResource(R.drawable.chips);
        }
    }
}