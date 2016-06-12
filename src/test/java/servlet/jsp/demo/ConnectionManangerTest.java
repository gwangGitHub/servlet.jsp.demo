package servlet.jsp.demo;

/**
 * import static静态导入是JDK1.5中的新特性。一般我们导入一个类都用 import com.....ClassName;
 * 而静态导入是这样：import static com.....ClassName.*;这里的多了个static，还有就是类名ClassName后面多了个 .* ，
 * 意思是导入这个类里的静态方法。当然，也可以只导入某个静态方法，只要把 .* 换成静态方法名就行了。然后在这个类中，
 * 就可以直接用方法名调用静态方法，而不必用ClassName.方法名 的方式来调用。 
 */
import static org.junit.Assert.*;

import org.junit.Test;

import servlet.jsp.demo.dao.ConnectionMananger;

public class ConnectionManangerTest {

	@Test
	public void testGetConnection() {
		assertNotNull(ConnectionMananger.getConnection());
	}
}
