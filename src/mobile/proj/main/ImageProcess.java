/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 이미지를 사이즈를 줄이고 원 형태로 잘라주는 부분. 
 */
package mobile.proj.main;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;



public class ImageProcess {
	Bitmap bitmap;
	// 선택된 이미지를 정사각형 편집
	public Bitmap reSizeBitMap(Bitmap bitMap) {
		int bitMapWidth = bitMap.getWidth(); //bitMap의 사이즈를 구해서 저장
		int bitMapHeight = bitMap.getHeight();

		if(bitMapWidth > bitMapHeight) { //bitMap의 가로가 세로보다길면 bitmap을 세로길이에 맞춤
			bitmap = Bitmap.createBitmap(bitMap, 0, 0, bitMapHeight, bitMapHeight);
		} else {
			bitmap = Bitmap.createBitmap(bitMap, 0, 0, bitMapWidth, bitMapWidth);
		}
	    bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, false); //bitmap 사이즈 축소 ! error 방지
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
	            bitmap.getHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(output); //원형태를 위한 canvas

	    final int color = 0xff424242;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());//bitmap의 사이즈를 가져와 Rect을 설정

	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,//원의 사이즈설정 부분
	            bitmap.getWidth() / 2, paint);
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);
	    return output;
	}
}
