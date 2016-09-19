package com.ttmv.datacenter.usercenter.domain.mark;

import java.util.HashMap;
import java.util.Map;

import com.ttmv.datacenter.usercenter.domain.mark.util.ByteUtil;
import com.ttmv.datacenter.usercenter.domain.mark.util.MarkConfig;
import com.ttmv.datacenter.usercenter.domain.mark.util.MarkFactory;


public class UserBaseMark extends MarkConfig{
	
	public final static String MAP_LOCATION_KEY = "location"; 
	public final static String MAP_VALUE_KEY = "value"; 

	//--------------------------------------
	public final static int USER_NORMAL = 0;
	public final static int USER_FREEZE= 1;
	public final static int USER_DELETE= 7;
	//-------------------------------------
	public final static int USER_NOT_VIPTYPE = 0;  
	public final static int USER_VIPTYPE = 1;
	
	
	
	private final static int errorIndex = -1;	

	//-------------------------------------------- 初始化 ------------------------------------------
   
	/**用户--初始mark,初始化位数为8*/
	public final static byte[] createMark(){return new byte[8];}
	
    //-------------------------------------------- state ------------------------------------------
	
	/**用户状态--正常 */
    public final static byte[] userNormal(byte[] data){return MarkFactory.getByte(data,state,statie_normal);}
	/**用户状态--冻结 */
    public final static byte[] userFreeze(byte[] data){return MarkFactory.getByte(data,state,static_freeze);}
	/**用户状态--删除 */
    public final static byte[] userDelete(byte[] data){return MarkFactory.getByte(data,state,static_delete);}
    
    /**获得用户状态 0--正常 1--冻结 7--删除 */
    public final static int getUserState(byte[] data){
    	byte[] value = MarkFactory.cutBytes(data, state);
		int index =  errorIndex;
    	if(ByteUtil.isEqualBytes(statie_normal, value))
    		index = USER_NORMAL;
    	if(ByteUtil.isEqualBytes(static_freeze, value))
            index = USER_FREEZE;
    	if(ByteUtil.isEqualBytes(static_delete, value))
    		index = USER_DELETE;
        return index;
    }
    
	/** 返回用户状态结构 **/
	public final static Map<String, Object> getUserStateFlat(int data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(MAP_LOCATION_KEY, state[0]);
		switch (data) {
		case USER_NORMAL:
			map.put(MAP_VALUE_KEY, statie_normal);
		case USER_FREEZE:
			map.put(MAP_VALUE_KEY, static_freeze);
		case USER_DELETE:
			map.put(MAP_VALUE_KEY, static_delete);
		default:
			map = null;
		}
		return map;
	}
    
    //--------------------------------------------- vipType ---------------------------------------
    
    /**用户--会员 */
    public final static byte[] vipType(byte[] data){return MarkFactory.getByte(data,vipType,vipType_true);}
    /**用户--非会员 */
    public final static byte[] notVipType(byte[] data){return MarkFactory.getByte(data,vipType,vipType_false);}
    
    /**获得用户是否是会员 0--非会员 1--会员  */
    public final static int getVipType(byte[] data){
    	byte[] value = MarkFactory.cutBytes(data, vipType);
		int index =  errorIndex;
    	if(ByteUtil.isEqualBytes(vipType_false, value))
    		index = USER_NOT_VIPTYPE;
    	if(ByteUtil.isEqualBytes(vipType_true, value))
            index = USER_VIPTYPE;
        return index;
    }
    
  	/** 返回用户是否冻结结构 **/
  	public final static Map<String, Object> getIsVipType(int data) {
  		Map<String, Object> map = new HashMap<String, Object>();
  		map.put(MAP_LOCATION_KEY, vipType[0]);
  		switch (data) {
  		case USER_VIPTYPE:
  			map.put(MAP_VALUE_KEY, vipType_true);
  		case USER_NOT_VIPTYPE:
  			map.put(MAP_VALUE_KEY, vipType_false);
  		default:
  			map = null;
  		}
  		return map;
  	}

}
