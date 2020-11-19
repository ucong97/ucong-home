package com.sbs.example.ucong.dto;

import java.util.Map;

public class Board {
	public int id;
	public String name;
	public String regDate; 
	public String updateDate; 
	public String code;
	public Board(Map<String, Object> boardMap) {
		this.id = (int)boardMap.get("id");
		this.name =(String)boardMap.get("name");
		this.code =(String)boardMap.get("code");
		this.regDate =(String)boardMap.get("regDate");
		this.updateDate =(String)boardMap.get("updateDate");
	}
}
