package jp.lambdamagic.json;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map.Entry;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.json.data.JSONArray;
import jp.lambdamagic.json.data.JSONBoolean;
import jp.lambdamagic.json.data.JSONData;
import jp.lambdamagic.json.data.JSONNull;
import jp.lambdamagic.json.data.JSONNumber;
import jp.lambdamagic.json.data.JSONObject;
import jp.lambdamagic.json.data.JSONString;
import jp.lambdamagic.pipeline.DataWriter;
import jp.lambdamagic.text.Encodings;

public class JSONWriter implements DataWriter<JSONData>, JSONDataVisitor {

    private Writer writer;
    private boolean asArray;
    private boolean isFirstWriting;
    
    public JSONWriter(Writer writer) throws IOException {
        if (writer == null) {
            throw new NullArgumentException("writer");
        }
        
        this.writer = writer;
        this.isFirstWriting = true;
    }

    public JSONWriter(String filePath, String encoding) throws IOException {
        if (filePath == null) {
            throw new NullArgumentException("filePath");
        }
        
        if (encoding == null) {
            throw new NullArgumentException("encoding");
        }
        
        this.writer = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(new File(filePath)), encoding));
        this.isFirstWriting = true;
    }

    public JSONWriter(String filePath) throws IOException {
        this(filePath, Encodings.UTF_8);
    }
    
    public void writeAsArray() throws IOException {
        if (asArray) {
            throw new IllegalStateException("JSONWriter writeAsArray property has already been true");
        }
        
        writer.write(JSONParser.JSON_ARRAY_START_CHAR);
        asArray = true;
    }
    
    @Override
    public void write(JSONData data) throws IOException {
        if (isFirstWriting) {
            isFirstWriting = false;
        } else {
            
            if (asArray) {
                writer.write(JSONParser.JSON_ARRAY_VALUE_SEPARATOR_CHAR);
            }
        }
        
        data.accept(this);
        writer.flush();
    }

    @Override
    public void visit(JSONObject object) throws IOException {
        writer.write(JSONParser.JSON_OBJECT_START_CHAR);
        Iterator<Entry<String, JSONData>> it = object.entrySet().iterator();
        
        if (it.hasNext()) {
            
            Entry<String, JSONData> entry = it.next();
            writeString(entry.getKey());
            writer.write(JSONParser.JSON_OBJECT_KEY_VALUE_DELIMITER_CHAR);
            entry.getValue().accept(this);
            
            while (it.hasNext()) {
                writer.write(JSONParser.JSON_OBJECT_SEPARATOR_CHAR);
                entry = it.next();
                writeString(entry.getKey());
                writer.write(JSONParser.JSON_OBJECT_KEY_VALUE_DELIMITER_CHAR);
                entry.getValue().accept(this);
            }
        }
        
        writer.write(JSONParser.JSON_OBJECT_END_CHAR);
    }

    @Override
    public void visit(JSONArray array) throws IOException {
        writer.write(JSONParser.JSON_ARRAY_START_CHAR);
        
        Iterator<JSONData> it = array.iterator();
        
        if (it.hasNext()) {
            JSONData data = it.next();
            data.accept(this);
            
            while (it.hasNext()) {
                writer.write(JSONParser.JSON_ARRAY_VALUE_SEPARATOR_CHAR);
                data = it.next();
                data.accept(this);
            }
        }
        
        writer.write(JSONParser.JSON_ARRAY_END_CHAR);
    }

    @Override
    public void visit(JSONNumber number) throws IOException {
        writer.write(number.getValue().toString());
    }

    @Override
    public void visit(JSONString string) throws IOException {
        writeString(string.getValue());
    }

    @Override
    public void visit(JSONBoolean bool) throws IOException {
        writer.write(bool.getValue() ? JSONParser.JSON_TRUE_STRING : JSONParser.JSON_FALSE_STRING);
    }

    @Override
    public void visit(JSONNull empty) throws IOException {
        writer.write(JSONParser.JSON_NULL_STRING);
    }
    
    @Override
    public void close() throws IOException {
        if (asArray) {
            writer.write(JSONParser.JSON_ARRAY_END_CHAR);
        }
        
        writer.flush();
        writer.close();
    }
    
    private void writeString(String string) throws IOException {
        writer.write(JSONParser.JSON_STRING_DELIMITER_CHAR);
        writer.write(string.replace("\r", "\\r").replace("\n", "\\n").replace("\t", "\t"));
        writer.write(JSONParser.JSON_STRING_DELIMITER_CHAR);
    }

}