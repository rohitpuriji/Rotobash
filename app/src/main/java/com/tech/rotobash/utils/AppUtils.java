package com.tech.rotobash.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ToggleButton;

import com.tech.rotobash.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 @Module class/module		:	AppUtils
 @Author Name			    :	Rohit Puri
 @Date					    :	Jan 3 , 2018
 @Purpose				    :	it contains handles all utilities method for this application
 */

public class AppUtils {


	private static final String PASSWORD_PATTERN =
			"((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[-!@#$%&*+?=/]).{8,20})";


	public static boolean isValidEmail(String target) {
		return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	}

	public static boolean checkIsAppLicationInstalled(Activity activity, String appName) {
		String apllicationPackage = "";
		if (appName.equalsIgnoreCase(AppConstant.sFacebooks) || appName.equalsIgnoreCase("com.facebook.android")) {
			apllicationPackage = AppConstant.sFacebookPackage;
		}

		PackageManager pm = activity.getPackageManager();
		boolean isInstalled;
		try {
			pm.getPackageInfo(apllicationPackage,
					PackageManager.GET_ACTIVITIES);
			isInstalled = true;
		} catch (PackageManager.NameNotFoundException e) {
			isInstalled = false;
		}
		return isInstalled;

	}


	public static void callAlertDialogForApplicationDownload(final String applicationPacakgeName, String mesageToDownload, final Activity activity, final ToggleButton toggleButton) {
		AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
		builder1.setMessage(mesageToDownload);
		builder1.setCancelable(false);
		builder1.setPositiveButton(
				R.string.ok,
				(dialog, id) -> {
					dialog.cancel();
					try {
						activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + applicationPacakgeName)));
					} catch (android.content.ActivityNotFoundException anfe) {
						activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + applicationPacakgeName)));
					}
				});
		builder1.setNegativeButton(
				R.string.cancel,
				(dialog, id) -> {
					if (toggleButton != null)
						toggleButton.setChecked(false);
					dialog.cancel();
				});

		AlertDialog alert11 = builder1.create();
		alert11.show();
	}

	public static void hideSoftKeyboard(Activity activity) {
		if(activity.getCurrentFocus()!=null) {
			InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
		}
	}

	public static void showSoftKeyboard(Dialog aDialog){
		final Window dialogWindow = aDialog.getWindow();
		dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

	}

	public static boolean validate(final String password){
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}
}


