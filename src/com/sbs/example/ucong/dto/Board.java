package com.sbs.example.ucong.dto;

import java.util.Map;

public class Board {
	private int id;
	private String name;
	private String regDate;
	private String updateDate;
	private String code;

	public Board(Map<String, Object> boardMap) {
		this.id = (int) boardMap.get("id");
		this.name = (String) boardMap.get("name");
		this.code = (String) boardMap.get("code");
		this.regDate = (String) boardMap.get("regDate");
		this.updateDate = (String) boardMap.get("updateDate");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
