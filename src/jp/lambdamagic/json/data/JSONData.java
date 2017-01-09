package jp.lambdamagic.json.data;

import jp.lambdamagic.json.JSONDataVisitor;

public interface JSONData {
	void accept(JSONDataVisitor visitor) throws Exception;
}
