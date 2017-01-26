package jp.lambdamagic.json.data;

import java.io.IOException;

import jp.lambdamagic.json.JSONDataVisitor;

public interface JSONData {
	void accept(JSONDataVisitor visitor) throws IOException;
}
