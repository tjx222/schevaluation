/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.schevaluation.common.web.nav;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: NavBeanDefinitionParser.java, v 1.0 2015年2月14日 下午3:09:47 tmser Exp $
 */
public class NavBeanDefinitionParser implements BeanDefinitionParser{

	private static final String NAV_ELEMENT = "nav";
	private static final String ELEM_ELEMENT = "elem";
	private static final Logger logger = LoggerFactory.getLogger(NavBeanDefinitionParser.class);
	
	/**
	 * 
	 * @param element
	 * @param bean
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#doParse(org.w3c.dom.Element, org.springframework.beans.factory.support.BeanDefinitionBuilder)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AbstractBeanDefinition parse(Element element, ParserContext parserContext) {
		BeanDefinition definition = null;
		try {
			definition = parserContext.getRegistry().getBeanDefinition(NavHolder.NAVHOLDER_BEAN_NAME);
		} catch (NoSuchBeanDefinitionException e) {
		}
		
		boolean isRegistied = definition != null;
		
		if(!isRegistied){
			logger.info("build the new navholder");
			definition = buildDefinitionSource(element,parserContext);
		}
			
		List<Element> navs = DomUtils.getChildElementsByTagName(element, NAV_ELEMENT);
		ManagedList<RootBeanDefinition> elemNavs = null;
		if(isRegistied){
			elemNavs = (ManagedList<RootBeanDefinition>)definition.getPropertyValues().getPropertyValue("navs").getValue();
		}else{
			elemNavs = new ManagedList<RootBeanDefinition>();
		}
		
		for (Element navElem : navs) {
			elemNavs.add(parseDefinitionSource(navElem,parserContext));
		}
		definition.getPropertyValues().add("navs", elemNavs);
		
		try{
			if(!isRegistied){
				registyBean(parserContext, definition);
				return (AbstractBeanDefinition)definition;
			}
		}catch (BeanDefinitionStoreException ex) {
			parserContext.getReaderContext().error(ex.getMessage(), element);
		}
		return null;
	}
	

	private BeanDefinition buildDefinitionSource(Element element, ParserContext parserContext){
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
		builder.getRawBeanDefinition().setBeanClass(NavHolder.class);
		builder.getRawBeanDefinition().setSource(parserContext.extractSource(element));
		if (parserContext.isNested()) {
			// Inner bean definition must receive same scope as containing bean.
			builder.setScope(parserContext.getContainingBeanDefinition().getScope());
		}
		if (parserContext.isDefaultLazyInit()) {
			// Default-lazy-init applies to custom bean definitions as well.
			builder.setLazyInit(true);
		}
		
		return builder.getBeanDefinition();
	}

	private RootBeanDefinition parseDefinitionSource(Element definition, ParserContext parserContext) {
		RootBeanDefinition navDefinition = new RootBeanDefinition(Nav.class);
		navDefinition.getPropertyValues().add("id", getAttributeValue(definition,"id",""));
		navDefinition.getPropertyValues().add("extend", getAttributeValue(definition,"extend",""));
		navDefinition.getPropertyValues().add("rollback", getAttributeValue(definition,"rollback",""));
		
		List<Element> navElemList = DomUtils.getChildElementsByTagName(definition, ELEM_ELEMENT);
		if(navElemList.size() > 0) {
			ManagedList<RootBeanDefinition> elemDefs = new ManagedList<RootBeanDefinition>(navElemList.size());
			for(Element ele : navElemList){
				RootBeanDefinition elemDefinition = new RootBeanDefinition(NavElem.class);
				elemDefinition.getPropertyValues().add("name", getAttributeValue(ele,"name",""));
				elemDefinition.getPropertyValues().add("href",getAttributeValue(ele,"href",""));
				elemDefinition.getPropertyValues().add("key", getAttributeValue(ele,"key",""));
				elemDefinition.getPropertyValues().add("chose", getAttributeValue(ele,"chose",""));
				elemDefinition.getPropertyValues().add("target", getAttributeValue(ele,"target",""));
				elemDefs.add(elemDefinition);
			}
			navDefinition.getPropertyValues().add("elems", elemDefs);
		}
		
		return navDefinition;
	}
	
	private static String getAttributeValue(Element element, String attributeName, String defaultValue) {
		String attribute = element.getAttribute(attributeName);
		if(StringUtils.hasText(attribute)) {
			return attribute.trim();
		}
		return defaultValue;
	}
	
	
	private void registyBean(ParserContext parserContext,BeanDefinition definition){
		String[] aliases = new String[0];
		BeanDefinitionHolder holder = new BeanDefinitionHolder(definition, NavHolder.NAVHOLDER_BEAN_NAME, aliases);
		registerBeanDefinition(holder, parserContext.getRegistry());
		if (shouldFireEvents()) {
			BeanComponentDefinition componentDefinition = new BeanComponentDefinition(holder);
			postProcessComponentDefinition(componentDefinition);
			parserContext.registerComponent(componentDefinition);
		}
	}
	
	/**
	 * Register the supplied {@link BeanDefinitionHolder bean} with the supplied
	 * {@link BeanDefinitionRegistry registry}.
	 * <p>Subclasses can override this method to control whether or not the supplied
	 * {@link BeanDefinitionHolder bean} is actually even registered, or to
	 * register even more beans.
	 * <p>The default implementation registers the supplied {@link BeanDefinitionHolder bean}
	 * with the supplied {@link BeanDefinitionRegistry registry} only if the {@code isNested}
	 * parameter is {@code false}, because one typically does not want inner beans
	 * to be registered as top level beans.
	 * @param definition the bean definition to be registered
	 * @param registry the registry that the bean is to be registered with
	 * @see BeanDefinitionReaderUtils#registerBeanDefinition(BeanDefinitionHolder, BeanDefinitionRegistry)
	 */
	protected void registerBeanDefinition(BeanDefinitionHolder definition, BeanDefinitionRegistry registry) {
		BeanDefinitionReaderUtils.registerBeanDefinition(definition, registry);
	}

