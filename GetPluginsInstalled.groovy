
  /*
   *    Get all installed plugins and their version
   */
		Jenkins.instance.pluginManager.plugins.each{
			plugin ->
			  println ("${plugin.getDisplayName()} (${plugin.getShortName()}): ${plugin.getVersion()}")
		  }
