/**
 * 
 */
package com.mangocity.mbr.invoker.httpinvoker;

/**
 * @Package org.springframework.remoting.httpinvoker
 * @Description :
 * @author YangJie
 * @email <a href='yangjie_software@163.com'>yangjie</a>
 * @date 2015-9-16
 */
public interface MethodInvocation extends Invocation {
	public abstract java.lang.reflect.Method getMethod();
}
