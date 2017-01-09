package jp.lambdamagic.json.data;

import jp.lambdamagic.json.JSONDataVisitor;

public class JSONNull implements JSONData {
	
	@Override
	public void accept(JSONDataVisitor visitor) throws Exception {
		visitor.visit(this);
	}
	
}