	/**
	 * Should an ID be generated instead of read from the passed in {@link Element}?
	 * <p>Disabled by default; subclasses can override this to enable ID generation.
	 * Note that this flag is about <i>always</i> generating an ID; the parser
	 * won't even check for an "id" attribute in this case.
	 * @return whether the parser should always generate an id
	 */
	protected boolean shouldGenerateId() {
		return false;
	}

	/**
	 * Should an ID be generated instead if the passed in {@link Element} does not
	 * specify an "id" attribute explicitly?
	 * <p>Disabled by default; subclasses can override this to enable ID generation
	 * as fallback: The parser will first check for an "id" attribute in this case,
	 * only falling back to a generated ID if no value was specified.
	 * @return whether the parser should generate an id if no id was specified
	 */
	protected boolean shouldGenerateIdAsFallback() {
		return false;
	}

	/**
	 * Controls whether this parser is supposed to fire a
	 * {@link org.springframework.beans.factory.parsing.BeanComponentDefinition}
	 * event after parsing the bean definition.
	 * <p>This implementation returns {@code true} by default; that is,
	 * an event will be fired when a bean definition has been completely parsed.
	 * Override this to return {@code false} in order to suppress the event.
	 * @return {@code true} in order to fire a component registration event
	 * after parsing the bean definition; {@code false} to suppress the event
	 * @see #postProcessComponentDefinition
	 * @see org.springframework.beans.factory.parsing.ReaderContext#fireComponentRegistered
	 */
	protected boolean shouldFireEvents() {
		return true;
	}

	/**
	 * Hook method called after the primary parsing of a
	 * {@link BeanComponentDefinition} but before the
	 * {@link BeanComponentDefinition} has been registered with a
	 * {@link org.springframework.beans.factory.support.BeanDefinitionRegistry}.
	 * <p>Derived classes can override this method to supply any custom logic that
	 * is to be executed after all the parsing is finished.
	 * <p>The default implementation is a no-op.
	 * @param componentDefinition the {@link BeanComponentDefinition} that is to be processed
	 */
	protected void postProcessComponentDefinition(BeanComponentDefinition componentDefinition) {
	}

	
}
