package net.softm.lib;

import java.io.File;
import java.util.LinkedHashMap;

import android.os.Environment;
/**
 * Constant
 * @author softm 
 */
public class Constant {
	public static final boolean SERVER 				= true; //
	public static final boolean DEBUG 					= BuildConfig.DEBUG; // > ADT r17
	public final static String SD_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+(Constant.DEBUG?"":".")+"citis";
	// DefaultApplication에서 초기화. ------------
	public static String WORK_DIR     = "";
	// ------------ DefaultApplication에서 초기화.
	public static String TMP_DIR    = ""; // 템프
	public static String PIC_DIR    = ""; // 사진

	// 공통 코드 정의
	public static String CODE_JSON_COM01 = "[{\"Y\":\"완료\",\"N\":\"미완료\"}]";
	/** 완료여부 **/
	public static LinkedHashMap<String, String> CODE_END_YN = new LinkedHashMap<String, String>();

	static {
		CODE_END_YN.put("Y", "완료" );
		CODE_END_YN.put("N", "미완료");
	}

	public static String SERVER_COMMON_URL= SERVER?"":""; // 공통
	public static String SERVER_CHECK_URL = SERVER?"":""; //
	public static String SERVER_DATA_URL  = SERVER?"":""; //

	// 처리 상수
	/** 성공 */
	public final static int HTTP_CODE_SUCCESS = 200; // 성공
	/** 실패  */
	public final static int HTTP_CODE_ERROR   = 500; // 실패

	/** 네트웍 NOT CONNECTED */
	public final static int HTTP_CODE_NETWORK       = 444; // 네트웍 NOT CONNECTED
	/** SOCKETTIMEOUT */
	public final static int HTTP_CODE_SOCKETTIMEOUT = 599; // SOCKETTIMEOUT
	/** NOT FOUND */
	public final static int HTTP_CODE_NOT_FOUND     = 404; // NOT FOUND

	/** Y 'Y' */
	public static String CODE_Y = "Y"; // 'Y'
	/** N 'N' */
	public static String CODE_N = "N"; // 'N'

	/** 완료여부 'Y' */
	public static String CODE_END_Y = "Y"; // 완료여부 'Y'
	/** 완료여부 'N' */
	public static String CODE_END_N = "N"; // 완료여부 'N'

	public final static int PROC_ID_TEST    	= 999; // 테스트
	public final static int PROC_ID_TAKE_CAMERA = 1; // 사진 촬영.
	public final static int PROC_ID_PIC_VIWER   = 2; // 사진뷰어 오픈.

	public static final String LOG_TAG = "HAN" ;

	/** HTTP 연결에 대한 기본 Timeout */
	public static final int DEFAULT_CONNECT_TIMEOUT = 5000;
	/** HTTP 연결 이후 Input Stream 읽기에 대한 기본 Timeout */
	public static final int DEFAULT_READ_TIMEOUT = 10000;
	/** HTTP 연결 이후 Input Stream 쓰기기에 대한 기본 Timeout softm add */
	public static final int DEFAULT_WRITE_TIMEOUT = 10000;
}
