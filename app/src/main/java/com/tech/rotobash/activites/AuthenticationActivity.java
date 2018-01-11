package com.tech.rotobash.activites;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.tech.rotobash.R;
import com.tech.rotobash.Validations.FieldValidations;
import com.tech.rotobash.ViewModel.UserViewModel;
import com.tech.rotobash.databinding.ActivityLoginBinding;
import com.tech.rotobash.interfaces.SocialMediaListners;
import com.tech.rotobash.model.UserResponse;
import com.tech.rotobash.utils.AppConstant;
import com.tech.rotobash.utils.AppPreferences;
import com.tech.rotobash.utils.AppUtils;
import com.tech.rotobash.utils.Network;
import com.tech.rotobash.utils.SocialMediaManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static com.tech.rotobash.utils.AppConstant.ePasswordReq;
import static com.tech.rotobash.utils.AppConstant.sDeviceType;
import static com.tech.rotobash.utils.AppConstant.sEnterEmail;
import static com.tech.rotobash.utils.AppConstant.sEnterValidEmail;
import static com.tech.rotobash.utils.AppConstant.sEnterValidPassword;
import static com.tech.rotobash.utils.AppConstant.sFacebook;
import static com.tech.rotobash.utils.AppConstant.sGoogle;
import static com.tech.rotobash.utils.AppConstant.sLoginWith;
import static com.tech.rotobash.utils.AppConstant.sPasswordMismatch;
import static com.tech.rotobash.utils.AppConstant.sRegisterWith;

/**
 @Module Name/Class		:	AuthenticationActivity
 @Author Name			:	Rohit Puri
 @Date					:	Jan 3rd , 2018
 @Purpose				:	This class contains both login and registration functionality
 */

