package com.lbs.nettyserver.utils.sysutils;

import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lbs.nettyserver.handler.ChannelCommunicateHandler;
import com.lbs.nettyserver.model.sys.VerificationCodeConstants;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import net.sf.json.JSONObject;

/**
 * 验证码
 * @author visual
 *
 */
public class SmsVerificationCodeUtil {
	
	private static final Log logger = LogFactory.getLog(SmsVerificationCodeUtil.class);
/*	@Autowired
	private RedisUtil redisUtil;*/

	/**
	 * 生成随机验证码
	 * @param charCount
	 * @return
	 */
	public static String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }
	
	
	public static int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }
	

	
	/**
	 * 发送验证码至对应的手机号码
	 * @param phone
	 * @return
	 */
	public static boolean sendVerificationCode(String phone,String verificationCode){
		try {
			TaobaoClient client = new DefaultTaobaoClient(VerificationCodeConstants.serverUrl, VerificationCodeConstants.appKey, VerificationCodeConstants.appSecret);
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			req.setExtend( "" );
			req.setSmsType( VerificationCodeConstants.smsType );
			req.setSmsFreeSignName( VerificationCodeConstants.smsFreeSignName );
			req.setSmsParamString("{\"code\":\""+verificationCode+"\"}");
			req.setRecNum( phone );
			req.setSmsTemplateCode( VerificationCodeConstants.smsTemplateCode );
    	
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			
			JSONObject respJson=JSONObject.fromObject(rsp.getBody());
			if(respJson.get("alibaba_aliqin_fc_sms_num_send_response")==null){
				logger.error("验证码发送API接口错误："+respJson.get("error_response").toString());
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("验证码发送错误："+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
