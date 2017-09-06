package net.codingme.po;

/**
 * 信息表对应的实体类
 * 
 * @author Niu on 2017年9月4日 下午3:31:26
 */
public class News {
	// 信息id
	private Integer newsId;
	// 信息所属栏目id
	private Integer cId;
	// 信息标题
	private String newsTitle;
	// 信息正文
	private String newsContent;
	// 附件1
	private String newsFile1;
	// 附件2
	private String newsFile2;
	// 附件3
	private String newsFile3;

	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public Integer getcId() {
		return cId;
	}

	public void setcId(Integer cId) {
		this.cId = cId;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public String getNewsFile1() {
		return newsFile1;
	}

	public void setNewsFile1(String newsFile1) {
		this.newsFile1 = newsFile1;
	}

	public String getNewsFile2() {
		return newsFile2;
	}

	public void setNewsFile2(String newsFile2) {
		this.newsFile2 = newsFile2;
	}

	public String getNewsFile3() {
		return newsFile3;
	}

	public void setNewsFile3(String newsFile3) {
		this.newsFile3 = newsFile3;
	}

	public News(Integer newsId, Integer cId, String newsTitle, String newsContent, String newsFile1, String newsFile2,
			String newsFile3) {
		super();
		this.newsId = newsId;
		this.cId = cId;
		this.newsTitle = newsTitle;
		this.newsContent = newsContent;
		this.newsFile1 = newsFile1;
		this.newsFile2 = newsFile2;
		this.newsFile3 = newsFile3;
	}

	public News() {
	}

}
