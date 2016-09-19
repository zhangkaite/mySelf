package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.UserInfo;
import com.ttmv.monitoring.entity.WhiteList;
import com.ttmv.monitoring.entity.page.Page;
import com.ttmv.monitoring.entity.querybean.WhiteListQuery;
import com.ttmv.monitoring.inter.IWhiteListInter;
import com.ttmv.monitoring.mapper.WhiteListDaoMapper;

public class IWhiteListInterImpl implements IWhiteListInter {

	private WhiteListDaoMapper whiteListDaoMapper;
	
	public Integer addWhiteList(WhiteList whiteListInter)
			throws Exception {
		Integer result = whiteListDaoMapper.addWhiteList(whiteListInter);
		return result;
	}

	public Integer updateWhiteList(WhiteList whiteListInter)
			throws Exception {
		Integer result = whiteListDaoMapper.updateWhiteList(whiteListInter);
		return result;
	}

	public Integer deleteWhiteList(BigInteger id) throws Exception {
		Integer result = whiteListDaoMapper.deleteWhiteList(id);
		return result;
	}

	public WhiteList queryWhiteList(BigInteger id) throws Exception {
		WhiteList result = whiteListDaoMapper.queryWhiteList(id);
		return result;
	}

	public List<WhiteList> queryListByConditions(WhiteList whiteList) throws Exception {
		List<WhiteList> list = whiteListDaoMapper.queryListByConditions(whiteList);
		return list;
	}

	public Page queryPageWhiteList(WhiteListQuery whiteListQuery)
			throws Exception {
		Integer page = whiteListQuery.getPage();
		Integer pageSize = whiteListQuery.getPageSize();
		if(page == null || pageSize == null){
			throw new Exception("page或是pageSize不是为空！");
		}
		if(page < 1 || pageSize < 1 ){
			throw new Exception("page不能小于1或是pageSize不能小于1！");
		}
		/* 结果对象 */
		Page p = new Page();
		/* 总数 */
		Integer sum = whiteListDaoMapper.queryPageWhiteListSum(whiteListQuery);
		if(sum == null || sum == 0){
			return null;
		}
		/* 计算起始位置 */
		Integer start = getPagingStart(sum,page,pageSize);
		whiteListQuery.setStart(start);
		List<WhiteList> datas = whiteListDaoMapper.queryPageWhiteList(whiteListQuery);
		p.setSum(sum);
		p.setData(datas);
		return p;
	}

	/**
	 * 获取分页的起始位置
	 * 
	 * @param solrs
	 * @param page
	 * @param pageSize
	 */
	private Integer getPagingStart(Integer sum, Integer page, Integer pageSize)throws Exception{
		Integer start = 0;
		/* 分页的总数 */
		Integer sumPage = sum / pageSize + 1;
		/* 取得起始位置 */
		if (page <= sumPage) {
			start = (page - 1) * pageSize;
		}else{
			throw new Exception("page不正确,不能大于总页数或是小于1！");
		}
		return start;
	}
	public WhiteListDaoMapper getWhiteListDaoMapper() {
		return whiteListDaoMapper;
	}

	public void setWhiteListDaoMapper(
			WhiteListDaoMapper whiteListDaoMapper) {
		this.whiteListDaoMapper = whiteListDaoMapper;
	}

}
