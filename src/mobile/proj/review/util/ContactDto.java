/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 :리뷰관련 DTO
 */
package mobile.proj.review.util;

public class ContactDto {

	private int no;
	private String title;
	private String userRating;
	private String review;

	public int getNo() {
		return no;
	}


	public void setNo(int no) {
		this.no = no;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getUserRating() {
		return userRating;
	}


	public void setUserRating(String userRating) {
		this.userRating = userRating;
	}


	public String getReview() {
		return review;
	}


	public void setReview(String review) {
		this.review = review;
	}
	

	public String toString() {
		String text =title + " - " + userRating + "점 [" + review + "]";
		return text;
		
	}

}
