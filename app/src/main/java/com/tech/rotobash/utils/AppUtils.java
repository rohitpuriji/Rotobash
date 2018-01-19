package com.tech.rotobash.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ToggleButton;

import com.tech.rotobash.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
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

	public static String getDateFormatYear(String date) {
		String oldFormat = "yyyy-MM-dd HH:mm:ss";
		String newFormat = "HH:mm:ss";

		String s = null;
		SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
		try {

			Date d = sdf.parse(date);
			sdf.applyPattern(newFormat);
			s = sdf.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return s;
	}

	public static Date getDateFormat(String date) {

		String dateFormat = "yyyy-MM-dd HH:mm:ss";
		Date newDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			newDate = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Log.e("return date :",newDate.toString());
		return newDate;
	}

	public static CharSequence getCountdownText(Date futureDate) {
		StringBuilder countdownText = new StringBuilder();

		long timeRemaining = futureDate.getTime() - new Date().getTime();

		if (timeRemaining > 0) {

			int days = (int) TimeUnit.MILLISECONDS.toDays(timeRemaining);
			timeRemaining -= TimeUnit.DAYS.toMillis(days);
			int hours = (int) TimeUnit.MILLISECONDS.toHours(timeRemaining);
			timeRemaining -= TimeUnit.HOURS.toMillis(hours);
			int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(timeRemaining);
			timeRemaining -= TimeUnit.MINUTES.toMillis(minutes);
			int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(timeRemaining);

			if (days > 0) {
				countdownText.append(days);
				countdownText.append(":");
			}
			if (days > 0 || hours > 0) {
				countdownText.append(hours);
				countdownText.append(":");
			}
			if (days > 0 || hours > 0 || minutes > 0) {
				countdownText.append(minutes);
				countdownText.append(":");
			}
			if (days > 0 || hours > 0 || minutes > 0 || seconds > 0) {
				countdownText.append(seconds);
				countdownText.append("");
			}
		}
		Log.e("return time :",countdownText.toString());

		return countdownText.toString();
	}

}


