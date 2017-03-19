package jp.lambdamagic.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import jp.lambdamagic.NullArgumentException;

public class MimeTypesTest {
    
    @Test(expected=NullArgumentException.class)
    public void isImage_mustThrowNullArgumentExceptionWhenNullMimeTypeIsGiven() {
        MimeTypes.isImage(null);
    }
    
    @Test
    public void isImage_returnFalseWhenNonImageMimeTypeIsGiven() {
        boolean result = MimeTypes.isImage("application/pdf");
        assertThat(result, is(false));
    }
    
    @Test
    public void isImage_returnTreuWhenImageMimeTypeIsGiven() {
        boolean result = MimeTypes.isImage("image/png");
        assertThat(result, is(true));
    }

    @Test(expected=NullArgumentException.class)
    public void getFileExtension_mustThrowNullArgumentExceptionWhenNullMimeTypeIsGiven() {
        MimeTypes.getFileExtension(null);
    }
    
    @Test
    public void getFileExtension_returnFileExtensionFromMimeType() {
        String result = MimeTypes.getFileExtension("image/png");
        assertThat(result, is("png"));
    }
    
    @Test(expected=NullArgumentException.class)
    public void guessFromFileExtension_mustThrowNullArgumentExceptionWhenNullMimeTypeIsGiven() {
        MimeTypes.guessFromFileExtension(null);
    }
    
    @Test
    public void guessFromFileExtension_guessMimeTypeFromFileExtension() {
        String result = MimeTypes.guessFromFileExtension("jpeg");
        assertThat(result, is("image/jpeg"));
    }

}
