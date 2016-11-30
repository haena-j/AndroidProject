/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 :  데이터베이스를 관리하는 클래스,  Helper 를 사용하여 데이터베이스 접근 수행. insert, update, delete,select 기능 구현
*/
package mobile.proj.movie.util;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MovieDataManager {

//	테이블 명은 자주 사용하므로 상수로 지정
	private final static String TABLE_NAME = "movie_table";
	
//	SQLiteOpenHelper 상속 클래스를 멤버변수로 지정
	private MovieDBHelper helper = null;

//	생성자 내에서 Helper 객체 생성
	public MovieDataManager(Context context) {
		helper = new MovieDBHelper(context);
	}
	
	
//	새로운 위치정보 입력
//	위치정보 dto 를 전달받아 DB 에 저장
	public void insertNewMovie(MovieDto dto) {
		SQLiteDatabase db = helper.getWritableDatabase();
		// dto 정보를 사용하여 테이블에 새로운 위치 삽입 코드 작성 
		String query = "INSERT INTO " + TABLE_NAME + " VALUES ( '"+dto.getMovieCd() + "','"+ dto.getRank()+"','" + dto.getMovieNm() + "','" + dto.getOpenDt() + "','" + dto.getAudiAcc()  + "','" + "" + "');";
		
//		Logcat 창에서 query 문을 살펴볼 수 있도록 출력
		Log.i("MovieDataManager", "SQL: " + query);
		
		db.execSQL(query);
		db.close();
	}

	public void updateImage(String moiveNm, String image){
		SQLiteDatabase db = helper.getWritableDatabase();
		// dto 정보를 사용하여 테이블에 새로운 위치 삽입 코드 작성 
		String query = "UPDATE " + TABLE_NAME + " SET IMAGE = '"+ image + "' WHERE movieNm = '" + moiveNm + "';";
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
	
//	전체 위치 정보 검색
//	DB 에서 전체 위치정보를 가져온 후 레코드 별로 dto 를 생성하여 ArrayList 에 저장 후 반환
	public ArrayList<MovieDto> getAllLocations() {
		
//		연락처 dto 를 저장할 ArrayList 객체 생성
		ArrayList<MovieDto> movieList = new ArrayList<MovieDto>();
		
//		DB 획득 및 SQL 실행
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + ";", null);
		
		while(cursor.moveToNext()) {
//			dto 객체 생성
			MovieDto dto = new MovieDto();
			
//			레코드의 컬럼별 값을 dto에 저장 
			dto.setMovieCd(cursor.getString(0));
			dto.setRank(cursor.getString(1));
			dto.setMovieNm(cursor.getString(2));
			dto.setOpenDt(cursor.getString(3));
			dto.setAudiAcc(cursor.getString(4));
			dto.setImage(cursor.getString(5));
//			ArrayList 객체에 dto 객체 저장
			movieList.add(dto);
		}
		
		cursor.close();
		helper.close();
		
		return movieList;
	}

}





