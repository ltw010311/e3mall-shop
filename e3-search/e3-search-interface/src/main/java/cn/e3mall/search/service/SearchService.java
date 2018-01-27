package cn.e3mall.search.service;

import cn.e3mall.search.pojo.SearchResult;

public interface SearchService {

	SearchResult search(String keyword,int page,int rows) throws Exception;
}
