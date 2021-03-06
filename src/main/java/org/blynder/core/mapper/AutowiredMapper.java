package org.blynder.core.mapper;

import java.lang.reflect.Field;
import java.util.List;

import org.blynder.core.util.Pair;
import org.blynder.core.util.SystemInstanceManager;

/**
 * 
 * The built-in implementation of the IAutowiredMapper interface. This class
 * will inject a dependecy into the autowired fields. For more information read
 * the IAutowiredMapper documentation.
 *
 */
public class AutowiredMapper implements IAutowiredMapper{
	
	public void mapAutowired(Object parentInstance, List<Class<?>> beans, List<Pair<Class<?>,Field>> fields) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		for(Pair<Class<?>,Field> pair : fields)
			initField(parentInstance, pair, beans);
	}
	
	/**
	 * 
	 * Given a pair composed with a field and the class it is in and the bean
	 * classes of the project, this method will inject a dependency of the field
	 * type into the field for the class that owns that field.
	 * @param parentInstance
	 * The class that owns what will be injected.
	 * @param field
	 * A pair composed with a field and the class that owns it.
	 * @param classes
	 * A list with the beans of the project
	 * @throws IllegalArgumentException
	 * if the specified object is not an instance of the class or interface 
	 * declaring the underlying field (or a subclass or implementor thereof), 
	 * or if an unwrapping conversion fails.
	 * @throws IllegalAccessException
	 * f this Field object is enforcing Java language access control and the 
	 * underlying field is either inaccessible or final.
	 * @throws InstantiationException
	 * if this Class represents an abstract class, an interface, an array class, 
	 * a primitive type, or void; or if the class has no nullary constructor; 
	 * or if the instantiation fails for some other reason.
	 * 
	 */
	private void initField(Object parentInstance, Pair<Class<?>,Field> field, List<Class<?>> classes) throws IllegalArgumentException, IllegalAccessException, InstantiationException{
		Class<?> clazz = getFieldClass(field.object2, classes);
		Field f = field.object2;
		
		Object toInject = SystemInstanceManager.lookForInstance(clazz);
		
		f.setAccessible(true);
		f.set(parentInstance, toInject);
		f.setAccessible(false);
	}  
	
	/**
	 * 
	 * A helper method that looks for the type of the field inside the beans
	 * list.
	 * @param field
	 * The field itself.
	 * @param classes
	 * A list of the beans of the project.
	 * @return
	 * A type that references the type of the field.
	 * 
	 */
	private Class<?> getFieldClass(Field field, List<Class<?>> classes){
		return 	classes.stream()
				.filter(c -> c.equals(field.getType()))
				.findFirst()
				.orElse(null);
	}
	
}
