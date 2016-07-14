package com.mangocity.mbr.controller.point.exchange;

import static com.mangocity.ce.util.CommonUtils.isBlank;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.book.ConstantArgs;
import com.mangocity.ce.book.ErrorConstant;
import com.mangocity.ce.book.SysBook;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.ce.exception.IllegalParamException;
import com.mangocity.ce.exception.SystemException;
import com.mangocity.ce.util.CommonUtils;
import com.mangocity.ce.util.New;
import com.mangocity.mbr.book.ErrorCode;
import com.mangocity.mbr.controller.mbr.MbrCommService;
import com.mangocity.mbr.controller.mbrship.MbrShipService;
import com.mangocity.mbr.controller.point.PointCommService;
import com.mangocity.mbr.controller.point.PointService;
import com.mangocity.mbr.factory.ApplicationContext;
import com.mangocity.mbr.factory.BeanFactory;
import com.mangocity.mbr.util.DateFormatUtil;
import com.mangocity.mbr.util.ErrorUtils;
import com.mangocity.mbr.util.RightUtils;
import com.mangocity.mbr.util.ServerCall;

/**
 * 测试服务
 * @author mbr.yangjie
 */
public class TestService {

	private static final Logger log = Logger.getLogger(TestService.class);
	
	/** 工厂实例 */
	private BeanFactory beanFactory = new ApplicationContext();

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	/**
	 * 芒果网创建订单接口(积分互换)
	 {
	 	"authorizeId":"",
	 	"selCode":"",
	 	"points":"100",
	 	"type":"IN-OUT"
	 }
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean partnersCreateOrder4Test(EngineBean pb) throws ExceptionAbstract {
		log.info("PointExchangeInService partnersCreateOrder4Test begin()...appId: " + pb.getAppId() + " ,params: "
				+ pb.getHeadMap());
		String type = String.valueOf(pb.getHead("type"));
		if(CommonUtils.isBlankIncludeNull(type)){
			type = "IN";
		}else if(!"IN".equals(type) && !"OUT".equals(type)){
			return ErrorUtils.error(pb, ErrorConstant.ERROR_PARAM_NULL_10000, "type只能是IN或者OUT");
		}
		
		//step1:判断该授权id是否已经存在,并且有效
		log.info("queryPointAuthorizeByAuthorizeId....begin");
		pb.setCommand("queryPointAuthorizeByAuthorizeId");
		EngineBean pointAuthEb = ServerCall.call(pb);
		log.info("pointAuthEb: " + pointAuthEb);
		if(!SysBook.SUCCESS.equals(pointAuthEb.getResultCode())){
			return  pointAuthEb;
		}
		pb.setHead("bindUserName", pointAuthEb.getBody("USERNAME"));
		
		//step2: 再根据mbrId查询会员信息是否有效
		log.info("queryMbrByMbrId....begin");
		pb.setCommand("queryMbrByMbrId");
		pb.setHead("mbrId", pointAuthEb.getBody("MBR_ID"));
		MbrCommService mbrCommService = beanFactory.getBean("MbrCommService", MbrCommService.class);
		EngineBean mb = mbrCommService.queryMbrByMbrId(pb);
		if(!SysBook.SUCCESS.equals(mb.getResultCode())){
			return mb;
		}
		
		//step3: 检查平安万里通的充值流水是否存在
		log.info("isExistWltOrder....begin");
		pb.setCommand("isExistWltOrder");
		PointConvertBindService pointConvertBindService = beanFactory.getBean("PointConvertBindService", PointConvertBindService.class);
		EngineBean pointConvertEb = pointConvertBindService.isExistWltOrder(pb);
		if("IN".equals(type)){//换入
			if(SysBook.SUCCESS.equals(pointConvertEb.getResultCode())){//如果已经存在了充值流水,则直接报错
				return ErrorUtils.error(pb, ErrorConstant.PointExchange.ERROR_WANLIT_FILL_TRANS_IS_EXISTED, "该充值流水已经存在");
			}
		}else if("OUT".equals(type)){//退还
			//积分退还校验(退还积分不能大于转入积分...)
			pointConvertEb = pointRefundValidation(pb, pointConvertEb);
			log.info("pointRefundValidation: " + pointConvertEb);
			if(!SysBook.SUCCESS.equals(pointConvertEb.getResultCode())){//如果校验不通过,则直接抛出异常
				return pointConvertEb;
			}
		}
		
		//step4:设置属性值
		pb.setCommand("convertPointByRefund");
		pb.setHead("mbrId", mb.getBody("mbrId"));
		pb.setHead("mbrCd", mb.getBody("mbrCd"));
		pb.setHead("compCode", pb.getHead("partnerAsignNum"));
		pb.setHead("mangoPoint", pb.getHead("points"));
		pb.setHead("coagentPoint", "01");
		pb.setHead("handchargePoint", "01");
		pb.setHead("createBy", "MEMBER-API");
		pb.setHead("updateBy", "MEMBER-API");
		pb.setHead("transType", ConstantArgs.POINT_TRANS_TYPE_CONVERT);
		pb.setHead("transDateTime", new Date());
		pb.setHead("points", pb.getHead("points"));
		pb.setHead("transStatus", "P");
		pb.setHead("transactionSubType", ConstantArgs.TRANSACTION_SUB_TYPE);
		pb.setHead("transactionChannel",ConstantArgs.WEB_TRANSACTION_CHANNEL);
		pb.setHead("cTSTransactionChannelSN", ConstantArgs.WEB_CTSTRANSACTION_CHANNELSN);
		pb.setHead("cTSTransactionOrgCode", ConstantArgs.WEB_CTSTRANSACTION_ORGCODE_MARKET);
		pb.setHead("partNumber", ConstantArgs.WEB_PART_NUMBER_IN);
		pb.setHead("partnerAsignNum", "00000001");//合作方编号,这里指万里通
		
		//设置积分调整类型
		if("IN".equals(type)){
			pb.setHead("adjustReasonCode", ConstantArgs.LOY_TXN_TYPE_CD_ACCRUAL);//设置调整编码为积分增加
			pb.setHead("inoutStatus", "0");
		}else if("OUT".equals(type)){
			pb.setHead("adjustReasonCode", ConstantArgs.LOY_TXN_TYPE_CD_REDEMPTION);//设置调整编码为积分减少
			pb.setHead("inoutStatus", "1");
		}
		
		//step5:开始互换积分
		log.info("------------------互换积分------------------begin()...");
		pointConvertEb = this.convertPointByRefund(pb);//开始积分互换
		log.info("convertPointByRefund resultEngineBean: " + pointConvertEb);
		log.info("判断是否是集团用户,如果是集团用户,则加集团兑换积分...");
		
		//step6:操作集团积分
		return operateCrm(pb, type, pointConvertEb);
	}

	private EngineBean pointRefundValidation(EngineBean pb, EngineBean pointConvertEb) throws IllegalParamException {
		if(!SysBook.SUCCESS.equals(pointConvertEb.getResultCode())){//如果退还积分,必须使用之前兑换积分的流水号
			return ErrorUtils.error(pb, ErrorConstant.PointExchange.ERROR_WLTORDER_IS_NOT_EXIST, "该充值流水不存在,无法退还积分");
		}
		
		//查询该积分流水兑换的积分,且退还积分不能大于已兑换积分
		log.info("querySumMangoPointByOrder根据万里通订单查询转入积分总数....begin");
		pb.setCommand("querySumMangoPointByOrder");
		pb.setHead("inOutStatus", "0");//转入
		pointConvertEb = ServerCall.call(pb);
		if(!SysBook.SUCCESS.equals(pointConvertEb.getResultCode())){
			return pointConvertEb;
		}
		Long inPoints = CommonUtils.objectToLong(String.valueOf(pointConvertEb.getBody("totalPoints")), 0L);//转入积分
		log.info("转入积分: " + inPoints);
		
		log.info("querySumMangoPointByOrder根据万里通订单查询冲账积分总数....begin");
		pb.setCommand("querySumMangoPointByOrder");
		pb.setHead("inOutStatus", "1");//冲账
		pointConvertEb = ServerCall.call(pb);//存在没有记录的情况
		//计算冲账总额
		Long outPoints = CommonUtils.objectToLong(String.valueOf(pointConvertEb.getBody("totalPoints")), 0L);//已经冲账了的积分
		log.info("冲账积分: " + outPoints);
		//获取已经冲账的积分数
		Long points = Long.parseLong(String.valueOf(pb.getHead("points")));
		log.info("流水号: " + pb.getHead("selCode") + " ,总转入积分: "+inPoints +" ,已冲账积分: " + outPoints + " ,本次应退还积分: " + points);
		
		if(outPoints>inPoints){//如果出账积分大于转入积分,则
			log.info("冲账积分大于转入积分...数据异常...");
			pointConvertEb.setResultCode(ErrorConstant.ERROR_DATA_EXCEPTION_10010);
			pointConvertEb.setResultMsg("冲账积分大于转入积分,数据异常");
			pointConvertEb.setBodyMap(new HashMap<String, Object>());
			return pointConvertEb;
		}
		
		//如果退还积分大于兑换积分,则抛出错误码
		if(points.intValue() + outPoints.intValue()>inPoints.intValue()){
			pointConvertEb.setResultCode(ErrorConstant.Point.ERROR_POINT_CRM_POINTS_NOT_ENOUGH_20012);
			pointConvertEb.setResultMsg("退还积分不能大于兑换积分");
			pointConvertEb.setBodyMap(new HashMap<String, Object>());
			return pointConvertEb;
		}
		return pointConvertEb;
	}

	//操作集团
	private EngineBean operateCrm(EngineBean pb, String type, EngineBean pointConvertEb) throws SystemException,
			ExceptionAbstract, IllegalParamException {
		log.info("operateCrm begin()...");
		//如果本地兑换成功,判断是否为集团用户
		if(SysBook.SUCCESS.equals(pointConvertEb.getResultCode())){
			pb.setCommand("isCrmMbr");
			EngineBean mbrEb = ServerCall.call(pb);
			if (SysBook.SUCCESS.equals(mbrEb.getResultCode())) {//如果是集团
				//先获得集团原始积分
				pb.setCommand("queryEnabledPoint");
				PointService pointService = beanFactory.getBean("PointService", PointService.class);
				EngineBean eb = pointService.queryEnabledPoint(pb);
				if (!SysBook.SUCCESS.equals(eb.getResultCode())) {
					return eb;
				}
				// 取出可用余额 用于确认是否退还积分成功
				Long enablePoints = Long.parseLong(String.valueOf(eb.getBody("pointTotal")));
				log.info("集团原始积分: " + enablePoints);
				//设置交易时间
				pb.setHead("transactionDate", DateFormatUtil.getDateFormat().format(new Date()));
				pb.setHead("oldCrmEnablePoints", enablePoints);
				setDefaultParamReqCrm(pb);//设置请求crm增加积分接口
				/*if(!RightUtils.hasRight2Call()){//判断是否有权限调用集团
					return ErrorUtils.error(pb, ErrorConstant.ERROR_UNSUPPORTED_OPERATION_10007, "测试环境没有权限访问集团");
				}*/
				
