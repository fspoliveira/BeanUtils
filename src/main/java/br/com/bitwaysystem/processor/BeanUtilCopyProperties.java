package br.com.bitwaysystem.processor;

import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.thoughtworks.xstream.XStream;

import br.com.bitwaysystem.entity.People;
import br.com.bitwaysystem.entity.Pessoa;
import br.com.bitwaysystem.util.BeanPropertyCopyUtil;

public class BeanUtilCopyProperties {

	public static void main(String[] args) {

		Pessoa pessoa = new Pessoa(35, "João");
		People people = new People(30, "Jhonny");

		List<String> crossRef = new ArrayList<String>();
		crossRef.add("idade age");
		crossRef.add("nome name");

		String[] crossRefArray = new String[crossRef.size()];
		crossRefArray = crossRef.toArray(crossRefArray);

		System.out.println(ToStringBuilder.reflectionToString(pessoa));
		System.out.println(ToStringBuilder.reflectionToString(people));

		try {
			System.out.println("Copying properties from fromBean to toBean");
			try {

				System.out.println(crossRef.toString());
				BeanPropertyCopyUtil.copyProperties(pessoa, people,
						crossRefArray);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		System.out.println(ToStringBuilder.reflectionToString(pessoa));
		System.out.println(ToStringBuilder.reflectionToString(people));

		/*****************************************************************************************
		 * Xstream Generation XML
		 *****************************************************************************************/
		XStream xstream = new XStream();
		xstream.alias("person", People.class);

		System.out.println("XML Generatade by Xstream\n"
				+ xstream.toXML(people) + "\n");

		/*****************************************************************************************
		 * Valocity Generation XML
		 *****************************************************************************************/
		VelocityEngine ve = new VelocityEngine();
		ve.init();

		VelocityContext context = new VelocityContext();
		Template t = ve.getTemplate("generateXML.vm");

		ArrayList<Map> list = new ArrayList<Map>();
		Map<String, String> map = new HashMap<String, String>();

		map.put("name", people.getName());
		map.put("age", new String(Integer.toString(people.getAge())));
		list.add(map);

		context.put("peopleList", list);

		StringWriter writer = new StringWriter();

		t.merge(context, writer);
		System.out.println("XML Generatade by Velocity\n" + writer.toString());

	}

}
