/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : image정보만을 parsing. 네이버 api를 통해 영화순위의 이미지를 검색할때 사용
 */package mobile.proj.movie.util;

import java.io.StringReader;
import java.util.ArrayList;

import mobile.proj.search.util.ItemDto;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.text.Html;
import android.util.Log;

public class ImageXMLParser {

	public final static String TAG = "ImageXMLParser";
	
	private XmlPullParser mParser = null;

	public ImageXMLParser() {
		try {

			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			mParser = factory.newPullParser();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
	}
	
	public String parse(String xml) {

		String result = "";

		try {
//			String으로 전달 받은 xml 을 XmlPullParser 에 설정
			mParser.setInput(new StringReader(xml));
			
			int tagType = 0;
//			문서의 마지막이 될 때까지 읽어들이는 부분의 이벤트를 구분하여 반복 수행
			for (int eventType = mParser.getEventType();  eventType != XmlPullParser.END_DOCUMENT; eventType = mParser.next()) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:

					break;
				case XmlPullParser.END_DOCUMENT:
	
					break;
				case XmlPullParser.START_TAG:
					
					if(mParser.getName().equals("image")) tagType = 1;
					
					break;
				case XmlPullParser.END_TAG:
					
					break;
				case XmlPullParser.TEXT:
					
					if (tagType == 1) result = mParser.getText();
					tagType = 0;
					break;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
