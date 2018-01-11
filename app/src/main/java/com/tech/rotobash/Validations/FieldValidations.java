package com.tech.rotobash.Validations;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.tech.rotobash.databinding.ActivityLoginBinding;
import com.tech.rotobash.databinding.LayoutResetPasswordBinding;
import com.tech.rotobash.utils.AppUtils;

import static com.tech.rotobash.utils.AppConstant.ePasswordReq;
import static com.tech.rotobash.utils.AppConstant.sAgreeTnc;
import static com.tech.rotobash.utils.AppConstant.sEnterEmail;
import static com.tech.rotobash.utils.AppConstant.sEnterName;
import static com.tech.rotobash.utils.AppConstant.sEnterNewPassword;
import static com.tech.rotobash.utils.AppConstant.sEnterOldPassword;
import static com.tech.rotobash.utils.AppConstant.sEnterPassword;
import static com.tech.rotobash.utils.AppConstant.sEnterValidEmail;
import static com.tech.rotobash.utils.AppConstant.sEnterValidPassword;
import static com.tech.rotobash.utils.AppConstant.sPasswordMismatch;
import static com.tech.rotobash.utils.AppConstant.sReEnterPassword;

/**
 * @Module class/module		:	FieldValidations
 * @Author Name                :	Rohit Puri
 * @Date                     :	Jan 8 , 2018
 * @Purpose                 :	It contains all validation methods using Binding
 */
public class FieldValidations {

    public static boolean doLoginValidation(ActivityLoginBinding mBinding) {
        if (TextUtils.isEmpty(mBinding.etRegEmail.getText().toString())) {
            mBinding.etRegEmail.requestFocus();
            mBinding.tvErrorEmail.setVisibility(View.VISIBLE);
            mBinding.tvErrorEmail.setText(sEnterEmail);
            return false;
        } else if (!AppUtils.isValidEmail(mBinding.etRegEmail.getText().toString())) {
            mBinding.etRegEmail.requestFocus();
            mBinding.tvErrorEmail.setVisibility(View.VISIBLE);
            mBinding.tvErrorEmail.setText(sEnterValidEmail);
            return false;
        } else if (TextUtils.isEmpty(mBinding.etRegPassword.getText().toString())) {
            mBinding.etRegPassword.requestFocus();
            mBinding.tvErrorPassword.setVisibility(View.VISIBLE);
            mBinding.tvErrorPassword.setText(sEnterPassword);
            return false;
        } else if (mBinding.etRegPassword.getText().toString().length() < 8) {
            mBinding.etRegPassword.requestFocus();
            mBinding.tvErrorPassword.setVisibility(View.VISIBLE);
            mBinding.tvErrorPassword.setText(sEnterValidPassword);
            return false;
        } else {
            return true;
        }
    }

    public static boolean doRegValidation(AppCompatActivity appCompatActivity, ActivityLoginBinding mBinding) {
        if (TextUtils.isEmpty(mBinding.etName.getText().toString())) {
            mBinding.etName.requestFocus();
            mBinding.tvErrorName.setVisibility(View.VISIBLE);
            mBinding.tvErrorName.setText(sEnterName);
            return false;
        } else if (TextUtils.isEmpty(mBinding.etRegEmail.getText().toString())) {
            mBinding.etRegEmail.requestFocus();
            mBinding.tvErrorEmail.setVisibility(View.VISIBLE);
            mBinding.tvErrorEmail.setText(sEnterEmail);
            return false;
        } else if (!AppUtils.isValidEmail(mBinding.etRegEmail.getText().toString())) {
            mBinding.etRegEmail.requestFocus();
            mBinding.tvErrorEmail.setVisibility(View.VISIBLE);
            mBinding.tvErrorEmail.setText(sEnterValidEmail);
            return false;
        } else if (TextUtils.isEmpty(mBinding.etRegPassword.getText().toString())) {
            mBinding.etRegPassword.requestFocus();
            mBinding.tvErrorPassword.setVisibility(View.VISIBLE);
            mBinding.tvErrorPassword.setText(sEnterPassword);
            return false;
        } else if (mBinding.etRegPassword.getText().toString().length() < 8) {
            mBinding.etRegPassword.requestFocus();
            mBinding.tvErrorPassword.setVisibility(View.VISIBLE);
            mBinding.tvErrorPassword.setText(sEnterValidPassword);
            return false;
        } else if (!AppUtils.validate(mBinding.etRegPassword.getText().toString())) {
            mBinding.etRegPassword.requestFocus();
            mBinding.tvErrorPassword.setVisibility(View.VISIBLE);
            mBinding.tvErrorPassword.setText(ePasswordReq);
            return false;
        } else if (TextUtils.isEmpty(mBinding.etConfirmPass.getText().toString())) {
            mBinding.etConfirmPass.requestFocus();
            mBinding.tvErrorConPassword.setVisibility(View.VISIBLE);
            mBinding.tvErrorConPassword.setText(sReEnterPassword);
            return false;
        } else if (!mBinding.etConfirmPass.getText().toString().equals(mBinding.etRegPassword.getText().toString())) {
            Toast.makeText(appCompatActivity, sPasswordMismatch, Toast.LENGTH_LONG).show();
            return false;
        } else if (!mBinding.chkTnC.isChecked()) {
            Toast.makeText(appCompatActivity, sAgreeTnc, Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public static boolean doResetValidation(LayoutResetPasswordBinding mBinding) {
        if (TextUtils.isEmpty(mBinding.etOldPass.getText().toString())) {
            mBinding.etOldPass.requestFocus();
            mBinding.tvErrorOldPassword.setVisibility(View.VISIBLE);
            mBinding.tvErrorOldPassword.setText(sEnterOldPassword);
            //  mBinding.etOldPass.setError(sEnterOldPassword);
            return false;
        } else if (TextUtils.isEmpty(mBinding.etNewPass.getText().toString())) {
            mBinding.etNewPass.requestFocus();
            mBinding.tvErrorNewPassword.setVisibility(View.VISIBLE);
            mBinding.tvErrorNewPassword.setText(sEnterNewPassword);
            //  mBinding.etNewPass.setError(sEnterNewPassword);
            return false;
        } else if (mBinding.etNewPass.getText().toString().length() < 8) {
            mBinding.etNewPass.requestFocus();
            mBinding.tvErrorNewPassword.setVisibility(View.VISIBLE);
            mBinding.tvErrorNewPassword.setText(sEnterValidPassword);
            //  mBinding.etNewPass.setError(sEnterValidPassword);
            return false;
        } else if (!AppUtils.validate(mBinding.etNewPass.getText().toString())) {
            mBinding.etNewPass.requestFocus();
            mBinding.tvErrorNewPassword.setVisibility(View.VISIBLE);
            mBinding.tvErrorNewPassword.setText(ePasswordReq);
            //     mBinding.etNewPass.setError(ePasswordReq);
            return false;
        } else {
            return true;
        }
    }

}
