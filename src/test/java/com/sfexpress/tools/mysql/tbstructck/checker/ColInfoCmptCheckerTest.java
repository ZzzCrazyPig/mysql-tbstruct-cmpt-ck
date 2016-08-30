package com.sfexpress.tools.mysql.tbstructck.checker;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import com.sfexpress.tools.mysql.tbstructck.entity.ColumnInfo;

public class ColInfoCmptCheckerTest {
	
	@Test
	public void testTinyInt() throws Exception {
		
		ColInfoCmptChecker checker = new ColInfoCmptChecker();
		Method method = checker.getClass().getDeclaredMethod("checkCompatibility", new Class<?>[] {ColumnInfo.class, ColumnInfo.class});
		method.setAccessible(true);
		
		ColumnInfo pvCol = new ColumnInfo();
		pvCol.setDataType("tinyint");
		ColumnInfo gvCol = new ColumnInfo();
		gvCol.setDataType("tinyint");
		
		Assert.assertEquals(true, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("smallint"); // 对smallint可以兼容
		Assert.assertEquals(true, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("int"); // 对int可以兼容
		Assert.assertEquals(true, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("bigint"); // 对bigint可以兼容
		Assert.assertEquals(true, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("varchar"); // 对其他类型不可以兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("decimal"); // 对其他类型不可以兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
		
	}
	
	@Test
	public void testSmallInt() throws Exception {
		
		ColInfoCmptChecker checker = new ColInfoCmptChecker();
		Method method = checker.getClass().getDeclaredMethod("checkCompatibility", new Class<?>[] {ColumnInfo.class, ColumnInfo.class});
		method.setAccessible(true);
		
		ColumnInfo pvCol = new ColumnInfo();
		pvCol.setDataType("smallint");
		ColumnInfo gvCol = new ColumnInfo();
		gvCol.setDataType("smallint");
		
		// 对smallint可以兼容
		Assert.assertEquals(true, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("tinyint"); // 对tinyint不可以兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("int"); // 对int可以兼容
		Assert.assertEquals(true, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("bigint"); // 对bigint可以兼容
		Assert.assertEquals(true, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("varchar"); // 对其他类型不可以兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("decimal"); // 对其他类型不可以兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
		
	}
	
	@Test
	public void testInt() throws Exception {
		
		ColInfoCmptChecker checker = new ColInfoCmptChecker();
		Method method = checker.getClass().getDeclaredMethod("checkCompatibility", new Class<?>[] {ColumnInfo.class, ColumnInfo.class});
		method.setAccessible(true);
		
		ColumnInfo pvCol = new ColumnInfo();
		pvCol.setDataType("int");
		ColumnInfo gvCol = new ColumnInfo();
		gvCol.setDataType("int");
		
		Assert.assertEquals(true, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("integer"); // 同义词判断
		Assert.assertEquals(true, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("bigint"); // 对bigint可以兼容
		Assert.assertEquals(true, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("tinyint"); // 对tinyint不可以兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("varchar"); // 对其他类型不可以兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("decimal"); // 对其他类型不可以兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
	}
	
	@Test
	public void testBigInt() throws Exception {
		
		ColInfoCmptChecker checker = new ColInfoCmptChecker();
		Method method = checker.getClass().getDeclaredMethod("checkCompatibility", new Class<?>[] {ColumnInfo.class, ColumnInfo.class});
		method.setAccessible(true);
		
		ColumnInfo pvCol = new ColumnInfo();
		pvCol.setDataType("bigint");
		ColumnInfo gvCol = new ColumnInfo();
		gvCol.setDataType("bigint");
		
		Assert.assertEquals(true, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("int"); // 不兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("varchar"); // 对其他类型不可以兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("decimal"); // 对其他类型不可以兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
	}

	@Test
	public void testChar() throws Exception {
		
		ColInfoCmptChecker checker = new ColInfoCmptChecker();
		Method method = checker.getClass().getDeclaredMethod("checkCompatibility", new Class<?>[] {ColumnInfo.class, ColumnInfo.class});
		method.setAccessible(true);
		
		ColumnInfo pvCol = new ColumnInfo();
		pvCol.setDataType("char");
		pvCol.setCharMaxLen(10);
		ColumnInfo gvCol = new ColumnInfo();
		gvCol.setDataType("char");
		gvCol.setCharMaxLen(10);
		
		Assert.assertEquals(true, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setCharMaxLen(20);; // 长度不一致,不兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("varchar"); // 对其他类型不可以兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("decimal"); // 对其他类型不可以兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
		
	}
	
	@Test
	public void testVarchar() throws Exception {
		
		ColInfoCmptChecker checker = new ColInfoCmptChecker();
		Method method = checker.getClass().getDeclaredMethod("checkCompatibility", new Class<?>[] {ColumnInfo.class, ColumnInfo.class});
		method.setAccessible(true);
		
		ColumnInfo pvCol = new ColumnInfo();
		pvCol.setDataType("varchar");
		pvCol.setCharMaxLen(10);
		ColumnInfo gvCol = new ColumnInfo();
		gvCol.setDataType("varchar");
		gvCol.setCharMaxLen(10);
		
		Assert.assertEquals(true, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setCharMaxLen(20); // 长度扩展,兼容
		Assert.assertEquals(true, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setCharMaxLen(5); // 长度减少,不兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("char"); // 对其他类型不可以兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("decimal"); // 对其他类型不可以兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
		
	}
	
	@Test
	public void testDecimal() throws Exception {
		
		ColInfoCmptChecker checker = new ColInfoCmptChecker();
		Method method = checker.getClass().getDeclaredMethod("checkCompatibility", new Class<?>[] {ColumnInfo.class, ColumnInfo.class});
		method.setAccessible(true);
		
		ColumnInfo pvCol = new ColumnInfo();
		pvCol.setDataType("decimal");
		pvCol.setNumericPrecision(10);
		pvCol.setNumericScale(2);
		ColumnInfo gvCol = new ColumnInfo();
		gvCol.setDataType("decimal");
		gvCol.setNumericPrecision(10);
		gvCol.setNumericScale(2);
		
		// decimal(10,2) vs decimal(10,2)
		Assert.assertEquals(true, method.invoke(checker, pvCol, gvCol));
		
		// decimal(10,2) vs numeric(10,2)
		gvCol.setDataType("numeric"); // 同义词,兼容
		Assert.assertEquals(true, method.invoke(checker, pvCol, gvCol));
		
		// decimal(10,2) vs numeric(20,2);
		gvCol.setNumericPrecision(20); // 位数扩大,兼容
		Assert.assertEquals(true, method.invoke(checker, pvCol, gvCol));
		
		// decimal(10,2) vs numeric(10,4)
		gvCol.setNumericPrecision(10);
		gvCol.setNumericScale(4); // 位数变小,不兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
		
		// decimal(10,2) vs numeric(10,1)
		gvCol.setNumericPrecision(10);
		gvCol.setNumericScale(1); // 精度变小,不兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
		
		// decimal(10,2) vs numeric(11,3)
		gvCol.setNumericPrecision(11);
		gvCol.setNumericScale(3); // 位数不变,精度变大,兼容
		Assert.assertEquals(true, method.invoke(checker, pvCol, gvCol));
		
		// decimal(10,2) vs numeric(11,2)
		gvCol.setNumericPrecision(11);
		gvCol.setNumericScale(2); // 位数变大,精度不变,兼容
		Assert.assertEquals(true, method.invoke(checker, pvCol, gvCol));
		
		// decimal(10,2) vs numeric(14,4)
		gvCol.setNumericPrecision(11);
		gvCol.setNumericScale(2); // 位数变大,精度变大,兼容
		Assert.assertEquals(true, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("char"); // 对其他类型不可以兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
		
		gvCol.setDataType("int"); // 对其他类型不可以兼容
		Assert.assertEquals(false, method.invoke(checker, pvCol, gvCol));
		
	}
	
}
