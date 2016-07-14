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
public interface JoinPoint {
	// Field descriptor #9 Ljava/lang/String;
	public static final java.lang.String METHOD_EXECUTION = "method-execution";

	// Field descriptor #9 Ljava/lang/String;
	public static final java.lang.String METHOD_CALL = "method-call";

	// Field descriptor #9 Ljava/lang/String;
	public static final java.lang.String CONSTRUCTOR_EXECUTION = "constructor-execution";

	// Field descriptor #9 Ljava/lang/String;
	public static final java.lang.String CONSTRUCTOR_CALL = "constructor-call";

	// Field descriptor #9 Ljava/lang/String;
	public static final java.lang.String FIELD_GET = "field-get";

	// Field descriptor #9 Ljava/lang/String;
	public static final java.lang.String FIELD_SET = "field-set";

	// Field descriptor #9 Ljava/lang/String;
	public static final java.lang.String STATICINITIALIZATION = "staticinitialization";

	// Field descriptor #9 Ljava/lang/String;
	public static final java.lang.String PREINITIALIZATION = "preinitialization";

	// Field descriptor #9 Ljava/lang/String;
	public static final java.lang.String INITIALIZATION = "initialization";

	// Field descriptor #9 Ljava/lang/String;
	public static final java.lang.String EXCEPTION_HANDLER = "exception-handler";

	// Field descriptor #9 Ljava/lang/String;
	public static final java.lang.String SYNCHRONIZATION_LOCK = "lock";

	// Field descriptor #9 Ljava/lang/String;
	public static final java.lang.String SYNCHRONIZATION_UNLOCK = "unlock";

	// Field descriptor #9 Ljava/lang/String;
	public static final java.lang.String ADVICE_EXECUTION = "adviceexecution";

	// Method descriptor #37 ()Ljava/lang/String;
	public abstract java.lang.String toString();

	// Method descriptor #37 ()Ljava/lang/String;
	public abstract java.lang.String toShortString();

	// Method descriptor #37 ()Ljava/lang/String;
	public abstract java.lang.String toLongString();

	// Method descriptor #41 ()Ljava/lang/Object;
	public abstract java.lang.Object getThis();

	// Method descriptor #41 ()Ljava/lang/Object;
	public abstract java.lang.Object getTarget();

	// Method descriptor #44 ()[Ljava/lang/Object;
	public abstract java.lang.Object[] getArgs();

	// Method descriptor #46 ()Lorg/aspectj/lang/Signature;
	/*public abstract org.aspectj.lang.Signature getSignature();

	// Method descriptor #48 ()Lorg/aspectj/lang/reflect/SourceLocation;
	public abstract org.aspectj.lang.reflect.SourceLocation getSourceLocation();

	// Method descriptor #37 ()Ljava/lang/String;
	public abstract java.lang.String getKind();

	// Method descriptor #51 ()Lorg/aspectj/lang/JoinPoint$StaticPart;
	public abstract org.aspectj.lang.JoinPoint.StaticPart getStaticPart();*/
}
