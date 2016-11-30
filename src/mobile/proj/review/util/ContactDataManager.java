/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 :리뷰관련 manager 
 */
package mobile.proj.review.util;

import java.util.ArrayList;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ContactDataManager {
	private final static String TABLE_NAME = "review_table";
	private ContactDbHelper helper = null;
	ContactDto dto = new ContactDto();
	
	public ContactDataManager(Context context) {
		helper = new ContactDbHelper(context);
	}
	
//	전체 리뷰 검색
//	DB 에서 전체 리뷰정보를 가져온 후 레코드 별로 dto 를 생성하여 ArrayList 에 저장 후 반환
	public ArrayList<ContactDto> getAllContacts() { 
//		리뷰 dto 를 저장할 ArrayList 객체 생성
		ArrayList<ContactDto> contactList = new ArrayList<ContactDto>();
		

//		DB 획득 및 SQL 실행
		SQLiteDatabase db = helper.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + ";", null);
		
		while (cursor.moveToNext()) {

//			dto 객체 생성
			ContactDto dto = new ContactDto();
			
//			레코드의 컬럼별 값을 dto에 저장 
		
			dto.setNo(cursor.getInt(0));
			dto.setTitle(cursor.getString(1));
			dto.setUserRating(cursor.getString(2));
			dto.setReview(cursor.getString(3));
			

			contactList.add(dto);
		}
		

		cursor.close(); 
		helper.close();
		
		return contactList;
	}

	//이름으로 리뷰 검색
	public ContactDto searchByTitle(String searchTitle) { 
		ContactDto dto = new ContactDto();
		SQLiteDatabase db = helper.getReadableDatabase();
		
		Log.i("test", searchTitle);
		Cursor cursor = db.rawQuery("SELECT * FROM review_table where title = '" + searchTitle + "';", null);
		
		while (cursor.moveToNext()){
			dto.setNo(cursor.getInt(0));
			dto.setTitle(cursor.getString(1));
			dto.setUserRating(cursor.getString(2));
			dto.setReview(cursor.getString(3));
		}
		cursor.close();
		helper.close();
		return dto;
		
	}
	
	//별점으로 리뷰 검색
	public ArrayList<ContactDto> searchByUserRating(String rating) { 
		ArrayList<ContactDto> contactList = new ArrayList<ContactDto>();
		SQLiteDatabase db = helper.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("SELECT * FROM review_table where userRating = '" + rating + "';", null);
		
			
		while (cursor.moveToNext()){
			ContactDto dto = new ContactDto();
			
			dto.setNo(cursor.getInt(0));
			dto.setTitle(cursor.getString(1));
			dto.setUserRating(cursor.getString(2));
			dto.setReview(cursor.getString(3));
			
			contactList.add(dto);
			
		}
		
		cursor.close();
		helper.close();
		
		return contactList;
	}
	
//	새로운 리뷰 입력
//	리뷰 정보 dto 를 전달받아 DB 에 저장
	public void addContact(ContactDto dto) {
		SQLiteDatabase db = helper.getWritableDatabase();
//		매개변수로 받은 dto 객체의 값을 사용하여 query 문장 생성
		String query = "INSERT INTO " + TABLE_NAME + " VALUES ( null, '" 
				+ dto.getTitle() + "', '" + dto.getUserRating() + "', '" + dto.getReview()+"');";
		
//		Logcat 창에서 query 문을 살펴볼 수 있도록 출력
		Log.i("ContactDataManager", "SQL: " + query);
		
		db.execSQL(query);
		db.close();
	}
	
	public void deleteList(int pos) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "DELETE FROM review_table WHERE no =" + pos +";";
		db.execSQL(sql);
		
		db.close();
		}

}

	
	
