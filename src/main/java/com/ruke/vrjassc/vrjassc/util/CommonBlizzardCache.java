package com.ruke.vrjassc.vrjassc.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.ruke.vrjassc.vrjassc.visitor.MainVisitor;

public class CommonBlizzardCache {

	public static void setCache(MainVisitor visitor) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(
				"/tmp/commonjblizzardj.vrjass");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);

		out.writeObject(visitor);
		out.close();
		fileOut.close();

		System.out
				.printf("Serialized data is saved in /tmp/commonjblizzardj.vrjass");
	}

	public static MainVisitor getCache() throws IOException,
			ClassNotFoundException {
		FileInputStream fileIn = new FileInputStream(
				"/tmp/commonjblizzardj.vrjass");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		MainVisitor visitor = (MainVisitor) in.readObject();

		in.close();
		fileIn.close();

		return visitor;
	}

}
