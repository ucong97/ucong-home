package com.sbs.example.ucong.dto;

import java.util.Map;

public class Board {
	public int id;
	public String name;
	
	public Board(Map<String, Object> boardMap) {
		this.id = (int)boardMap.get("id");
		this.name =(String)boardMap.get("name");
	}
}
