package jp.lambdamagic.sql.mapping;

import java.util.ArrayList;
import java.util.List;

public class SQLTableMappingStub implements SQLTableMapping {
    
    private List<String> columnNames;
    
    public SQLTableMappingStub() {
        this.columnNames = new ArrayList<>();
        columnNames.add("columnName");
    }

    @Override
    public List<String> getColumnNames() {
        return columnNames;
    }

    @Override
    public String getColumnName(String id) {
        return "columnName";
    }

    @Override
    public String getDeclarationTableName() {
        return "declarationTableName";
    }

    @Override
    public String getTableName() {
        return "tableName";
    }

}
