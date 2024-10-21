package net.softm.lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
/**
 * PicCamera
 * @author softm 
 */
public class PicCamera {
	public static final String TAG = "SOFTM";
	private Activity context;
    private String mode = null  ;
    private String fileName= null;
    private String imgRoot = null;
    private String prefix  = null;
    private String suffix  = null;
    private String tmpImgPath = null;
    private boolean singleMode = true;
    public final static String MODE_PICTURE = "P"; // 사진
    public final static String MODE_SIGN = "S"; // 서명
    File imgDir = new File(Constant.PIC_DIR); // directory
    
	public PicCamera(Activity context,String imgRoot, String mode, String fPreFix, String fSuffix) {
		this.context= context;
		this.imgRoot= imgRoot;		
		this.mode   = mode   ;		
		this.prefix = fPreFix;
		this.suffix = fSuffix;
		this.singleMode = true;
		init();
	}
	
	/**
	 * 단일파일만 사진촬영할건지 여부 기본값은 단일파일만.
	 * @param singleMode
	 */
	public void setSingleMode(boolean singleMode) {
		this.singleMode = singleMode;
		init();
	}
	/**
	 * 단일파일만 사진촬영할건지 여부 기본값은 단일파일만.
	 * @param singleMode
	 */
	public boolean getSingleMode() {
		return this.singleMode;
	}

	/**
	 * 사진 : PicCamera.MODE_PICTURE
	 * 서명 : PicCamera.MODE_SIGN
	 * @param mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	public int getMaxSeq() {
		File dir = new File(imgRoot);		
		int seq = 0;
		if ( singleMode ) {
			seq = 1;	
		} else {
			final String[] allFiles = dir.list();
			for (final String file : allFiles) {
				if (file.startsWith(mode + "_" + prefix )) {
					File f = new File(prefix + "/" + file);
					seq++;
				}
			}
		}
		return seq;
	}
	
	public int init() {
		Util.createWorkDir();
//		Log.d(TAG,imgRoot);
        int seq = getMaxSeq() + (!singleMode?1:0);		
        fileName = mode + "_" + prefix + "_" + String.format("%02d", seq) + suffix;
        while(new File(Constant.PIC_DIR + File.separator + fileName).isFile()) {
        	seq++;        	
        	fileName = mode + "_" + prefix + "_" + String.format("%02d", seq) + suffix;
        }
        return seq;
	}

	public void start() {
		Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
//        int seq = getMaxSeq() + (!singleMode?1:0);
        int seq = init();
        File image = null;
		try {
			image = File.createTempFile(
					mode + "_" + prefix + "_" + String.format("%02d", seq), // 파일명
					suffix,		// suffix
					imgDir
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
		tmpImgPath = image.getAbsolutePath();
		Log.d("test", "0 : " + mode + "_" + prefix + "/" + String.format("%02d", seq));
		Log.d("test", "1 : " + mode + "_" + prefix + "_" + String.format("%02d", seq));
		Log.d("test", "2 : " + suffix);
		Log.d("test", "3 : " + tmpImgPath);
        Uri outputFileUri = Uri.fromFile(image);
        intent.putExtra( MediaStore.EXTRA_OUTPUT, outputFileUri );
        context.startActivityFromChild(context.getParent(), intent, Constant.PROC_ID_TAKE_CAMERA);
	}
	
	public void save() {
//    	Log.d(TAG, "2 save : " + tmpImgPath );						
//    	Log.d(TAG, "     ->> : " + imgRoot+ File.separator + fileName);						
//				Util.deleteFilesWithExtension(Constant.PIC_DIR, "jpg");
		try {
			Util.forceRename(new File(tmpImgPath),new File(imgRoot+ File.separator + fileName));
			resize(imgRoot+ File.separator + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}     		
	}
	
	public final static int DISPLAYWIDTH = 500;
	public final static int DISPLAYHEIGHT = 500;
	
	public void resize(String filePath){
		
		// 이미지가 아니라 이미지의 치수를 로드한다.
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(filePath, bmpFactoryOptions);
		int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) DISPLAYHEIGHT);
		int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth  / (float) DISPLAYWIDTH);
		
		// 두 비율 다 1보다 크면 이미지의 가로, 세로 중 한쪽이 화면보다 크다.
/*
		if (heightRatio > 1 && widthRatio > 1) {
			if (heightRatio > widthRatio) {
				// 높이 비율이 더 커서 그에 따라 맞춘다.
				bmpFactoryOptions.inSampleSize = heightRatio;
			} else {
				// 너비 비율이 더 커서 그에 따라 맞춘다.
				bmpFactoryOptions.inSampleSize = widthRatio;
			}
		}
		bmpFactoryOptions.inSampleSize = 5;
*/
		// 실제로 디코딩한다.
		bmpFactoryOptions.inJustDecodeBounds = false;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Util.i("test","filePath : " + filePath);
		
