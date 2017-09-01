package com.mainbo.jy.evl.statics;

/**
 * 问卷参与成员状态
 * @author yc
 *
 */
public enum EvlMemberStatus {
	init("初始化",0),
	tongzhishibai("通知失败",10),
	tongzhichaoxian("通知短信数量超出上限",11),
	tongzhichenggong("通知成功", 20),
	tianxiewenjuan("填写问卷",30),
	tijiaowenjuan("提交问卷",40),
	qiquan("弃权",50);
    private  String name;
    private  Integer value;
    public String getName() {
        return name;
    }
    public Integer getValue() {
        return value;
    }
    private EvlMemberStatus(String name, int value) {
        this.name = name;
        this.value = value;
    }
    /**
     * 根据数值得到名称
     * @param value
     * @return
     */
    public static String getName(int value) {
        for (EvlMemberStatus c : EvlMemberStatus.values()) {
            if (c.getValue() == value) {
                return c.name;
            }
        }
        return null;
    }
}
