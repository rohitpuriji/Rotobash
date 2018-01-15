package com.tech.rotobash.activites;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.tech.rotobash.R;
import com.tech.rotobash.ViewModel.UserViewModel;
import com.tech.rotobash.databinding.ActivitySidemenuBinding;
import com.tech.rotobash.databinding.LayoutResetPasswordBinding;
import com.tech.rotobash.model.UserResponse;
import com.tech.rotobash.utils.AppConstant;
import com.tech.rotobash.utils.AppPreferences;
import com.tech.rotobash.utils.AppUtils;
import com.tech.rotobash.utils.Network;

import java.util.concurrent.TimeUnit;

import static com.tech.rotobash.Validations.FieldValidations.doResetValidation;
import static com.tech.rotobash.utils.AppConstant.ePasswordReq;
import static com.tech.rotobash.utils.AppConstant.sEnterValidPassword;
import static com.tech.rotobash.utils.AppConstant.sPleaseWait;

/**
 * @Module Name/Class		:	SidemenuActivity
 * @Author Name             :	Rohit Puri
 * @Date                    :	Jan 8rd , 2018
 * @Purpose                 :	This class contains both sidemenu screen and main matches contents
 */
public class SidemenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public ActivitySidemenuBinding mBinding;
    private UserResponse mUserResponse;
    private ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();

    }

    public void setDataToParentClass(UserResponse mUserResponse) {
        this.mUserResponse = mUserResponse;

    }

    /**
     * @Module Name/Class		:	initData
     * @Author Name             :	Rohit Puri
     * @Date                    :	Jan 8rd , 2018
     * @Purpose                 :	This method initialize the basic functionality
     */
    private void initData() {

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sidemenu);
        AppPreferences mPref = new AppPreferences(SidemenuActivity.this);
        mBinding.navigationView.setNavigationItemSelectedListener(this);
        if (mPref.getPreferenceSocial()) {
            mBinding.navigationView.getMenu().findItem(R.id.nav_reset).setVisible(false);
        }
        progressDoalog = new ProgressDialog(SidemenuActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage(sPleaseWait);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }


    @Override
    public void onBackPressed() {

        if (mBinding.drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            mBinding.drawerLayout.closeDrawer(Gravity.RIGHT);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sidemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            doLogout();
        } else if (id == R.id.nav_reset) {
            if (Network.isAvailable(SidemenuActivity.this)) {
                showResetDialog();
            } else {
                Toast.makeText(SidemenuActivity.this, AppConstant.sNoInternet, Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        mBinding.drawerLayout.closeDrawer(Gravity.RIGHT);
        return true;
    }

    /**
     * @Module Name/Class		:	doLogout
     * @Author Name             :	Rohit Puri
     * @Date                    :	Jan 11th , 2018
     * @Purpose                 :	This method observe the response coming after logout operation.
     */
    private void doLogout() {

        UserViewModel mLogoutViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        mLogoutViewModel.doLogout(progressDoalog, mUserResponse)
                .observe(this, userResponse -> {
                    Toast.makeText(SidemenuActivity.this, userResponse.getMessage(), Toast.LENGTH_LONG).show();
                    AppPreferences mAppPreferences = new AppPreferences(SidemenuActivity.this);
                    mAppPreferences.clearAll();
                    moveScreen();
                });
    }

    private void moveScreen() {
        Intent i = new Intent(SidemenuActivity.this, AuthenticationActivity.class);
        startActivity(i);
        finish();
    }

    /**
     * @Module Name/Class		:	showResetDialog
     * @Author Name            :	Rohit Puri
     * @Date :	Jan 11th , 2018
     * @Purpose :	This method shows the reset dialog
     */
    private void showResetDialog() {
        LayoutResetPasswordBinding mBinding = DataBindingUtil
                .inflate(LayoutInflater.from(SidemenuActivity.this), R.layout.layout_reset_password, null, false);

        final Dialog openDialog = new Dialog(SidemenuActivity.this);
        openDialog.setContentView(mBinding.getRoot());
        openDialog.setCancelable(true);

        mBinding.tvErrorOldPassword.setVisibility(View.GONE);
        mBinding.tvErrorNewPassword.setVisibility(View.GONE);
        AppUtils.showSoftKeyboard(openDialog);

        mBinding.btnCancel.setOnClickListener(v -> openDialog.dismiss());

        long delay = 1500;
        RxTextView.textChanges(mBinding.etNewPass)
                .debounce(delay, TimeUnit.MILLISECONDS)
                .subscribe(textChanged -> runOnUiThread(() -> {
                    if (mBinding.etNewPass.getText().toString().length() > 0) {
                        if (mBinding.etNewPass.getText().toString().length() < 8) {
                            mBinding.etNewPass.requestFocus();
                            mBinding.tvErrorNewPassword.setVisibility(View.VISIBLE);
                            mBinding.tvErrorNewPassword.setText(sEnterValidPassword);

                        } else if (!AppUtils.validate(mBinding.etNewPass.getText().toString())) {
                            mBinding.etNewPass.requestFocus();
                            mBinding.tvErrorNewPassword.setVisibility(View.VISIBLE);
                            mBinding.tvErrorNewPassword.setText(ePasswordReq);
                        }
                    }
                }));

        RxTextView.textChanges(mBinding.etOldPass)
                .subscribe(charSequence ->
                        mBinding.tvErrorOldPassword.setVisibility(View.GONE));

        RxTextView.textChanges(mBinding.etNewPass)
                .subscribe(charSequence ->
                        mBinding.tvErrorNewPassword.setVisibility(View.GONE));

        mBinding.btnSubmit.setOnClickListener(view -> {
            if (doResetValidation(mBinding)) {
                doResetPassword(openDialog, mBinding.etOldPass.getText().toString(), mBinding.etNewPass.getText().toString());
            }
        });

        openDialog.show();
    }

    /**
     * @Module Name/Class		:	doResetPassword
     * @Author Name            :	Rohit Puri
     * @Date :	Jan 11th , 2018
     * @Purpose :	This method observe the response coming from ViewModel class in form of LiveData
     */
    private void doResetPassword(Dialog aDialog, String aOldPass, String aNewPass) {

        UserViewModel mSignupViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        mSignupViewModel.getResetPassword(progressDoalog, mUserResponse, aOldPass, aNewPass)
                .observe(this, userResponse -> {
                    Toast.makeText(SidemenuActivity.this, userResponse.getMessage(), Toast.LENGTH_LONG).show();
                    AppUtils.hideSoftKeyboard(SidemenuActivity.this);
                    if (userResponse.getStatus().equalsIgnoreCase("success")) {
                        aDialog.dismiss();
                    }
                });
    }


}
