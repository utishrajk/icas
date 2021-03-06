
package com.fei.systems.client

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.tasks.Exec

class GruntTask extends Exec {
	private String gruntExecutable = Os.isFamily(Os.FAMILY_WINDOWS) ? "grunt.cmd" : "grunt"
	private String switches = "--no-color"

	String gruntArgs = ""

	public GruntTask() {
		super()
		this.setExecutable(gruntExecutable)
	}

	public void setGruntArgs(String gruntArgs) {
		this.args = "$switches $gruntArgs".trim().split(" ") as List
	}
}

	
	
	