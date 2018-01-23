package net.softm.lib.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Pref
 * @author softm
 */
public class Pref extends Object {

	public static final String TAG = "CITIS";

	// USER_ID
	public static void setUserId(final Context ctx, String value) {
		save(ctx, TAG, "USER_ID", value);
	}

	public static String getUserId(final Context ctx) {
		return read(ctx, TAG, "USER_ID", "");
	}

	// USER_PW
	public static void setUserPw(final Context ctx, String value) {
		save(ctx, TAG, "USER_PW", value);
	}

	public static String getUserPw(final Context ctx) {
		return read(ctx, TAG, "USER_PW", "");
	}

	// USER_NM
	public static void setUserNm(final Context ctx, String value) {
		save(ctx, TAG, "USER_NM", value);
	}

	public static String getUserNm(final Context ctx) {
		return read(ctx, TAG, "USER_NM", "");
	}
	
	// USER_ID_SAVE
	public static void setUserIdSaved(final Context ctx, boolean value) {
		save(ctx, TAG, "USER_ID_SAVE", value);
	}

	public static boolean getUserIdSaved(final Context ctx) {
		return read(ctx, TAG, "USER_ID_SAVE", false);
	}
	
	// EQIP_CD
	public static void setEqipCd(final Context ctx, String value) {
		save(ctx, TAG, "EQIP_CD", value);
	}
	
	public static String getEquipCd(final Context ctx) {
		return read(ctx, TAG, "EQIP_CD", "");
	}
	
	// BARCD_EQUIP_USE_YN
	public static void setBarcdEquipUseYn(final Context ctx, String value) {
		save(ctx, TAG, "BARCD_EQUIP_USE_YN", value);
	}
	
	public static String getBarcdEquipUseYn(final Context ctx) {
		return read(ctx, TAG, "BARCD_EQUIP_USE_YN", "");
	}
	
	// save
	public static void save(final Context ctx, String prefname, String name, int value) {
		SharedPreferences pref = ctx.getSharedPreferences(prefname, 0);
		SharedPreferences.Editor edit = pref.edit();
		edit.putInt(name, value);
		edit.commit();
	}

	public static void save(final Context ctx, String prefname, String name, String value) {
		SharedPreferences pref = ctx.getSharedPreferences(prefname, 0);
		SharedPreferences.Editor edit = pref.edit();
		edit.putString(name, value);
		edit.commit();
	}

	public static void save(final Context ctx, String prefname, String name, boolean value) {
		SharedPreferences pref = ctx.getSharedPreferences(prefname, 0);
		SharedPreferences.Editor edit = pref.edit();
		edit.putBoolean(name, value);
		edit.commit();
	}

	// read
	public static int read(final Context ctx, String prefname, String name, int defaultvalue) {
		SharedPreferences pref = ctx.getSharedPreferences(prefname, 0);
		return pref.getInt(name, defaultvalue);
	}

	public static String read(final Context ctx, String prefname, String name, String defaultvalue) {
		SharedPreferences pref = ctx.getSharedPreferences(prefname, 0);
		return pref.getString(name, defaultvalue);
	}

	public static boolean read(final Context ctx, String prefname, String name, boolean defaultvalue) {
		SharedPreferences pref = ctx.getSharedPreferences(prefname, 0);
		return pref.getBoolean(name, defaultvalue);
	}

	
	public static int read(final Context ctx, String name, int defaultvalue) {
	    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
		return pref.getInt(name, defaultvalue);
	}
	public static String read(final Context ctx, String name, String defaultvalue) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
		return pref.getString(name, defaultvalue);
	}
	public static boolean read(final Context ctx, String name, boolean defaultvalue) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
		return pref.getBoolean(name, defaultvalue);
	}
	
}
