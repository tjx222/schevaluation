/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.tmser.schevaluation.common.web.tag;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.tags.HtmlEscapingAwareTag;
import org.springframework.web.servlet.tags.Param;
import org.springframework.web.servlet.tags.ParamAware;

import com.tmser.schevaluation.common.web.nav.Nav;
import com.tmser.schevaluation.common.web.nav.NavElem;
import com.tmser.schevaluation.common.web.nav.NavHolder;

/**
 * <pre>
 * 导航标签
 * </pre>
 *
 * @author tmser
 * @version $Id: NavTag.java, v 1.0 2015年2月14日 上午10:44:49 tmser Exp $
 */
public class NavTag extends HtmlEscapingAwareTag implements ParamAware {

  protected static final long serialVersionUID = -9104260518603844927L;
  static final Pattern NAMED_PATTERN = Pattern
      .compile("\\{\\s*:([a-zA-Z0-9_]+)\\s*\\}");

  private static final Logger logger = LoggerFactory.getLogger(NavTag.class);
  protected Map<String, String> paramMap = new HashMap<>();
  /**
   * 导航包装div标签样式
   */
  protected String className = "page_links";

  /**
   * 导航元素分隔符
   */
  protected String delimiter = "&nbsp;>&nbsp;";

  /**
   * 要隐藏导航元素索引列表。以 ","分隔，自0开始。不符合要求的输入将被忽略
   */
  protected String hidden;

  protected String actualName;

  public String getHidden() {
    return hidden;
  }

