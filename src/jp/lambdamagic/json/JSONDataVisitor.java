package jp.lambdamagic.json;

import jp.lambdamagic.json.data.JSONArray;
import jp.lambdamagic.json.data.JSONBoolean;
import jp.lambdamagic.json.data.JSONNull;
import jp.lambdamagic.json.data.JSONNumber;
import jp.lambdamagic.json.data.JSONObject;
import jp.lambdamagic.json.data.JSONString;

public interface JSONDataVisitor {
	void visit(JSONObject object) throws Exception;
	void visit(JSONArray array) throws Exception;
	void visit(JSONNumber number) throws Exception;
	void visit(JSONString string) throws Exception;
	void visit(JSONBoolean bool) throws Exception;
	void visit(JSONNull empty) throws Exception;
}
