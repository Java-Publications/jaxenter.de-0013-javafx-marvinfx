package com.guigarage.marvfx.property.rules.future;

import com.guigarage.marvfx.property.rules.RuleFail;

public class WillChangeByDefinedCountRule<U> extends
		AbstractPropertyFutureRule<U> {

	private int count;
	
	private int currentCount;
	
	public WillChangeByDefinedCountRule(int count) {
		this.count = count;
	}
	
	@Override
	protected void check(U oldValue, U newValue) {
		currentCount++;
		if(currentCount > count) {
			fail();
		}
	}
	
	@Override
	public RuleFail checkConfirmation() {
		if(count != currentCount) {
			return new RuleFail();
		}
		return super.checkConfirmation();
	}
}
