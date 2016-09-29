package com.example.andtest.model.business;

import java.util.List;

/**
 * @Description: TODO 
 * @Package com.example.andtest.model.business TestModel.java
 * @author gf
 * @date 2016-9-14 下午3:30:05
 */
public class TestModel {
	private boolean success;
	private String status;
	private List<ItemBean> ConditionsList;
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ItemBean> getConditionsList() {
		return ConditionsList;
	}

	public void setConditionsList(List<ItemBean> conditionsList) {
		ConditionsList = conditionsList;
	}

	public static class ItemBean{
		private ItemChild1 CarConditionsModel;
		private List<ItemChild2> OldCarlist;
		public ItemChild1 getCarConditionsModel() {
			return CarConditionsModel;
		}
		public void setCarConditionsModel(ItemChild1 carConditionsModel) {
			CarConditionsModel = carConditionsModel;
		}
		public List<ItemChild2> getOldCarlist() {
			return OldCarlist;
		}
		public void setOldCarlist(List<ItemChild2> oldCarlist) {
			OldCarlist = oldCarlist;
		}
		
	}
	
	public static class ItemChild1{
		private String Id;

		public String getId() {
			return Id;
		}

		public void setId(String id) {
			Id = id;
		}
		
	}
	
	public static class ItemChild2{
		private String CarSourceID;

		public String getCarSourceID() {
			return CarSourceID;
		}

		public void setCarSourceID(String carSourceID) {
			CarSourceID = carSourceID;
		}
		
	}
}
