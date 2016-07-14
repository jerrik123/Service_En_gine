package com.mangocity.mbr.test;

import com.mangocity.mbr.util.SafeUtil;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] strs = { "isBindedMango", "mbrRegister",
				"queryMbrShipListByMbrid", "resetPassword", "sendSms",
				"thirdpartyBinding", "validateSms",
				"validateUniqueMbrByLoginName", "queryEnabledPoint",
				"queryPointTransaction", "queryAllCashAccountTrans",
				"queryCashAccountBalance", "queryNotUsedVoucher",
				"queryUsedVoucher", "queryVoucherBalanceByCd","updateMobileNo","cutPoint","queryMobileNoByMbrId","queryMobileNoByLoginName","addAwardPoints","login","adminLogin" };
		String[] md5str = null;
		for (int i = 0; i < strs.length; i++) {
			System.out.println(strs[i]+"==========="+SafeUtil.MD5(SafeUtil
					.MD5("wx17688f0881ac9513" + strs[i])
					+ "9aa273cf91a929ee6b59e1c584e88a98"));
		}
	}
}