				//根据type 判断是芒果网加积分还是扣积分
				if("IN".equals(type)){
					pb.setCommand("increaseCrmPoint");
					mbrEb = ServerCall.call(pb);
					log.info("increaseCrmPoint...result()...resultEngineBean: " + mbrEb);
				}else if("OUT".equals(type)){
					pb.setCommand("cutCrmPoint");
					mbrEb = ServerCall.call(pb);
					log.info("cutCrmPoint...result()...resultEngineBean: " + mbrEb);
				}
				if(!SysBook.SUCCESS.equals(mbrEb.getResultCode())){//如果集团操作失败,则直接返回错误
					return mbrEb;
				}
				//修改积分兑换交易状态
				return updatePointConvertTransStus(pb, pointConvertEb);
			}else{//如果不是集团,则直接返回
				//修改积分兑换交易状态
				return updatePointConvertTransStus(pb, pointConvertEb);
			}
		}
		return pointConvertEb;
	}

	//修改积分兑换交易状态
	private EngineBean updatePointConvertTransStus(EngineBean pb, EngineBean pointConvertEb) {
		//如果集团操作成功,则开始修改交易状态
		log.info("集团操作完成,开始修改积分兑换交易状态...");
		pb.setCommand("updatePointConvertTranStusByPointConvertId");
		pb.setHead("tranStus", "P");//设置成交易完成
		pb.setHead("pointConvertId", pointConvertEb.getBody("pointConvertId"));//获取积分兑换id
		EngineBean tEb = ServerCall.call(pb);
		if (!SysBook.SUCCESS.equals(tEb.getResultCode())) {
			return tEb;
		}
		return pointConvertEb;
	}

	private void setDefaultParamReqCrm(EngineBean pb) {
		pb.setHead("adjustType", ConstantArgs.POINT_INT_ADJUSTTYPE);
		pb.setHead("transactionType", ConstantArgs.LOY_TXN_TYPE_CD_ACCRUAL);
		pb.setHead("transType", ConstantArgs.POINT_TRANS_TYPE_CONVERT);//I: 积分操作类型_兑换
		pb.setHead("transDateTime", new Date());
		pb.setHead("transStatus","P");
		pb.setHead("transactionSubType", ConstantArgs.TRANSACTION_SUB_TYPE);//交易子类型
		pb.setHead("transactionChannel", ConstantArgs.WEB_TRANSACTION_CHANNEL);//交易渠道
		pb.setHead("cTSTransactionChannelSN", ConstantArgs.WEB_CTSTRANSACTION_CHANNELSN);//交易渠道序列号
		pb.setHead("partNumber", ConstantArgs.WEB_PART_NUMBER_OUT);
		pb.setHead("cTSTransactionOrgCode", ConstantArgs.WEB_CTSTRANSACTION_ORGCODE_MARKET);
		pb.setHead("ctsPointSubType", ConstantArgs.CTS_CONVERT_POINT_SUB_TYPE);//互换积分
	}

	/**
	 * 芒果网订单查询接口(根据万里通订单号查询)
	 * {
	 * 	"selCode":"wlt订单",
	 *  "authorizeId":"",
	 *  "type":"IN-OUT"
	 * }
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	@SuppressWarnings({ "unchecked", "null" })
	public EngineBean partnersOrderQuery4Test(EngineBean pb) throws ExceptionAbstract {
		log.info("PointExchangeInService partnersOrderQuery4Test begin()...appId: " + pb.getAppId() + " ,params: "
				+ pb.getHeadMap());
		//type不填默认查所有交易
		String type = String.valueOf(pb.getHead("type"));
		if(CommonUtils.isNotBlankExcludeNull(type)){
			if("IN".equals(type)){
				pb.setHead("inOutStatus", "0");
			}else if("OUT".equals(type)){
				pb.setHead("inOutStatus", "1");
			}else{
				return ErrorUtils.error(pb, ErrorConstant.ERROR_PARAM_INVALID_10008, "type只能是IN、OUT");
			}
		}
		
		//step1: 检查平安万里通的充值流水是否存在
		log.info("queryPointAuthorizeByAuthorizeId....begin");
		pb.setCommand("queryPointAuthorizeByAuthorizeId");
		EngineBean pointAuthEb = ServerCall.call(pb);
		log.info("pointAuthEb: " + pointAuthEb);
		if(!SysBook.SUCCESS.equals(pointAuthEb.getResultCode())){
			return  pointAuthEb;
		}
		
		log.info("isExistWltOrder...begin");
		pb.setCommand("isExistWltOrder");
		PointConvertBindService pointConvertBindService = beanFactory.getBean("PointConvertBindService", PointConvertBindService.class);
		EngineBean pointConvertEb = pointConvertBindService.isExistWltOrder(pb);
		if(!SysBook.SUCCESS.equals(pointConvertEb.getResultCode())){//不存在,则报错
			return pointConvertEb;
		}else{//step2: 否则根据wlt订单查询订单流水
			pb.setCommand("queryPointConvertOrderByWltOrder");
			pb.setHead("mbrId", pointAuthEb.getBody("MBR_ID"));
			pointConvertEb = pointConvertBindService.queryPointConvertOrderByWltOrder(pb);
			//因为换了绑定表,现在是与mbrId绑定的
			if(!SysBook.SUCCESS.equals(pointConvertEb.getResultCode())){//如果查询不到流水,则直接返回错误码
				return pointConvertEb;
			}else{
				//step3: 查询订单流水成功,则根据返回数据中的mbrId获取会籍编码
				Object obj = pointConvertEb.getBodyMap().get("result");
				Map<String,Object> pointConvertMap = null;
				List<Map<String,Object>> pointConvertList = New.list();
				if(obj instanceof Map){
					pointConvertMap = transferData((Map<String,Object>)obj);
					pointConvertEb.setBody("result", pointConvertMap);
				}else if(obj instanceof List){
					for(Map<String,Object> tMap : (List<Map<String,Object>>)obj){
						pointConvertMap = transferData(tMap);
						pointConvertList.add(pointConvertMap);
					}
					pointConvertEb.setBody("result", pointConvertList);
				}
				return pointConvertEb;
			}
		}
	}
	
	private Map<String,Object> transferData(Map<String, Object> tMap) {
		Map<String,Object> resultMap = New.map();
		resultMap.put("mbrId", tMap.get("MBR_ID"));
		resultMap.put("selCode", tMap.get("CELLULARQUERYREQUERTID"));
		resultMap.put("createTime", tMap.get("CREATE_TIM"));
		resultMap.put("mangoPoint", tMap.get("MANGOPOINT"));
		resultMap.put("state", CommonUtils.isEmpty(tMap.get("POINT_TRANSACTION_ID"))?"0":"1");//如果积分流水id为Null,则设置state为0,否则为1
		resultMap.put("tranStus", tMap.get("tranStus"));
		return resultMap;
	}

	/**
	 * 积分兑换服务
	 * @param pb
	 * @return
	 * @throws ExceptionAbstract
	 */
	public EngineBean convertPointByRefund(EngineBean pb) throws ExceptionAbstract {
		log.info("PointExchangeInService convertPointByRefund begin()...appId: " + pb.getAppId() + " ,params: "
				+ pb.getHeadMap()); 
		EngineBean resultEngineBean = ServerCall.call(pb);
		log.info("resultEngineBean: " + resultEngineBean);
		return resultEngineBean;
	}
}
