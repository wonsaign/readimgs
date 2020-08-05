package com.jiangjiang.readimgs.constant;

/**
 * @author wangsai
 */

public interface ConstantEnum {

    /**
     * 单据类型
     */
    enum InvoiceType{

        PAY(1, "付款"),
        RECEIVER(2, "收款");

        private Integer code;
        private String comment;
        private InvoiceType(Integer code, String comment) {
            this.code = code;
            this.comment = comment;
        }
        public Integer getCode() {
            return code;
        }
        public String getComment() {
            return comment;
        }

    }
}
