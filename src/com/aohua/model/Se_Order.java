package com.aohua.model;

//销售订单
public class Se_Order {	
	private Integer OrderID;
	private String OrderCode; //单号
	private Integer CustID; //客户ID
	//将CustID的id对应到custName，里面的CustName不是出自本张表
	private String CustName; //客户
	private Integer ClauseID; //条款ID
	private String ContractCode; //合同号
	private Integer SellerID; //业务员ID
	//获取业务员name
	private String SellerName;
	private Double SellerWDPrice; //纬密提成价
	private String SignAddr; //签订地址
	private String SignDate; //签订日期
	private String DeliveryAddr; //交货地址
	private String DeliveryDate; //交货日期
	private Double Freight; //运输费用
	private Integer TransportID; //运输方式
	//获取运输方式名称
	private String TransportName;
	private Integer SettleID;//结算方式
	//获取结算方式name
	private String SettleName;
	private String ReceDate; //限收款期
	private Integer ReceDays;//限收款天数
	private Integer DeptID;   //
	//获取部门名字
	private String DeptName;
	private Integer WritePersonID; //制单人ID
	private String WriteDate;   //制单日期
	private String Notes;
	private Integer State;
	private Integer ExeState;
	public Integer getOrderID() {
		return OrderID;
	}
	public void setOrderID(Integer orderID) {
		OrderID = orderID;
	}
	public String getOrderCode() {
		return OrderCode;
	}
	public void setOrderCode(String orderCode) {
		OrderCode = orderCode;
	}
	public Integer getCustID() {
		return CustID;
	}
	public void setCustID(Integer custID) {
		CustID = custID;
	}
	public Integer getClauseID() {
		return ClauseID;
	}
	public void setClauseID(Integer clauseID) {
		ClauseID = clauseID;
	}
	public String getContractCode() {
		return ContractCode;
	}
	public void setContractCode(String contractCode) {
		ContractCode = contractCode;
	}
	public Integer getSellerID() {
		return SellerID;
	}
	public void setSellerID(Integer sellerID) {
		SellerID = sellerID;
	}
	public Double getSellerWDPrice() {
		return SellerWDPrice;
	}
	public void setSellerWDPrice(Double sellerWDPrice) {
		SellerWDPrice = sellerWDPrice;
	}
	public String getSignAddr() {
		return SignAddr;
	}
	public void setSignAddr(String signAddr) {
		SignAddr = signAddr;
	}
	public String getSignDate() {
		return SignDate;
	}
	public void setSignDate(String signDate) {
		SignDate = signDate;
	}
	public String getDeliveryAddr() {
		return DeliveryAddr;
	}
	public void setDeliveryAddr(String deliveryAddr) {
		DeliveryAddr = deliveryAddr;
	}
	public String getDeliveryDate() {
		return DeliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		DeliveryDate = deliveryDate;
	}
	public Double getFreight() {
		return Freight;
	}
	public void setFreight(Double freight) {
		Freight = freight;
	}
	public Integer getTransportID() {
		return TransportID;
	}
	public void setTransportID(Integer transportID) {
		TransportID = transportID;
	}
	public Integer getSettleID() {
		return SettleID;
	}
	public void setSettleID(Integer settleID) {
		SettleID = settleID;
	}
	public String getReceDate() {
		return ReceDate;
	}
	public void setReceDate(String receDate) {
		ReceDate = receDate;
	}
	public Integer getReceDays() {
		return ReceDays;
	}
	public void setReceDays(Integer receDays) {
		ReceDays = receDays;
	}
	public Integer getDeptID() {
		return DeptID;
	}
	public void setDeptID(Integer deptID) {
		DeptID = deptID;
	}
	public Integer getWritePersonID() {
		return WritePersonID;
	}
	public void setWritePersonID(Integer writePersonID) {
		WritePersonID = writePersonID;
	}
	public String getWriteDate() {
		return WriteDate;
	}
	public void setWriteDate(String writeDate) {
		WriteDate = writeDate;
	}
	public String getNotes() {
		return Notes;
	}
	public void setNotes(String notes) {
		Notes = notes;
	}
	public Integer getState() {
		return State;
	}
	public void setState(Integer state) {
		State = state;
	}
	public Integer getExeState() {
		return ExeState;
	}
	public void setExeState(Integer exeState) {
		ExeState = exeState;
	}
	public String getCustName() {
		return CustName;
	}
	public void setCustName(String custName) {
		CustName = custName;
	}
	public String getTransportName() {
		return TransportName;
	}
	public void setTransportName(String transportName) {
		TransportName = transportName;
	}
	public String getSettleName() {
		return SettleName;
	}
	public void setSettleName(String settleName) {
		SettleName = settleName;
	}
	public String getDeptName() {
		return DeptName;
	}
	public void setDeptName(String deptName) {
		DeptName = deptName;
	}
	public String getSellerName() {
		return SellerName;
	}
	public void setSellerName(String sellerName) {
		SellerName = sellerName;
	}
}
