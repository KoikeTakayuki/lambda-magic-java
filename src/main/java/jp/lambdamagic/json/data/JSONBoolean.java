package jp.lambdamagic.json.data;

import java.io.IOException;

import jp.lambdamagic.json.JSONDataVisitor;

public class JSONBoolean implements JSONData {

	private boolean value;
	
	public JSONBoolean(boolean value) {
		this.value = value;
	}
	
	public boolean getValue() {
		return value;
	}
	
	@Override
	public void accept(JSONDataVisitor visitor) throws IOException {
		visitor.visit(this);
	}
	
}
