package com.yf.ability.task.enums;

/**
 * 任务前缀
 * @author bool
 */
public interface JobPrefix {

    /**
     * 强制交卷的
     */
    String BREAK_EXAM = "break_exam_";

    /**
     * 颁发证书
     */
    String GRANT_CERT = "grant_cert_";

    /**
     * 加入错题本
     */
    String ADD_BOOK = "add_book_";

    /**
     * 读取视频时长
     */
    String READ_DURATION = "read_duration_";
}
