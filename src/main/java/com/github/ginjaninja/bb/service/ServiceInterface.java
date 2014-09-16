package com.github.ginjaninja.bb.service;

import java.util.Map;

import com.github.ginjaninja.bb.message.ResultMessage;

public interface ServiceInterface <T>{
	public ResultMessage get(Integer id);
	public ResultMessage save(T t);
	public ResultMessage delete(Integer id);
	public ResultMessage deactivate(Integer id);
	public ResultMessage activate(Integer id);
	public ResultMessage getMany(String queryName, Map<String, Object> params);
	public ResultMessage getMany(String queryName);
}
