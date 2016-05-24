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
public class ExportEnumItem extends Export
{
	ItemList itemList = new ItemList();

	public ExportEnumItem(String version, File assetFolder, File outputFolder)
	{
		super(version, assetFolder, outputFolder);
	}

	@Override
	public boolean extract() throws IOException
	{
		File langFolder = new File(this.getOutputFolder() + File.separator + this.getVersion() + File.separator + "lang");
		File langFile = new File(langFolder + File.separator + "en_US.lang");

		// Enumerate items
		BufferedReader reader = new BufferedReader(new FileReader(langFile));
		for(String temp = reader.readLine(); temp != null; temp = reader.readLine())
		{
			if((temp.contains("tile.") || temp.contains("item.")) && temp.contains(".name") && temp.contains("="))
			{
				String[] tempStringArr = temp.split("=");
				if(tempStringArr.length==2)
					itemList.add(new Item(tempStringArr[0], tempStringArr[1]));
			}
		}
		reader.close();

		// Output to file
		File output = new File(this.getOutputFolder() + File.separator + this.getVersion() + File.separator + "EnumItem.txt");
		PrintWriter writer = new PrintWriter(output, "UTF-8");

		for(int i=0; i<itemList.getList().size(); i++)
		{
			writer.print(itemList.getList().get(i).toString());
			writer.println(i == itemList.getList().size() - 1 ? ";" : ",");
		}

		writer.close();

		return true;
	}




	/*
		Entity class
	*/
	private class Item
	{
		private String field;
		private String material;
		private String metadata;
		private String unlocalizedName;

		public Item(String field, String material, String unlocalizedName)
		{
			this.field = field;
			this.material = material;
			this.unlocalizedName = unlocalizedName;
		}

		public Item(String unlocalized, String name)
		{
			this.field = name.replaceAll(" ", "_").toUpperCase();
			this.material = unlocalized.substring(unlocalized.indexOf(".") + 1, unlocalized.indexOf(".",unlocalized.indexOf(".")+1)).replaceAll("(.)(\\p{Lu})", "$1_$2").toUpperCase();
			this.unlocalizedName = unlocalized;
		}

		public String getMaterial()
		{
			return material;
		}

		public void setMetadata(String metadata)
		{
			this.metadata = metadata;
		}

		public String toString()
		{
			StringBuilder code = new StringBuilder();
			code.append(field).append("(");
			code.append("Material.").append(material).append(", ");
			if(metadata!=null && !metadata.isEmpty())
				code.append(metadata).append(", ");
			code.append("\"").append(unlocalizedName).append("\"");
			code.append(")");
			return code.toString();
		}
	}




	private class ItemList
	{
		private List<Item> list = new ArrayList<Item>();

		public ItemList() {
			this.list.add(new Item("AIR","AIR","Air"));
		}

		public void add(Item item)
		{
			updateMetaData(item);
			this.list.add(item);
		}

		public List<Item> getList()
		{
			return list;
		}

		private void updateMetaData(Item item)
		{
			int count = 0;
			for(int i=0; i<list.size(); i++)
			{
				if(list.get(i).getMaterial().equals(item.getMaterial()))
					count++;
			}

			if(count>0)
				item.setMetadata(Integer.toString(count));
		}
	}
}
