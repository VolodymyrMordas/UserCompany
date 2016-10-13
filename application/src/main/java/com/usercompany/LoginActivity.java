package com.usercompany;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.applog.LogUtils;
import com.apputils.ui.AppButton;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.usercompany.entities.User;
import com.usercompany.entities.UserFacebook;
import com.usercompany.network.RequestListener;
import com.usercompany.network.ResponseModel;
import com.usercompany.network.request.FacebookLogin;
import com.usercompany.network.request.SigninRequest;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static String TAG = LoginActivity.class.getSimpleName();
    private static int RC_SIGN_IN = 0;
    private static int FB_SIGN_IN = 1;

    private Button mLoginButton;
    private Button mLoginButtonCancel;
    private LinearLayout mCustomLogin;
    private EditText mEmail;
    private EditText mPassword;

    private LoginButton loginFacebookButton;
    private AppButton loginFacebookAppButton;
    private CallbackManager callbackManager;

    private GoogleSignInOptions gso;
    public GoogleApiClient mGoogleApiClient;
    private AppButton loginGoogleAppButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        // Configure s                loginFacebookButton.setVisibility(View.VISIBLE);ign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        LogUtils.LOGI(TAG, "Google Login : onConnectionFailed");
                    }
                })
//                .enableAutoManage(this, LoginActivity.this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        handleView();
    }

    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;


    @Override
    public void onConnected(Bundle bundle) {
        LogUtils.LOGI(TAG,"google api onConnected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        LogUtils.LOGI(TAG,"google api onConnectionSuspended");
    }

    private void handleView() {
        mLoginButton = (Button) findViewById(R.id.login_fragment_button_do_login);

        mLoginButtonCancel = (Button) findViewById(R.id.login_fragment_button_do_login_cancel);
        mCustomLogin = (LinearLayout) findViewById(R.id.login_activity_custom_signin);
        mEmail = (EditText) findViewById(R.id.login_activity_email);
        mPassword = (EditText) findViewById(R.id.login_activity_password);

        mLoginButton.setOnClickListener(doLoginClickListener);
        mLoginButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginButtonCancel.setVisibility(View.GONE);
                mCustomLogin.setVisibility(View.GONE);
//                signInGoogleButton.setVisibility(View.VISIBLE);
            }
        });


        loginFacebookButton = new LoginButton(this);//(LoginButton) findViewById(R.id.login_button);
        loginFacebookButton.setReadPermissions(Arrays
                .asList(Constant.EMAIL, Constant.PUBLIC_PROFILE, Constant.USER_BIRTHDAY,
                        Constant.USER_FRIENDS, Constant.USER_PHOTOS));
        callbackManager = CallbackManager.Factory.create();

        // Callback registration
        loginFacebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (!Application.getApplication().getSpiceManager().isStarted()) {
                    LogUtils.LOGI(TAG, " - spicemanager is not start - ");
                } else {
                    LogUtils.LOGI(TAG, " - spicemanager is start - ");
                }
                getUserData(loginResult.getAccessToken());
//                Set<String> deniedPermissions = loginResult.getRecentlyDeniedPermissions();
//                if (deniedPermissions.contains(Constance.EMAIL)) {
//                    LoginManager.getInstance()
//                            .logInWithReadPermissions(LoginFragment.this,
//                                    Arrays.asList(Constance.EMAIL));
//                } else {
//                    getUserData();
//                }
            }

            @Override
            public void onCancel() {
                LogUtils.LOGI(TAG, "facebook - cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                LogUtils.LOGI(TAG, "facebook - exception");
                LogUtils.LOGI(TAG, exception.getMessage());
            }
        });

        loginFacebookAppButton = (AppButton) findViewById(R.id.app_btn_facebook_signin);
        loginFacebookAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.LOGI(TAG, "loginFacebookAppButton.setOnClickListener");
                loginFacebookButton.performClick();
            }
        });

        loginGoogleAppButton = (AppButton) findViewById(R.id.app_btn_google_signin);
        loginGoogleAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.LOGI(TAG, "do google app button click");
