package server;

/**
 * 快递基本单元类
 *
 * @author yuzha
 */
public class Express {
	private final String expressId;
	private final String expressCom;
	private int expressCode;
	private int expressPos;

	public Express(String expressId, String expressCom) {
		this.expressId = expressId;
		this.expressCom = expressCom;
	}

	public Express(String expressId, String expressCom, int expressCode, int expressPos) {
		this.expressId = expressId;
		this.expressCom = expressCom;
		this.expressCode = expressCode;
		this.expressPos = expressPos;
	}

	public int getExpressPos() {
		return expressPos;
	}

	public void setExpressPos(int expressPos) {
		this.expressPos = expressPos;
	}

	public int getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(int expressCode) {
		this.expressCode = expressCode;
	}

	public String getExpressId() {
		return expressId;
	}

	public String fileString() {
		return "#" + expressId + "#$" + expressCom + "$%" + expressCode + "%!" + expressPos + "!";
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
