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

import java.io.*;
import java.util.HashMap;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class ExportEnumLang extends Export
{
	public ExportEnumLang(String version, File assetFolder, File outputFolder)
	{
		super(version, assetFolder, outputFolder);
	}

	@Override
	public boolean extract() throws IOException
	{
		File langFolder = new File(this.getOutputFolder() + File.separator + this.getVersion() + File.separator + "lang");
		File[] langFiles = langFolder.listFiles();

		File output = new File(this.getOutputFolder() + File.separator + this.getVersion() + File.separator + "EnumLang.txt");
		PrintWriter writer = new PrintWriter(output, "UTF-8");
		if(langFiles != null)
		{
			int nbFiles = langFiles.length;

			for(int i = 0; i < nbFiles; ++i)
			{
				File file = langFiles[i];
				if(file.isFile()) writer.println(generateCode(file, i==nbFiles-1));
			}
		}

		writer.close();

		return true;
	}

	private String generateCode(File file, boolean last)
	{
		StringBuilder code = new StringBuilder();
		String name = file.getName().replaceAll(".lang", "");
		code.append(name.toUpperCase());
		code.append("(");
		code.append("\"").append(name).append("\"");
		code.append(", new HashMap<String, String>())");
		code.append(last?";":",");
		return code.toString();
	}
}
