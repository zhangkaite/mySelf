package com.ttmv.monitoring.imDataList;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author kate
 *
 */

public class ShowImDataListDao {
	
	private ShowImDataListDaoMapper showImDataListDaoMapper;
	

	public ShowImDataListDaoMapper getShowImDataListDaoMapper() {
		return showImDataListDaoMapper;
	}


	public void setShowImDataListDaoMapper(ShowImDataListDaoMapper showImDataListDaoMapper) {
		this.showImDataListDaoMapper = showImDataListDaoMapper;
	}


	public List<ImServiceDataEntity> showImDataList() throws Exception {

		List<ImServiceDataEntity> ls = new ArrayList<ImServiceDataEntity>();
		List<ImServiceDataEntity> mtsDataList = showImDataListDaoMapper.showImMtsDataList();
		List<ImServiceDataEntity> lbsDataList = showImDataListDaoMapper.showImLbsDataList();
		List<ImServiceDataEntity> mdsDataList = showImDataListDaoMapper.showImMdsDataList();
		List<ImServiceDataEntity> mssDataList = showImDataListDaoMapper.showImMssDataList();
		List<ImServiceDataEntity> prsDataList = showImDataListDaoMapper.showImPrsDataList();
		List<ImServiceDataEntity> rmsDataList = showImDataListDaoMapper.showImRmsDataList();
		List<ImServiceDataEntity> tasDataList = showImDataListDaoMapper.showImTasDataList();
		List<ImServiceDataEntity> umsDataList = showImDataListDaoMapper.showImUmsDataList();
		for (ImServiceDataEntity data : mtsDataList) {
			ls.add(data);
		}
		for (ImServiceDataEntity data : lbsDataList) {
			ls.add(data);
		}
		for (ImServiceDataEntity data : mdsDataList) {
			ls.add(data);
		}
		for (ImServiceDataEntity data : mssDataList) {
			ls.add(data);
		}
		for (ImServiceDataEntity data : prsDataList) {
			ls.add(data);
		}
		for (ImServiceDataEntity data : rmsDataList) {
			ls.add(data);
		}
		for (ImServiceDataEntity data : tasDataList) {
			ls.add(data);
		}
		for (ImServiceDataEntity data : umsDataList) {
			ls.add(data);
		}

		return ls;

	}

}
