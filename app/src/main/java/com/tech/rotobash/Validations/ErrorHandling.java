package com.tech.rotobash.Validations;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.tech.rotobash.databinding.ActivityLoginBinding;
import com.tech.rotobash.utils.AppUtils;

import java.util.concurrent.TimeUnit;

import static com.tech.rotobash.utils.AppConstant.ePasswordReq;
import static com.tech.rotobash.utils.AppConstant.sEnterValidEmail;
import static com.tech.rotobash.utils.AppConstant.sEnterValidPassword;
import static com.tech.rotobash.utils.AppConstant.sPasswordMismatch;

/**
 * Created by rohitpuri on 11/1/18.
 */

public class ErrorHandling {

    private static long mDelay = 1500;

    public static void checkEmailEditText(AppCompatActivity aAppCompatActivity, ActivityLoginBinding mBinding) {

        RxTextView.textChanges(mBinding.etRegEmail)
                .debounce(mDelay, TimeUnit.MILLISECONDS)
                .subscribe(textChanged -> aAppCompatActivity.runOnUiThread(() -> {
                    if (mBinding.etRegEmail.getText().toString().length() > 0) {
                        if (!AppUtils.isValidEmail(mBinding.etRegEmail.getText().toString())) {
                            mBinding.etRegEmail.requestFocus();
                            mBinding.tvErrorEmail.setVisibility(View.VISIBLE);
                            mBinding.tvErrorEmail.setText(sEnterValidEmail);
                        }
                    }
                }));

        RxTextView.textChanges(mBinding.etRegEmail)
                .subscribe(charSequence ->
                        mBinding.tvErrorEmail.setVisibility(View.GONE));
    }

    public static void checkPasswordEditText(AppCompatActivity aAppCompatActivity, ActivityLoginBinding mBinding, int mUiFor) {

        Log.e("mFor", mUiFor + "");
        RxTextView.textChanges(mBinding.etRegPassword)
                .debounce(mDelay, TimeUnit.MILLISECONDS)
                .subscribe(textChanged -> aAppCompatActivity.runOnUiThread(() -> {
                    if (mBinding.etRegPassword.getText().toString().length() > 0 && mBinding.etRegPassword.getText().toString().length() < 8) {
                        mBinding.etRegPassword.requestFocus();
                        mBinding.tvErrorPassword.setVisibility(View.VISIBLE);
                        mBinding.tvErrorPassword.setText(sEnterValidPassword);
                    }
                    if (mUiFor == 2) {
                        if (mBinding.etRegPassword.getText().toString().length() >= 8) {
                            if (!AppUtils.validate(mBinding.etRegPassword.getText().toString())) {
                                mBinding.etRegPassword.requestFocus();
                                mBinding.tvErrorPassword.setVisibility(View.VISIBLE);
                                mBinding.tvErrorPassword.setText(ePasswordReq);
                            }
                        }
                    } else
                        mBinding.tvErrorPassword.setVisibility(View.GONE);
                }));

        RxTextView.textChanges(mBinding.etRegPassword)
                .subscribe(charSequence ->
                        mBinding.tvErrorPassword.setVisibility(View.GONE));
    }

    public static void checkNameEdittext(ActivityLoginBinding mBinding) {

        RxTextView.textChanges(mBinding.etName)
                .subscribe(charSequence ->
                        mBinding.tvErrorName.setVisibility(View.GONE));
    }

    public static void checkConPasswordEditText(AppCompatActivity aAppCompatActivity, ActivityLoginBinding mBinding, int mUiFor) {
        RxTextView.textChanges(mBinding.tvErrorConPassword)
                .subscribe(charSequence ->
                        mBinding.tvErrorConPassword.setVisibility(View.GONE));

        RxTextView.textChanges(mBinding.etConfirmPass)
                .debounce(mDelay, TimeUnit.MILLISECONDS)
                .subscribe(textChanged -> aAppCompatActivity.runOnUiThread(() -> {
                    if (mUiFor == 2) {
                        if (mBinding.etConfirmPass.getText().toString().length() > 0) {
                            if (!mBinding.etConfirmPass.getText().toString().equals(mBinding.etRegPassword.getText().toString())) {
                                Toast.makeText(aAppCompatActivity, sPasswordMismatch, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }));
    }

}
