package com.shallowan.seckill.exception;

import com.shallowan.seckill.result.CodeMsg;

/**
 * @author ShallowAn
 */
public class GlobalException extends RuntimeException {

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
