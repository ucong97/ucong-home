package com.sbs.example.ucong.dao;

import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;

public class Ga4DataDao {



	public int deletePagePath(String pagePath) {
		SecSql sql = new SecSql();
		sql.append("DELETE FROM ga4DataPagePath");
		sql.append("WHERE pagePath = ?", pagePath);

		return MysqlUtil.delete(sql);
		
	}

	public int savePagePath(String pagePath, int hit) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO ga4DataPagePath");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", pagePath = ?" , pagePath);
		sql.append(", hit = ?" , hit);

		return MysqlUtil.insert(sql);
	}

}
