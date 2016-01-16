package com.sinjvf.tetris;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.google.example.games.basegameutils.BaseGameUtils;


public class SignInActivity extends BaseGameActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private Button
            buttonBeginWithout,
            buttonBeginWith,
            buttonSignOut;
    private Button buttonSignIn;
    private final int REQUEST_CODE=1;
    private Intent intent;
    private static int RC_SIGN_IN = 9001;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInFlow = false;
    private boolean mSignInClicked = false;

    private boolean mExplicitSignOut = false;
    private boolean mInSignInFlow = false;



    GoogleApiClient mGoogleApiClient;
    @Override
    public void onSignInSucceeded() {
    }

    @Override
    public void onSignInFailed() {
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (!mInSignInFlow && !mExplicitSignOut) {
            // auto sign in
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Attempt to reconnect
        mGoogleApiClient.connect();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (mResolvingConnectionFailure) {
            // Already resolving
            return;
        }
        String s = getString(R.string.signin_other_error);
        if (mSignInClicked || mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;
            if (!BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, s)) {
                mResolvingConnectionFailure = false;
            }
        }

        // Put code here to display the sign-in button
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                BaseGameUtils.showActivityResultError(this,
                        requestCode, resultCode, R.string.signin_failure);
            }
        }else
            if (requestCode==Const.STANDART) {
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
    }
    @Override
    public void onConnected(Bundle connectionHint) {
        buttonSignIn.setVisibility(View.GONE);
        buttonSignOut.setVisibility(View.VISIBLE);
        buttonBeginWithout.setVisibility(View.GONE);
        buttonBeginWith.setVisibility(View.VISIBLE);
        Const.Connect =true;

        Intent intent = getIntent();
        ApiProperties apiProp =  intent.getParcelableExtra(Const.SIGN_IN);
        Log.d(Const.LOG_TAG, "apiprop="+apiProp);
        Log.d(Const.LOG_TAG, "getApiClient()p="+getApiClient());
        Log.d(Const.LOG_TAG, "getApiClient().isConnected()="+getApiClient().isConnected());
        if (apiProp!=null && getApiClient() != null && getApiClient().isConnected()) {
            Log.d(Const.LOG_TAG, "sign not null");
            String lead_id;
            switch (apiProp.getType()) {
                case Const.STANDART:
                    lead_id =getString( R.string.leaderboard_standart);
                    break;
                case Const.AWRY:
                    lead_id =getString( R.string.leaderboard_awry);
                    break;
                default:
                    lead_id =getString( R.string.leaderboard_hex);
                    break;
            }
            if (apiProp.getScore()>=0) {
                Log.d(Const.LOG_TAG, "sign set score");
                Games.Leaderboards.submitScore(getApiClient(), lead_id, apiProp.getScore());
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else if (apiProp.getScore()==Const.SCORE_FOR_RESULTS)

                Log.d(Const.LOG_TAG, "sign print score");
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(),
                    lead_id), Const.STANDART);
        }
      /*  intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    */}


    private void initAPI(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                        // add other APIs and scopes here as needed
                .build();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAPI();
        setContentView(R.layout.sign_in_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        buttonBeginWithout = (Button)findViewById(R.id.button_begin_without);

        buttonBeginWith = (Button)findViewById(R.id.button_begin_with);
        buttonSignIn = (Button) findViewById(R.id.sign_in_button);
        buttonSignOut = (Button)findViewById(R.id.sign_out_button);
        buttonBeginWithout.setOnClickListener(this);
        buttonBeginWith.setOnClickListener(this);
        buttonSignIn.setOnClickListener(this);
        buttonSignOut.setOnClickListener(this);
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                    R.drawable.logo_small);
            ActivityManager.TaskDescription taskDesc =
                    new ActivityManager.TaskDescription(getString(R.string.app_name),
                            icon, ContextCompat.getColor(this, R.color.dark_primary));
            this.setTaskDescription(taskDesc);
        }



    }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sign_in_button:
                    // start the asynchronous sign in flow
                    initAPI();
                    mSignInClicked = true;
                    mGoogleApiClient.connect();
                    break;
                case R.id.sign_out_button:
                    // sign out.
                    mSignInClicked = false;
                    Games.signOut(mGoogleApiClient);
                    Const.Connect = false;
                    // show sign-in button, hide the sign-out button
                    buttonSignIn.setVisibility(View.VISIBLE);
                    buttonSignOut.setVisibility(View.GONE);
                    buttonBeginWithout.setVisibility(View.VISIBLE);
                    buttonBeginWith.setVisibility(View.GONE);
                    break;

               default:
                    intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
        }
    }


}