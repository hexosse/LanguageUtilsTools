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
import java.util.ArrayList;
import java.util.List;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class ExportEnumEnchantmentLevel extends Export
{
	EnchantmentList enchantmentList = new EnchantmentList();

	public ExportEnumEnchantmentLevel(String version, File assetFolder, File outputFolder)
	{
		super(version, assetFolder, outputFolder);
	}

	@Override
	public boolean extract() throws IOException
	{
		File langFolder = new File(this.getOutputFolder() + File.separator + this.getVersion() + File.separator + "lang");
		File langFile = new File(langFolder + File.separator + "en_US.lang");

		// Enumerate entities
		BufferedReader reader = new BufferedReader(new FileReader(langFile));
		for(String temp = reader.readLine(); temp != null; temp = reader.readLine())
		{
			if(temp.contains("enchantment.") && temp.contains(".level") && temp.contains("="))
			{
				String[] tempStringArr = temp.split("=");
				if(tempStringArr.length==2)
					enchantmentList.add(new EnchantmentLevel(tempStringArr[0], tempStringArr[1]));
			}
		}
		reader.close();

		// Output to file
		File output = new File(this.getOutputFolder() + File.separator + this.getVersion() + File.separator + "EnumEnchantmentLevel.txt");
		PrintWriter writer = new PrintWriter(output, "UTF-8");

		for(int i = 0; i< enchantmentList.getList().size(); i++)
		{
			writer.print(enchantmentList.getList().get(i).toString());
			writer.println(i == enchantmentList.getList().size() - 1 ? ";" : ",");
		}

		writer.close();

		return true;
	}




	/*
		EnchantmentLevel class
	*/
	private class EnchantmentLevel
	{
		private String field;
		private String level;
		private String unlocalizedName;

		public EnchantmentLevel(String field, String level, String unlocalizedName)
		{
			this.field = field;
			this.level = level;
			this.unlocalizedName = unlocalizedName;
		}

		public EnchantmentLevel(String unlocalized, String name)
		{
			this.field = name.replaceAll(" ", "_").toUpperCase();
			this.level = unlocalized.substring(unlocalized.indexOf(".") + 1).replaceAll("(.)(\\p{Lu})", "$1_$2").toUpperCase();
			this.unlocalizedName = unlocalized;
		}

		public String getLevel()
		{
			return level;
		}

		public String toString()
		{
			StringBuilder code = new StringBuilder();
			code.append(this.level.replace(".","")).append("(");
			code.append("EnchantmentType.").append(level).append(", ");
			code.append("\"").append(unlocalizedName).append("\"");
			code.append(")");
			return code.toString();
		}
	}




	private class EnchantmentList
	{
		private List<EnchantmentLevel> list = new ArrayList<EnchantmentLevel>();

		public EnchantmentList() {
		}

		public void add(EnchantmentLevel item)
		{
			this.list.add(item);
		}

		public List<EnchantmentLevel> getList()
		{
			return list;
		}
	}
}
