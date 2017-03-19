package jp.lambdamagic.text;

import java.util.Iterator;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.collection.iterator.Iterables;

public final class Strings {
  
  public static String EMPTY_STRING = "";

  public static boolean isNullOrEmpty(String s) {
    return ((s == null) || s.isEmpty());
  }
  
  public static String join(String separator, Iterable<String> strings) {
    if (separator == null) {
      throw new NullArgumentException("separator");
    }
    
    if (strings == null) {
      throw new NullArgumentException("strings");
    }
    
    Iterator<String> it = strings.iterator();

    if (!it.hasNext()) {
      return EMPTY_STRING;
    }
    
    StringBuilder sb = new StringBuilder();
    sb.append(it.next());
    
    while (it.hasNext()) {
      sb.append(separator);
      sb.append(it.next());
    }
    return sb.toString();
  }
  
  public static String join(String separator, String... strings) {
    return join(separator, Iterables.asIterable(strings));
  }
  
  public static String concat(Iterable<String> strings) {
    if (strings == null) {
      throw new NullArgumentException("strings");
    }
    
    StringBuffer sb = new StringBuffer();

    for (String s : strings)
      sb.append(s);
    
    return sb.toString();
  }
  
  public static String concat(String... strings) {
    return concat(Iterables.asIterable(strings));
  }
  
  public static String crop(String targetString, String startString, String endString) {
    if (targetString == null) {
      throw new NullArgumentException("targetString");
    }
    
    if (startString == null) {
      throw new NullArgumentException("startString");
    }
    
    if (endString == null) {
      throw new NullArgumentException("endString");
    }

    int startIndex = targetString.indexOf(startString) + startString.length();
    int endIndex = targetString.indexOf(endString, startIndex);

    if (startIndex == -1 || endIndex == -1) {
      return EMPTY_STRING;
    }

    return targetString.substring(startIndex, endIndex);
  }
  
  private Strings() {}
  
}
