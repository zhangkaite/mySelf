package com.ttmv.service.worker.impl.control;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.Cmp;
import com.ttmv.dao.constant.Constant;
import com.ttmv.dao.inter.mysql.ICmpExpireInter;
import com.ttmv.service.callback.redisqueue.CmpCallbackQ;
import com.ttmv.service.worker.AbstractWorker;

/***
 * 金色弹窗定期调度
 * 
 * @author kate
 *
 */
@Service
public class InOverdueStartCMPWorkImpl extends AbstractWorker {
	private static Logger logger = LogManager.getLogger(InOverdueStartCMPWorkImpl.class);
	@Resource(name = "icmpExpireInterImpl")
	private ICmpExpireInter iCmpExpireInterImpl;
	@Resource(name = "cmpCallbackQ")
	private CmpCallbackQ cmpCallbackQ;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Scheduled(cron = "0 1 00 * * ? ")
	//@Scheduled(cron = "0 0/1 * * * ? ")
	@Override
	public void traversalMysql() {

		try {
			Cmp cmp = new Cmp();
			cmp.setTag(Constant.STARTCMP);
			cmp.setWarntime(sdf.format(new Date()));
			List<Cmp> cmps = iCmpExpireInterImpl.queryListCmpByBean(cmp);
			if (null != cmps && cmps.size() > 0) {
				for (Cmp cmp_ : cmps) {
					cmpCallbackQ.excute(cmp_);
				}
			}
		} catch (Exception e) {
			logger.error("金色弹窗开始提醒查询mysql失败，失败的原因是:", e);
		}

	}

	@Override
	public void traversalRedis() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshMysql() {
		// TODO Auto-generated method stub

	}

}
