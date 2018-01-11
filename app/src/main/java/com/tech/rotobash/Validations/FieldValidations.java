package com.tech.rotobash.Validations;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.tech.rotobash.activites.AuthenticationActivity;
import com.tech.rotobash.databinding.ActivityLoginBinding;
import com.tech.rotobash.utils.AppUtils;

import static com.tech.rotobash.utils.AppConstant.ePasswordReq;
import static com.tech.rotobash.utils.AppConstant.sAgreeTnc;
import static com.tech.rotobash.utils.AppConstant.sEnterEmail;
import static com.tech.rotobash.utils.AppConstant.sEnterName;
import static com.tech.rotobash.utils.AppConstant.sEnterPassword;
import static com.tech.rotobash.utils.AppConstant.sEnterValidEmail;
import static com.tech.rotobash.utils.AppConstant.sEnterValidPassword;
import static com.tech.rotobash.utils.AppConstant.sPasswordMismatch;
import static com.tech.rotobash.utils.AppConstant.sReEnterPassword;

/**
 * Created by rohitpuri on 5/1/18.
 */

public class FieldValidations {

    public static boolean doLoginValidation(ActivityLoginBinding mBinding){
        if (TextUtils.isEmpty(mBinding.etRegEmail.getText().toString())){
            mBinding.etRegEmail.requestFocus();
            mBinding.etRegEmail.setError(sEnterEmail);
            return false;
        }else if (!AppUtils.isValidEmail(mBinding.etRegEmail.getText().toString())){
            mBinding.etRegEmail.requestFocus();
            mBinding.etRegEmail.setError(sEnterValidEmail);
            return false;
        }else if (TextUtils.isEmpty(mBinding.etRegPassword.getText().toString())){
            mBinding.etRegPassword.requestFocus();
            mBinding.etRegPassword.setError(sEnterPassword);
            return false;
        }else if (mBinding.etRegPassword.getText().toString().length() < 8){
            mBinding.etRegPassword.requestFocus();
            mBinding.etRegPassword.setError(sEnterValidPassword);
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean doRegValidation(AppCompatActivity appCompatActivity,ActivityLoginBinding mBinding){
        if (TextUtils.isEmpty(mBinding.etName.getText().toString())){
            mBinding.etName.requestFocus();
            mBinding.etName.setError(sEnterName);
            return false;
        }else if (TextUtils.isEmpty(mBinding.etRegEmail.getText().toString())){
            mBinding.etRegEmail.requestFocus();
            mBinding.etRegEmail.setError(sEnterEmail);
            return false;
        }else if (!AppUtils.isValidEmail(mBinding.etRegEmail.getText().toString())){
            mBinding.etRegEmail.requestFocus();
            mBinding.etRegEmail.setError(sEnterValidEmail);
            return false;
        }else if (TextUtils.isEmpty(mBinding.etRegPassword.getText().toString())){
            mBinding.etRegPassword.requestFocus();
            mBinding.etRegPassword.setError(sEnterPassword);
            return false;
        }else if (mBinding.etRegPassword.getText().toString().length() < 8){
            mBinding.etRegPassword.requestFocus();
            mBinding.etRegPassword.setError(sEnterValidPassword);
            return false;
        }else if(!AppUtils.validate(mBinding.etRegPassword.getText().toString())){
            mBinding.etRegPassword.requestFocus();
            mBinding.etRegPassword.setError(ePasswordReq);
            return false;
        }else if (TextUtils.isEmpty(mBinding.etConfirmPass.getText().toString())){
            mBinding.etConfirmPass.requestFocus();
            mBinding.etConfirmPass.setError(sReEnterPassword);
            return false;
        }else if (!mBinding.etConfirmPass.getText().toString().equals(mBinding.etRegPassword.getText().toString())){
            Toast.makeText(appCompatActivity,sPasswordMismatch,Toast.LENGTH_LONG).show();
            return false;
        }else if (!mBinding.chkTnC.isChecked()){
            Toast.makeText(appCompatActivity,sAgreeTnc,Toast.LENGTH_LONG).show();
            return false;
        }else {
            return true;
        }
    }

}
