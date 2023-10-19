package com.spring.model;

import org.springframework.stereotype.Component;

@Component
public class CalculatorDetails {
	private int firstOperandValue;
	private int secondOperandValue;
	private String operator;
	private int result;
	
	public CalculatorDetails() {}
	
	public CalculatorDetails(int firstOperandValue,int secondOperandValue,String operator,int result) {
		this.firstOperandValue=firstOperandValue;
		this.secondOperandValue=secondOperandValue;
		this.operator=operator;
		this.result=result;
	}
	public int getFirstOperandValue() {
		return firstOperandValue;
	}
	public int getSecondOperandValue() {
		return secondOperandValue;
	}
	public String getOperator() {
		return operator;
	}
	public int getResult() {
		return result;
	}
	public void setFirstOperandValue(int firstOperandValue) {
		this.firstOperandValue=firstOperandValue;
	}
	public void setSecondOperandValue(int secondOperandValue) {
		this.secondOperandValue=secondOperandValue;
	}
	public void setOperator(String operator) {
		this.operator=operator;
	}
	public void setResult(int result) {
		this.result=result;
	}
}