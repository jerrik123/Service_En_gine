package com.mangocity.mbr.controller.point;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.bean.ResultBean;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.ce.util.CommonUtils;
import com.mangocity.mbr.book.ErrorCode;
import com.mangocity.mbr.factory.ApplicationContext;
import com.mangocity.mbr.factory.BeanFactory;
import com.mangocity.mbr.util.ErrorUtils;
import com.mangocity.mbr.util.ServerCall;

/**
* @ClassName: PointCommService 
* @Description: 积分原子服务
* @author YangJie
* @date 2016年4月05日
 */
public class PointCommService {
private static final Logger log = Logger.getLogger(PointCommService.class);
	
	/**工厂实例*/
	private BeanFactory beanFactory = new ApplicationContext();
	
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	/**
	 * 查询芒果网用户积分
	 * {
	 * 		"mbrId":"xxx"
	 * }
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean queryLocalEnabledPoint(EngineBean pb) throws ExceptionAbstract {
		log.info("PointCommService queryLocalEnabledPoint begin()...");
		Long mbrId = CommonUtils.objectToLong(String.valueOf(pb.getHead("mbrId")), -1L);
		if(-1L == mbrId){
			return ErrorUtils.error(pb, ErrorCode.ERROR_REQUEST_PARAM_INVALID, "mbrId必须为合法数字");
		}
		EngineBean resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
	
	/**
	 * 查询集团用户积分
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean queryCrmEnabledPoint(EngineBean pb) throws ExceptionAbstract {
		log.info("PointCommService queryCrmEnabledPoint begin()...");
		EngineBean resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
	
	/**
	 * 注意:该方法只能作为原子接口被调用(不包含短信校验) 扣减芒果网本地积分
	 * 
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean cutLocalPoint(EngineBean pb) throws ExceptionAbstract {
		log.info("PointCommService cutLocalPoint begin()...appId: " + pb.getAppId() + " ,params: " + pb.getHeadMap());
		EngineBean resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}

	/**
	 * 注意:该方法只能作为原子接口被调用(不包含短信校验) 扣减集团积分
	 * 
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean cutCrmPoint(EngineBean pb) throws ExceptionAbstract {
		log.info("PointCommService cutCrmPoint begin()...appId: " + pb.getAppId() + " ,params: " + pb.getHeadMap());
		EngineBean resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
	
	/**
	 * 退还集团用户积分
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean increaseCrmPoint(EngineBean pb) throws ExceptionAbstract {
		log.info("PointCommService increaseCrmPoint begin()...");
		EngineBean resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
	
	/**
	 * 退还本地用户积分
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean increaseLocalPoint(EngineBean pb) throws ExceptionAbstract {
		log.info("PointCommService increaseLocalPoint begin()...");
		EngineBean resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
	
}
