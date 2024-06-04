package com.mazihao.market.cart_order.common;

import org.springframework.beans.factory.annotation.Value;

public class QRCodeConstant {
    public static String FILE_UPLOAD_DIR;

    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }

}
