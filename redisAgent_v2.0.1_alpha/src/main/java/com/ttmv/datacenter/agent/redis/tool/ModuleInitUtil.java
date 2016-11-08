package com.ttmv.datacenter.agent.redis.tool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by zbs on 15/11/11.
 */
public class ModuleInitUtil {

    private static final Logger logger = LogManager.getLogger(ModuleInitUtil.class);

    public static void failInitByEnv(String moduleName, String environment, Exception e){
        String fatalLog = "Failed to init ";
        if(!CheckParameterUtil.checkIsEmpty(moduleName)){
            fatalLog = fatalLog + moduleName;
        }
        fatalLog = fatalLog + " for ";
        if(!CheckParameterUtil.checkIsEmpty(environment)){
            fatalLog = fatalLog + environment;
        }
        fatalLog = fatalLog + "-----details: ";
        if(e != null && e.getMessage() != null){
            fatalLog = fatalLog + e.getMessage();
        }
        logger.fatal(fatalLog);
    }

    public static void failInitBySet(String moduleName, String[] checkProperty){
        String fatalLog = "Failed to init ";
        if(!CheckParameterUtil.checkIsEmpty(moduleName)){
            fatalLog = fatalLog + moduleName;
        }
        fatalLog = fatalLog + ", please check property : ";
        if(checkProperty != null && checkProperty.length > 0){
            for(int i = 0; i < checkProperty.length; i++){
                fatalLog = fatalLog + checkProperty[i] + ",";
            }
        }
        logger.fatal(fatalLog);
    }
}
