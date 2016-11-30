/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 영화 검색 에서 검색한 영화를  클릭할 경우 실행되는 Activity
 */
package mobile.proj.search;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import mobile.proj.join.R;
import mobile.proj.review.ChoiceInsertContactActivity;
import mobile.proj.search.ItemClickActivity;
import mobile.proj.search.util.ItemDataManager;
import mobile.proj.search.util.ItemDto;
import mobile.proj.search.util.ScrapDataManager;

public class ItemClickActivity extends Activity{
	private TextView tvTitle;
	private TextView tvSubTitle;
	private TextView tvUserRating;
	private TextView tvPubData;
	private TextView tvDirector;
	private TextView tvActor;
	private ImageView ivImage;
	private ArrayList<ItemDto> resultList;
	ItemDataManager itemManager = new ItemDataManager(this);
	ScrapDataManager scrapManager = new ScrapDataManager(this);
	URL imageUrl;
	String title;
	protected void onCreate(Bundle savedInstanceState) {
		
		//intent로 넘겨받은 값들을 저장하여 출력해서 보여줌
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_detail);
		title = getIntent().getStringExtra("title");
		String image = getIntent().getStringExtra("image");
		String userRating = getIntent().getStringExtra("userRating");
		String pubData = getIntent().getStringExtra("pubData");
		String director = getIntent().getStringExtra("director");
		String actor = getIntent().getStringExtra("actor");
		String subTitle = getIntent().getStringExtra("subTitle");

		tvTitle = (TextView)findViewById(R.id.tvMovieNm);
		tvSubTitle = (TextView)findViewById(R.id.tvOpenDt);
		tvUserRating = (TextView)findViewById(R.id.tUser);
		tvPubData = (TextView)findViewById(R.id.tPubData);
		tvDirector = (TextView)findViewById(R.id.tDirector);
		tvActor = (TextView)findViewById(R.id.tActor);
		ivImage = (ImageView)findViewById(R.id.iVMovie);
		
		tvTitle.setText(title);
		tvSubTitle.setText(subTitle);
		tvUserRating.setText("평점 : " + userRating);
		tvPubData.setText("제작년도 : " + pubData);
		tvDirector.setText("감독 : " + director);
		tvActor.setText("배우 : " + actor);
		try{ //이미지 예외처리
			imageUrl = new URL(image);
			HttpURLConnection con = (HttpURLConnection)imageUrl.openConnection();
			BufferedInputStream bis = new BufferedInputStream(con.getInputStream(), 10240);
			Bitmap bm = BitmapFactory.decodeStream(bis);
			ivImage.setImageBitmap(bm);
			bis.close();
			
			
		}catch(Exception e){e.printStackTrace();}

	}
	public void onClick(View v){
		Intent intent = null;
		switch(v.getId()){
		case R.id.scrapMovie: //영화스크랩시 무비함에 저장
			resultList = itemManager.getAllLocations();
			for(ItemDto dto : resultList){
				if(dto.getTitle().equals(tvTitle.getText())){
					scrapManager.inserMovie(dto);
					Toast.makeText(this,tvTitle.getText() + "을(를) scrap함에 저장", Toast.LENGTH_SHORT).show();
					break;
				}
			}
			break;
		case R.id.scrapReview: //리뷰작성시 Choice리뷰작성페이지로 이동  
			Bundle extras = new Bundle();
			extras.putString("title", getIntent().getStringExtra("title"));
			intent = new Intent(this, ChoiceInsertContactActivity.class);
			intent.putExtras(extras);
			startActivity(intent);
			break;
	
		}
	}
	
}
