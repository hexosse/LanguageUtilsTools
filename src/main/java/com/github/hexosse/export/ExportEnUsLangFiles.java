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

import com.github.hexosse.tool.JarResources;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class ExportEnUsLangFiles extends Export
{
	public ExportEnUsLangFiles(String version, File assetFolder, File outputFolder)
	{
		super(version, assetFolder, outputFolder);
	}

	@Override
	public boolean extract() throws IOException
	{
		File jarFile = new File(FilenameUtils.normalize(this.getAssetFolder() + File.separator + ".." + File.separator + "versions" + File.separator + this.getVersion() + File.separator + this.getVersion() + ".jar"));
		JarResources jar = new JarResources(jarFile.getAbsolutePath());
		byte[] bytes = jar.getResource("assets/minecraft/lang/en_US.lang");

		FileUtils.writeByteArrayToFile(new File(this.getOutputFolder() + File.separator + this.getVersion() + File.separator + "lang" + File.separator  + "en_US.lang"), bytes);

		return true;
	}
}
