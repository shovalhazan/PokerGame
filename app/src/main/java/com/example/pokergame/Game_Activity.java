package com.example.pokergame;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokergame.utils.SP;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game_Activity extends AppCompatActivity {
    private static final String TAG = "gameAc";
    //image Views
    private ImageView curCard1;
    private ImageView  curCard2;
    //text Views
    private TextView num_of_wins_player1;
    private TextView num_of_wins_player2;
    //for the game
    //cards
    private Card card1;
    private Card card2;
    // int counters
    private int player1_count_wins=0;
    private int player2_count_wins=0;
    //deck
    private Deck deck=new Deck();
    //winner
    private Record newRecord;
    //array list for handle records
    private ArrayList<Record> records = new ArrayList<Record>();
    //for delay
    Handler handler = new Handler();
    Runnable runnable;
    //for location
  //  private LatLng recLatlng;//for location coordinate
    private FusedLocationProviderClient fusedLocationProviderClient; // Fetching location
    private Location mLastKnownLocation; // Last known location of the device
    private LocationCallback locationCallback; // Updating users request if last known location is null
    //final
    private  static final int DELAY = 100;
    private ProgressBar progressBar;
    private int progressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_);
        MySignal.getInstance().play(R.raw.horn);
        Log.d(TAG, "onCreate:creating ");
        hideTheStatusBar();
        findViews();
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                if(deck.getCards().size()==0) {
                    getRecords();
                    winnerActivity();
                }else {
                    progressStatus += 1;
                    oneTurn(deck.getCards());
                    progressBar.setProgress(progressStatus);
                    handler.postDelayed(runnable, DELAY);
                }
            }
         }, DELAY);
    }
    private void hideTheStatusBar(){
        View decorView = getWindow().getDecorView();
        int uiOption=View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOption);

    }
    public void findViews(){
        curCard1 = findViewById(R.id.IMG_card1);
        curCard2 = findViewById(R.id.IMG_card2);
        num_of_wins_player1 = findViewById(R.id.game_LBL_leftScore);
        num_of_wins_player2 = findViewById(R.id.game_LBL_rightScore);
        progressBar=findViewById(R.id.progressBar);
    }


    public String msgForWinnerActivity(){
        String winner;
        if(player1_count_wins>player2_count_wins)
            winner = "player 1 with " + player1_count_wins + " score";
        else if(player1_count_wins<player2_count_wins)
            winner = "player 2 with " + player2_count_wins + " score";
        else
            winner = "equals scores!! they both win with:" + player1_count_wins;
        return winner;
    }
    /*move to winner activity*/
   public void winnerActivity()
    {   String winner=msgForWinnerActivity();
       Intent myIntent = new Intent(Game_Activity.this, Winner_Activity.class);
        myIntent.putExtra(Winner_Activity.EXTRA_COUNT, winner);
        startActivity(myIntent);
    }
/**this function get from SP the records array list as string and put it in global array list with gson
 * finally (after we get the loat/init array) move to isLocationEnabled()**/
    public void getRecords(){
        String recArrStr=SP.getInstance().getString("arr",null); //get from SP the records arrayList that we save until now
        Gson gson = new Gson();
        Type recordType = new TypeToken<ArrayList<Record>>() {//the type we want to get from the gson
                  }.getType();
        records = gson.fromJson(recArrStr, recordType);
        if (records != null) { // If there is something to load
           System.out.println("Records array: " + records.toString());//for check
        } else { // If there is nothing to load, init array
           records = new ArrayList<>();
        }
        //check is location enable
        isLocationEnabled();
    }

