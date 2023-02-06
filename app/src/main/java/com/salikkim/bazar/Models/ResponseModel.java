package com.salikkim.bazar.Models;

import java.io.IOException;
import java.io.InputStream;

public class ResponseModel extends InputStream {
    private String message;
    private Boolean status;

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}
