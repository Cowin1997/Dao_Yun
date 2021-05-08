package cn.edu.fzu.daoyun.utils;


import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "daoyun.sms")
public class SmsUtils {
    private String SecretId;
    private String SecretKey;
    private Integer expire;

    public  String getSmsCode(String phone,String smsCode){
        try{
            Credential cred = new Credential(SecretId, SecretKey);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            SmsClient client = new SmsClient(cred, "", clientProfile);
            SendSmsRequest req = new SendSmsRequest();
            String[] phoneNumberSet1 = {"+86"+phone};
            req.setPhoneNumberSet(phoneNumberSet1);
            req.setTemplateID("928310");
            req.setSign("Spring学习2");
            req.setSmsSdkAppid("1400510605");
            String[] templateParamSet1 = {smsCode};
            req.setTemplateParamSet(templateParamSet1);
            SendSmsResponse resp = client.SendSms(req);
            return SendSmsResponse.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            return null;
        }

    }


     public static String genSmsCode(){
        Integer r = (int) (Math.random()*9000)+1000;
        return String.valueOf(r);
    }




}
