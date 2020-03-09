package com.example.json.ioc;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.json.ioc.annotation.Autowired;
import com.example.json.ioc.annotation.Inject;
import com.example.json.ioc.utils.ClassUtil;
/**
 * @author hexian
 * 
 * Bean容器
 * 参考自:  
 *   https://zzzzbw.cn/post/9  
 *   https://github.com/zzzzbw/doodle
 *   
 */
public class Ioc {

    /**
     * Bean容器
     */
    private BeanContainer beanContainer;

    public Ioc() {
        beanContainer = BeanContainer.getInstance();
    }

    /**
     * 执行Ioc
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void doIoc() {
    	//遍历Bean容器中所有的Bean
        for (Class<?> clz : beanContainer.getClasses()) { 
            final Object targetBean = beanContainer.getBean(clz);
            Field[] fields = clz.getDeclaredFields();
            //遍历Bean中的所有属性
            for (Field field : fields) { 
            	// 如果该属性被 @Autowired / @Inject 注解，则对其注入
                if (field.isAnnotationPresent(Autowired.class) || field.isAnnotationPresent(Inject.class)) {
                    final Class<?> fieldClass = field.getType();
                    // 如果Class是集合类型
                    if(Collection.class.isAssignableFrom(fieldClass)) {
                    	// 获取泛型的实际类型
                    	Class<?> componentType = getComponentType(field, 0);
                    	List<Object> candidateBean = getCandidateBeanList(componentType, targetBean);
                    	if(Set.class.isAssignableFrom(fieldClass)) {
                    		Set list = new HashSet();
                    		list.addAll(candidateBean);
                    		ClassUtil.setField(field, targetBean, list);
                    	} else 
                    	if(List.class.isAssignableFrom(fieldClass)){// List
                    	   ClassUtil.setField(field, targetBean, candidateBean);
                    	}
                    } else 
                    /**
                     *  Map的注入(因为不能按照名称注入所以这里的Map的key只能是Bean的简单名称首字母小写)
                     *  Map有点复杂, 还是不支持的比较好
                     */
                    if(Map.class.isAssignableFrom(fieldClass)) {
                    	/**
                    	 * // 获取泛型的实际类型
                    	 * Class<?> componentType = getComponentType(field, 1);
                    	 * 
                    	 */
                    } else {
                    	Object fieldValue = getClassInstance(fieldClass);
                    	if (null != fieldValue) {
                    		ClassUtil.setField(field, targetBean, fieldValue);
                    	} else {
                    		throw new RuntimeException("无法注入对应的类，目标类型:" + fieldClass.getName());
                    	}
                    }
                }
            }
        }
    }
    
    private Class<?> getComponentType(Field field, int position) {
    	 Type genericType = field.getGenericType();
         if (null == genericType) {
            throw new RuntimeException("请为集合类型指定泛型类型");
         }
         
         if (genericType instanceof ParameterizedType) {
             ParameterizedType pt = (ParameterizedType) genericType;
             // 得到泛型里的class类型对象
             Class<?> actualTypeArgument = (Class<?>)pt.getActualTypeArguments()[position];
             
             return actualTypeArgument;
         }
         
         return null;
    }
    
    public List<Object> getCandidateBeanList(final Class<?> clz, Object targetBean){
    	List<Object> candidateBean= beanContainer.getBeans().stream().filter(bean -> clz.isInstance(bean)).collect(Collectors.toCollection(LinkedList::new));
    	// 候选的bean不能包含自己
    	if(candidateBean.contains(targetBean)) {
    	   candidateBean.remove(targetBean);
    	}
    	
    	return candidateBean;
    }

    /**
     * 根据Class获取其实例或者实现类
     */
    private Object getClassInstance(final Class<?> clz) {
        return Optional
                .ofNullable(beanContainer.getBean(clz))
                .orElseGet(() -> {
                    Class<?> implementClass = getImplementClass(clz);
                    if (null != implementClass) {
                        return beanContainer.getBean(implementClass);
                    }
                    return null;
                });
    }

    /**
     * 获取接口的实现类
     */
    private Class<?> getImplementClass(final Class<?> interfaceClass) {
        return beanContainer.getClassesBySuper(interfaceClass)
                .stream()
                .findFirst()
                .orElse(null);
    }

}