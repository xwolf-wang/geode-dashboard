/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.tzolov.geode.archive;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.LogLevel;
import org.influxdb.InfluxDBFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.tzolov.geode.archive.loader.StatisticsToInfluxLoader;

@SpringBootApplication
@Configuration
public class StatisticsLoaderApplication implements CommandLineRunner {

	@Autowired
	private StatisticsToInfluxLoader statisticsLoader;

	public static void main(String[] args) {
		SpringApplication.run(StatisticsLoaderApplication.class, args);
	}

	@Bean
	public InfluxDB influxDB(
			@Value("${influxUrl}") String influxUrl,
			@Value("${influxUser}") String influxUser,
			@Value("${influxPassword}") String influxPassword) {
		InfluxDB influxDB = InfluxDBFactory.connect(influxUrl, influxUser, influxPassword);
		influxDB.setLogLevel(LogLevel.NONE);
		return influxDB;
	}

	@Override
	public void run(String... strings) throws Exception {
		statisticsLoader.load();
	}
}
