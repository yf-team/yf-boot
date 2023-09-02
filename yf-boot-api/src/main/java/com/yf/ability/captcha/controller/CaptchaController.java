package com.yf.ability.captcha.controller;

import com.yf.ability.captcha.service.CaptchaService;
import com.yf.base.api.api.controller.BaseController;
import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


/**
 * <p>
 * 图形验证码生成
 * </p>
 *
 * @author 聪明笨狗
 *
 * @since 2019-04-16 10:14
 */
@Api(tags = {"验证码生成类"})
@RestController
@RequestMapping("/api/common/captcha")
public class CaptchaController extends BaseController {

    @Autowired
    private CaptchaService captchaService;


    @RequestMapping(value="/gen", method = RequestMethod.GET)
    @ApiOperation(value = "验证码图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "验证码ID,前端自行使用UUID生成，务必确保整个系统不重复",
                    dataType = "string",
                    paramType = "query",
                    example = "873d327b-00a1-441c-8509-fd51233654dc",
                    required = true)
    })
    public void captcha(HttpServletResponse response, @RequestParam("key") String key) throws Exception {

        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 算术类型
        SpecCaptcha captcha = new SpecCaptcha(130, 48);
        // 几位数运算，默认是两位
        captcha.setLen(4);

        // 输出图片流
        ServletOutputStream os = null;
        try {
            os = response.getOutputStream();
            captcha.out(os);
        }finally {
            os.close();
        }

        // 存入REDIS
        captchaService.saveCaptcha(key, captcha.text().toLowerCase());

    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID());
    }

}