/** if we enable to get the location - we have a permission the task from settings success**/
    @SuppressLint("MissingPermission") // Don't need, already did it on start
    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: Getting users location");
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) { // Got result
                if (task.isSuccessful()) {
                    mLastKnownLocation = task.getResult();//return the last location of the device
                    if (mLastKnownLocation != null) { // If the result is not null we can save it
                        Log.d(TAG, "onComplete: Result is not null");
                        makeRecord();

                    } else { // If the location is null, we need to request updated location
                        Log.d(TAG, "onComplete: Result is null, requesting location update");
                        //Request for location
                        final LocationRequest locationRequest = openLocationRequest();
                        //Callback-we try again
                        locationCallback = new LocationCallback() {
                            /** We tried to get the last location from the fusedLocationProvider,
                             it returned null so we need to create a location request
                             if the result is still null, we return. else we update our last known
                             location variable*/
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                if (locationResult == null) {//if we still not get a last location
                                    Log.d(TAG, "onLocationResult: result is null, didnt get updated location");
                                    //Todo : can be a problem
                                    return;
                                }
                                Log.d(TAG, "onLocationResult: got updated location");
                                mLastKnownLocation = locationResult.getLastLocation();//update
                                makeRecord();//now we have location so save
                                fusedLocationProviderClient.removeLocationUpdates(locationCallback); // Remove the updates so we wont keep getting location updates.
                            }

                        };
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest
                                , locationCallback, null);
                    }
                } else { // In case we were unable to get last known location
                    Log.d(TAG, "onComplete: Im here unable to get last known location");
                    Toast.makeText(Game_Activity.this, "Unable to get last location"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void makeRecord(){
        Log.d(TAG, "saveLocation: ");
        //variable for reord
        LatLng latlng = new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude());
        Log.d(TAG, "saveLocation: lat="+latlng.latitude+" lng=" +latlng.longitude);
        int score =Math.max(player1_count_wins,player2_count_wins);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy\nHH:mm:ss");//for date
        newRecord= new Record(getWinnerName(),format.format(System.currentTimeMillis()),score);
        newRecord.setLocation(latlng);
        saveRecordArr();
    }


    public void saveRecordArr(){
        if(records.size()<10)
            records.add(newRecord);
        else if(records.get(9).getScore()<newRecord.getScore())
            records.add(newRecord);
        Collections.sort(records);
        Log.d(TAG, "saveRecord: "+records.toString());
        Gson gson = new Gson();
        String recStr=gson.toJson(records);
        Log.d(TAG, "saveRecord: "+recStr);
        SP.getInstance().putString("arr",recStr);
    }
    private void isLocationEnabled() {
        Log.d(TAG, "isLocationEnabled: ");
        // Open a new location request
        final LocationRequest locationRequest = openLocationRequest();
        // Pass the location request to the builder
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addAllLocationRequests(Collections.singleton(locationRequest));
        //get setting
        SettingsClient settingsClient = LocationServices.getSettingsClient(Game_Activity.this);
        // Check whether the location is on or not
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        //if location is already enabled-on success listener
        task.addOnSuccessListener(Game_Activity.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {//if the location enable go to get device location
                Log.d(TAG, "onSuccess: Location is already enabled");
                getDeviceLocation();
            }
        });
        // Location is not enable(Failure listener) , but we can check if the issue can be resolved
        task.addOnFailureListener(Game_Activity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                    try { // Try to resolve the issue
                        Log.d(TAG, "onFailure: Location not enabled, trying to resolve");
                        // Show the user a dialog where he can accept or not the location toggle
                        resolvableApiException.startResolutionForResult(Game_Activity.this, 51);
                    } catch (IntentSender.SendIntentException ex) {
                        Log.d(TAG, "onFailure: NOT RESOLVABLE");
                        ex.printStackTrace();
                    }
                }
            }
        });

    }

    private LocationRequest openLocationRequest() {
        Log.d(TAG, "openLocationRequest: Opening location request");
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setExpirationDuration(30000);
        return locationRequest;
    }

    public void setWinner(int winner){
        if(winner==1) {
            player1_count_wins++;
            num_of_wins_player1.setText(" "+player1_count_wins);
        }else if(winner==2) {
            player2_count_wins++;
            num_of_wins_player2.setText(" "+player2_count_wins);
        }else{
            player1_count_wins++;
            player2_count_wins++;
            num_of_wins_player1.setText(" "+player1_count_wins);
            num_of_wins_player2.setText(" "+player2_count_wins);
        }
    }

    public String getWinnerName(){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy\nHH:mm:ss");
        String date = format.format(System.currentTimeMillis());
        if (player1_count_wins>player2_count_wins)
            return "player 1";
        else if (player1_count_wins< player2_count_wins)
            return "player 2";
        else
            return "both";
    }

    public void oneTurn(ArrayList<Card> deck){
        //get a random 2 cards from the deck that left while we have a cards in the deck
        //remove from the deck the card we already play .
        //the function return the number of the winner.
        int winner;
        card1 = getRandomCard(deck);
        deck.remove(card1);
        card2 = getRandomCard(deck);
        deck.remove(card2);
        //set the random cards to the viewImage
        curCard1.setImageResource(card1.getCard_img_id());
        curCard2.setImageResource(card2.getCard_img_id());

        winner=compareCard(card1,card2);
        setWinner(winner);
    }

    public int compareCard(Card card1,Card card2){
        //compare between two cards by rank
        //return: 0 if equals,1 if card1 bigger,2 if card2 bigger
        if(card1.getRank()==card2.getRank())
            return 0;
        else if(card1.getRank().ordinal()>card2.getRank().ordinal())
            return 1;
        else
            return 2;
    }

    public Card getRandomCard(ArrayList<Card>curDeck){
        int rnd = new Random().nextInt(curDeck.size());
        return curDeck.get(rnd);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }
    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }
    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }


    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestory");
        super.onDestroy();
    }

}
