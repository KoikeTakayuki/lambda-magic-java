package jp.lambdamagic.sql.mapping;

import java.util.List;

public interface SQLColumnSetMapping {
  List<String> getColumnNames();
  String getColumnName(String id);
}
