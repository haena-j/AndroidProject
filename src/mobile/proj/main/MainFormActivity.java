/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 로그인 후 나오는 액티비티. 회원의 Db를 가져와 회원의 이름을 보여줌. 이미지 설정및 각각의 액티비티로 이동할수 있는 페이지.
 */
package mobile.proj.main;

import java.io.File;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import mobile.proj.join.R;
import mobile.proj.join.settingActivity;
import mobile.proj.join.util.ContactDataManager;
import mobile.proj.join.util.ContactDto;
import mobile.proj.main.ImageProcess;
import mobile.proj.map.MapActivity;
import mobile.proj.movie.MovieChartActivity;
import mobile.proj.movie.util.MovieDataManager;
import mobile.proj.review.reviewMainActivity;
import mobile.proj.search.SearchActivity;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainFormActivity extends Activity{
	private String mPath;//사진경로
	private String mFileName;//사진파일이름
	TextView tv; //회원이름
	ImageView myIv; //이미지뷰객체선언
	ContactDto dto = new ContactDto();
	int REQUEST_IMAGE;
	Bitmap bitmap;
	private static final int OpenCamera = 1, OpenGallery = 2;
	ImageProcess imgPrcs = new ImageProcess(); //이미지 크기 조정을 위해사용
	MovieDataManager movieManager = new MovieDataManager(this); //오늘 영화 순위 미리 db에 저장
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_form);
		
		mPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		mFileName = mPath + "/tmp/image.jpg";
		Log.d("CameraTest", mPath);
		
		
		myIv = (ImageView)findViewById(R.id.iVMovie);

		bitmap = BitmapFactory.decodeFile(mFileName);
		if(bitmap != null){ //image가 없을경우 기존 이미지 사용, 있을경우 이미지불러와 사용
			myIv.setImageBitmap(imgPrcs.reSizeBitMap(bitmap));
		}
		ContactDataManager manager = new ContactDataManager(this);
		dto = manager.searchInfo();
		 tv = (TextView)findViewById(R.id.TextView2);
		 tv.setText(dto.getId() + "님");
		 
		
		 
	}
	
	public void onClick(View v){
		Intent intent = null;
		switch(v.getId()){
			case R.id.btnCamera: //camera로 이미지설정	
				intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mFileName)));
				startActivityForResult(intent, OpenCamera);
				break;
				
			case R.id.btnGallery:	//갤러리 이미지로 사진 설정
				intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, OpenGallery);
				break;
			case R.id.testbtnMAP: //지도보기 기능
				intent = new Intent(this, MapActivity.class);
				startActivity(intent);
				break;
			case R.id.testBtnSearch: //영화검색 기능
				intent = new Intent(this, SearchActivity.class);
				startActivity(intent);
				break;
			case R.id.testBtnChart: //영화순위 기능
				intent = new Intent(this, MovieChartActivity.class);
				startActivity(intent);
				break;
			case R.id.btnMovieScrap: //무비함 기능
				intent = new Intent(this, MovieScrapActivity.class);
				startActivity(intent);
				break;
			case R.id.btnReviewScrap: //리뷰 기능
				intent = new Intent(this, reviewMainActivity.class);
				startActivity(intent);
				break;
			case R.id.btnSetting: //설정 기능
				intent = new Intent(this, settingActivity.class);
				startActivity(intent);
				break;
		}	
	}


	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//사진을 설정하기 위해 필요한 부분
		if(resultCode == RESULT_OK){
			switch(requestCode){
			case OpenCamera:
				bitmap = BitmapFactory.decodeFile(mFileName);
				bitmap = imgPrcs.reSizeBitMap(bitmap);//ImageProcess를 통해 사진사이즈조절및 모양변경
				myIv.setImageBitmap(bitmap);
				break;
				
			case OpenGallery:
					copyImageFile(mFileName, data.getData());
					bitmap = BitmapFactory.decodeFile(mFileName);
					bitmap = imgPrcs.reSizeBitMap(bitmap);
					myIv.setImageBitmap(bitmap);

				
			}
		}
	}
	

private void copyImageFile(String destFileName, Uri selectedImageUri){
		
		File sourceFile = new File(getRealPathFromURI(selectedImageUri));
		File destFile = new File(destFileName);
		
		if (!sourceFile.exists()) return;

		FileChannel source = null;
		FileChannel destination = null;
		
		try {
			source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();
	        
	        if (destination != null && source != null) {
	            destination.transferFrom(source, 0, source.size());
	        }
	        
	        if (source != null) source.close();

	        if (destination != null) destination.close();
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    private String getRealPathFromURI(Uri contentUri) {
       String[] proj = { MediaStore.Video.Media.DATA };
       Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
       int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
       cursor.moveToFirst();
       return cursor.getString(column_index);
    }
    

}