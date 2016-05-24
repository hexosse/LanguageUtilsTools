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
public class ExportEnumEntity extends Export
{
	EntityList entityList = new EntityList();

	public ExportEnumEntity(String version, File assetFolder, File outputFolder)
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
			if(temp.contains("entity.") && temp.contains(".name") && temp.contains("="))
			{
				String[] tempStringArr = temp.split("=");
				if(tempStringArr.length==2)
					entityList.add(new Entity(tempStringArr[0], tempStringArr[1]));
			}
		}
		reader.close();

		// Output to file
		File output = new File(this.getOutputFolder() + File.separator + this.getVersion() + File.separator + "EnumEntity.txt");
		PrintWriter writer = new PrintWriter(output, "UTF-8");

		for(int i = 0; i< entityList.getList().size(); i++)
		{
			writer.print(entityList.getList().get(i).toString());
			writer.println(i == entityList.getList().size() - 1 ? ";" : ",");
		}

		writer.close();

		return true;
	}




	/*
		Entity class
	*/
	private class Entity
	{
		private String field;
		private String type;
		private String unlocalizedName;

		public Entity(String field, String type, String unlocalizedName)
		{
			this.field = field;
			this.type = type;
			this.unlocalizedName = unlocalizedName;
		}

		public Entity(String unlocalized, String name)
		{
			this.field = name.replaceAll(" ", "_").toUpperCase();
			this.type = unlocalized.substring(unlocalized.indexOf(".") + 1, unlocalized.indexOf(".",unlocalized.indexOf(".")+1)).replaceAll("(.)(\\p{Lu})", "$1_$2").toUpperCase();
			this.unlocalizedName = unlocalized;
		}

		public String getType()
		{
			return type;
		}

		public String toString()
		{
			StringBuilder code = new StringBuilder();
			code.append(field).append("(");
			code.append("EntityType.").append(type).append(", ");
			code.append("\"").append(unlocalizedName).append("\"");
			code.append(")");
			return code.toString();
		}
	}




	private class EntityList
	{
		private List<Entity> list = new ArrayList<Entity>();

		public EntityList() {
		}

		public void add(Entity item)
		{
			this.list.add(item);
		}

		public List<Entity> getList()
		{
			return list;
		}
	}
}
