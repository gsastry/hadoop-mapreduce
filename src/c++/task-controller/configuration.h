/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <unistd.h>

#define INCREMENT_SIZE 1000
#define MAX_SIZE 10

struct confentry {
  const char *key;
  const char *value;
};


struct configuration {
  int size;
  struct confentry **confdetails;
};

FILE *LOGFILE;

#ifdef HADOOP_CONF_DIR
  #define CONF_FILE_PATTERN "%s/taskcontroller.cfg"
#else
  #define CONF_FILE_PATTERN "%s/conf/taskcontroller.cfg"
#endif

extern struct configuration config;
//configuration file contents
#ifndef HADOOP_CONF_DIR
  extern char *hadoop_conf_dir;
#endif
//method exposed to get the configurations
const char * get_value(const char* key);
//method to free allocated configuration
void free_configurations();

//function to return array of values pointing to the key. Values are
//comma seperated strings.
const char ** get_values(const char* key);
