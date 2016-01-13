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
            buttonBegin,
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
        }
    }
    @Override
    public void onConnected(Bundle connectionHint) {
        buttonSignIn.setVisibility(View.GONE);
        buttonSignOut.setVisibility(View.VISIBLE);
        buttonBegin.setVisibility(View.GONE);

        Log.d(Const.LOG_TAG, "Client="+getApiClient()+", connect="+ getApiClient().isConnected());
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


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
        buttonBegin = (Button)findViewById(R.id.button_begin);
        buttonSignIn = (Button) findViewById(R.id.sign_in_button);
        buttonSignOut = (Button)findViewById(R.id.sign_out_button);

        buttonBegin.setOnClickListener(this);
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
                    // show sign-in button, hide the sign-out button
                    buttonSignIn.setVisibility(View.VISIBLE);
                    buttonSignOut.setVisibility(View.GONE);
                    buttonBegin.setVisibility(View.VISIBLE);
                    break;

                case R.id.button_begin:
                    intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
        }
    }


}