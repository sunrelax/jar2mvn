package org.apache.maven.pom.test;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.Element;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.maven.pom.Dependency;
import org.apache.maven.pom.Dependency.Exclusions;
import org.apache.maven.pom.Exclusion;
import org.apache.maven.pom.Model;
import org.apache.maven.pom.Model.Dependencies;
import org.apache.maven.pom.Model.Properties;
import org.apache.maven.pom.ObjectFactory;

import it.eng.mvn.service.PomCreator;

public class CreatePom {

	public static void main(String[] args) throws JAXBException, IOException {
		testDependency2();
	}

	public static void testDependency2() throws JAXBException, IOException {
		File f = new File("/home/giuseppe/Documents/java/workspace/av_be/ws_regauth/ws_regauth/lib");
		PomCreator.listFilesForFolder(f, "ws_regauth");

	}

	public static void testDependency() throws JAXBException {
		ObjectFactory of = new ObjectFactory();
		Dependencies dependencies = of.createModelDependencies();

		Dependency dependency = of.createDependency();
		dependency.setGroupId("org.springframework");
		dependency.setArtifactId("spring-web");
		dependency.setVersion("${spring}");
		Exclusion exclusion = new Exclusion();
		exclusion.setGroupId("*");
		exclusion.setArtifactId("*");
		Exclusions exclusions = new Exclusions();
		dependency.setExclusions(exclusions);
		dependency.getExclusions().getExclusion().add(exclusion);
		dependencies.getDependency().add(dependency);
		Dependency dependency2 = of.createDependency();
		dependency2.setGroupId("org.springframework");
		dependency2.setArtifactId("spring-web");
		dependency2.setVersion("${spring}");
		dependency2.setExclusions(exclusions);
		// dependency2.getExclusions().getExclusion().add(exclusion);
		dependencies.getDependency().add(dependency2);

		File file = new File("file.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(Dependency.class);
		// Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// dependencies.getDependency().add(dependency);

		// JAXBElement<Dependency> jaxbElement = new JAXBElement<Dependency>(
		// new QName("http://maven.apache.org/POM/4.0.0", "Dependency"),
		// Dependency.class, dependency);
		// jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		// jaxbMarshaller.marshal(jaxbElement, file);
		// jaxbMarshaller.marshal(jaxbElement, System.out);

		File file2 = new File("file2.xml");
		JAXBContext jaxbContext2 = JAXBContext.newInstance(Dependencies.class);
		Marshaller jaxbMarshaller2 = jaxbContext2.createMarshaller();
		JAXBElement<Dependencies> jaxbElement2 = new JAXBElement<Dependencies>(
				new QName("http://maven.apache.org/POM/4.0.0", "Model.Dependencies"), Dependencies.class, dependencies);
		jaxbMarshaller2.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller2.marshal(jaxbElement2, file2);
		jaxbMarshaller2.marshal(jaxbElement2, System.out);

	}

	// public static void testProperties() throws JAXBException {
	// Dependencies dependencies = new Dependencies();
	// ObjectFactory of = new ObjectFactory();
	//
	// Properties properties = of.createModelProperties();
	// Element e = new Element() {
	// };
	// properties.getAny().add(e);
	// dependency.setGroupId("org.springframework");
	// dependency.setArtifactId("spring-web");
	// dependency.setVersion("${spring}");
	// Exclusion exclusion = new Exclusion();
	// exclusion.setGroupId("*");
	// exclusion.setArtifactId("*");
	// Exclusions exclusions = new Exclusions();
	// dependency.setExclusions(exclusions);
	// dependency.getExclusions().getExclusion().add(exclusion);
	// File file = new File("file.xml");
	// JAXBContext jaxbContext = JAXBContext.newInstance(Dependency.class);
	// Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	// //
	// jaxbMarshaller.setProperty("com.sun.xml.bind.marshaller.namespacePrefixMapper",
	// // "");
	// // output pretty printed
	// jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	// JAXBElement<Dependency> jaxbElement = new JAXBElement(new
	// QName("Dependency"), Dependency.class, dependency);
	// jaxbMarshaller.marshal(jaxbElement, file);
	// jaxbMarshaller.marshal(jaxbElement, System.out);
	//
	// }
}