package com.sbs.example.ucong.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;
import com.sbs.example.ucong.dto.Tag;

public class TagDao {

	public List<Tag> getDedupTagsByRelTypeCode(String relTypeCode) {
		List<Tag> tags = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT T.*");
		sql.append("FROM tag AS T");
		sql.append("WHERE 1");
		if (relTypeCode != null && relTypeCode.length() > 0) {
			sql.append("AND T.relTypeCode = ?", relTypeCode);
		}
		sql.append("GROUP BY T.body");

		sql.append("ORDER BY T.body ASC");

		List<Map<String, Object>> List = MysqlUtil.selectRows(sql);
		for (Map<String, Object> map : List) {
			tags.add(new Tag(map));

		}
		return tags;
	}

	public List<String> getDedupTagBodiesByRelTypeCode(String relTypeCode) {
		List<String> tagBodies = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT T.body");
		sql.append("FROM tag AS T");
		sql.append("WHERE 1");
		if (relTypeCode != null && relTypeCode.length() > 0) {
			sql.append("AND T.relTypeCode = ?", relTypeCode);
		}
		sql.append("GROUP BY T.body");

		sql.append("ORDER BY T.body ASC");

		List<Map<String, Object>> List = MysqlUtil.selectRows(sql);
		for (Map<String, Object> map : List) {
			tagBodies.add((String) map.get("body"));

		}
		return tagBodies;
	}

	public List<Tag> getDedupTagsByArticleId(int id) {
		List<Tag> tags = new ArrayList<>();
		SecSql sql = new SecSql();
		
		sql.append("SELECT *");
		sql.append("FROM tag AS T");
		sql.append("WHERE relId = ? ", id);
		sql.append("GROUP BY T.body");
		sql.append("ORDER BY T.body ASC");

		List<Map<String, Object>> List = MysqlUtil.selectRows(sql);
		for (Map<String, Object> map : List) {
			tags.add(new Tag(map));

		}

		return tags;
	}

}
