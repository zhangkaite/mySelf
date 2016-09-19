package com.ttmv.datacenter.usercenter.service.processor.template;

import java.util.Map;

import com.ttmv.datacenter.usercenter.domain.protocol.GeneralPro;

/**
 * 处理器（公共处理模板）
 * 
 * @author Damon_Zs & 2015-1-24 11:42:09
 * 
 */
public abstract class Processor{
	
	public abstract Map execute(GeneralPro generalPro,Object object);

}
