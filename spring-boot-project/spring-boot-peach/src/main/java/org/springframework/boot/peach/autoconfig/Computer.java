package org.springframework.boot.peach.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.peach.aop.Log;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
@ConfigurationProperties(prefix = "computer")
public class Computer {
	// 由application.properties文件的computer前缀的配置注入以下字段

	private String brand;
	private String type;
	private double price;

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	// 使用注解Log记录方法执行 耗时
	@Log(level = "TRACE")
	public String buy() {
		return "Buy a computer.";
	}

	@PostConstruct
	void afterConstruct() {
		System.out.println("Computer created.");
	}

	@Override
	public String toString() {
		return "Computer{" +
				"brand='" + brand + '\'' +
				", type='" + type + '\'' +
				", price=" + price +
				'}';
	}
}
