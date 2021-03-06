/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.android.calculator.model;

import jscl.math.function.IConstant;

import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

/**
 * User: serso
 * Date: 11/7/11
 * Time: 7:52 PM
 */
public class VarTest {

	private static final String xml = "<vars>\n" +
			"   <vars class=\"java.util.ArrayList\">\n" +
			"      <var>\n" +
			"         <name>e</name>\n" +
			"         <value>2.718281828459045</value>\n" +
			"         <system>true</system>\n" +
			"         <description>description</description>\n" +
			"      </var>\n" +
			"      <var>\n" +
			"         <name>;</name>\n" +
			"         <value>3.0</value>\n" +
			"         <system>true</system>\n" +
			"      </var>\n" +
			"   </vars>\n" +
			"</vars>";

	@Test
	public void testXml() throws Exception {
		final Vars vars = new Vars();
		Var first = new Var.Builder("e", Math.E).setDescription("description").setSystem(true).create();
		vars.getEntities().add(first);
		Var second = new Var.Builder(";", 3d).setSystem(true).create();
		vars.getEntities().add(second);

		final StringWriter sw = new StringWriter();
		final Serializer serializer = new Persister();
		serializer.write(vars, sw);

		assertEquals(xml, sw.toString());

		final Vars result = serializer.read(Vars.class, xml);
		final IConstant actualFirst = result.getEntities().get(0);
		final IConstant actualSecond = result.getEntities().get(1);

		areEqual(first, actualFirst);
		areEqual(second, actualSecond);

	}

	private void areEqual(IConstant expected, IConstant actual) {
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getValue(), actual.getValue());
	}
}
