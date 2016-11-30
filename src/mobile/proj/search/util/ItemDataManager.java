/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 영화정보 관련 DB관리 Manager
 */

package mobile.proj.search.util;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ItemDataManager {

//	테이블 명은 자주 사용하므로 상수로 지정
	private final static String TABLE_NAME = "mymovie_table";
//	SQLiteOpenHelper 상속 클래스를 멤버변수로 지정
	private ItemDBHelper helper = null;
//	생성자 내에서 Helper 객체 생성
	public ItemDataManager(Context context) {
		helper = new ItemDBHelper(context);
	}
	
	
//	새로운 영화정보 입력
//	영화정보 dto 를 전달받아 DB 에 저장
	public void inserMyMovie(ItemDto dto) {
		SQLiteDatabase db = helper.getWritableDatabase();
		// dto 정보를 사용하여 테이블에 새로운 영화 삽입 코드 작성 
		String query = "INSERT INTO " + TABLE_NAME + " VALUES ( '"+dto.getTitle() + "','"+ dto.getSubtitle()+"','" + dto.getPubDate() + "','" + dto.getDirector() + "','" + dto.getActor()  + "','" + dto.getUserRating() + "','" + dto.getImage()  + "');";
		
//		Logcat 창에서 query 문을 살펴볼 수 있도록 출력
		Log.i("MovieDataManager", "SQL: " + query);
		
		db.execSQL(query);
		db.close();
	}

	
	public void deleteDb(){
		SQLiteDatabase db = helper.getWritableDatabase();
		String query = "delete from " + TABLE_NAME;
		db.execSQL(query);
		db.close();
	}
	
//	전체 영화 정보 검색
//	DB 에서 전체 영화정보를 가져온 후 레코드 별로 dto 를 생성하여 ArrayList 에 저장 후 반환
	public ArrayList<ItemDto> getAllLocations() {
		
//		연락처 dto 를 저장할 ArrayList 객체 생성
		ArrayList<ItemDto> movieList = new ArrayList<ItemDto>();
		
//		DB 획득 및 SQL 실행
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + ";", null);
		
		while(cursor.moveToNext()) {
//			dto 객체 생성
			ItemDto dto = new ItemDto();
			
//			레코드의 컬럼별 값을 dto에 저장 
			dto.setTitle(cursor.getString(0));
			dto.setSubtitle(cursor.getString(1));
			dto.setPubDate(cursor.getString(2));
			dto.setDirector(cursor.getString(3));
			dto.setActor(cursor.getString(4));
			dto.setUserRating(cursor.getString(5));
			dto.setImage(cursor.getString(6));
			
//			ArrayList 객체에 dto 객체 저장
			movieList.add(dto);
		}
		
		cursor.close();
		helper.close();
		
		return movieList;
	}

}





