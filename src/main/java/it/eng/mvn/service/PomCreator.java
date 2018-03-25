package it.eng.mvn.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.pom.Dependency;
import org.apache.maven.pom.Dependency.Exclusions;
import org.apache.maven.pom.Exclusion;
import org.apache.maven.pom.Model.Dependencies;
import org.apache.maven.pom.ObjectFactory;

public class PomCreator {

	public static void listFilesForFolder(final File folder, String nomeProgetto) throws IOException, JAXBException {
		ObjectFactory of = new ObjectFactory();
		Dependencies dependencies = of.createModelDependencies();
		StringBuilder props = new StringBuilder();
		StringBuilder mvnInstall = new StringBuilder();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry, nomeProgetto);
			} else {
//				System.out.println();
//				System.out.println(fileEntry.getName());
				FileInputStream fileInputStream = new FileInputStream(fileEntry);
				JarInputStream jarStream = new JarInputStream(fileInputStream);
				Manifest mf = jarStream.getManifest();
//				System.out.println(mf.getMainAttributes().getValue(Name.CLASS_PATH));
//				System.out.println(mf.getMainAttributes().getValue(Name.EXTENSION_INSTALLATION));
//				System.out.println(mf.getMainAttributes().getValue(Name.EXTENSION_LIST));
//				System.out.println(mf.getMainAttributes().getValue(Name.EXTENSION_NAME));
//				System.out.println(mf.getMainAttributes().getValue(Name.IMPLEMENTATION_TITLE));
//				System.out.println(mf.getMainAttributes().getValue(Name.IMPLEMENTATION_URL));
//				System.out.println(mf.getMainAttributes().getValue(Name.IMPLEMENTATION_VENDOR));
//				System.out.println(mf.getMainAttributes().getValue(Name.IMPLEMENTATION_VENDOR_ID));
//				System.out.println(mf.getMainAttributes().getValue(Name.IMPLEMENTATION_VERSION));
//				System.out.println(mf.getMainAttributes().getValue(Name.MAIN_CLASS));
//				System.out.println(mf.getMainAttributes().getValue(Name.MANIFEST_VERSION));
//				System.out.println(mf.getMainAttributes().getValue(Name.SEALED));
//				System.out.println(mf.getMainAttributes().getValue(Name.SIGNATURE_VERSION));
//				System.out.println(mf.getMainAttributes().getValue(Name.SPECIFICATION_TITLE));
//				System.out.println(mf.getMainAttributes().getValue(Name.SPECIFICATION_VENDOR));
//				System.out.println(mf.getMainAttributes().getValue(Name.SPECIFICATION_VERSION));

				Attributes a = mf.getMainAttributes();

				Dependency dependency = of.createDependency();

				String groupId = null;
				if (StringUtils.isNotBlank(a.getValue(Name.EXTENSION_NAME))) {
					groupId = a.getValue(Name.EXTENSION_NAME);
					// dependency.setGroupId(a.getValue(Name.EXTENSION_NAME));
				} else if (StringUtils.isNotBlank(a.getValue("Name"))) {
					groupId = a.getValue("Name").replaceAll("/", ".");
					// dependency.setGroupId(a.getValue("Name").replaceAll("/", "."));
				} else if (StringUtils.contains(fileEntry.getName(), "-") && StringUtils
						.isNumeric(StringUtils.substringAfterLast(fileEntry.getName(), "-").substring(0, 1))) {
					groupId = StringUtils.substringBeforeLast(fileEntry.getName(), "-").replaceAll(".jar", "");
					// dependency.setGroupId(StringUtils.substringBeforeLast(fileEntry.getName(),
					// "-").replaceAll(".jar", ""));
				} else {
					groupId = fileEntry.getName().replaceAll(".jar", "");
					// dependency.setGroupId(fileEntry.getName().replaceAll(".jar", ""));
				}
				dependency.setGroupId(groupId);

				String artifactId = null;
				if (StringUtils.contains(fileEntry.getName(), "-") && StringUtils
						.isNumeric(StringUtils.substringAfterLast(fileEntry.getName(), "-").substring(0, 1))) {
					artifactId = StringUtils.substringBeforeLast(fileEntry.getName(), "-").replaceAll(".jar", "");

				} else {
					artifactId = fileEntry.getName().replaceAll(".jar", "");
				}
				dependency.setArtifactId(artifactId);

				String version = null;
				if (StringUtils.isNotBlank(a.getValue(Name.IMPLEMENTATION_VERSION))) {
					version = a.getValue(Name.IMPLEMENTATION_VERSION).replaceAll(" ", "");

				} else if (StringUtils.isNotBlank(a.getValue(Name.SPECIFICATION_VERSION))) {
					version = a.getValue(Name.SPECIFICATION_VERSION).replaceAll(" ", "");
				} else if (StringUtils.contains(fileEntry.getName(), "-") && StringUtils
						.isNumeric(StringUtils.substringAfterLast(fileEntry.getName(), "-").substring(0, 1))) {
					version = StringUtils.substringAfterLast(fileEntry.getName(), "-").replaceAll(" ", "_")
							.replaceAll(" ", "").replaceAll(".jar", "");
				} else {
					version = "1.0";
				}
				// dependency.setVersion(version);
				dependency.setVersion("${" + artifactId + "}");

				Exclusions exclusions = new Exclusions();
				dependency.setExclusions(exclusions);

				Exclusion exclusion = new Exclusion();
				exclusion.setGroupId("*");
				exclusion.setArtifactId("*");

				dependency.getExclusions().getExclusion().add(exclusion);
				dependencies.getDependency().add(dependency);

//				File file = new File("file.xml");
//				JAXBContext jaxbContext = JAXBContext.newInstance(Dependency.class);

				

				props.append("<");
				props.append(artifactId);
				props.append(">");
				props.append(version);
				props.append("</");
				props.append(artifactId);
				props.append(">");
				props.append("\n");

				mvnInstall.append("mvn install:install-file -Dfile=./");
				mvnInstall.append(nomeProgetto);
				mvnInstall.append("/");
				mvnInstall.append(fileEntry.getName());
				mvnInstall.append(" -DgroupId=" + groupId);
				mvnInstall.append(" -DartifactId=" + artifactId);
				mvnInstall.append(" -Dversion=" + version);
				mvnInstall.append(" -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true ");
				mvnInstall.append("\n");
			}

		}
		
		System.out.println();
		System.out.println(mvnInstall.toString());
		
		System.out.println();
		System.out.println(props.toString());
		
		File file2 = new File("file2.xml");
		JAXBContext jaxbContext2 = JAXBContext.newInstance(Dependencies.class);
		Marshaller jaxbMarshaller2 = jaxbContext2.createMarshaller();
		JAXBElement<Dependencies> jaxbElement2 = new JAXBElement<Dependencies>(
				new QName("http://maven.apache.org/POM/4.0.0", "Model.Dependencies"), Dependencies.class,
				dependencies);
		jaxbMarshaller2.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller2.marshal(jaxbElement2, file2);
		jaxbMarshaller2.marshal(jaxbElement2, System.out);
		
		
	}

}
