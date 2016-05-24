package com.github.hexosse.export;

/*
 * Copyright 2015 hexosse
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class ExportSimplifiedLangFiles extends Export
{
	private boolean itemOnly = false;
	private static List<String> need = new ArrayList();


	public ExportSimplifiedLangFiles(String version, File assetFolder, File outputFolder, boolean itemOnly)
	{
		super(version, assetFolder, outputFolder);
		this.itemOnly = itemOnly;

		init(itemOnly);
	}

	private static void init(boolean onlyItems)
	{
		need.add("tile.");
		need.add("item.");
		need.add("potion.");
		need.add("entity.");
		need.add("enchantment.");
		if(!onlyItems) {
			;
		}
	}

	@Override
	public boolean extract() throws IOException
	{
		File langFolder = new File(this.getOutputFolder() + File.separator + this.getVersion() + File.separator + "lang");
		File[] langFiles = langFolder.listFiles();
		File simplifiedFolder = new File(this.getOutputFolder() + File.separator + this.getVersion() + File.separator + "simplified_lang");
		if(!simplifiedFolder.exists())
			simplifiedFolder.mkdirs();

		if(langFiles != null)
		{
			int nbFiles = langFiles.length;

			for(int i = 0; i < nbFiles; ++i) {
				File file = langFiles[i];
				if(file.isFile())
					simplifyLang(file);
			}
		}

		return true;
	}


	private void simplifyLang(File file) throws IOException
	{
		File simplifiedFolder = new File(this.getOutputFolder() + File.separator + this.getVersion() + File.separator + "simplified_lang");
		File simplifiedLang = new File(simplifiedFolder + File.separator + file.getName());

		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
		PrintWriter writer = new PrintWriter(simplifiedLang, "UTF-8");

		while(true) {
			String line;
			String prefix;
			String second;
			do {
				do {
					do {
						do {
							do {
								do {
									do {
										do {
											if((line = reader.readLine()) == null) {
												reader.close();
												writer.close();
												return;
											}

											prefix = line.substring(0, line.indexOf(".") + 1);
											second = line.substring(line.indexOf(".") + 1);
										} while(this.itemOnly && prefix.contains("item") && second.startsWith("banner") && !second.contains("name"));
									} while(second.startsWith("fireworksCharge") && !second.contains("name"));
								} while(second.startsWith("fireworks.flight"));
							} while(second.startsWith("dyed"));
						} while(second.startsWith("unbreakable"));
					} while(second.startsWith("canBreak"));
				} while(second.startsWith("canPlace"));
			} while(this.itemOnly && prefix.contains("potion") && !second.contains("fix"));

			if(need.contains(prefix)) {
				writer.println(line);
			}
		}
	}
}
