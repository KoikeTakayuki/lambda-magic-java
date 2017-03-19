package jp.lambdamagic.json;

import java.io.IOException;

import jp.lambdamagic.json.data.JSONArray;
import jp.lambdamagic.json.data.JSONBoolean;
import jp.lambdamagic.json.data.JSONNull;
import jp.lambdamagic.json.data.JSONNumber;
import jp.lambdamagic.json.data.JSONObject;
import jp.lambdamagic.json.data.JSONString;

public interface JSONDataVisitor {
    void visit(JSONObject object) throws IOException;
    void visit(JSONArray array) throws IOException;
    void visit(JSONNumber number) throws IOException;
    void visit(JSONString string) throws IOException;
    void visit(JSONBoolean bool) throws IOException;
    void visit(JSONNull empty) throws IOException;
}
