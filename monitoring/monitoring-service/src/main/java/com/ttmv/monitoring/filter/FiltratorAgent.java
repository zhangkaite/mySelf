package com.ttmv.monitoring.filter;

import com.ttmv.monitoring.inter.IWhiteListInter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import java.util.BitSet;
import java.util.Map;

/**
 * Created by zbs on 15/10/15.
 */
public abstract class FiltratorAgent {

    private static Logger logger = LogManager.getLogger(FiltratorAgent.class);

    /**
     * 对值进行白名单校验，如果在白名单中有，返回true，如果没有返回false
     * @param str
     * @return
     */
    public boolean checkWhite(String str){
        try {
            if (str == null || "".equals(str))
                throw new Exception("传入的待检查的ip为空。");
            String key = getMapKey(str);
            Integer bitSetKey = getBitSetKey(str);
            Map<String, BitSet> map = getWhiteList();
            if (key == null || "".equals(key) || 0 == bitSetKey || map == null)
                throw new Exception("白名单数据有为空的数据");
            //如果map中没有这个key表示不通过
            if (map.get(key) == null)
                return false;
            BitSet bitSet = map.get(key);
            //如果白名单中有值表示能通过
            return bitSet.get(bitSetKey);
        }catch (Exception e){
            logger.error("白名单检查出现异常.",e);
            return false;
        }
    }

    /**
     * 对白名单中增加一个值
     * @param obj
     */
    public abstract void addWhiteListValue(Object obj);

    /**
     * 对白名单中值进行更新
     */
    public abstract void updateWhiteValue();

    /**
     * 初始化一个白名单
     */
    public abstract void inifWhiteList() throws Exception;

    /**
     * 获取MAP的key
     * @param str
     * @return
     */
    protected abstract String getMapKey(String str) throws Exception;

    /**
     * 获取放入bitmap的key
     * @param str
     * @return
     */
    protected abstract Integer getBitSetKey(String str) throws Exception;

    /**
     * 获取白名单map
     * @return
     */
    protected abstract Map<String,BitSet> getWhiteList();

}
