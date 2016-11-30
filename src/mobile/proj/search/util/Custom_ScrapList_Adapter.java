/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 :무비함 커스텀어댑터뷰. 무비함의 출력 결과를 새로 설정해준다
 */
package mobile.proj.search.util;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import mobile.proj.join.R;
import mobile.proj.search.ItemClickActivity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Custom_ScrapList_Adapter extends ArrayAdapter<MyItemDto>{
	Context mContext;
	private ArrayList<MyItemDto> items;

	public Custom_ScrapList_Adapter(Context context, int textViewResourceId, ArrayList<MyItemDto> items){
			super(context, textViewResourceId, items);
			this.items = items;
			mContext = context;
			
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View v = convertView;
		MyItemDto MyItemDto = items.get(position);
		
		if(android.os.Build.VERSION.SDK_INT > 8)
		{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		if(v == null){
			LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.search_list, null);
		}
		if(MyItemDto != null){
			
			ImageView iv = (ImageView)v.findViewById(R.id.iVMovie);
			TextView tv_title = (TextView)v.findViewById(R.id.movieTitleText);
			TextView tv_director = (TextView)v.findViewById(R.id.directorText);
			TextView tv_year = (TextView)v.findViewById(R.id.yearText);
			TextView tv_sub =(TextView)v.findViewById(R.id.subText);
			URL imageUrl;
			tv_title.setText(MyItemDto.getTitle());
			tv_sub.setText("(" + MyItemDto.getSubtitle() + ")");
			tv_director.setText("감독 " + MyItemDto.getDirector());
			tv_year.setText("개봉년도 " + MyItemDto.getPubDate());
			
			try{//이미지예외처리
				imageUrl = new URL(MyItemDto.getImage());
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
