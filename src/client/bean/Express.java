package client.bean;

/**
 * 快递基本单元类
 *
 * @author yuzha
 */
public class Express {
	private String expressId;
	private String expressCom;
	private int expressCode;
	private int expressPos;

	public String getExpressCom() {
		return expressCom;
	}


	public String getExpressId() {
		return expressId;
	}

	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}

	public void setExpressCom(String expressCom) {
		this.expressCom = expressCom;
	}

	@Override
	public String toString() {
		return "快递单号='" + expressId + '\'' +
				", 快递公司='" + expressCom + '\'' +
				", 取件码=" + expressCode +
				", 快递柜编号=" + expressPos;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Express express = (Express) o;
		return expressCode == express.expressCode;
	}
}
