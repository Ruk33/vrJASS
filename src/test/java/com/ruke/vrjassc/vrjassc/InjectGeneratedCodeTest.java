package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.ProjectPath;

import de.peeeq.jmpq.JmpqEditor;

public class InjectGeneratedCodeTest {

	@Test
	public void test() throws IOException {
		File map = new File(ProjectPath.getTest() + "/MUI.w3m");
		File code = File.createTempFile("war3map", ".j");
		File output = File.createTempFile("output", ".j");
		PrintWriter writer = new PrintWriter(code);
		JmpqEditor editor = new JmpqEditor(map);

		writer.print("lorem ipsum");
		writer.close();

		editor.delete("war3map.j");
		editor.injectFile(code, "war3map.j");
		editor.extractFile("war3map.j", output);

		// yes, i'm quite lazy, deal with it
		assertEquals("[lorem ipsum]", Files.readAllLines(output.toPath())
				.toString());

		editor.close();
	}

}
