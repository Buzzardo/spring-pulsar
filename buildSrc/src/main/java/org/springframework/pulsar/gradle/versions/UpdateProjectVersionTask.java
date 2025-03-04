/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.pulsar.gradle.versions;

import java.io.File;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

public abstract class UpdateProjectVersionTask extends DefaultTask {

	protected void updateVersionInGradleProperties(String newVersion) {
		String currentVersion = getProject().getVersion().toString();
		File gradlePropertiesFile = getProject().getRootProject().file(Project.GRADLE_PROPERTIES);
		if (!gradlePropertiesFile.exists()) {
			throw new RuntimeException("No gradle.properties to update version in");
		}
		System.out.printf("Updating the project version in %s from %s to %s%n",
				Project.GRADLE_PROPERTIES, currentVersion, newVersion);
		FileUtils.replaceFileText(gradlePropertiesFile, (gradlePropertiesText) -> {
			gradlePropertiesText = gradlePropertiesText.replace(
					"version=" + currentVersion, "version=" + newVersion);
			return gradlePropertiesText;
		});
	}
}
