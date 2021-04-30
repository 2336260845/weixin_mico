package com.dreamer.weixin.entity.templateBean;

import java.util.Map;

public class TemplateMessage {
    private String touser;                          //用户OpenID
    private String template_id;                     //模板消息ID
    private String url;                             //URL设空，点模板消息进入空白页面
    private String topcolor;                        //标题颜色
    private Map<String,TemplateData> templateDat;   //模板详细信息

    public static TemplateMessage New(){
        return new TemplateMessage();
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopcolor() {
        return topcolor;
    }

    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }

    public Map<String, TemplateData> getTemplateDat() {
        return templateDat;
    }

    public void setTemplateDat(Map<String, TemplateData> templateDat) {
        this.templateDat = templateDat;
    }
}
