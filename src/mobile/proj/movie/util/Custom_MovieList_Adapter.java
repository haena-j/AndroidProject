/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 영화순위 ListView에 보여질 커스텀어댑터뷰
 */package mobile.proj.movie.util;


import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import mobile.proj.join.R;
import mobile.proj.movie.util.MovieDto;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Custom_MovieList_Adapter extends ArrayAdapter<MovieDto>{
	ImageView iv;
	private ArrayList<MovieDto> items;
	MovieDto MovieDto;
	
		
	public MovieDto getMovieDto() {
		return MovieDto;
	}

	public Custom_MovieList_Adapter(Context context, int textViewResourceId, ArrayList<MovieDto> items){
			super(context, textViewResourceId, items);
			this.items = items;
			
			

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View v = convertView;
		MovieDto = items.get(position);
		
		if(android.os.Build.VERSION.SDK_INT > 8)//오류처리를 위해 사용
		{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		if(v == null){//View가 null값일 경우
			LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.moviechart_list, null);
		}
		if(MovieDto != null){//dto의 값이 들어있을때
			
			//보여질 값들을 가져와 저장
			iv = (ImageView)v.findViewById(R.id.iVMovie);
			TextView tv_MoiveNm = (TextView)v.findViewById(R.id.tvMovieNm);
			TextView tv_OpenDt = (TextView)v.findViewById(R.id.tvOpenDt);
			TextView tv_Rank = (TextView)v.findViewById(R.id.tvRank);
			TextView tv_AudiAcc =(TextView)v.findViewById(R.id.tvAudiAcc);
			URL imageUrl;
			tv_MoiveNm.setText(MovieDto.getMovieNm());
			tv_OpenDt.setText("개봉일 : " + MovieDto.getOpenDt());
			tv_Rank.setText("순위 :  " + MovieDto.getRank());
			tv_AudiAcc.setText("총관람객 : " + MovieDto.getAudiAcc() + " 명");
			
			try{ //image의 경우 네트워크문제등으로 예외발생가능성이 있어 예외처리가 필요
				imageUrl = new URL(MovieDto.getImage());
				HttpURLConnection con = (HttpURLConnection)imageUrl.openConnection();
				BufferedInputStream bis = new BufferedInputStream(con.getInputStream(), 10240);
				Bitmap bm = BitmapFactory.decodeStream(bis);
				iv.setImageBitmap(bm);
				bis.close();
				
				
			}catch(Exception e){e.printStackTrace();}

		}
		
		return v;
	}
	

}
