package com.test.myapp.ref;

// 보통 오라클 테이블 1개당 DTO 1개(이상)를 만든다.
public class RefBoardDTO {
	
	private String seq;
	private String id;
	private String subject;
	private String content;
	private String regdate;
	private String readcount;
	
	// Original File Name : 클라이언트가 업로드한 원본파일명
	private String ofile;
	// Saved File Name : 파일명을 변경한 후 서버에 저장될 파일명
	private String sfile;
	// 자료실이므로 파일을 다운로드 한 횟수를 카운트한다.
	private int downcount;
	
	private String name; // 가상 컬럼 만들기 - 추가 멤버
	private String isnew; // 새글 표시
	private String ccnt;
	
	private int thread; // 답변형 게시판
	private int depth;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getReadcount() {
		return readcount;
	}
	public void setReadcount(String readcount) {
		this.readcount = readcount;
	}
	public String getOfile()
	{
		return ofile;
	}
	public void setOfile(String ofile)
	{
		this.ofile = ofile;
	}
	public String getSfile()
	{
		return sfile;
	}
	public void setSfile(String sfile)
	{
		this.sfile = sfile;
	}
	public int getDowncount()
	{
		return downcount;
	}
	public void setDowncount(int downcount)
	{
		this.downcount = downcount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsnew() {
		return isnew;
	}
	public void setIsnew(String isnew) {
		this.isnew = isnew;
	}
	public String getCcnt() {
		return ccnt;
	}
	public void setCcnt(String ccnt) {
		this.ccnt = ccnt;
	}
	public int getThread() {
		return thread;
	}
	public void setThread(int thread) {
		this.thread = thread;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
}
