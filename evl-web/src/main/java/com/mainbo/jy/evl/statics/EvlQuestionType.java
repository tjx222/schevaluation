package com.mainbo.jy.evl.statics;

import java.util.Random;


/**
 * 问卷流程
 * @author ljh
 *
 */
public enum EvlQuestionType {
	init("初始化",0),xiangguanshezhi("相关设置",1),shejiwenjuan("设计问卷", 2),fabu("发布问卷",3),jieshu("结束问卷",4);
    private  String name;
    private  Integer value;
    public String getName() {
        return name;
    }
    public Integer getValue() {
        return value;
    }
    private EvlQuestionType(String name, int value) {
        this.name = name;
        this.value = value;
    }
    /**
     * 根据数值得到名称
     * @param value
     * @return
     */
    public static String getName(int value) {
        for (EvlQuestionType c : EvlQuestionType.values()) {
            if (c.getValue() == value) {
                return c.name;
            }
        }
        return null;
    }
    public static EvlQuestionType getType(int max) {
		Random random = new Random(System.currentTimeMillis());
		int num = random.nextInt(max);
		switch (num) {
		case 0:
			return EvlQuestionType.init;
		case 1:
			return EvlQuestionType.xiangguanshezhi;
		case 2:
			return EvlQuestionType.shejiwenjuan;
		case 3:
			return EvlQuestionType.fabu;
		case 4:
			return EvlQuestionType.jieshu;
		default:
			return EvlQuestionType.init;
		}
	}
}
