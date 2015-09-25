package com.whcard.bean;

public class DiscountBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8112752946703561967L;
	private String discount_info; //һ�仰����
	private String discount_address;
	private String discount_img;
	private String co_name;
	private String co_tel;
	public DiscountBean(String discount_info, String discount_address,
			String discount_img, String co_name, String co_tel) {
		super();
		this.discount_info = discount_info;
		this.discount_address = discount_address;
		this.discount_img = discount_img;
		this.co_name = co_name;
		this.co_tel = co_tel;
	}
	public DiscountBean(){
		
	};
	public String getDiscount_info() {
		return discount_info;
	}
	public void setDiscount_info(String discount_info) {
		this.discount_info = discount_info;
	}
	public String getDiscount_address() {
		return discount_address;
	}
	public void setDiscount_address(String discount_address) {
		this.discount_address = discount_address;
	}
	public String getDiscount_img() {
		return discount_img;
	}
	public void setDiscount_img(String discount_img) {
		this.discount_img = discount_img;
	}
	public String getCo_name() {
		return co_name;
	}
	public void setCo_name(String co_name) {
		this.co_name = co_name;
	}
	public String getCo_tel() {
		return co_tel;
	}
	public void setCo_tel(String co_tel) {
		this.co_tel = co_tel;
	}
	
}
