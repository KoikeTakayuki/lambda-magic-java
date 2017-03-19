package jp.lambdamagic.web;

import jp.lambdamagic.NullArgumentException;
import jp.lambdamagic.text.Strings;

public final class MimeTypes {
    
    public static final String TEXT_PLAIN = "text/plain; charset=utf-8";
    public static final String TEXT_HTML = "text/html; charset=utf-8";
    public static final String TEXT_CSV = "text/csv; charset=utf-8";
    public static final String APPLICATION_JAVASCRIPT = "application/json; charset=utf-8";
    public static final String APPLICATION_PDF = "application/pdf";
    public static final String APPLICATION_EXCEL = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String APPLICATION_WORD = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    public static final String APPLICATION_POWERPOINT = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    public static final String IMAGE_JPEG = "image/jpeg";
    public static final String IMAGE_PNG = "image/png";
    
    public static boolean isImage(String mimeType) {
        if (mimeType == null) {
            throw new NullArgumentException("mimeType");
        }
        
        return mimeType.startsWith("image");
    }
    
    public static String getFileExtension(String mimeType) {
        if (mimeType == null) {
            throw new NullArgumentException("mimeType");
        }
        
        int startIndex = mimeType.indexOf('/');
        if (startIndex == -1) {
            return Strings.EMPTY_STRING;
        }
            
        int endIndex = mimeType.indexOf(';');
        if (endIndex == -1) {
            endIndex = mimeType.length();
        }
        
        return mimeType.substring(startIndex + 1, endIndex);
    }
    
    public static String guessFromFileExtension(String extension) {
        if (extension == null) {
            throw new NullArgumentException("extension");
        }
        
        if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")) {
            return IMAGE_JPEG;
        }
        
        if (extension.equalsIgnoreCase("png")) {
            return IMAGE_PNG;
        }
        
        if (extension.equalsIgnoreCase("pdf")) {
            return APPLICATION_PDF;
        }
        
        if (extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx")) {
            return APPLICATION_EXCEL;
        }
        
        if (extension.equalsIgnoreCase("doc") || extension.equalsIgnoreCase("docx")) {
            return APPLICATION_WORD;
        }
        
        if (extension.equalsIgnoreCase("ppt") || extension.equalsIgnoreCase("pptx")) {
            return APPLICATION_POWERPOINT;
        }
        
        return null;
    }
    
    private MimeTypes() {}
    
}
