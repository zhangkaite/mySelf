package com.datacenter.dams.business.service.activity.pro;

import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.datacenter.dams.util.JsonUtil;
import com.datacenter.domain.live.activity.LevelDomain;

public class LevelDomainUtil {

	public static LevelDomain getLevelDomainFromTemp(TmpLevelDomain tmp)throws Exception{
		if(tmp != null){			
			LevelDomain level = new LevelDomain();
			BeanUtils.copyProperties(tmp, level, new String[]{"activityPackage"});
			Map<String,String> map = tmp.getActivityPackage();
			if(map != null){				
				String activitPackageStr = JsonUtil.getObjectToJson(map);
				level.setActivityPackage(activitPackageStr);
			}
			return level;
		}
		return null;
	}
}
