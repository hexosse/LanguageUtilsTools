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

import org.apache.commons.cli.*;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class CmdLine
{
	private static final Logger log = Logger.getLogger(CmdLine.class.getName());
	private String[] args = null;
	private Options options = new Options();

	private String version =  "1.8";
	private File assetFolder = new File(System.getProperty("user.dir"));
	private File outputFolder = new File(System.getProperty("user.dir") + File.separator + "output");


	/**
	 * @param args args fom main class
	 */
	public CmdLine(String[] args)
	{
		this.args = args;

		options.addOption("h", "help", false, "show help.");
		options.addOption(Option.builder("v")
		.longOpt("version")
		.hasArg()
		.optionalArg(true)
		.desc("Minecraft version")
		.build());
		options.addOption(Option.builder("af")
		.longOpt("assetfolder")
		.hasArg()
		.optionalArg(true)
		.desc("Minecraft asset folder")
		.build());
		options.addOption(Option.builder("o")
		.longOpt("output")
		.hasArg()
		.optionalArg(true)
		.desc("Output folder")
		.build());
	}


	/**
	 * @return CmdLine
	 */
	public CmdLine parse()
	{
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;

		try
		{
			cmd = parser.parse(options, args);

			if(cmd.hasOption("h")) help();
			if(cmd.hasOption("v"))
			{
				version = cmd.getOptionValue("v");
			}
			if(cmd.hasOption("af"))
			{
				File f = new File(cmd.getOptionValue("af"));
				if(f.exists() && f.isDirectory())
					assetFolder = f;
				else
					help();
			}
			if(cmd.hasOption("o"))
			{
				File f = new File(cmd.getOptionValue("o"));
				File f2 = new File(assetFolder.getAbsolutePath() + File.separator + cmd.getOptionValue("o"));
				if(f.exists() && f.isDirectory())
					outputFolder = f;
				else if((f2.exists() && f2.isDirectory()) || (f2.getParentFile().exists() && f2.getParentFile().isDirectory()))
					outputFolder = f2;
				else
					help();
			}

		} catch(ParseException e)
		{
			log.log(Level.SEVERE, "Failed to parse comand line properties", e);
			help();
		}
		return this;
	}

	/**
	 *
	 */
	private void help()
	{
		// This prints out some help
		HelpFormatter formater = new HelpFormatter();

		formater.printHelp("LanguageUtilsTools", options);
		System.exit(0);
	}

	public String getVersion()
	{
		return version;
	}

	public File getAssetFolder()
	{
		return assetFolder;
	}

	public File getOutputFolder()
	{
		return outputFolder;
	}
}
