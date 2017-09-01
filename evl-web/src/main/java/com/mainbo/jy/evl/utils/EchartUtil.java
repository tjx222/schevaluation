package com.mainbo.jy.evl.utils;

import java.util.HashMap;
import java.util.Map;
import com.github.abel533.echarts.Label;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.axis.AxisTick;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.FontStyle;
import com.github.abel533.echarts.code.FontWeight;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.style.ItemStyle;
import com.github.abel533.echarts.style.TextStyle;
import com.github.abel533.echarts.style.itemstyle.Emphasis;
import com.github.abel533.echarts.style.itemstyle.Normal;
public class EchartUtil {
	/**
	 * 柱状图通用方法
	 * @param legend
	 * @param title
	 * @param name
	 * @param data
	 * @return
	 */
	public static Option packageBarOption(String legend, Object[] title, String name, Object[] data) {
		// 图表对象
		Option option = new Option();
		// 鼠标悬停提示框设置
		option.tooltip(Trigger.axis);
		TextStyle ts = new TextStyle();
		ts.setAlign(X.left);
		// a-图表标签，b-横轴标签，c-纵轴标签值
		option.getTooltip().setTextStyle(ts);
		// 横轴为值轴
		ValueAxis value = new ValueAxis();
		value.type(AxisType.category);
		AxisTick axisTick = new AxisTick();
		axisTick.show(true);
		if(title.length==0){
			title = new Object[]{"无数据"};
			data = new Object[]{0};
		}
		Object[] objects = new Object[title.length];
		for (int i = 0; i < title.length; i++) {
			Map<String, Object> object = new HashMap<String, Object>();
			TextStyle textStyle = new TextStyle();
			textStyle.setColor("#4a4a4a");
			textStyle.setAlign(X.center);
			textStyle.setFontStyle(FontStyle.normal);
			textStyle.setFontFamily("Microsoft YaHei");
			textStyle.setFontSize(12);
			object.put("textStyle", textStyle);
			object.put("value", title[i]);
			objects[i] = object;
		}
		value.data(objects).axisTick(axisTick);
		value.splitLine().setShow(false);
		value.axisLabel().interval(0).clickable(false);// 节点间隔
		option.xAxis(value);
		// 创建柱状图对象
		Bar series = new Bar();
		series.setType(SeriesType.bar);
		series.setName(name);
		series.data(data);
		series.setBarWidth(40);// 宽度
		// 柱状图值标签设置
		ItemStyle itemStyle = new ItemStyle();
		Normal normal = new Normal();
		Label label = new Label();
		label.setShow(true);
		label.setPosition("top");
		normal.setLabel(label);
		itemStyle.setNormal(normal);
		series.setItemStyle(itemStyle);
		option.series(series);// 加载图表类型对象，可以加载多个
		// 图表标题
		Title titles = new Title();
		titles.text(legend);
		titles.x(X.center);
		TextStyle textStyle = new TextStyle();
		textStyle.setFontWeight(FontWeight.normal);
		textStyle.setFontFamily("Microsoft YaHei");
		textStyle.setFontSize(16);
		textStyle.setColor("#4a4a4a");
		titles.setTextStyle(textStyle);
		option.title(titles);
		// 纵轴样式设置
		ValueAxis valueAxis1 = new ValueAxis();
		valueAxis1.type(AxisType.value);
		valueAxis1.setShow(false);
		valueAxis1.splitLine().setShow(false);
		option.yAxis(valueAxis1);
		option.calculable(false);// 拖拽重计算,false-禁止柱状图表鼠标拖动效果
		// 图表框边距及边框宽度设置--x,y图表左上角位置，x2,y2图表右下角位置
		option.grid().x(20).x2(20).y(100).y2(60).borderWidth(0);
		// 图表加载动画设置
		option.setAnimation(true);// 如要获取图表base64编码需禁用加载时的动画效果
		return option;
	}
	/**
	 * 饼图通用方法
	 * @param legend
	 * @param name
	 * @param data
	 * @return
	 */
	public static Option packagePieOption(String legend, String name, Object[] data) {
		// 图表对象
		Option option = new Option();
		// 图表标题
		Title titles = new Title();
		titles.text(legend);
		titles.x(X.center);
		TextStyle textStyle = new TextStyle();
		textStyle.setFontWeight(FontWeight.lighter);
		titles.setTextStyle(textStyle);
		option.title(titles);
		// 鼠标悬停提示框设置
		option.tooltip(Trigger.item);
		TextStyle ts = new TextStyle();
		ts.setAlign(X.left);
		// a-图表标签，b-饼图区域标签，c-饼图区域标签值 ，d-饼图分隔百分比
		option.getTooltip().formatter("{a}<br/>{b}：{c}({d}%)").setTextStyle(ts);
		// 饼图
		Pie series = new Pie();
		series.setType(SeriesType.pie);
		series.setName(name);
		series.setRadius("55%");
		series.setCenter(new Object[] { "50%", "60%" });
		series.data(data);
		// 饼图图值标签设置
		ItemStyle itemStyle = new ItemStyle();
		Emphasis emphasis = new Emphasis();
		itemStyle.setEmphasis(emphasis);
		series.setItemStyle(itemStyle);
		option.series(series);
		// 图表加载动画设置
		option.setAnimation(true);// 如要获取图表base64编码需禁用加载时的动画效果
		return option;
	}
}
