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

import com.github.hexosse.export.Export;
import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class ExportLangFiles extends Export
{
	public ExportLangFiles(String version, File assetFolder, File outputFolder)
	{
		super(version, assetFolder, outputFolder);
	}

	@Override
	public boolean extract() throws IOException
	{
		// json file
		File jsonFile = new File(this.getAssetFolder() + File.separator + "indexes" + File.separator + this.getVersionSmall() + ".json");
		if(!jsonFile.exists() || !jsonFile.isFile()) return false;

		// read json file
		BufferedReader reader = new BufferedReader(new FileReader(jsonFile));

		// parse json file and extract files
		String line;
		while((line = reader.readLine()) != null)
		{
			if(line.contains("minecraft/lang/"))
			{
				String filename = cleanLine(line).replaceAll("minecraft/lang/", "").replaceAll(":", "").replaceAll("\\{", "");
				String process = reader.readLine();
				String hash = process.split(":")[1].replaceAll("\\p{P}", "").replaceAll("\\s", "");
				String prefix = hash.substring(0, 2);
				System.out.println("filename: " + filename);
				System.out.println("hash: " + hash);
				System.out.println("prefix: " + prefix);
				System.out.println();

				FileUtils.copyFile(
					new File(this.getAssetFolder() + File.separator + "objects" + File.separator + prefix + File.separator +  hash)
					, new File(this.getOutputFolder() + File.separator + this.getVersion() + File.separator + "lang" + File.separator  + filename));
			}
		}

		reader.close();

		return true;
	}

	private String cleanLine(String string)
	{
		return string.replaceAll(" ", "").replaceAll("\"", "").replaceAll(",", "").replaceAll("\t", "");
	}
}
