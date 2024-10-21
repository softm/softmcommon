package net.softm.lib.common;

import android.os.Environment;
import android.util.Log;

import net.softm.lib.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;



/**
 * custom log util
 */
public class WLog {

	public static final String LOG_FILE_PATH = Environment.getExternalStorageDirectory() + "/SOFTM";
	
	public static final int LOG_FILE_MAX_COUNT = 10;
	
	private static boolean isTraceLog = true;
	
	private static String TAG = "softm";

	private static boolean isDebug = BuildConfig.DEBUG;
	/**
	 * print info
	 */
	public static void v(String tag, String msg){
            if(isDebug){
                Log.i(TAG + tag, msg);
		}
	}


	/**
	 * print debug
	 */
	public static void v(String msg) {
		if (isDebug) {
			Log.v(TAG, msg);
		}
	}

	/**
	 * print info
	 */
	public static void i(String tag, String msg){
		if(isDebug){
			Log.i(TAG + tag, msg);
		}
	}

	/**
	 * print info
	 */
	public static void i(String msg){
		if(isDebug){
			Log.i(TAG, msg);
		}
	}
	/**
	 * print debug
	 */
	public static void d(String tag, String msg) {
		if (isDebug) {
			Log.d(TAG + tag, msg);
		}
	}
	
	/**
	 * print debug
	 */
	public static void d(String msg) {
		if (isDebug) {
			Log.d(TAG, msg);
		}
	}

	/**
	 * print warning
	 */
	public static void w(String tag, String msg) {
		if (isDebug) {
			Log.w(TAG + tag, msg);
		}
	}
	
	/**
	 * print warning
	 */
	public static void w( String msg) {
		if (isDebug) {
			Log.w(TAG, msg);
		}
	}
	
	/**
	 * 로그 파일 저장
	 */
	public static void ww(String log) {
		
		if (isDebug) {
			Log.w(TAG, log);
		}
		
		if (!isTraceLog) {
            return;
        }
        log = TAG + " - " + log;
        writeLog(TAG, log);
		
	}
	
	/**
	 * 로그 파일 저장
	 */
	public static void ww(String tag, String log) {
		
		if (isDebug) {
			Log.w(tag, log);
		}
		
		if (!isTraceLog) {
            return;
        }
        log = tag + " - " + log;
        writeLog(tag, log);
	}
	
	
	/**
	 * print error
	 */
	public static void e(String tag, String msg) {
		if (isDebug) {
			Log.e(tag, msg);
		}
	}
	
	/**
	 * print error
	 */
	public static void e(String msg) {
		if (isDebug) {
			Log.e(TAG, msg);
		}
	}

    public static void printException(Exception e) {
		if (isDebug) {
			Log.e(TAG, "printException >> " + Log.getStackTraceString(e));
		}
    }
	
	
	/**
	 * 로그 파일 저장
	 */
	public static void ee(String log) {
		if (isDebug) {
			Log.e(TAG, log);
		}
		
		if (!isTraceLog) {
            return;
        }
        log = TAG + " - " + log;
        writeLog(TAG, log);
		
	}
	
	/**
	 * 로그 파일 저장
	 */
	public static void ee(String tag, String log) {
		
		if (isDebug) {
			Log.e(tag, log);
		}

		if (!isTraceLog) {
            return;
        }
        log = tag + " - " + log;
        writeLog(tag, log);
	} 
	
	
	
	
	/**
     * 파일에 로그 저장
     */
    private static void writeLog(String tag, String log) {
    	
    	if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && isDebug)
    	{
    		String path = LOG_FILE_PATH;
    		String fileName = getCurrentDate("yyyy-MM-dd") + ".txt";
            File file = new File(path , fileName);

            FileWriter fw = null;
            BufferedWriter bw = null;
            try {
                File dir = new File(path);
                if (!dir.exists()) {
                    boolean result = dir.mkdir();
					WLog.d(TAG, "result : " + result);
                }

                if (!file.exists()) {
                    boolean result = file.createNewFile();
                    WLog.d(TAG, "result : " + result);

                    // kris 26.Aug.13 - 로그 파일 저장 개수는 7개로 제한
                    // After
                    limitLogFileCount(file);
                    //
                }

                fw = new FileWriter(file, true);
                bw = new BufferedWriter(fw);

                final String currentDateAndTime = getCurrentDateAndTime(System.currentTimeMillis());

                bw.newLine();
                bw.append(currentDateAndTime + "    " + log);
                bw.newLine();
                bw.flush();
                bw.close();

            }catch (IOException e) {
                WLog.printException(e);
            }finally {
                if(fw != null){
                    try {
                        fw.close();
                    } catch (IOException e) {
                        WLog.printException(e);
                    }
                }
                if(bw != null){
                    try {
                        bw.close();
                    } catch (IOException e) {
                        WLog.printException(e);
                    }
                }
            }
        }
    }
    
    private static void limitLogFileCount(File file) {
        File mLogFileInExternalMemory = new File(LOG_FILE_PATH);
        int mLogFileCount;

        if(mLogFileInExternalMemory.listFiles() != null){
            mLogFileCount = mLogFileInExternalMemory.listFiles().length;
        }else{
            mLogFileCount = 0;
        }

        if (mLogFileCount >= LOG_FILE_MAX_COUNT) {
            File[] mLogFileListInExternalMemory = mLogFileInExternalMemory
                    .listFiles();

            Arrays.sort(mLogFileListInExternalMemory, new Comparator<File>() {

                @Override
                public int compare(File o1, File o2) {
                    return o2.getName().compareTo(o1.getName());
                }
            });

            for (int i = 0; i < mLogFileCount; i++) {
                if (i >= LOG_FILE_MAX_COUNT) {
                    mLogFileListInExternalMemory[i].delete();

                    FileWriter fw = null;
                    BufferedWriter bw = null;
                    try {
                        fw = new FileWriter(file, true);
                        bw = new BufferedWriter(fw);

                        String log = "Deleted - Log file name : "
                                + mLogFileListInExternalMemory[i].getName();

                        bw.newLine();
                        bw.append(log);

                        fw.flush();
                        fw.close();

                        bw.flush();
                        bw.close();
                    }catch (IOException e) {
                        WLog.printException(e);
                    }finally {
                        if(fw != null){
                            try {
                                fw.close();
                            } catch (IOException e) {
                                WLog.printException(e);
                            }
                        }
                        if(bw != null){
                            try {
                                bw.close();
                            } catch (IOException e) {
                                WLog.printException(e);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static String getCurrentDate(String format)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		long now = System.currentTimeMillis();
		return dateFormat.format(new Date(now));
	}
    
    public static String getCurrentDateAndTime(long milliseconds)
	{
		Date currentTime = new Date(milliseconds);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(currentTime);
	}
	
}