//                signInGoogleButton.performClick();
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }

    private void getUserData(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject jsonObject, final GraphResponse graphResponse) {
                LogUtils.LOGI(TAG, " - send server requst - ");
                LogUtils.LOGI(TAG, " - "+graphResponse.getRawResponse()+" - ");


                UserFacebook userFacebook = (new Gson()).fromJson(graphResponse.getRawResponse(),UserFacebook.class);

                final User user = new User();
                user.setFirstName(userFacebook.getFirstName());
                user.setLastName(userFacebook.getLastName());
                user.setEmail(userFacebook.getEmail());
                user.setSocnetUid(userFacebook.getSocnetUid());
                user.setSocnetType(User.SOCNET_T_FACEBOOK);

                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

                try {
                    user.setBirthday(new Timestamp(formatter.parse(userFacebook.getBirthday()).getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(userFacebook.getGender().equals("male")){
                    user.setSex(User.SEX_MALE);
                } else if(userFacebook.getGender().equals("female")){
                    user.setSex(User.SEX_FEMALE);
                } else user.setSex(User.SEX_UNDEFINED);


                LogUtils.LOGI(TAG,"---->" + (new Gson()).toJson(user));
                userRegister(user, new OnSignUp() {
                    @Override
                    public void onSuccess() {
                        gotoMainActivity();
                    }

                    @Override
                    public void onError() {}
                });
            }
        });


        Bundle parameters = new Bundle();
        parameters.putString(Constant.FIELDS,
                Constant.BIRTHDAY + "," + Constant.FIRST_NAME + "," + Constant.LAST_NAME + "," +
                        Constant.EMAIL + "," + Constant.GENDER + "," + Constant.ID);

        request.setParameters(parameters);
        request.executeAsync();
    }

    interface OnSignUp {
        void onSuccess();
        void onError();
    }

    private void userRegister(User user, final OnSignUp onSignUp) {
        FacebookLogin facebookLogin = new FacebookLogin();
        facebookLogin.addData(user);
        LogUtils.LOGI(TAG, " - " + (Application.getApplication().getGson()
                .toJson(facebookLogin)) + " - ");

        SigninRequest signinRequest = new SigninRequest(facebookLogin);
        Application.getApplication().getSpiceManager()
                .execute(signinRequest, new RequestListener<ResponseModel>() {

                    @Override
                    public void onRequestFailure(SpiceException spiceException) {
                        LogUtils.LOGI(TAG,"onRequestFailure --->");
                        onSignUp.onError();
                    }

                    @Override
                    public void onRequestSuccess(ResponseModel signinRequest) {
                        LogUtils.LOGI(TAG, "onRequestSuccess->>" + (new Gson()).toJson(signinRequest));
                        onSignUp.onSuccess();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.LOGI(TAG,"google signin onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        LogUtils.LOGI(TAG, "google-signin : " + (new Gson()).toJson(result));
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            User user = new User();
            user.setEmail(acct.getEmail());
            LogUtils.LOGI(TAG, "google-signin : " + acct.getDisplayName());
//            user.setFirstName(acct.getDisplayName());
//            user.setLastName(acct.getDisplayName());
            user.setSocnetUid(acct.getId());
            user.setSocnetType(User.SOCNET_T_GOOGLE);

//            gotoMainActivity();
            userRegister(user, new OnSignUp() {
                @Override
                public void onSuccess() {
                    gotoMainActivity();
                }

                @Override
                public void onError() {
                }
            });
        } else {

            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            Toast.makeText(getApplicationContext(),"do logout",Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private View.OnClickListener doLoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mCustomLogin.getVisibility() == View.GONE){
                mLoginButtonCancel.setVisibility(View.VISIBLE);
                mCustomLogin.setVisibility(View.VISIBLE);
//                loginButton.setVisibility(View.GONE);
//                signInGoogleButton.setVisibility(View.GONE);
            } else {
                doSignIn(v);
            }
        }
    };

    private void doSignIn(View v) {
        if (v instanceof Button) {
            doEmailSignIn();
        }
    }

    private void gotoMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void doEmailSignIn() {
        if ((mPassword.getText() != null) && !(mPassword.getText().equals(""))) {
            if ((mEmail.getText() != null) && !(mEmail.getText().equals(""))) {
                if (isValidEmail(mEmail.getText())) {
                    final User user = new User();
                    user.setSocnetType(User.SOCNET_T_EMAIL);
                    user.setSocnetUid(mEmail.getText().toString());
                    user.setEmail(mEmail.getText().toString());
                    user.setPassword(mPassword.getText().toString());
                    userRegister(user, new OnSignUp() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getApplicationContext(), "do login", Toast.LENGTH_SHORT).show();
                            gotoMainActivity();
                        }

                        @Override
                        public void onError() {
                            Toast.makeText(getApplicationContext(), "error on email signup", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "wrong email", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "wrong email", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "wrong password", Toast.LENGTH_SHORT).show();
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.LOGI(TAG," - onStart - ");
        if (!Application.getApplication().getSpiceManager().isStarted()){
            LogUtils.LOGI(TAG," - SpiceManager is not start - ");
            Application.getApplication()
                    .getSpiceManager().start(getApplicationContext());
        } else {
            LogUtils.LOGI(TAG," - SpiceManager is start - ");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
