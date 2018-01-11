package com.tech.rotobash.Validations;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tech.rotobash.R;
import com.tech.rotobash.databinding.ActivityLoginBinding;
import com.tech.rotobash.databinding.ActivitySidemenuBinding;
import com.tech.rotobash.utils.TextFontsBold;
import com.tech.rotobash.utils.TextFontsRegular;

import static com.tech.rotobash.utils.AppConstant.sLoginWith;
import static com.tech.rotobash.utils.AppConstant.sRegisterWith;

/**
 * Created by rohitpuri on 11/1/18.
 */

public class ViewsVisibilites {

    public static void handleLoginViews(ActivityLoginBinding mBinding, Typeface mBoldTypeface, Typeface mNormalTypeFace){
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
    }

    public static void handleRegViews(ActivityLoginBinding mBinding, Typeface mBoldTypeface, Typeface mNormalTypeFace){
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
        mBinding.btnRegsiter.setText(R.string.txt_startPlaying);
        mBinding.rlLoginChk.setVisibility(View.GONE);
        mBinding.rlRegChk.setVisibility(View.VISIBLE);
        mBinding.tvLogin.setTypeface(mNormalTypeFace);
        mBinding.tvRegister.setTypeface(mBoldTypeface);
        mBinding.imgLogin.setVisibility(View.GONE);
        mBinding.imgRegister.setVisibility(View.VISIBLE);
        mBinding.tvWith.setText(sRegisterWith);
        mBinding.imgRefer.setVisibility(View.VISIBLE);
    }


    public static void clearFocusView(ActivityLoginBinding mBinding){
        mBinding.etName.clearFocus();
        mBinding.etRegEmail.clearFocus();
        mBinding.etConfirmPass.clearFocus();
        mBinding.etRegPassword.clearFocus();
    }

    public static void removeError(ActivityLoginBinding mBinding) {
        mBinding.tvErrorName.setVisibility(View.GONE);
        mBinding.tvErrorConPassword.setVisibility(View.GONE);
        mBinding.tvErrorConPassword.setVisibility(View.GONE);
        mBinding.tvErrorEmail.setVisibility(View.GONE);
    }

    public static void cleanRegFields(ActivityLoginBinding mBinding) {
        mBinding.etName.setText("");
        mBinding.etRegEmail.setText("");
        mBinding.etRegPassword.setText("");
        mBinding.etConfirmPass.setText("");
    }

    public static void showCurrentMatchesView(AppCompatActivity aAppCompatActivity,ActivitySidemenuBinding mBinding){
        mBinding.included.includedContent.swipeContainerCurrent.setVisibility(View.VISIBLE);
        mBinding.included.includedContent.swipeContainerUpcoming.setVisibility(View.GONE);
        mBinding.included.includedContent.btnCurrent.setBackgroundColor(ContextCompat.getColor(aAppCompatActivity, R.color.color_current_gray));
        mBinding.included.includedContent.btnComing.setBackgroundColor(ContextCompat.getColor(aAppCompatActivity, R.color.color_white));
        mBinding.included.includedContent.btnCurrent.setTextColor(ContextCompat.getColor(aAppCompatActivity, R.color.color_red));
        mBinding.included.includedContent.btnComing.setTextColor(ContextCompat.getColor(aAppCompatActivity, R.color.light_grey));

    }

    public static void showComingMatchesView(AppCompatActivity aAppCompatActivity,ActivitySidemenuBinding mBinding){
        mBinding.included.includedContent.swipeContainerCurrent.setVisibility(View.GONE);
        mBinding.included.includedContent.swipeContainerUpcoming.setVisibility(View.VISIBLE);
        mBinding.included.includedContent.btnCurrent.setBackgroundColor(ContextCompat.getColor(aAppCompatActivity, R.color.color_white));
        mBinding.included.includedContent.btnComing.setBackgroundColor(ContextCompat.getColor(aAppCompatActivity, R.color.color_current_gray));
        mBinding.included.includedContent.btnCurrent.setTextColor(ContextCompat.getColor(aAppCompatActivity, R.color.light_grey));
        mBinding.included.includedContent.btnComing.setTextColor(ContextCompat.getColor(aAppCompatActivity, R.color.color_red));
    }
}