		int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
		int exofDegree = Util.exifOrientationToDegrees(exifOrientation);
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, bmpFactoryOptions);
		bitmap = Util.rotate(bitmap, exofDegree);
		FileOutputStream fosObj = null;

		try {
		    // 리사이즈 이미지 동일파일명 덮어 쒸우기 작업
		    fosObj = new FileOutputStream(filePath);
//		    bitmap.compress(CompressFormat.JPEG, 100, fosObj);
		    bitmap.compress(CompressFormat.JPEG, 30, fosObj);
		} catch (Exception e){
		} finally {
		    try {
				fosObj.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    try {
				fosObj.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		    
//		int targetW = mImagePicViewer.getWidth(); // ImageView 의 가로 사이즈 구하기
//		int targetH = mImagePicViewer.getHeight(); // ImageView 의 세로 사이즈 구하기
//		if ( targetW == 0 ) targetW = 70;
//		if ( targetH == 0 ) targetH = 70;
        // ImageView 객체 생성
//		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)imgBtnTop1.getLayoutParams();
//		params.addRule(RelativeLayout.CENTER_IN_PARENT);
//		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//		params.addRule(RelativeLayout.LEFT_OF, R.id.btn_top2);

		// Bitmap 정보를 가져온다.
//		BitmapFactory.Options bmOptions = new BitmapFactory.Options(); 
////		bmOptions.inJustDecodeBounds = true;
////		BitmapFactory.decodeFile( Constant.PIC_DIR + File.separator + mFileName, bmOptions);
////		int photoW = bmOptions.outWidth; // 사진의 가로 사이즈 구하기
////		int photoH = bmOptions.outHeight; // 사진의 세로 사이즈 구하기
////
////		// 사진을 줄이기 위한 비율 구하기
////		int scaleFactor = Math.min( photoW/targetW, photoH/targetH);
////		bmOptions.inJustDecodeBounds = false;
////		bmOptions.inSampleSize = scaleFactor;
////		bmOptions.inPurgeable = true;
////		
//		ExifInterface exif = null;
//		try {
//			exif = new ExifInterface(fileName);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		Util.i("test","fileName : " + fileName);
//		
//		int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//		int exofDegree = Util.exifOrientationToDegrees(exifOrientation);
//		Bitmap bitmap = BitmapFactory.decodeFile(fileName, bmOptions);
//		bitmap = Util.rotate(bitmap, exofDegree);
	}
	
	public void tempDelete() {
//    	Log.d(TAG, "2 delete : " + tmpImgPath );						
//    	Log.d(TAG, "     ->> : " + imgRoot+ File.separator + fileName);						
//				Util.deleteFilesWithExtension(Constant.PIC_DIR, "jpg");
		try {
			new File(tmpImgPath).delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void delete() {
//    	Log.d(TAG, "2 delete : " + tmpImgPath );						
//    	Log.d(TAG, "     ->> : " + imgRoot+ File.separator + fileName);						
//				Util.deleteFilesWithExtension(Constant.PIC_DIR, "jpg");
		try {
			new File(tmpImgPath).delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			new File(imgRoot+ File.separator + fileName).delete();
		} catch (Exception e) {
			e.printStackTrace();
		}     		
	}
	public int fileCount() {
		File dir = new File(imgRoot);
//		Log.d(TAG,imgRoot);
		final String[] allFiles = dir.list();
		int seq = 0;
		for (final String file : allFiles) {
//			Log.d(TAG,file);
			if (file.startsWith(mode + "_" + prefix )) {
				seq++;
			}
		}
		return seq;
	}
	
	public String[] getFiles() {
		File file = new File( imgRoot );
		String[] list = file.list( new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) 
            {
                return name.startsWith(mode + "_" + prefix );
            }
		});
		return list;
	}
	
	public String getFileName() {
		return fileName;
	}
	
}
