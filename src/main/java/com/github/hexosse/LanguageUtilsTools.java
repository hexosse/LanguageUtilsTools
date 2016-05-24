package com.github.hexosse;

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

import com.github.hexosse.export.*;

import java.io.IOException;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class LanguageUtilsTools
{
	private static CmdLine cmdLine;

	public static void main(String[] args)
	{
		// Parse command line
		cmdLine = new CmdLine(args).parse();

		try
		{
			// Extract Lang file
			ExportEnUsLangFiles enUsLangFiles = new ExportEnUsLangFiles(cmdLine.getVersion(), cmdLine.getAssetFolder(), cmdLine.getOutputFolder());
			if(!enUsLangFiles.extract()) System.exit(0);

			// Extract Lang file
			ExportLangFiles langFiles = new ExportLangFiles(cmdLine.getVersion(), cmdLine.getAssetFolder(), cmdLine.getOutputFolder());
			if(!langFiles.extract()) System.exit(0);

			// Simplify Lang Files
			// todo : item only
			ExportSimplifiedLangFiles simplifiedLangFiles = new ExportSimplifiedLangFiles(cmdLine.getVersion(), cmdLine.getAssetFolder(), cmdLine.getOutputFolder(), false);
			if(!simplifiedLangFiles.extract()) System.exit(0);

			// Lang Code for EnumLang
			ExportEnumLang enumLang = new ExportEnumLang(cmdLine.getVersion(), cmdLine.getAssetFolder(), cmdLine.getOutputFolder());
			if(!enumLang.extract()) System.exit(0);

			// Items Code for EnumItem
			ExportEnumItem enumItem = new ExportEnumItem(cmdLine.getVersion(), cmdLine.getAssetFolder(), cmdLine.getOutputFolder());
			if(!enumItem.extract()) System.exit(0);

			// Items Code for EnumEntity
			ExportEnumEntity enumEntity = new ExportEnumEntity(cmdLine.getVersion(), cmdLine.getAssetFolder(), cmdLine.getOutputFolder());
			if(!enumEntity.extract()) System.exit(0);

			// Items Code for EnumEnchantmentLevel
			ExportEnumEnchantmentLevel enumEnchantmentLevel = new ExportEnumEnchantmentLevel(cmdLine.getVersion(), cmdLine.getAssetFolder(), cmdLine.getOutputFolder());
			if(!enumEnchantmentLevel.extract()) System.exit(0);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
