package org.springframework.boot.peach.message.enums;

import java.nio.charset.Charset;

public enum MessageCharset {
    ASCII("ASCII"),
    GB2312("GB2312"),
    UTF8("UTF-8");
    private Charset charset;

    MessageCharset(String charsetName) {
        this.charset = Charset.forName(charsetName);
    }

    public Charset getCharset(){
        return charset;
    }
}