public class AuthenticationActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private ActivityLoginBinding mBinding;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private ProgressDialog progressDoalog;
    private Typeface mBoldTypeface,mNormalTypeFace;
    private CallbackManager mCallbackManager;
    private int mUiFor = 1;
    private AppPreferences mAppPreferences;
    private long mDelay = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();

        mBinding.tvRegister.setOnClickListener(view -> showRegistration());

        mBinding.tvLogin.setOnClickListener(view -> showLogin());

        mBinding.rlRegBtn.setOnClickListener(view -> {

            if (mUiFor == 1){
                if (FieldValidations.doLoginValidation(mBinding)) {
                    if (Network.isAvailable(AuthenticationActivity.this)) {
                        doLogin();
                    }else {
                        Toast.makeText(AuthenticationActivity.this, AppConstant.sNoInternet,Toast.LENGTH_LONG).show();
                    }
                }
            }else{
                if (FieldValidations.doRegValidation(this,mBinding)) {
                    if (Network.isAvailable(AuthenticationActivity.this)) {
                        doRegistration();
                    }else {
                        Toast.makeText(AuthenticationActivity.this, AppConstant.sNoInternet,Toast.LENGTH_LONG).show();
                    }
                }
            }

        });

        mBinding.btnGoogle.setOnClickListener(view -> {
            if (Network.isAvailable(AuthenticationActivity.this)) {
                googleConnect();
            }else {
                Toast.makeText(AuthenticationActivity.this, AppConstant.sNoInternet,Toast.LENGTH_LONG).show();
            }
        });


        RxTextView.textChanges(mBinding.etRegEmail)
                .debounce(mDelay, TimeUnit.MILLISECONDS)
                .subscribe(textChanged -> runOnUiThread(() -> {
                    if (mBinding.etRegEmail.getText().toString().length() > 0) {
                        if (!AppUtils.isValidEmail(mBinding.etRegEmail.getText().toString())) {
                            mBinding.etRegEmail.requestFocus();
                            mBinding.etRegEmail.setError(sEnterValidEmail);
                        }
                    }
                }));

        RxTextView.textChanges(mBinding.etRegPassword)
                .debounce(mDelay, TimeUnit.MILLISECONDS)
                .subscribe(textChanged -> runOnUiThread(() -> {
                    if (mBinding.etRegPassword.getText().toString().length() > 0  && mBinding.etRegPassword.getText().toString().length()<8) {
                        mBinding.etRegPassword.requestFocus();
                        mBinding.etRegPassword.setError(sEnterValidPassword);
                    }
                    if (mUiFor == 2){
                        if (mBinding.etRegPassword.getText().toString().length() >= 8) {
                            if (!AppUtils.validate(mBinding.etRegPassword.getText().toString())) {
                                mBinding.etRegPassword.requestFocus();
                                mBinding.etRegPassword.setError(ePasswordReq);
                            }
                        }
                    }
                }));

        RxTextView.textChanges(mBinding.etConfirmPass)
                .debounce(mDelay, TimeUnit.MILLISECONDS)
                .subscribe(textChanged -> runOnUiThread(() -> {
                    if (mUiFor == 2) {
                        if (mBinding.etConfirmPass.getText().toString().length() > 0) {
                            if (!mBinding.etConfirmPass.getText().toString().equals(mBinding.etRegPassword.getText().toString())) {
                                Toast.makeText(AuthenticationActivity.this, sPasswordMismatch, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }));



        mBinding.btnFb.setOnClickListener(view -> doFbConnect());

        mBinding.tvForget.setOnClickListener(view -> showForgetDialog());

    }

    /**
     @Module Name/Class		:	initData
     @Author Name			:	Rohit Puri
     @Date					:	Jan 3rd , 2018
     @Purpose				:	This method initialize the basic parameters
     */
    private void initData() {

        getWindow().setBackgroundDrawableResource(R.drawable.login_bg);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mAppPreferences  = new AppPreferences(AuthenticationActivity.this);

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        configGoogle();

        mBoldTypeface = Typeface.createFromAsset(getAssets(), "bold.ttf");
        mNormalTypeFace = Typeface.createFromAsset(getAssets(), "regular.ttf");

        progressDoalog = new ProgressDialog(AuthenticationActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Please wait....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        showLogin();
    }

    /**
     @Module Name/Class		:	showForgetDialog
     @Author Name			:	Rohit Puri
     @Date					:	Jan 4rd , 2018
     @Purpose				:	This method used to show the forget password dialog
     */
    private void showForgetDialog(){
        final Dialog openDialog = new Dialog(AuthenticationActivity.this);
        openDialog.setContentView(R.layout.layout_forget_password);
        openDialog.setCancelable(true);

        EditText etSubmitId = openDialog.findViewById(R.id.etSubmitId);
        Button btnSubmit = openDialog.findViewById(R.id.btnSubmit);
        Button btnCancel = openDialog.findViewById(R.id.btnCancel);

        AppUtils.showSoftKeyboard(openDialog);

        btnCancel.setOnClickListener(v -> openDialog.dismiss());

        RxTextView.textChanges(etSubmitId)
                .debounce(mDelay, TimeUnit.MILLISECONDS)
                .subscribe(textChanged -> runOnUiThread(() -> {
                    if (etSubmitId.getText().toString().length() > 0) {
                        if (!AppUtils.isValidEmail(etSubmitId.getText().toString())) {
                            etSubmitId.requestFocus();
                            etSubmitId.setError(sEnterValidEmail);
                        }
                    }
                }));

        btnSubmit.setOnClickListener(view -> {
            if (TextUtils.isEmpty(etSubmitId.getText().toString())){
                etSubmitId.requestFocus();
                etSubmitId.setError(sEnterEmail);
            }else if (!AppUtils.isValidEmail(etSubmitId.getText().toString())){
                etSubmitId.requestFocus();
                etSubmitId.setError(sEnterValidEmail);
            }else{
                doForgetPassword(openDialog,etSubmitId.getText().toString());
            }
        });

        openDialog.show();
    }

    /**
     @Module Name/Class		:	configGoogle
     @Author Name			:	Rohit Puri
     @Date					:	Jan 4rd , 2018
     @Purpose				:	This method used for google configuration on start
     */
    private void configGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(AuthenticationActivity.this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    /**
     @Module Name/Class		:	googleConnect
     @Author Name			:	Rohit Puri
     @Date					:	Jan 5rd , 2018
     @Purpose				:	This method used to start google activity
     */
    private void googleConnect() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }

    /**
     @Module Name/Class		:	handleSignInResult
     @Author Name			:	Rohit Puri
     @Date					:	Jan 5rd , 2018
     @Purpose				:	This method used to get google account details
     */
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            if(acct != null) {
                Log.e("user name :", acct.getDisplayName());
                Log.e("user email :", acct.getEmail());
                doSocialConnect(acct);
            }
        }
    }

    /**
     @Module Name/Class		:	showLogin
     @Author Name			:	Rohit Puri
     @Date					:	Jan 8rd , 2018
     @Purpose				:	This method used to show login screen fields only
     */
    private void showLogin(){
        mUiFor  = 1;
        mBinding.viewLogin.setVisibility(View.VISIBLE);
        mBinding.viewRegister.setVisibility(View.INVISIBLE);
        mBinding.imgLogin.setVisibility(View.VISIBLE);
        mBinding.imgRegister.setVisibility(View.GONE);
        mBinding.etName.setVisibility(View.GONE);
        mBinding.etRegEmail.setVisibility(View.VISIBLE);
        mBinding.etConfirmPass.setVisibility(View.GONE);
        mBinding.etRegPassword.setVisibility(View.VISIBLE);
        mBinding.etRegEmail.setHint(R.string.txt_email);
        mBinding.etRegPassword.setHint(R.string.txt_pass);
        mBinding.btnRegsiter.setText(R.string.txt_login);
        mBinding.rlLoginChk.setVisibility(View.VISIBLE);
        mBinding.chkRemember.setVisibility(View.INVISIBLE);
        mBinding.rlRegChk.setVisibility(View.GONE);
        mBinding.tvLogin.setTypeface(mBoldTypeface);
        mBinding.tvRegister.setTypeface(mNormalTypeFace);
        mBinding.tvWith.setText(sLoginWith);
        mBinding.imgRefer.setVisibility(View.GONE);
        clearFocus();
        cleanRegFields();
        removeError();
    }

    private void clearFocus(){
        mBinding.etName.clearFocus();
        mBinding.etRegEmail.clearFocus();
        mBinding.etConfirmPass.clearFocus();
        mBinding.etRegPassword.clearFocus();
    }

    private void removeError(){
        mBinding.etName.setError(null);
        mBinding.etRegEmail.setError(null);
        mBinding.etConfirmPass.setError(null);
        mBinding.etRegPassword.setError(null);
    }

    /**
     @Module Name/Class		:	showRegistration
     @Author Name			:	Rohit Puri
     @Date					:	Jan 8rd , 2018
     @Purpose				:	This method used to show registration screen fields only
     */
    private void showRegistration(){
        mUiFor  = 2;
        mBinding.viewLogin.setVisibility(View.INVISIBLE);
        mBinding.viewRegister.setVisibility(View.VISIBLE);
        mBinding.etName.setVisibility(View.VISIBLE);
        mBinding.etRegEmail.setVisibility(View.VISIBLE);
        mBinding.etConfirmPass.setVisibility(View.VISIBLE);
        mBinding.etRegPassword.setVisibility(View.VISIBLE);
        mBinding.etRegEmail.setHint(R.string.txt_id);
        mBinding.etRegPassword.setHint(R.string.txt_password);
        mBinding.etConfirmPass.setHint(R.string.txt_con_password);
        mBinding.etName.setHint(R.string.txt_name);
        mBinding.btnRegsiter.setText(R.string.txt_register);
        mBinding.rlLoginChk.setVisibility(View.GONE);
        mBinding.rlRegChk.setVisibility(View.VISIBLE);
        mBinding.tvLogin.setTypeface(mNormalTypeFace);
        mBinding.tvRegister.setTypeface(mBoldTypeface);
        mBinding.imgLogin.setVisibility(View.GONE);
        mBinding.imgRegister.setVisibility(View.VISIBLE);
        mBinding.tvWith.setText(sRegisterWith);
        mBinding.imgRefer.setVisibility(View.VISIBLE);
        removeError();
        clearFocus();
        cleanRegFields();
    }

    private void cleanRegFields(){
        mBinding.etName.setText("");
        mBinding.etRegEmail.setText("");
        mBinding.etRegPassword.setText("");
        mBinding.etConfirmPass.setText("");
    }

    /**
     @Module Name/Class		:	doFbConnect
     @Author Name			:	Rohit Puri
     @Date					:	Jan 8rd , 2018
     @Purpose				:	This method used to perform facebook button functionality where a Social
     media manager class is using to handle facebook requests
     */
    private void doFbConnect(){
        if (Network.isAvailable(AuthenticationActivity.this)) {
            if (AppUtils.checkIsAppLicationInstalled(AuthenticationActivity.this, AppConstant.sFacebooks)) {
                LoginManager.getInstance().logInWithReadPermissions(AuthenticationActivity.this, Arrays.asList("public_profile", "email"));

                SocialMediaManager socialMediaManager = new SocialMediaManager(AuthenticationActivity.this, new SocialMediaListners() {
                    @Override
                    public void loginSuccess(JSONObject aJsonObject) {
                        Log.e("fb json :", aJsonObject.toString());
                        doSocialConnect(aJsonObject);
                    }

                    @Override
                    public void loginError(String loginError) {
                        Log.e("fb json :", loginError);
                        Toast.makeText(AuthenticationActivity.this, AppConstant.sConnectionErr, Toast.LENGTH_LONG).show();
                    }
                });
                socialMediaManager.initfb(mCallbackManager);
            } else {
                AppUtils.callAlertDialogForApplicationDownload(AppConstant.sFacebookPackage, AppConstant.sFacebookDownloadMsg, AuthenticationActivity.this, null);
            }
        } else {
            Toast.makeText(AuthenticationActivity.this, AppConstant.sNoInternet, Toast.LENGTH_LONG).show();
        }
    }

    /**
     @Module Name/Class		:	doForgetPassword
     @Author Name			:	Rohit Puri
     @Date					:	Jan 9rd , 2018
     @Purpose				:	This method used to perform forget password functionality
     */
    private void doForgetPassword(Dialog aDialog,String aEmailId){

        UserViewModel mSignupViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        mSignupViewModel.getForgetPassword(aEmailId)
                .observe(this,userResponse ->{
                    Toast.makeText(AuthenticationActivity.this,userResponse.getMessage(),Toast.LENGTH_LONG).show();
                    AppUtils.hideSoftKeyboard(AuthenticationActivity.this);
                    if (userResponse.getStatus().equalsIgnoreCase("success")){
                        aDialog.dismiss();
                    }
                });
    }

    /**
     @Module Name/Class		:	doSocialConnect
     @Author Name			:	Rohit Puri
     @Date					:	Jan 9rd , 2018
     @Purpose				:	This method used to access details from facebook or google login
     */
    private void doSocialConnect(Object any){

        UserViewModel mSignupViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        if (any instanceof GoogleSignInAccount){
            GoogleSignInAccount acct = (GoogleSignInAccount)any;
            mSignupViewModel.getSocialConnect(progressDoalog,acct.getDisplayName(),acct.getEmail(),sGoogle,
                    acct.getId(), FirebaseInstanceId.getInstance().getToken(),sDeviceType)
                    .observe(this,userResponse ->{

                        Toast.makeText(AuthenticationActivity.this,userResponse.getMessage(),Toast.LENGTH_LONG).show();
                        if (userResponse.getStatus().equalsIgnoreCase("success")){
                            rememberMe(userResponse);
                            mAppPreferences.setPreferenceSocial(true);
                        }
                    });
        }else{
            JSONObject acct = (JSONObject)any;

            try {
                mSignupViewModel.getSocialConnect(progressDoalog,acct.getString("name"),acct.getString("email"),
                        sFacebook,acct.getString("id"),FirebaseInstanceId.getInstance().getToken(),sDeviceType)
                        .observe(this,userResponse ->{

                            Toast.makeText(AuthenticationActivity.this,userResponse.getMessage(),Toast.LENGTH_LONG).show();
                            if (userResponse.getStatus().equalsIgnoreCase("success")){
                                rememberMe(userResponse);
                                mAppPreferences.setPreferenceSocial(true);
                            }
                        });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     @Module Name/Class		:	doRegistration
     @Author Name			:	Rohit Puri
     @Date					:	Jan 9rd , 2018
     @Purpose				:	This method used to perform registration functionality
     */
    private void doRegistration(){
        UserViewModel mSignupViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        mSignupViewModel.getLiveSignupData(progressDoalog,mBinding.etName.getText().toString(),mBinding.etRegEmail.getText().toString(),
                mBinding.etRegPassword.getText().toString(), FirebaseInstanceId.getInstance().getToken(),"Android")
                .observe(this,userResponse ->{
                    Toast.makeText(AuthenticationActivity.this,userResponse.getMessage(),Toast.LENGTH_LONG).show();
                    if (userResponse.getStatus().equalsIgnoreCase("success")){
                        showLogin();
                        cleanRegFields();
                        AppUtils.hideSoftKeyboard(AuthenticationActivity.this);
                    }
                });

    }

    /**
     @Module Name/Class		:	doRegistration
     @Author Name			:	Rohit Puri
     @Date					:	Jan 9rd , 2018
     @Purpose				:	This method used to perform login functionality
     */
    private void doLogin(){
        UserViewModel mSigninViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        mSigninViewModel.getLiveSigninData(progressDoalog,mBinding.etRegEmail.getText().toString(),mBinding.etRegPassword.getText().toString(), FirebaseInstanceId.getInstance().getToken(),"Android")
                .observe(this,userResponse ->{
                    Toast.makeText(AuthenticationActivity.this,userResponse.getMessage(),Toast.LENGTH_LONG).show();
                    if (userResponse.getStatus().equalsIgnoreCase("success")){
                        rememberMe(userResponse);
                        mAppPreferences.setPreferenceSocial(false);
                    }
                    AppUtils.hideSoftKeyboard(AuthenticationActivity.this);

                });
    }

    /**
     @Module Name/Class		:	doRegistration
     @Author Name			:	Rohit Puri
     @Date					:	Jan 9rd , 2018
     @Purpose				:	This method used to check wheather user enable remember me option or not
     */
    private void rememberMe(UserResponse aUserResponse) {
        mAppPreferences.setUserData(aUserResponse);
        mAppPreferences.setPreferenceBoolean(true);
        moveScreen(aUserResponse);
    }

    private void moveScreen(UserResponse aUserResponse){
        Intent i = new Intent(AuthenticationActivity.this, SidemenuActivity.class);
        i.putExtra("UserResponse",aUserResponse);
        startActivity(i);
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(AuthenticationActivity.this,connectionResult.getErrorMessage(),Toast.LENGTH_LONG).show();
    }
}
