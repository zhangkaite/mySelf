package com.ttmv.service;

import java.util.Map;

/**
 * Created by zbs on 15/11/9.
 */
public abstract class OverdueService {

	public Map execute(Map reqMap) {
		return handler(reqMap);
	}

	public abstract Map handler(Map obj);

	public abstract Object getDataObject(Map reqMap) throws Exception;

	public abstract void checkData(Map reqMap) throws Exception;
}
