/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 영화순위 관련 kobis api의 결과를 parsing
 */package mobile.proj.movie.util;

import java.io.StringReader;
import java.util.ArrayList;

import mobile.proj.movie.util.MovieDto;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class MovieXMLParser {
public final static String TAG = "MovieXMLParser";
	
	private XmlPullParser mParser = null;
	
	public MovieXMLParser() {
		try {
			Log.i(TAG, "start XMLParser");
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			mParser = factory.newPullParser();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<MovieDto> parse(String xml) {
		ArrayList<MovieDto> resultList = new ArrayList<MovieDto>();

		try {
//			String으로 전달 받은 xml 을 XmlPullParser 에 설정

			mParser.setInput(new StringReader(xml));
			
			int tagType = 0;
			MovieDto tmpDto = new MovieDto();
//			문서의 마지막이 될 때까지 읽어들이는 부분의 이벤트를 구분하여 반복 수행
			for (int eventType = mParser.getEventType();  eventType != XmlPullParser.END_DOCUMENT; eventType = mParser.next()) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					Log.i(TAG, "start document");
					break;
				case XmlPullParser.END_DOCUMENT:
					Log.i(TAG, "end document");
					break;
				case XmlPullParser.START_TAG:
					
					if(mParser.getName().equals("movieCd"))	tagType = 1;
					else if(mParser.getName().equals("rank"))	tagType = 2;
					else if (mParser.getName().equals("movieNm")) tagType = 3;
					else if(mParser.getName().equals("openDt")) tagType = 4;
					else if(mParser.getName().equals("audiAcc")) tagType = 5;
					
					break;
				case XmlPullParser.END_TAG:
					
					if(mParser.getName().equals("dailyBoxOffice")){
						resultList.add(tmpDto);
						tmpDto = new MovieDto();
					}
					
					break;
				case XmlPullParser.TEXT:
					
					if(tagType == 1) tmpDto.setMovieCd(mParser.getText());

					else if (tagType == 2)tmpDto.setRank(mParser.getText());

					else if (tagType == 3) tmpDto.setMovieNm(mParser.getText());
	
					else if (tagType == 4) tmpDto.setOpenDt(mParser.getText());
					
					else if (tagType == 5) tmpDto.setAudiAcc(mParser.getText());
					tagType = 0;
					break;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultList;
	}

}