  public void setHidden(String hidden) {
    this.hidden = hidden;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public void setDelimiter(String delimiter) {
    if (delimiter != null && !"".equals(delimiter.trim()))
      this.delimiter = delimiter;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  /**
   * @param param
   * @see org.springframework.web.servlet.tags.ParamAware#addParam(org.springframework.web.servlet.tags.Param)
   */
  @Override
  public void addParam(Param param) {
    paramMap.put(param.getName(), param.getValue());
  }

  /**
   * @return
   * @throws Exception
   * @see org.springframework.web.servlet.tags.RequestContextAwareTag#doStartTagInternal()
   */
  @Override
  protected int doStartTagInternal() {
    return EVAL_BODY_INCLUDE;
  }

  @Override
  public int doEndTag() throws JspException {
    try {
      writeMessage(buildNavContent());
    } catch (IOException e) {
      throw new JspException(e);
    }
    return EVAL_PAGE;
  }

  /**
   * 构建面包屑内容
   * 
   * @return
   */
  protected String buildNavContent() {
    StringBuilder bf = new StringBuilder();
    if (!"".equals(id)) {
      List<NavElem> navList = getNavElems();
      if (navList.size() > 0) {
        int index = 0;
        List<Integer> hiddenNavs = parseDisplay(navList.size());
        int count = navList.size() - hiddenNavs.size();
        int navIndex = 0;
        for (NavElem navElem : navList) {
          if (hiddenNavs.size() > 0) {// 不显示hidden 中列出的导航栏目
            if (hiddenNavs.contains(index)) {
              index++;
              continue;
            }
          }
          navIndex++;
          index++;
          if (!StringUtils.isBlank(navElem.getHref())) {
            bf.append("<a target=\"").append(navElem.getTarget())
                .append("\" href=\"")
                .append(StringEscapeUtils.unescapeHtml4(navElem.getHref()))
                .append("\" id=\"ra-navs-nav").append(index).append("\">")
                .append(getNavElemName(navElem)).append("</a>");
          } else {
            bf.append("<a target=\"").append(navElem.getTarget())
                .append("\" href=\"javascript:void(0);\"")
                .append(" class=\"a_blank\">").append(getNavElemName(navElem))
                .append("</a>");
          }

          if (navIndex != count) {
            bf.append(delimiter);
          }

        }
      }
    }
    return formatMsg(bf.toString());
  }

  private String parseMsg(String msg, List<String> values) {
    StringBuffer sb = new StringBuffer();
    Matcher m = NAMED_PATTERN.matcher(msg);
    String paramName = "";
    Map<String, Integer> nameMap = new HashMap<>();
    while (m.find()) {
      paramName = m.group(1);
      if (!StringUtils.isBlank(paramMap.get(paramName))) {
        Integer pos = nameMap.get(paramName);

        if (pos == null) {
          pos = Integer.valueOf(nameMap.size());
          nameMap.put(paramName, pos);
          values.add(paramMap.get(paramName));
        }
        m.appendReplacement(sb, "{" + pos + "}");
      }
    }
    m.appendTail(sb);

    return sb.toString();
  }

  /**
   * Write the message to the page.
   * <p>
   * Can be overridden in subclasses, e.g. for testing purposes.
   * 
   * @param msg
   *          the message to write
   * @throws IOException
   *           if writing failed
   */
  protected void writeMessage(String msg) throws IOException {
    pageContext.getOut().write(String.valueOf(msg));
  }

  /*
   * 解析要显示导航列表
   */
  protected List<Integer> parseDisplay(int max) {
    List<Integer> rs = Collections.emptyList();
    String displayIndexs = hidden;
    if (displayIndexs != null) {
      rs = new ArrayList<Integer>();
      while (displayIndexs.length() > 0) {
        String index = null;
        int comma = displayIndexs.indexOf(',');
        if (comma >= 0) {
          index = displayIndexs.substring(0, comma).trim();
          displayIndexs = displayIndexs.substring(comma + 1);
        } else {
          index = displayIndexs.trim();
          displayIndexs = "";
        }
        try {
          int i = Integer.valueOf(index);
          if (i < max && i >= 0) {
            rs.add(i);
          }
        } catch (NumberFormatException e) {
          // do nothing
        }
        if (displayIndexs.length() < 1) {
          break;
        }
      }
    }

    return rs;
  }

  protected List<NavElem> getNavElems() {
    Map<String, Nav> mp = ((NavHolder) getRequestContext()
        .getWebApplicationContext().getBean(NavHolder.NAVHOLDER_BEAN_NAME))
        .getNavMap();

    List<NavElem> navList = Collections.emptyList();
    Nav nav = mp.get(id);

    if (nav != null) {
      if (nav.getExtend() != null) {
        navList = mergeNavElems(mp,
            Collections.unmodifiableList(nav.getElems()), nav);
      } else {
        navList = Collections.unmodifiableList(nav.getElems());
      }
    }

    return navList;
  }

  /**
   * 合并父类导航
   * 
   * @param mp
   *          所有导航几乎
   * @param nav
   *          要合并的导航
   * @return
   */
  protected List<NavElem> mergeNavElems(Map<String, Nav> mp,
      List<NavElem> childList, Nav nav) {

    List<NavElem> navList = Collections.emptyList();
    if (nav == null) {
      return navList;
    }

    Nav parent = mp.get(nav.getExtend());
    if (parent != null) {
      navList = copyNavElemList(parent);
      if (nav.getRollback() != null && "true".equals(nav.getRollback())
          && navList.size() > 0) {// 删除最后一级条件，nav
                                  // needback属性为true,且父级导航元素个数多于一个
        navList.remove(navList.size() - 1);
      }
      navList.addAll(childList);

      if (parent.getExtend() != null) {// 递归添加父级导航
        navList = mergeNavElems(mp, navList, parent);
      }
    } else {
      return childList;
    }

    return navList;
  }

  protected List<NavElem> copyNavElemList(Nav nav) {
    List<NavElem> rsList = Collections.emptyList();
    List<NavElem> navElemList = Collections.unmodifiableList(nav.getElems());
    if (navElemList != null && navElemList.size() > 0) {
      rsList = new ArrayList<NavElem>(navElemList.size());
      for (NavElem ne : navElemList) {
        rsList.add(ne);
      }
    }
    return rsList;

  }

  protected String getNavElemName(NavElem e) {
    String name = "";
    if (!StringUtils.isBlank(e.getKey())) {
      name = resolveMessage(e.getKey());
    } else if (!StringUtils.isBlank(e.getName())) {
      name = e.getName();
    }

    return StringEscapeUtils.escapeHtml4(name);
  }

  private String formatMsg(final String msg) {
    String rs = "";
    MessageFormat format = null;

    List<String> values = new ArrayList<>();
    String parsedMsg = parseMsg(msg, values);
    if (!StringUtils.isBlank(parsedMsg)) {
      format = new MessageFormat(parsedMsg);
      Object[] argsArray = ((values != null) ? values.toArray() : null);
      rs = format.format(argsArray);
    }
    return rs;
  }

  /**
   * Use the current RequestContext's application context as MessageSource.
   */
  protected MessageSource getMessageSource() {
    return getRequestContext().getMessageSource();
  }

  /**
   * Resolve the specified message into a concrete message String. The returned
   * message String should be unescaped.
   */
  protected String resolveMessage(String key) {
    MessageSource messageSource = getMessageSource();
    if (messageSource == null) {
      logger.error("No corresponding MessageSource found");
      return key;
    }

    return messageSource.getMessage(key, null, "", getRequestContext()
        .getLocale());
  }

  /**
   * Release any acquired resources.
   */
  @Override
  public void doFinally() {
    super.doFinally();
    paramMap.clear();
    delimiter = "&nbsp;>&nbsp;";
    className = "page_links";
    id = null;
    hidden = null;
  }
}
