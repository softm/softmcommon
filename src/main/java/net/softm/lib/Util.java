package net.softm.lib;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;
/**
 * Util
 * @author softm 
 */
public class Util {
	public static void createWorkDir() {
		File f1 = new File(Constant.SD_DIR);
		if (!f1.exists()) {
			f1.mkdirs();
		}
		File f2 = new File(Constant.WORK_DIR);
		if (!f2.exists()) {
			f2.mkdirs();
		}
		File f3 = new File(Constant.PIC_DIR);
		if (!f3.exists()) {
			f3.mkdirs();
		}
		File f4 = new File(Constant.TMP_DIR);
		if (!f4.exists()) {
			f4.mkdirs();
		}
	}

	public static String getFormatSize(double size) {
		if (size >= 1024 * 1024 * 1024) {
			Double dsize = size / (1024 * 1024 * 1024);
			return new DecimalFormat("#.00").format(dsize) + "G";
		} else if (size >= 1024 * 1024) {
			Double dsize = size / (1024 * 1024);
			return new DecimalFormat("#.00").format(dsize) + "M";
		} else if (size >= 1024) {
			Double dsize = size / 1024;
			return new DecimalFormat("#.00").format(dsize) + "K";
		} else {
			return String.valueOf((int) size) + "B";
		}
	}

	public static void deleteFilesWithExtension(final String directoryName,
			final String extension) {

		final File dir = new File(directoryName);
		final String[] allFiles = dir.list();
		for (final String file : allFiles) {
			if (file.endsWith(extension)) {
				new File(directoryName + "/" + file).delete();
			}
		}
	}
	public static void deleteFilesWithPrefix(final String directoryName,
			final String prefix) {
		
		final File dir = new File(directoryName);
		final String[] allFiles = dir.list();
		for (final String file : allFiles) {
			if (file.startsWith(prefix)) {
				new File(directoryName + "/" + file).delete();
			}
		}
	}

	public static void forceRename(File source, File target) throws IOException {
		if (target.exists())
			target.delete();
		source.renameTo(target);
	}

	/**
	 * EXIF정보를 회전각도로 변환하는 메서드
	 * 
	 * @param exifOrientation
	 *            EXIF 회전각
	 * @return 실제 각도
	 */
	public static int exifOrientationToDegrees(int exifOrientation) {
		if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
			return 90;
		} else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
			return 180;
		} else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
			return 270;
		}
		return 0;
	}

	/**
	 * 이미지를 회전시킵니다.
	 * 
	 * @param bitmap
	 *            비트맵 이미지
	 * @param degrees
	 *            회전 각도
	 * @return 회전된 이미지
	 */
	public static Bitmap rotate(Bitmap bitmap, int degrees) {
		if (degrees != 0 && bitmap != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) bitmap.getWidth() / 2,
					(float) bitmap.getHeight() / 2);

			try {
				Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
						bitmap.getWidth(), bitmap.getHeight(), m, true);
				if (bitmap != converted) {
					bitmap.recycle();
					bitmap = converted;
				}
			} catch (OutOfMemoryError ex) {
				// 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
			}
		}
		return bitmap;
	}

	public static String getSysYYYYMMDDFormat() {
		String rtn = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		return rtn;
	}
	
	public static String getSysYYYYMMDD() {
		String rtn = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
		return rtn;
	}
	
	public static String getSysYYYYMM() {
		String rtn = new java.text.SimpleDateFormat("yyyyMM").format(new java.util.Date());
		return rtn;
	}
	
	public static String getSysYYYY() {
		String rtn = new java.text.SimpleDateFormat("yyyy").format(new java.util.Date());
		return rtn;
	}
	
	public static String getSysMM() {
		String rtn = new java.text.SimpleDateFormat("MM").format(new java.util.Date());
		return rtn;
	}

	public static String getSysHHMMSS() {
		String rtn = new java.text.SimpleDateFormat("hhmmss").format(new java.util.Date());
		return rtn;
	}
	
	public static String getSysYYYYMMDDHHMMSSFormat() {
		String rtn = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date());
		return rtn;
	}
	
	// log
	public static void w(String msg) {
		w(Constant.LOG_TAG, msg);
	}

	public static void w(String tag, String msg) {
		if (Constant.DEBUG)
			Log.w(tag, msg);
	}

	public static void d(String msg) {
		d(Constant.LOG_TAG, msg);
	}

	public static void d(String tag, String msg) {
		if (Constant.DEBUG)
			Log.d(tag, msg);
	}

	public static void e(String msg) {
		e(Constant.LOG_TAG, msg);
	}

	public static void e(String tag, String msg) {
		if (Constant.DEBUG)
			Log.e(tag, msg);
	}
	
	public static void i(Object msg) {
		Class<?> cls = msg.getClass();
	   	Field fieldlist[] = cls.getDeclaredFields();
	    for (int i = 0; i < fieldlist.length; i++) {
	    	 Field fld = fieldlist[i];
//	         Util.i("name = " + fld.getName());
//	         Util.i("decl class = " + fld.getDeclaringClass());
//	         Util.i("type = " + fld.getType());
//	         int mod = fld.getModifiers();
//	         Util.i("modifiers = " + Modifier.toString(mod));
	        try {
	        	Util.i(fld.getName() + "=" + fld.get(msg));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
	    }
	}

	public static void i(String msg) {
		i(Constant.LOG_TAG, msg);
	}

	public static void i(String tag, String msg) {
		if (Constant.DEBUG)
			Log.i(tag, msg);
	}

	public static void v(String msg) {
		v(Constant.LOG_TAG, msg);
	}

	public static void v(String tag, String msg) {
		if (Constant.DEBUG)
			Log.v(tag, msg);
	}
	
}