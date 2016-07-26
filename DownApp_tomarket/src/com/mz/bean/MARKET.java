package com.mz.bean;

public enum MARKET {
	MARKET_BAIDU(0), MARKET_360(1), MARKET_YYB(2), MARKET_WDJ(3), MARKET_PP(4), MARKET_AZ(
			5), MARKET_MU(6), MARKET_GP(7);
	private int value = 0;

	MARKET(int value) {
		this.value = value;
	}

	public int value() {
		return this.value;
	}

	public static MARKET valueOf(int value) { // 手写的从int到enum的转换函数
		switch (value) {
		case 0:
			return MARKET_BAIDU;
		case 1:
			return MARKET_360;
		case 2:
			return MARKET_YYB;
		case 3:
			return MARKET_WDJ;
		case 4:
			return MARKET_PP;
		case 5:
			return MARKET_AZ;
		case 6:
			return MARKET_MU;
		case 7:
			return MARKET_GP;
		default:
			return null;
		}
	}
	
}
