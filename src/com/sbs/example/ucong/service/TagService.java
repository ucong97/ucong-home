package com.sbs.example.ucong.service;

import java.util.List;

import com.sbs.example.ucong.container.Container;
import com.sbs.example.ucong.dao.TagDao;
import com.sbs.example.ucong.dto.Tag;

public class TagService {
	private TagDao tagDao;

	public TagService() {
		tagDao = Container.tagDao;
	}

	public List<Tag> getDedupTagsByRelTypeCode(String relTypeCode) {
		return tagDao.getDedupTagsByRelTypeCode(relTypeCode);
	}

	public List<String> getDedupTagBodiesByRelTypeCode(String relTypeCode) {
		return tagDao.getDedupTagBodiesByRelTypeCode(relTypeCode);

	}

	public List<Tag> getDedupTagsByArticleId(int id) {
		return tagDao.getDedupTagsByArticleId(id);
	}

	
}
